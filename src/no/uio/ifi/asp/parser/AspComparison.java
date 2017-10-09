package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

public class AspComparison extends AspSyntax {

    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOpr = new ArrayList<>(); // null;

    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");

        AspComparison comparison = new AspComparison(s.curLineNum());

        while (true) {
            comparison.terms.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            // if (compOpr != null) parserError("Only one compare operator per comparison allowed.", s.curLineNum());

            comparison.compOpr.add(AspCompOpr.parse(s));
            skip(s, s.curToken().kind);
        }
        if(comparison.compOpr.size()+1 != comparison.terms.size())
        {
            parserError("Number of comparasions doesn't match number of operators.", s.curLineNum());
        }
        Main.log.leaveParser("comparison");
        return comparison;
    }

    @Override
    void prettyPrint() {
        int size = terms.size();
        for (int x = 0; x < size; x++)
        {
            terms.get(x).prettyPrint();

            if (x+1 < size)
                compOpr.get(x).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspComparison(int n) {
        super(n);
    }

}
