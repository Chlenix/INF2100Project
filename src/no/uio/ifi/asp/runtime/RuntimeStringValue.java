package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {

    String value;

    public RuntimeStringValue(String value) {
        this.value = value;
    }

    public String getStringValue(String what, AspSyntax where) {
        return value;
    }

    public boolean getBoolValue(String what, AspSyntax where) {
        return !value.isEmpty();
    }

    @Override
    protected String typeName() {
        return "string";
    }
}
