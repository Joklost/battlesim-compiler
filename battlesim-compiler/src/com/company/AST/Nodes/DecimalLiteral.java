package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class DecimalLiteral extends StdLiteral {
    //public Double decimal;

    public DecimalLiteral(Double d, int ln) {
        super(ln);
        this.value = d;
    }
}
