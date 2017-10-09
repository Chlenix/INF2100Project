package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {

    // 0
    //
    Long value;

    public RuntimeIntegerValue(long value) {
        this.value = value;
    }

    @Override
    protected String typeName() {
        return "integer";
    }

    public String toString() {
        return value.toString();
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return value.doubleValue();
    }

    @Override
    public long getIntValue(String what, AspSyntax where) { return value; }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return value != 0L;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return value.toString();
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where)
    {
        return new RuntimeIntegerValue(value * -1);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue || v instanceof RuntimeBoolValue) {

            return new RuntimeIntegerValue(this.value * v.getIntValue(v.toString(), where));
        } else if (v instanceof RuntimeFloatValue) {

            return new RuntimeFloatValue(this.value * v.getFloatValue(v.toString(), where));
        } else if (v instanceof RuntimeStringValue) {

            StringBuilder sb = new StringBuilder("");
            // append the string x-amount of times
            for (int i = 0; i < this.value; i++) {
                sb.append(v);
            }

            return new RuntimeStringValue(sb.toString());
        }
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue || v instanceof RuntimeBoolValue) {
            return new RuntimeIntegerValue(this.value + v.getIntValue(v.toString(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.value + v.getFloatValue(v.toString(), where));
        }
        runtimeError("'+' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue || v instanceof RuntimeBoolValue) {
            return new RuntimeIntegerValue(this.value + v.getIntValue(v.toString(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.value + v.getFloatValue(v.toString(), where));
        }
        runtimeError("'-' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue || v instanceof RuntimeFloatValue) {
            // 0 == 0.0, 0.0 == 0.0000, 0 == 0
            // doesn't matter if we convert member long before comparison.
            return new RuntimeBoolValue(
                    this.getFloatValue(this.toString(), where) == v.getFloatValue(v.toString(), where)
            );
        }
        runtimeError(
                "'==' undefined for comparison between "
                + v.typeName() + " and "
                + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
}
