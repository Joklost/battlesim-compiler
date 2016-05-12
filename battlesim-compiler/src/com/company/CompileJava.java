package com.company;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joklost on 19-04-16.
 */
public class CompileJava {
    private String outputName;
    private List<String> fileNames;
    private Map<String, List<String>> code;

    public boolean deleteOutput = true;

    public CompileJava(String outputName, Map<String, List<String>> code) {
        this.outputName = outputName;
        this.code = code;
    }

    private void writeCodeToFile() throws IOException {
        File com = new File("com");
        if (!com.mkdir()) {
            throw new Error("Unable to create directory: " + com.getAbsolutePath());
        }
        File battleSim = new File(com.getAbsoluteFile() + File.separator + "BattleSim");
        if (!battleSim.mkdir()) {
            throw new Error("Unable to create directory: " + battleSim.getAbsolutePath());
        }

        for (String key : code.keySet()) {
            String file = battleSim.getAbsolutePath() + File.separator + key + ".java";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for(String s: code.get(key)){
                    bw.write(s);
                }
            }
        }
    }

    private void createManifest() throws IOException {
        Path file = Paths.get("Manifest.txt");

        ArrayList<String> manifest = new ArrayList<>();
        manifest.add("Main-Class: com.BattleSim.Main\n");

        Files.write(file, manifest, Charset.forName("UTF-8"));
    }

    private void runProcess(String command) throws IOException, InterruptedException {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + ": stdout:", pro.getInputStream());
        printLines(command + ": stderr:", pro.getErrorStream());
        pro.waitFor();
        //System.out.println(command + " exitValue() " + pro.exitValue());
    }

    private void printLines(String name, InputStream ins) throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }


    public void compile() {
        try {
            writeCodeToFile();

            String dotJavaFiles = "";
            String dotClassFiles = "";
            for (String s : code.keySet()) {
                String path = "com" + File.separator + "BattleSim" + File.separator + s;
                dotJavaFiles += path + ".java ";
                dotClassFiles += path + ".class ";
            }

            runProcess("javac " + dotJavaFiles);
            createManifest();
            runProcess("jar cfm " + outputName + ".jar Manifest.txt " + dotClassFiles);
            //runProcess("java -jar " + outputName + ".jar");
            if(deleteOutput){
                if (!deleteFiles()) {
                    System.err.println("Unable to delete generated files.");
                }
            }
        } catch (Exception e) {
            if (!deleteFiles()) {
                System.err.println("Unable to delete generated files.");
            }
            e.printStackTrace();
        }
    }


    private boolean deleteFiles() {
        //return true;
        return deleteDir(new File("com")) && (new File("Manifest.txt")).delete();
    }

    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success){
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
