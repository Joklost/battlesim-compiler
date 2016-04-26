package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class Start extends ASTNode {
    public DclBlock dclBlock;
    public SimBlock simBlock;
    public TypeDeclarationList typeDeclarationList;
    public FunctionDclList functionDclList1;
    public Program program;

    public Start(DclBlock dcls, SimBlock sb, TypeDeclarationList tl, FunctionDclList fd1, Program p, int ln) {
        super(ln);
        this.dclBlock = dcls;
        this.simBlock = sb;
        this.typeDeclarationList = tl;
        this.functionDclList1 = fd1;
        this.program = p;
    }

}
