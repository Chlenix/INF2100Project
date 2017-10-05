package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspExpr extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
        super(n);
    }


    public static AspExpr parse(Scanner s) {
        Main.log.enterParser("expr");

        AspExpr expr = new AspExpr(s.curLineNum());
        while (true) {
            expr.andTests.add(AspAndTest.parse(s));
            if (s.curToken().kind != TokenKind.orToken) break;
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("expr");
        return expr;
    }


    @Override
    public void prettyPrint() {
        //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}