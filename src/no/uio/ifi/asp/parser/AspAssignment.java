package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspAssignment extends AspSyntax {

    AspName name = null;
    AspExpr expr = null;
    ArrayList<AspSubscription> subs = new ArrayList<>();

    @Override
    void prettyPrint() {

    }

    public static AspAssignment parse(Scanner s) {
        Main.log.enterParser("assignment");

        AspAssignment assignment = new AspAssignment(s.curLineNum());
        assignment.name = AspName.parse(s);
        while (s.curToken().kind == TokenKind.leftBracketToken) {
            assignment.subs.add(AspSubscription.parse(s));
        }
        skip(s, TokenKind.equalToken);
        assignment.expr = AspExpr.parse(s);

        skip(s, TokenKind.newLineToken);
        Main.log.leaveParser("assignment");
        return assignment;
    }

    public AspAssignment(int lineNum) {
        super(lineNum);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
