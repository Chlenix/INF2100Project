package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

public class AspComparison extends AspSyntax {

    ArrayList<AspTerm> terms = new ArrayList<>();
    AspCompOpr compOpr = null;

    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");

        AspComparison comparison = new AspComparison(s.curLineNum());

        while (true) {
            comparison.terms.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            // if (compOpr != null) parserError("Only one compare operator per comparison allowed.", s.curLineNum());
            comparison.compOpr = AspCompOpr.parse(s);
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("comparison");
        return comparison;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspComparison(int n) {
        super(n);
    }

}
