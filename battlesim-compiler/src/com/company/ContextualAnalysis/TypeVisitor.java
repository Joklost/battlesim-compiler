package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;

import static com.company.ContextualAnalysis.TypeConsts.array1DType;
import static com.company.ContextualAnalysis.TypeConsts.array2DType;
import static com.company.ContextualAnalysis.TypeConsts.listType;

/**
 * Created by joklost on 12-04-16.
 */
public class TypeVisitor extends TopDeclVisitor {

    // TODO objekter defineres ud fra stdlib

    //public void visit(ObjectDefining o) {
        //o.typeDescriptor = new ObjectTypeDescriptor()
        //o.typeDescriptor.fields = new SymbolTable();
    //
    //    // fields tilføjes til nyt symboltable
    //    // se s. 313
    //}


    public void visit(Array1D a) {
        a.typeName.accept(this);
        a.index.accept(this);
        a.type = array1DType;

        a.typeDesc = new Array1DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize = a.index;
    }

    public void visit(Array2D a) {
        a.typeName.accept(this);
        a.index1.accept(this);
        a.index2.accept(this);
        a.type = array2DType;

        a.typeDesc = new Array2DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize1 = a.index1;
        a.typeDesc.arraySize2 = a.index2;
    }

    public void visit(ListOf l) {
        l.typeName.accept(this);
        //l.type = l.typeName.type;
        l.type = listType;
    }

}

