package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AspDictDisplay extends AspSyntax {

    LinkedHashMap<AspStringLiteral, AspExpr> items = new LinkedHashMap<>();

    AspDictDisplay(int n) {
        super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
        Main.log.enterParser("dict display");

        skip(s, TokenKind.leftBraceToken);
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());

        if (s.curToken().kind == TokenKind.stringToken) {
            while (true) {
                AspStringLiteral key = AspStringLiteral.parse(s);
                skip(s, TokenKind.colonToken);
                dictDisplay.items.put(key, AspExpr.parse(s));
                if (s.curToken().kind != TokenKind.commaToken) break;
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightBraceToken);

        Main.log.leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    void prettyPrint()
    {
        Main.log.prettyWrite(" {");
        final int[] counter = {0};
        items.forEach((key, value) -> {
            if (counter[0]>0)
            {
                Main.log.prettyWrite(", ");
            }
            key.prettyPrint();
            Main.log.prettyWrite(": ");
            value.prettyPrint();
            counter[0]++;
        });
        Main.log.prettyWrite("}");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
