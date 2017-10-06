package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspAndTest extends AspSyntax {

    ArrayList<AspNotTest> notTests = new ArrayList<>();

    static AspAndTest parse(Scanner s) {
        Main.log.enterParser("and test");
        AspAndTest andTest = new AspAndTest(s.curLineNum());

        while (true) {
            andTest.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != TokenKind.andToken) break;
            skip(s, TokenKind.andToken);
        }

        Main.log.leaveParser("and test");
        return andTest;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;

        for (AspNotTest ant : notTests) {
            if (nPrinted++ > 0) {
                Main.log.prettyWrite(" and ");
            }
            ant.prettyPrint();
        }
    }

    AspAndTest(int lineNum) {
        super(lineNum);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//        RuntimeValue v = notTests.get(0).eval(curScope);
//        for (int i = 1; i < notTests.size(); ++i) {
//            if (! v.getBoolValue("and operand",this))
//                return v;
//            v = notTests.get(i).eval(curScope);
//            }
//        return v;
        return null;
    }
}
