package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Start extends ASTNode {
    public DclBlock dclBlock;
    public SimulationList simulationList;
    public FunctionDclList functionDclList1;
    public Program program;
    public FunctionDclList functionDclList2;

    public Start(DclBlock dcls, SimulationList sl, FunctionDclList fd1, Program p, FunctionDclList fd2, int ln) {
        super(ln);
        this.dclBlock = dcls;
        this.simulationList = sl;
        this.functionDclList1 = fd1;
        this.program = p;
        this.functionDclList2 = fd2;
    }

}
