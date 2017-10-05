package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNotTest extends AspSyntax {

    boolean negativize = false; // this is actually a legit word
    AspComparison comparison = null;

    public AspNotTest(int lineNum) {
        super(lineNum);
    }

    static AspNotTest parse(Scanner s) {
        Main.log.enterParser("not test");

        AspNotTest notTest = new AspNotTest(s.curLineNum());
        if (s.curToken().kind == TokenKind.notToken) {
            skip(s, TokenKind.notToken);
            notTest.negativize = true;
        }
        notTest.comparison = AspComparison.parse(s);
        Main.log.leaveParser("not test");
        return notTest;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
