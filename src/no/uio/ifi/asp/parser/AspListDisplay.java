package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspListDisplay extends AspSyntax {

    ArrayList<AspExpr> items = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    public static AspListDisplay parse(Scanner s) {
        Main.log.enterParser("list display");

        skip(s, TokenKind.leftBracketToken);
        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());

        if (s.curToken().kind != TokenKind.rightBracketToken)
        while (true) {
            listDisplay.items.add(AspExpr.parse(s));
            if (s.curToken().kind != TokenKind.commaToken) break;
            skip(s, TokenKind.commaToken);
        }

        skip(s, TokenKind.rightBracketToken);

        Main.log.leaveParser("list display");
        return listDisplay;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("[");

        int itemsLength = items.size();
        for (int i = 0; i < itemsLength; i++)
        {
            items.get(i).prettyPrint();
            if ((i+1) < itemsLength)
            {
                Main.log.prettyWrite(", ");
            }
        }
        Main.log.prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> list = new ArrayList<>();
        items.forEach(item -> {
            try {
                list.add(item.eval(curScope));
            } catch (RuntimeReturnValue runtimeReturnValue) {
                runtimeReturnValue.printStackTrace();
            }
        });
        return new RuntimeListValue(list);
    }
}
