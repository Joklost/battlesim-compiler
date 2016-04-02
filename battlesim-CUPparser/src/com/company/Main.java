package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.FileReader("/home/joklost/git/P4-Code/Code_Examples/BattleSim_CodeExamples/BubbleSort.bs"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(scanner);
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
