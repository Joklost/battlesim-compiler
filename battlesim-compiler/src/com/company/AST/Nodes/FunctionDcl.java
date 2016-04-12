package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionDcl extends ASTNode {
    public TypeIdentifier returnType;
    public Identifier identifier;
    public ParamList paramList;
    public StmtList stmtList;

    public FunctionDcl(TypeIdentifier tid, Identifier id, ParamList p, StmtList sl, int ln) {
        super(ln);
        this.returnType = tid;
        this.identifier = id;
        this.paramList = p;
        this.stmtList = sl;
    }

}

