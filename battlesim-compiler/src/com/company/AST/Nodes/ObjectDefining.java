package com.company.AST.Nodes;

/**
 * Created by joklost on 4/26/16.
 */
public class ObjectDefining extends TypeName {
    public Identifier name;
    public ObjectDefining(Identifier id, int ln) {
        super(ln);
        name = id;
    }
}
