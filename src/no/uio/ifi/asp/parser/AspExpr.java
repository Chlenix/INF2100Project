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
        int nPrinted = 0;
        for (AspAndTest aat : andTests) {
            if (nPrinted++ > 0) {
                Main.log.prettyWrite(" or ");
            }
            aat.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rv = andTests.get(0).eval(curScope);
        for (int i = 1; i < andTests.size(); ++i) {
            if (rv.getBoolValue("or operand", this)) {
                return rv;
            }
            rv = andTests.get(i).eval(curScope);
        }
        return rv;
    }
}
