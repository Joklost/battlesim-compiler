package com.company;

import com.company.AST.Nodes.Start;
import com.company.CodeGeneration.GenerateJavaVisitor;
import com.company.ContextualAnalysis.SemanticsVisitor;
import com.company.SyntaxAnalysis.Parser;
import com.company.SyntaxAnalysis.Preprocessor;
import com.company.SyntaxAnalysis.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean errorFound = false;
    public static String currentFile;

    public static void main(String[] args) {

        List<String> paths = new ArrayList<>();

        if (args.length > 0) {
            for (String s : args) {
                File f = new File(System.getProperty("user.dir") + File.separator + args[0]);
                if (f.isFile()) {
                    paths.add(f.getAbsolutePath());
                }
            }
        } else {
            // stier skal ind her, hvis det skal køres fra IntelliJ
            paths.add("/home/joklost/git/P4-Code/battlesim-compiler/battlesim/jonastest/javatest.bs");
        }

        boolean printCode = true;
        boolean generatedCode = true;

        Preprocessor preprocessor = null;
        Scanner scanner;
        Parser parser;
        Start startNode;

        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        GenerateJavaVisitor generateJavaVisitor = new GenerateJavaVisitor();

        try {
            for (String path : paths) {
                preprocessor = new Preprocessor(path);
                File f = new File(path);
                currentFile = f.getName();
                //System.out.println(path + "\n");
                String newPath = preprocessor.makeFile();
                scanner = new Scanner(new java.io.FileReader(newPath));
                parser = new Parser(scanner, true);

                startNode = (Start)parser.parse().value;

                if (!parser.errorFound) {
                    startNode.accept(semanticsVisitor);
                    if (!errorFound) {
                        startNode.accept(generateJavaVisitor);

                        if (printCode) {
                            for (String s : generateJavaVisitor.getCode()) {
                                System.out.print(s);
                            }
                        }

                        if (generatedCode) {
                            CompileJava cj = new CompileJava("Main", generateJavaVisitor.getCode());
                            cj.compile();
                        }
                    }
                }



                preprocessor.removeOutFile();




                //SymbolTable.printTable();
            }


        } catch (Exception e) {
            if (preprocessor != null) {
                preprocessor.removeOutFile();
            }
            e.printStackTrace();
        }
    }
}
