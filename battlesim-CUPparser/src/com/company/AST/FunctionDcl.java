package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionDcl extends ASTNode {
    public TypeIdentifier typeIdentifier;
    public String identifier;
    public ParamList paramList;
    public StmtList stmtList;

    public FunctionDcl(TypeIdentifier tid, String id, ParamList p, StmtList sl, int ln) {
        super(ln);
        this.typeIdentifier = tid;
        this.identifier = id;
        this.paramList = p;
        this.stmtList = sl;
    }

}

