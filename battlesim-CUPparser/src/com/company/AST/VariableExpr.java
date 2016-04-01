package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class VariableExpr extends Expression {
    public Variable variable;

    public VariableExpr(Variable v, int ln) {
        super(ln);
        this.variable = v;
    }
}
