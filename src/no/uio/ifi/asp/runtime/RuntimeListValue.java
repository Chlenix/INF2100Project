package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> list = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "glo";
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return super.evalNegate(where);
    }

    @Override
    protected String typeName() {
        return "list";
    }
}
