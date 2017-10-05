package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspStmt<T> extends AspSyntax {

    T t = null;

    public AspStmt(int lineNum) {
        super(lineNum);
    }

    @Override
    void prettyPrint() {

    }

    public static AspStmt parse(Scanner s) {
        Main.log.enterParser("stmt");

        AspStmt stmt = new AspStmt(s.curLineNum());

        if (s.anyEqualToken()) {
            stmt.t = AspAssignment.parse(s);
        } else {
            switch (s.curToken().kind) {
                case whileToken:
                    stmt.t = AspWhileStmt.parse(s);
                    break;
                case ifToken:
                    stmt.t = AspIfStmt.parse(s);
                    break;
                case returnToken:
                    stmt.t = AspReturnStmt.parse(s);
                    break;
                case passToken:
                    stmt.t = AspPassStmt.parse(s);
                    break;
                case defToken:
                    stmt.t = AspFuncDef.parse(s);
                    break;
                default:
                    stmt.t = AspExprStmt.parse(s);
                    break;
            }
        }

        Main.log.leaveParser("stmt");
        return stmt;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
