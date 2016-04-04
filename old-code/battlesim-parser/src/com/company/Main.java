package com.company;


public class Main {

    public static void main(String[] args) {
        if (args[0] == "" || args[0] == null) {
            System.out.println("Usage: java -jar scanner.jar <file>");
        } else {
            try {
                Scanner scanner = new Scanner(new java.io.FileReader(args[0]));
                Parser parser = new Parser(scanner);
                parser.Start();
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File not found : \"" + args[0] + "\"");
            } catch (Exception e) {
                System.out.println("Unexpected exception:");
                e.printStackTrace();
            }
        }
    }


}
