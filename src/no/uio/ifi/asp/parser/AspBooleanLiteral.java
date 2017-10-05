package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspBooleanLiteral extends AspSyntax {

    Boolean value = null;

    AspBooleanLiteral(int n) {
        super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
        Main.log.enterParser("boolean literal");

        AspBooleanLiteral booleanLiteral = new AspBooleanLiteral(s.curLineNum());
        booleanLiteral.value = Boolean.valueOf(s.curToken().name.toLowerCase());
        skip(s, s.curToken().kind);

        Main.log.leaveParser("boolean literal");
        return booleanLiteral;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
