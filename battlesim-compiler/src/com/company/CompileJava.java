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
    public CompileJava(String file, List<String> code) {
        this.fileName = file;
        this.code = code;

    }

    private void writeCodeToFile() throws IOException {
        Path file = Paths.get(fileName + ".java");
        Files.write(file, code, Charset.forName("UTF-8"));
    }

    private void createManifest() throws IOException {
        Path file = Paths.get("META-INF/MANIFEST.MF");
        ArrayList<String> manifest = new ArrayList<>();
        manifest.add("Manifest-Version: 1.0");
        manifest.add("Created-By: 1.8.0_77 (Oracle Corporation)");
        manifest.add("Main-Class: Main");
        Files.write(file, manifest, Charset.forName("UTF-8"));
    }

    private void runProcess(String command) throws IOException, InterruptedException {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
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
            runProcess("mkdir META-INF");
            createManifest();
            runProcess("javac Main.java");
            //runProcess("java Main");
            runProcess("jar cmvf META-INF/MANIFEST.MF " + fileName + ".jar " + fileName + ".class");
            runProcess("java -jar Main.jar");
            if (!deleteFiles()) {
                System.err.println("Unable to delete generated files.");
            } else {
                runProcess("rmdir META-INF");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean deleteFiles() {
        boolean succesJava = true;
        boolean succesClass = true;
        boolean succesManifest = true;
        succesJava = (new File(fileName + ".java")).delete();
        succesClass = (new File(fileName + ".class")).delete();
        succesManifest = (new File("META-INF/MANIFEST.MF").delete());
        return succesJava && succesClass && succesManifest;
    }


}
