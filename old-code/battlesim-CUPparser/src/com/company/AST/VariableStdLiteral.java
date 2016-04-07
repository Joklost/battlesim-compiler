package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class VariableStdLiteral extends Variable {
    public StdLiteral stdLiteral;

    public VariableStdLiteral(StdLiteral std, int ln) {
        super(ln);
        this.stdLiteral = std;
    }
}
