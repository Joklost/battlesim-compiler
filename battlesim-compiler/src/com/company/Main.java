package com.company;

import com.company.AST.Nodes.Start;
import com.company.AST.Nodes.TypeDeclaration;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.Visitor.PrettyPrintVisitor;
import com.company.CodeGeneration.GenerateJasminVisitor;
import com.company.CodeGeneration.GenerateJavaVisitor;
import com.company.ContextualAnalysis.SemanticsVisitor;
import com.company.SyntaxAnalysis.Parser;
import com.company.SyntaxAnalysis.Preprocessor;
import com.company.SyntaxAnalysis.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static boolean errorFound = false;
    public static String currentFile;
    public static SymbolTable currentSymbolTable = new SymbolTable();
    public static SymbolTable symbolTable = currentSymbolTable;

    public static void main(String[] args) {

        List<String> paths = new ArrayList<>();

        boolean printCode = false;
        boolean generatedCode = true;
        boolean deleteTmpFiles = true;
        boolean generateJasmin = false;
        String outputName = "Main";


        int readArgs = 0;
        if (args.length > 0) {
            for (String s : args) {
                switch (s) {
                    case "-pc":
                        printCode = true;
                        break;
                    case "-o":
                        outputName = args[1 + readArgs];
                        break;
                    case "-asm":
                        printCode = true;
                        generatedCode = false;
                        generateJasmin = true;
                    default:
                        if (args[readArgs].contains(".bs")) {
                            File f = new File(System.getProperty("user.dir") + File.separator + args[readArgs]);
                            if (f.isFile()) {
                                paths.add(f.getAbsolutePath());
                            }
                        }
                        break;
                }
                readArgs++;
            }
        } else {
            // stier skal ind her, hvis det skal køres fra IntelliJ
            //paths.add("/home/joklost/scopetest.bs");
            //paths.add("C:\\Users\\Magnus\\Documents\\P4-Code\\battlesim-compiler\\battlesim\\jonastest\\javatest.bs");
            //paths.add("/home/pgug/Code/P4-Code/unittest/Test10_TestSwitch.bs");
            //paths.add("C:\\Users\\Magnus\\Documents\\P4-Code\\unittest\\Test14_TestTypes.bs");
            //paths.add("C:\\Users\\Magnus\\Documents\\P4-Code\\unittest\\Test15_EngineStressTest.bs");
        }

        Preprocessor preprocessor = null;
        Scanner scanner;
        Parser parser;
        Start startNode;

        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        GenerateJavaVisitor generateJavaVisitor = new GenerateJavaVisitor();
        GenerateJasminVisitor generateJasminVisitor = new GenerateJasminVisitor();

        try {
            for (String path : paths) {
                preprocessor = new Preprocessor(path);
                File f = new File(path);
                currentFile = f.getName();

                String newPath = preprocessor.makeFile();
                scanner = new Scanner(new java.io.FileReader(newPath));
                parser = new Parser(scanner, true);

                startNode = (Start)parser.parse().value;

                if(generateJasmin) {
                    if (!parser.errorFound) {
                        startNode.accept(semanticsVisitor);
                        if (!errorFound) {
                            startNode.accept(generateJasminVisitor);
                            Map<String, List<String>> map = generateJasminVisitor.getCode();
                            map.putAll(SimulationFileReader.getDST());
                            if (printCode) {
                                int ln = 1;
                                for (String s : map.keySet()) {
                                    List<String> ls = map.get(s);
                                    for (String ss : ls) {
                                        System.out.print(ss);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (!parser.errorFound) {
                        startNode.accept(semanticsVisitor);
                        if (!errorFound) {
                            startNode.accept(generateJavaVisitor);
                            Map<String, List<String>> map = generateJavaVisitor.getCode();
                            map.putAll(SimulationFileReader.getDST());
                            if (printCode) {
                                int ln = 1;
                                for (String s : map.keySet()) {
                                    List<String> ls = map.get(s);
                                    for (String ss : ls) {
                                        System.out.print(ss);
                                    }
                                }
                            }

                            if (generatedCode) {

                                CompileJava cj = new CompileJava(outputName, map);
                                cj.compile();
                            }
                        }
                    }
                }



                preprocessor.removeOutFile();
            }


        } catch (Exception e) {
            if (preprocessor != null && deleteTmpFiles) {
                preprocessor.removeOutFile();
            }
            e.printStackTrace();
        }
    }
}
