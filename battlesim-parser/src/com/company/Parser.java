package com.company;

import java.lang.reflect.Member;
import java.lang.reflect.Type;

/**
 * Created by joklost on 3/10/16.
 */
public class Parser {
    private TokenStream ts;

    public Parser(Scanner scanner) {
        this.ts = new TokenStream(scanner);
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
///////CFG//////////////////////////////////////////////////////////////////////
    public void Start() {
        if (ts.peek() == Token.INCLUDE || ts.peek() == Token.DEFINE || ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            Includes();
            Defines();
            TypeDcls();
            FunctionDcls();
            Program();
            FunctionDcls();
            expect(Token.EOF);
        } else {
            error("Start: Expected Include, Define, Type, Function or Begin.");
        }
    }

    public void Includes() {
        if (ts.peek() == Token.INCLUDE) {
            Include();
            Includes();
        } else if(ts.peek() == Token.DEFINE || ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("Includes: Expected Include, Define, Type, Function or Begin.");
        }
    }

    public void Include() {
        if (ts.peek() == Token.INCLUDE) {
            expect(Token.INCLUDE);
            expect(Token.LPAREN);
            HeaderId();
            expect(Token.RPAREN);
        } else {
            error("Include: Expected Include.");
        }
    }

    public void Defines() {
        if (ts.peek() == Token.DEFINE) {
            Define();
            Defines();
        } else if (ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("Defines: Expected Define, Type, Function or Begin.");
        }
    }

    public void Define() {
        if (ts.peek() == Token.DEFINE) {
            expect(Token.DEFINE);
        } else {
            error("Define: Expected Define.");
        }
    }

    public void DefineType() {
        if (ts.peek() == Token.NUMBER_LITERAL) {
            Number_Lit();
        } else if (ts.peek() == Token.STRING_LITERAL) {
            String_Lit();
        } else if (ts.peek() == Token.BOOLEAN_LITERAL) {
            Boolean_Lit();
        } else {
            error("DefineType: Expected Number_Literal, String_Literal or Boolean_Literal.");
        }
    }

    public void TypeDcls() {
        if (ts.peek() == Token.TYPE) {
            TypeDcl();
            TypeDcls();
        } else if (ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("TypeDcls: Expected Type, Function or Begin.");
        }
    }

    public void TypeDcl() {
        if (ts.peek() == Token.TYPE) {
            expect(Token.TYPE);
            Id();
            MemberDcl();
            MemberDcls();
            FunctionDcls();
            expect(Token.END);
            expect(Token.TYPE);
        } else {
            error("TypeDcl: Expected Type.");
        }
    }

    public void MemberDcls() {
        if (ts.peek() == Token.IDENTIFIER) {
            MemberDcl();
            MemberDcls();
        } else if (ts.peek() == Token.FUNCTION || ts.peek() == Token.END) {
            // Lambda
        } else {
            error("MemberDcls: Expected Identifier, Function or End.");
        }
    }

    public void MemberDcl() {
        if (ts.peek() == Token.IDENTIFIER) {
            Id();
            expect(Token.AS);
            TypeId();
        } else {
            error("MemberDcl: Expected Identifier.");
        }
    }

    public void FunctionDcls() {
        if (ts.peek() == Token.FUNCTION) {
            FunctionDcl();
            FunctionDcls();
        } else if (ts.peek() == Token.BEGIN || ts.peek() == Token.EOF) {
            // Lambda
        } else {
            error("FunctionDcls: Expected Function, Begin or EOF.");
        }
    }

    public void FunctionDcl() {
        if (ts.peek() == Token.FUNCTION) {
            // CFG'en skal refactores
        } else {
            error("FunctionDcl: Expected Function.");
        }
    }

    public void Params() {
        if (ts.peek() == Token.IDENTIFIER) {
            // CFG'en skal refactores
        } else {
            error("Params: Expected Identifier.");
        }
    }

    public void Program() {
        if (ts.peek() == Token.BEGIN) {
            expect();
        }
    }


    /*
    public void Prog() {
		if (ts.peek() == FLTDCL || ts.peek() == INTDCL || ts.peek() == ID || ts.peek() == PRINT || ts.peek() == EOF) {
			Dcls();
			Stmts();
			expect(EOF);
		}
		else error("expected floatdcl, intdcl, id, print, or eof");
	}
     */
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    private void expect(int type) {
        Token t = ts.advance();
        if (t.getType() != type) {
            error("Expected type " + Token.getTokenName(type) +
                    " but received type " + Token.getTokenName(t.getType()));
        }
    }

    private void error(String message) {
        throw new Error(message);
    }

}
