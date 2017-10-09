package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspArguments extends AspSyntax {

    ArrayList<AspExpr> expressions = new ArrayList<>();

    public static AspArguments parse(Scanner s) {
        Main.log.enterParser("arguments");

        AspArguments arguments = new AspArguments(s.curLineNum());

        skip(s, TokenKind.leftParToken);
        if (s.curToken().kind != TokenKind.rightParToken) {
            while (true) {
                arguments.expressions.add(AspExpr.parse(s));
                if (s.curToken().kind != TokenKind.commaToken) break;
                skip(s, TokenKind.commaToken);
            }
        }
        skip(s, TokenKind.rightParToken);

        Main.log.leaveParser("arguments");
        return arguments;
    }

    @Override
    void prettyPrint() {
        int argsPrinted = 0;
        for (AspExpr arg : expressions) {
            arg.prettyPrint();
            if (expressions.size() > ++argsPrinted) {
                Main.log.prettyWrite(", ");
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspArguments(int n) {
        super(n);
    }
}
