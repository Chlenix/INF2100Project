package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

    Double value;

    public RuntimeFloatValue(double value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where)
    {
        return new RuntimeFloatValue(value * -1);
    }

    public double getFloatValue(String what, AspSyntax where) {
        return value;
    }

    public boolean getBoolValue(String what, AspSyntax where) {
        return value != 0.0;
    }

    public String getStringValue(String what, AspSyntax where) {
        return value.toString();
    }

    @Override
    protected String typeName() {
        return "float";
    }
}
