package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNoneValue extends RuntimeValue {
    @Override
    protected String typeName() {
        return "None";
    }


    @Override
    public String toString() {
        return "None";
    }


    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return false;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return super.evalNegate(where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(true);
    }


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        return new RuntimeBoolValue(true);
    }
}
