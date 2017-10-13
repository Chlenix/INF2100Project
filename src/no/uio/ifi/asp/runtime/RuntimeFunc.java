package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return super.evalNegate(where);
    }

    @Override
    protected String typeName() {
        return "def func";
    }
}
