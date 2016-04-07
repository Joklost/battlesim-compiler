package com.company.AST.Visitor;

/**
 * Created by joklost on 03-04-16.
 */
public interface Visitable {
    void accept(Visitor v);
}
