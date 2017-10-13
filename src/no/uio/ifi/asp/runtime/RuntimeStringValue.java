package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {

    String value;

    @Override
    public String toString() {
        return "\'" + value + "\'";
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return super.evalNegate(where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(value.equals(v.getStringValue(typeName(), where)));
        } else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        return super.evalEqual(v, where);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeStringValue(value + v.getStringValue(typeName(), where));
        }
        return super.evalAdd(v, where);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!value.equals(v.getStringValue(typeName(), where)));
        } else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        return super.evalNotEqual(v, where);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(value.compareTo(v.getStringValue(typeName(), where)) < 0);
        }
        return super.evalLess(v, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(value.compareTo(v.getStringValue(typeName(), where)) <= 0);
        }
        return super.evalLessEqual(v, where);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(value.compareTo(v.getStringValue(typeName(), where)) > 0);
        }
        return super.evalGreater(v, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(value.compareTo(v.getStringValue(typeName(), where)) >= 0);
        }
        return super.evalGreaterEqual(v, where);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            StringBuilder newString = new StringBuilder("");
            long count = v.getIntValue(typeName(), where);
            for (int i = 0; i < count; i++) {
                newString.append(value);
            }
            return new RuntimeStringValue(newString.toString());
        }
        return super.evalMultiply(v, where);
    }

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
