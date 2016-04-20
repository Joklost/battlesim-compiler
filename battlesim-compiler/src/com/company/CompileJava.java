package com.company;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joklost on 19-04-16.
 */
public class CompileJava {
    private String fileName;
    private List<String> code;

    public boolean deleteOutput = false;

    public CompileJava(String file, List<String> code) {
        this.fileName = file;
        this.code = code;
    }

    private void writeCodeToFile() throws IOException {
        Path file = Paths.get(fileName + ".java");
        Files.write(file, code, Charset.forName("UTF-8"));
    }

    private void createManifest() throws IOException {
        //File dir = new File("META-INF");
        //dir.mkdir();
        //Path file = Paths.get(dir.getPath() + File.separator + "MANIFEST.MF");
        Path file = Paths.get("Manifest.txt");

        // skal muligvis autogenereres
        ArrayList<String> manifest = new ArrayList<>();
        manifest.add("Main-Class: Main\n");

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
            createManifest();
            runProcess("javac Main.java");
            //runProcess("java Main");

            runProcess("jar cfmv " + fileName + ".jar Manifest.txt " + fileName + ".class");
            runProcess("java -jar Main.jar");
            if (!deleteFiles()) {
                System.err.println("Unable to delete generated files.");
            }
        } catch (Exception e) {
            if (!deleteFiles()) {
                System.err.println("Unable to delete generated files.");
            }
            e.printStackTrace();
        }
    }


    private boolean deleteFiles() {
        boolean successJava = true;
        boolean successClass = true;
        boolean successManifest = true;
        successJava = (new File(fileName + ".java")).delete();
        successClass = (new File(fileName + ".class")).delete();
        successManifest = (new File("Manifest.txt")).delete();
        return successJava && successClass && successManifest;
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
