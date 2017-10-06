package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


public class AspProgram extends AspSyntax {
    public ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }

    public static AspProgram parse(Scanner s) {
        Main.log.enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != TokenKind.eofToken) {
            ap.stmts.add(AspStmt.parse(s));
        }

        Main.log.leaveParser("program");
        return ap;
    }


    @Override
    public void prettyPrint() {

        for (AspStmt stmt : stmts) {
            stmt.prettyPrint();
            Main.log.prettyWriteLn();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
