package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchCase extends ASTNode {
    public Variable variable;
    public StmtList stmtList;

    public SwitchCase(Variable v, StmtList s, int ln) {
        super(ln);
        this.variable = v;
        this.stmtList = s;
    }
}
