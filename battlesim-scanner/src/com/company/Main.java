package com.company;
/**
 * Created by joklost on 3/9/16.
 */
public class Main {
    public static void main(String argv[]) {
        if (argv.length == 0) {
            System.out.println("Usage : java Scanner <inputfile>");
        } else {
            for (int i = 0; i < argv.length; i++) {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(new java.io.FileReader(argv[i]));
                    while (!scanner.getZZAtEOF()) {
                        java_cup.runtime.Symbol sym = scanner.next_token();
                        System.out.print(Token.getTokenName(sym.sym) + " - ");
                        System.out.print(sym.value + "\n");
                    }
                } catch (java.io.FileNotFoundException e) {
                    System.out.println("File not found : \"" + argv[i] + "\"");
                } catch (java.io.IOException e) {
                    System.out.println("IO error scanning file \"" + argv[i] + "\"");
                    System.out.println(e);
                } catch (Exception e) {
                    System.out.println("Unexpected exception:");
                    e.printStackTrace();
                }
            }
        }
    }
}
