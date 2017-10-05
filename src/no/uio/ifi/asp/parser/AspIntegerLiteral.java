package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIntegerLiteral extends AspSyntax {

    Long value = null;

    AspIntegerLiteral(int n) {
        super(n);
    }

    public static AspIntegerLiteral parse(Scanner s) {
        Main.log.enterParser("integer literal");

        AspIntegerLiteral integerLiteral = new AspIntegerLiteral(s.curLineNum());
        integerLiteral.value = s.curToken().integerLit;
        skip(s, TokenKind.integerToken);

        Main.log.leaveParser("integer literal");
        return integerLiteral;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
