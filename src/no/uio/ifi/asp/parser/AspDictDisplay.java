package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspDictDisplay extends AspSyntax {

    ArrayList<AspExpr> items = new ArrayList<>();

    AspDictDisplay(int n) {
        super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
        Main.log.enterParser("dict display");

        skip(s, TokenKind.leftBraceToken);
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());

        if (s.curToken().kind == TokenKind.stringToken) {
            while (true) {
                skip(s, TokenKind.stringToken);
                skip(s, TokenKind.colonToken);
                dictDisplay.items.add(AspExpr.parse(s));
                if (s.curToken().kind != TokenKind.commaToken) break;
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightBraceToken);

        Main.log.leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
