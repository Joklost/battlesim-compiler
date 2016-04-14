package com.company.AST.Visitor;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;

import static com.company.AST.SymbolTable.SymbolTable.*;

/**
 * Created by joklost on 12-04-16.
 */
public class TypeVisitor extends TopDeclVisitor {

    // objekter defineres ud fra stdlib

    //public void visit(ObjectDefining o) {
        //o.typeDescriptor = new ObjectTypeDescriptor()
        //o.typeDescriptor.fields = new SymbolTable();
    //
    //    // fields tilføjes til nyt symboltable
    //    // se s. 313
    //}


    public void visit(Array1D a) {

    }

    public void visit(Array2D a) {

    }


}

