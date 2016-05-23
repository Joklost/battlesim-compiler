package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchStmt extends Stmt {
    public Variable control;
    public SwitchCaseList switchCaseList;
    public SwitchDef switchDef;

    public SwitchStmt(Variable v, SwitchCaseList sl, SwitchDef sd, int ln) {
        super(ln);
        this.control = v;
        this.switchCaseList = sl;
        this.switchDef = sd;
    }
}
