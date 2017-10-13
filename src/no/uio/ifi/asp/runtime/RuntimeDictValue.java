package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspStringLiteral;

import java.util.HashMap;

public class RuntimeDictValue extends RuntimeValue {

    HashMap<AspStringLiteral, AspExpr> dict = new HashMap<>();

    public RuntimeDictValue(HashMap<AspStringLiteral, AspExpr> dict) {
        this.dict = dict;
    }

    @Override
    protected String typeName() {
        return "dict";
    }
}
