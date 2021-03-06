package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspStringLiteral extends AspSyntax {

    String value = null;

    AspStringLiteral(int n) {
        super(n);
    }

    public static AspStringLiteral parse(Scanner s) {
        Main.log.enterParser("string literal");

        AspStringLiteral stringLiteral = new AspStringLiteral(s.curLineNum());
        stringLiteral.value = s.curToken().stringLit;
        skip(s, TokenKind.stringToken);

        Main.log.leaveParser("string literal");
        return stringLiteral;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("\"" + value + "\"");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeStringValue(value);
    }
}
