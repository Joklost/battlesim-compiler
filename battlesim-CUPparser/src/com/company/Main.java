package com.company;

import com.company.AST.Start;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path = "/home/joklost/git/P4-Code/Code_Examples/BattleSim_CodeExamples/BubbleSort.bs";
        boolean parseErrorFound = false;

        Scanner scanner = null;
        Parser parser = null;
        Start startNode = null;

        try {

            scanner = new Scanner(new java.io.FileReader(path));
            parser = new Parser(scanner);
            startNode = (Start)parser.parse().value;

        } catch (Exception e) {
            parseErrorFound = true;
            e.printStackTrace();
        }

        if (!parseErrorFound) {
            System.out.println("The input has been succesfully parsed!");
        }
    }

/*
    public static void ScanFile(String path) {
        Scanner scanner = null;
        int tNum = 1;
        try {
            scanner = new Scanner(new java.io.FileReader(path));
            while (!scanner.getZZAtEOF()) {

                java_cup.runtime.Symbol sym = scanner.next_token();


                System.out.print(tNum + ":");
                if (sym.value != null) {
                    System.out.print(sym.value + " ");
                } else {
                    System.out.print(Sym.terminalNames[sym.sym] + " ");
                }

                if (sym.sym == Sym.EOL) {
                    System.out.println();
                }

                tNum++;


            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found : \"" + path + "\"");
        } catch (java.io.IOException e) {
            System.out.println("IO error scanning file \"" + path + "\"");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }
    */
}
