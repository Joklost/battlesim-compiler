package com.company;
/**
 * Created by joklost on 3/9/16.
 */
public class Main {
    public static void main(String argv[]) {
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
        }
    }

    public static void ScanFile(String path) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.FileReader(path));
            while (!scanner.getZZAtEOF()) {

                java_cup.runtime.Symbol sym = scanner.next_token();
                Token token = new Token(sym.sym, sym.value == null ? "" : sym.value.toString());

                if (token.getType() == Token.EOL) {
                    System.out.println();
                } else {
                    System.out.print(token.toString() + " ");
                }

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
}
