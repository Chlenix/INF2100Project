package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AspFuncDef extends AspSyntax {

    AspName fnName = null;
    ArrayList<AspName> args = new ArrayList<>();
    AspSuite body = null;

    AspFuncDef(int n) {
        super(n);
    }

    public static AspFuncDef parse(Scanner s) {
        Main.log.enterParser("func def");

        AspFuncDef funcDef = new AspFuncDef(s.curLineNum());
        skip(s, TokenKind.defToken);
        funcDef.fnName = AspName.parse(s);
        skip(s, TokenKind.leftParToken);

        if (s.curToken().kind == TokenKind.nameToken) {
            while (true) {
                funcDef.args.add(AspName.parse(s));
                if (s.curToken().kind != TokenKind.commaToken) break;
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);

        funcDef.body = AspSuite.parse(s);

        Main.log.leaveParser("func def");
        return funcDef;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("def ");
        fnName.prettyPrint();

        Main.log.prettyWrite("(");

        int argsPrinted = 0;
        for (AspName arg : args) {
            arg.prettyPrint();
            if (args.size() > ++argsPrinted) {
                Main.log.prettyWrite(", ");
            }
        }
        Main.log.prettyWrite("):");
        body.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
