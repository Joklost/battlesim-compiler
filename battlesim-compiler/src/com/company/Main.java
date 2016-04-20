package com.company;

import com.company.AST.Nodes.Start;
import com.company.AST.Visitor.PrettyPrintVisitor;
import com.company.CodeGeneration.GenerateJavaVisitor;
import com.company.ContextualAnalysis.SemanticsVisitor;
import com.company.SyntaxAnalysis.Parser;
import com.company.SyntaxAnalysis.Preprocessor;
import com.company.SyntaxAnalysis.Scanner;

import java.io.File;

public class Main {

    public static boolean errorFound = false;
    public static String currentFile;

    public static void main(String[] args) {
        String[] paths = {
                //"/home/joklost/git/P4-Code/example-code/battlesim/semtester.bs",
//                "/home/joklost/git/P4-Code/example-code/battlesim/BubbleSort.bs",
                //"C:\\Users\\Magnus\\Documents\\P4-Code\\example-code\\battlesim\\new\\simWithIncludes\\simWithInclude.bs",
                //"/home/joklost/git/P4-Code/example-code/battlesim/new/new.bs",
                //"/home/joklost/git/P4-Code/battlesim-compiler/battlesim/tester.bs",
                //"/home/joklost/git/P4-Code/battlesim-compiler/battlesim/javatest.bs",
                "/home/pgug/Code/P4-Code/battlesim-compiler/battlesim/javatest.bs",
        };

        boolean generatedCode = true;

        Preprocessor preprocessor = null;
        Scanner scanner;
        Parser parser;
        Start startNode;

        PrettyPrintVisitor prettyPrint = new PrettyPrintVisitor();
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

                        if (generatedCode) {
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
