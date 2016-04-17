package com.company;

import com.company.AST.Nodes.Start;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.Visitor.PrettyPrintVisitor;
import com.company.ContextualAnalysis.SemanticsVisitor;
import com.company.SyntaxAnalysis.Parser;
import com.company.SyntaxAnalysis.Preprocessor;
import com.company.SyntaxAnalysis.Scanner;

import java.io.File;

public class Main {

    public static String currentFile;

    public static void main(String[] args) {
        String[] paths = {
                //"/home/joklost/git/P4-Code/example-code/battlesim/semtester.bs",
//                "/home/joklost/git/P4-Code/example-code/battlesim/BubbleSort.bs",
                //"C:\\Users\\Magnus\\Documents\\P4-Code\\example-code\\battlesim\\new\\simWithIncludes\\simWithInclude.bs",
                //"/home/joklost/git/P4-Code/example-code/battlesim/new/new.bs",
                "/home/joklost/git/P4-Code/battlesim-compiler/battlesim/tester.bs",
        };

        boolean parseSuccesful = true;

        Preprocessor preprocessor;
        Scanner scanner;
        Parser parser;
        Start startNode;

        PrettyPrintVisitor prettyPrint = new PrettyPrintVisitor();
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();

        try {
            for (String path : paths) {
                File f = new File(path);
                currentFile = f.getName();
                //System.out.println(path + "\n");
                preprocessor = new Preprocessor(path);
                String newPath = preprocessor.MakeFile();
                scanner = new Scanner(new java.io.FileReader(newPath));
                parser = new Parser(scanner, true);

                startNode = (Start)parser.parse().value;

                //if (!parser.getErrorFound()) {
                    startNode.accept(semanticsVisitor);
                ///}

                preprocessor.RemoveOutFile();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
