package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.nio.channels.Pipe;

public class Main {
/*
    public static void main(String[] args) {
	// write your code here
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.FileReader("/home/joklost/git/P4-Code/Code_Examples/NewBattleSim_CodeExamples/new.bs"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(scanner);
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String argv[]) {
        /*
        String[] paths = new String[] {
                "/home/joklost/git/P4-Code/Code_Examples/BattleSim_CodeExamples/BubbleSort.bs",
                //"/home/joklost/Repos/P4-Code/Code_Examples/BattleSim_CodeExamples/BasicBattleSim.bs",
                //"/home/joklost/Repos/P4-Code/Code_Examples/BattleSim_CodeExamples/EuclideanAlgorithm.bs",
                //"/home/joklost/Repos/P4-Code/Code_Examples/BattleSim_CodeExamples/BellmanFord.bs",
                //"C:\\Users\\Jonas\\P4-Code\\Code_Examples\\BattleSim_CodeExamples\\BellmanFord.bs",
        };
        if (argv.length == 0) {
            if (paths.length != 0) {
                for (String path : paths) {
                    System.out.println("Scanning : \"" + path + "\"");
                    ScanFile(path);


                }
            } else {
                System.out.println("Usage : java Scanner <inputfile>");
            }
        } else {
            for (int i = 0; i < argv.length; i++) {
                ScanFile(argv[i]);
            }
        } */

        String path = "/home/joklost/git/P4-Code/Code_Examples/BattleSim_CodeExamples/BubbleSort.bs";
        ScanFile(path);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(scanner);
        try {
            Symbol symbol = parser.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void ScanFile(String path) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.FileReader(path));
            while (!scanner.getZZAtEOF()) {

                java_cup.runtime.Symbol sym = scanner.next_token();
                if (sym.value != null) {
                    System.out.print(sym.value + " ");
                } else {
                    System.out.print(Sym.terminalNames[sym.sym] + " ");
                }



            }
            System.out.println("\n\n");

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
}
