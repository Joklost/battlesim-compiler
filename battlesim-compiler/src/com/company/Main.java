package com.company;

import com.company.AST.Start;
import com.company.AST.Terrain;
import com.company.AST.Visitor.PrettyPrint;
import com.company.AST.Visitor.TreeVisitor;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String[] paths = {
                //"/home/joklost/git/P4-Code/example-code/battlesim/BubbleSort.bs",
                "C:\\Users\\Magnus\\Documents\\P4-Code\\example-code\\battlesim\\new\\simWithIncludes\\simWithInclude.bs",
                //"/home/joklost/git/P4-Code/example-code/battlesim/new/new.bs",
        };

        boolean parseSuccesful = true;

        Preprocessor preprocessor;
        Scanner scanner;
        Parser parser;
        Start startNode;

        TreeVisitor treeVisitor = new TreeVisitor();
        PrettyPrint prettyPrint = new PrettyPrint();

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
