package com.company.AST.Nodes;

/**
 * Created by joklost on 4/26/16.
 */
public class TypeDeclaration extends ASTNode {
    public Identifier name;
    public DeclarationList declarationList;
    public FunctionDclList functionDclList;

    public TypeDeclaration(Identifier i, DeclarationList dl, FunctionDclList fl, int ln) {
        super(ln);
        name = i;
        declarationList = dl;
        functionDclList = fl;
    }
}
