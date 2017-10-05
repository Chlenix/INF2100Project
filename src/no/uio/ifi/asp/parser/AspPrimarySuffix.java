package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspPrimarySuffix extends AspSyntax {
    AspSubscription subscription = null;
    AspArguments arguments = null;

    public static AspPrimarySuffix parse(Scanner s) {
        Main.log.enterParser("primary suffix");

        AspPrimarySuffix primarySuffix = new AspPrimarySuffix(s.curLineNum());

        if (s.curToken().kind == TokenKind.leftParToken) {
            // arguments
            primarySuffix.arguments = AspArguments.parse(s);
        } else {
            // guaranteed to be subscription by Primary while condition
            primarySuffix.subscription = AspSubscription.parse(s);
        }
        Main.log.leaveParser("primary suffix");
        return primarySuffix;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspPrimarySuffix(int n) {
        super(n);
    }

}
