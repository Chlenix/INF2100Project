package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAtom<T extends AspSyntax> extends AspSyntax {

    T t = null;

    public static AspAtom parse(Scanner s) {

        Main.log.enterParser("atom");

        AspAtom atom = new AspAtom(s.curLineNum());

        switch (s.curToken().kind) {
            case nameToken:
                atom.t = AspName.parse(s);
                break;
            case stringToken:
                atom.t = AspStringLiteral.parse(s);
                break;
            case integerToken:
                atom.t = AspIntegerLiteral.parse(s);
                break;
            case floatToken:
                atom.t = AspFloatLiteral.parse(s);
                break;
            case trueToken:
            case falseToken:
                atom.t = AspBooleanLiteral.parse(s);
                break;
            case leftBracketToken:
                atom.t = AspListDisplay.parse(s);
                break;
            case leftBraceToken:
                atom.t = AspDictDisplay.parse(s);
                break;
            case noneToken:
                atom.t = AspNoneLiteral.parse(s);
                break;
            default:
                atom.t = AspInnerExpr.parse(s);
                break;
        }
        Main.log.leaveParser("atom");
        return atom;
    }

    @Override
    void prettyPrint() {
        t.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspAtom(int n) {
        super(n);
    }

}
