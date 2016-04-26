package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.SymbolTable.SymbolTable.*;

import static com.company.ContextualAnalysis.TypeConsts.*;

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

    public void visit(TypeDeclaration t) {
        t.typeDescriptor = new ObjectTypeDescriptor();
        t.typeDescriptor.fields = new SymbolTable();

        for (int i = 0; i < t.declarationList.size(); i++) {
            t.declarationList.elementAt(i).typeName.accept(this);
            for (int k = 0; k < t.declarationList.elementAt(i).dclIdList.size(); k++) {
                if (t.typeDescriptor.fields.declaredLocally(t.declarationList.elementAt(i).dclIdList.elementAt(k).name)) {
                    error(t.getLineNumber(), "Field " + t.declarationList.elementAt(i).dclIdList.elementAt(k).name + " cannot be redeclared.");
                    t.declarationList.elementAt(i).dclIdList.elementAt(k).type = errorType;
                    t.declarationList.elementAt(i).dclIdList.elementAt(k).def = null;
                } else {
                    TypeIdentifier def = t.declarationList.elementAt(i).typeName;
                    def.type = t.declarationList.elementAt(i).typeName.type;
                    t.typeDescriptor.fields.enterSymbol(t.declarationList.elementAt(i).dclIdList.elementAt(k).name, def);
                    t.declarationList.elementAt(i).dclIdList.elementAt(k).type = t.declarationList.elementAt(i).typeName.type;
                    t.declarationList.elementAt(i).dclIdList.elementAt(k).def = def;

                    //t.typeDescriptor.fields.printTable();
                }
            }
        }

        for (int i = 0; i < t.functionDclList.size(); i++) {
            t.functionDclList.elementAt(i).returnType.accept(this);
            if (t.typeDescriptor.fields.declaredLocally(t.functionDclList.elementAt(i).functionName.name)) {
                error(t.getLineNumber(), "Function " + t.functionDclList.elementAt(i).functionName.name + " cannot be redeclared.");
                t.functionDclList.elementAt(i).functionName.type = errorType;
                t.functionDclList.elementAt(i).functionName.def = null;
            } else {
                t.typeDescriptor.fields.enterSymbol(t.functionDclList.elementAt(i).functionName.name, t.functionDclList.elementAt(i));
                t.functionDclList.elementAt(i).type = functionType;
            }

            t.typeDescriptor.fields.openScope();

            FunctionDcl oldCurrentFunction = currentFunction;
            currentFunction = t.functionDclList.elementAt(i);


            for (int k = 0; k < t.functionDclList.elementAt(i).paramList.size(); k++) {
                t.functionDclList.elementAt(i).paramList.elementAt(k).accept(this);
            }

            for (int k = 0; k < t.functionDclList.elementAt(i).stmtList.size(); k++) {
                t.functionDclList.elementAt(i).stmtList.elementAt(k).accept(this);
            }

            currentFunction = oldCurrentFunction;

            t.typeDescriptor.fields.closeScope();

            boolean containsReturnStmt = false;

            if (t.functionDclList.elementAt(i).returnType.type != voidType && t.functionDclList.elementAt(i).type != errorType) {
                for (int k = 0; k < t.functionDclList.elementAt(i).stmtList.size(); k++) {
                    if (t.functionDclList.elementAt(i).stmtList.elementAt(k) instanceof ReturnExpr) {
                        containsReturnStmt = true;
                    }
                }

                if (!containsReturnStmt) {
                    error(t.functionDclList.elementAt(i).getLineNumber(), "Field function " + t.functionDclList.elementAt(i).functionName.name + " must return a value.");
                    t.functionDclList.elementAt(i).type = errorType;
                }
            }
        }

        t.typeDescriptor.fields.printTable();

    }


    /*
    TypeVisitor typeVisitor = new TypeVisitor();
    d.typeName.accept(typeVisitor);     // sætter d.typename.type
    // s. 311 i bogen: ArrayDefining foregår i typeVisitor

    for (int i = 0; i < d.dclIdList.size(); i++) {
        String id = d.dclIdList.elementAt(i).name;
        if (declaredLocally(id)) {
            errorDeclaredLocally(id);
            d.dclIdList.elementAt(i).type = errorType;
            d.dclIdList.elementAt(i).def = null;
        } else {
            d.dclIdList.elementAt(i).type = d.typeName.type;
            TypeIdentifier def = d.typeName;
            def.type = d.typeName.type;
            d.dclIdList.elementAt(i).def = def;
            //System.out.println(id);
            enterSymbol(id, def);
        }
    }
    */
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

