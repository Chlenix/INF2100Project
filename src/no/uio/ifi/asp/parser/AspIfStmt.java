package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AspIfStmt extends AspSyntax {

    // TreeMap for easier first/last element access
    // Which are "if", and "else" respectively
    // All values inbetween are "elif"-suites
    // Collection<Condition, Statement>

    LinkedHashMap<AspExpr, AspSuite> blocks = new LinkedHashMap<>(); // linkedhashmap to hold the right order.
    AspSuite elseSuite = null;

    AspIfStmt(int n) {
        super(n);
    }

    public static AspIfStmt parse(Scanner s) {
        Main.log.enterParser("if stmt");

        skip(s, TokenKind.ifToken);

        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());
        while (true) {
            AspExpr condition = AspExpr.parse(s);
            skip(s, TokenKind.colonToken);
            AspSuite body = AspSuite.parse(s);

            ifStmt.blocks.put(condition, body);

            if (s.curToken().kind != TokenKind.elifToken) break;
            skip(s, TokenKind.elifToken);
        }

        if (s.curToken().kind == TokenKind.elseToken) {
            skip(s, TokenKind.elseToken);
            skip(s, TokenKind.colonToken);
            ifStmt.elseSuite = AspSuite.parse(s);
        }

        Main.log.leaveParser("if stmt");
        return ifStmt;
    }

    @Override
    void prettyPrint() {

        Boolean firstIfPrinted = false;
        for (HashMap.Entry<AspExpr, AspSuite> block : blocks.entrySet()) {
            Main.log.prettyWrite(firstIfPrinted ? "elif " : "if " );
            if (!firstIfPrinted)
                firstIfPrinted = true;


            block.getKey().prettyPrint();
            Main.log.prettyWrite(":");
            block.getValue().prettyPrint();
        }
        if (elseSuite != null) {
            Main.log.prettyWrite("else:");
            elseSuite.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
