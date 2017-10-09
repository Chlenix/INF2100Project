package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspPrimarySuffix<T extends AspSyntax> extends AspSyntax {
    // either a subscription or an argument
    T suffix = null;

    public static AspPrimarySuffix parse(Scanner s) {
        Main.log.enterParser("primary suffix");

        AspPrimarySuffix primarySuffix = new AspPrimarySuffix(s.curLineNum());

        if (s.curToken().kind == TokenKind.leftParToken) {
            // arguments
            primarySuffix.suffix = AspArguments.parse(s);
        } else {
            // guaranteed to be subscription by Primary while condition
            primarySuffix.suffix = AspSubscription.parse(s);
        }
        Main.log.leaveParser("primary suffix");
        return primarySuffix;
    }

    @Override
    void prettyPrint() {

        if (suffix instanceof AspSubscription)
        {
            suffix.prettyPrint();
        }
        else
        {
            String opening = "(";
            String closing = ")";
            Main.log.prettyWrite(opening);
            suffix.prettyPrint();
            Main.log.prettyWrite(closing);
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspPrimarySuffix(int n) {
        super(n);
    }

}
