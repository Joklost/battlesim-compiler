package com.company.AST.SymbolTable;

import com.company.AST.Nodes.ASTNode;

/**
 * Created by joklost on 11-04-16.
 */
public class SymbolTable {

    public static boolean debug = false;

    private static void debugMsg(String s) {
        if (debug) {
            System.err.println(s);
        }
    }

    private static final int HASH_TABLE_SIZE = 211;
    private static Bucket[] symbolTable = new Bucket [HASH_TABLE_SIZE];
    //private static List<Bucket> symbolTable = new ArrayList<>();
    private static int level = 0;
    private static Scope scopeList = new Scope();

    public SymbolTable() {}

    public static int getLevel() {
        return level;
    }

    public static void enterSymbol(String str, ASTNode def) {
        Bucket buck;
        int i = hash(str);

        debugMsg("Enter called with '" + str + "'");

        buck = new Bucket();
        buck.setId(str);
        buck.setScope(level);
        buck.setDef(def);
        buck.setHashVal(i);

        buck.setNext(symbolTable[i]);
        symbolTable[i] = buck;

        buck.setSlink(scopeList.getSlink());
        scopeList.setSlink(buck);
    }

    public static ASTNode retrieveSymbol(String str) {
        int i = hash(str);
        Bucket buck = symbolTable[i];

        debugMsg("Find called with '" + str + "'");

        while (buck != null) {
            if (buck.getId() == str) {
                return buck.getDef();
            }
            buck = buck.getNext();
        }
        return null;
    }

    public static boolean declaredLocally(String str) {
        int i = hash(str);
        Bucket buck = symbolTable[i];

        debugMsg("Already declared called with '" + str + "'");

        while (buck != null) {
            if (buck.getId() == str) {
                return (buck.getScope() == level);
            }
            buck = buck.getNext();
        }
        return false;
    }

    public static void openScope() {
        Scope sc = new Scope();
        sc.setSlink(null);
        sc.setNext(scopeList);
        scopeList = sc;
        level++;

        debugMsg("OpenScope called: new level=" + level);
    }

    public static void closeScope() throws Error {
        Bucket buck, temp;
        Scope sc;
        if (level <= 0) {
            throw new Error("Trying to close unopened scope.");
        }
        buck = scopeList.getSlink();
        while (buck != null) {
            symbolTable[buck.getHashVal()] = buck.getNext();
            temp = buck.getSlink();
            buck.setDef(null);
            buck.setNext(null);
            buck.setSlink(null);
            buck = temp;
        }
        sc = scopeList;
        scopeList = scopeList.getNext();
        sc.setSlink(null);
        sc.setNext(null);
        level--;

        debugMsg("CloseScope called: new level=" + level);
    }

    public static int hash(String p) {
        int i = p.hashCode();
        if (i == 0x80000000) {
            i = 123;
        } else if (i < 0) {
            i = -i;
        }
        return i % HASH_TABLE_SIZE;
    }

    public static void printTable() {
        Bucket buck;
        System.out.println("==========  The Symbol Table  ==========");
        for (int lev = level; 0 <= lev; lev--) {
            System.out.println("  ==========  Scope Level=" + lev +"  ==========");
            for (int i = 0; i < HASH_TABLE_SIZE; i++) {
                buck = symbolTable[i];
                if (buck != null && buck.getScope() == lev) {
                    System.out.println ("      ==========  Bucket List # " + i + "  ==========");
                    while (buck != null) {
                        if (buck.getScope() == lev) {
                            System.out.println("        " + buck.getId() + "  [level=" + buck.getScope() + ", hashVal=" + buck.getHashVal() + "]");
                            printDef(buck.getDef());
                        }
                        buck = buck.getNext();
                    }
                }
            }
            //System.out.println ("  ===============================");
        }
        System.out.println("===============================");
    }

    // Skal muligvis tilpasses så det kun er de typer der rent faktisk skal i symbol table..?
    private static void printDef(ASTNode p) {
        if (p != null) {
            System.out.println("            " + p.getClass().toString());
        }
    }
}
