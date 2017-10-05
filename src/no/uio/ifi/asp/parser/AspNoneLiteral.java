package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNoneLiteral extends AspSyntax {

    AspNoneLiteral(int n) {
        super(n);
    }

    public static AspNoneLiteral parse(Scanner s) {
        Main.log.enterParser("none literal");

        AspNoneLiteral noneLiteral = new AspNoneLiteral(s.curLineNum());
        skip(s, TokenKind.noneToken);

        Main.log.leaveParser("none literal");
        return noneLiteral;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
