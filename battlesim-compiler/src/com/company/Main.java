package com.company;

import com.company.AST.Nodes.Start;
import com.company.AST.Visitor.PrettyPrintVisitor;
import com.company.SyntaxAnalysis.Parser;
import com.company.SyntaxAnalysis.Preprocessor;
import com.company.SyntaxAnalysis.Scanner;

public class Main {

    public static void main(String[] args) {
        String[] paths = {
                //"/home/joklost/git/P4-Code/example-code/battlesim/BubbleSort.bs",
                //"C:\\Users\\Magnus\\Documents\\P4-Code\\example-code\\battlesim\\new\\simWithIncludes\\simWithInclude.bs",
                "/home/joklost/git/P4-Code/example-code/battlesim/new/new.bs",
        };

        boolean parseSuccesful = true;

        Preprocessor preprocessor;
        Scanner scanner;
        Parser parser;
        Start startNode;

        PrettyPrintVisitor prettyPrint = new PrettyPrintVisitor();

        try {
            for (String path : paths) {
                System.out.println(path + "\n");
                preprocessor = new Preprocessor(path);
                String newPath = preprocessor.MakeFile();
                scanner = new Scanner(new java.io.FileReader(newPath));
                parser = new Parser(scanner);

                startNode = (Start)parser.parse().value;

                startNode.accept(prettyPrint);
                preprocessor.RemoveOutFile();
            }


        } catch (Exception e) {
            parseSuccesful = false;
            e.printStackTrace();
        }

        if (parseSuccesful) {
            System.out.println("The input has been succesfully parsed!");
        }
    }
}
