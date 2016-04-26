package com.company.AST.Nodes;

import java.util.Vector;

/**
 * Created by joklost on 4/26/16.
 */
public class TypeDeclarationList extends ASTNode {
    private Vector list;

    public TypeDeclarationList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(TypeDeclaration f) {
        list.addElement(f);
    }

    public TypeDeclaration elementAt(int i) {
        return (TypeDeclaration) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
