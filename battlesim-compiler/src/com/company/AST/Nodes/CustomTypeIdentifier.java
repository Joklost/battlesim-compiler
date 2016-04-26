package com.company.AST.Nodes;

/**
 * Created by joklost on 4/26/16.
 */
public class CustomTypeIdentifier extends TypeName {
    public Identifier name;
    public CustomTypeIdentifier(Identifier id, int ln) {
        super(ln);
        name = id;
    }
}
