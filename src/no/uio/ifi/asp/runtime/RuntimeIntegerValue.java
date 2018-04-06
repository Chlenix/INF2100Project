package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {

    Long value;

    public RuntimeIntegerValue(long value) {
        this.value = value;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return value < 0 ? new RuntimeIntegerValue(-1 * value) : new RuntimeIntegerValue(value);
    }

    @Override
    protected String typeName() {
        return "integer";
    }

    public String toString() {
        return value.toString();
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntegerValue(-1 * this.value);
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorDiv(value, v.getIntValue(typeName(), where)));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(value / v.getFloatValue(typeName(), where)));
        }
        return super.evalIntDivide(v, where);
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(value / v.getFloatValue(typeName(), where));
        } else if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(value / v.getIntValue(typeName(), where));
        }
        return super.evalDivide(v, where);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value < v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value < v.getFloatValue(typeName(), where));
        }
        return super.evalLess(v, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value <= v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value <= v.getFloatValue(typeName(), where));
        }
        return super.evalLessEqual(v, where);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value > v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value > v.getFloatValue(typeName(), where));
        }
        return super.evalGreater(v, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value >= v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value >= v.getFloatValue(typeName(), where));
        }
        return super.evalGreaterEqual(v, where);
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
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {

            return new RuntimeIntegerValue(value * v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {

            return new RuntimeFloatValue(value * v.getFloatValue(typeName(), where));
        } else if (v instanceof RuntimeStringValue) {

            StringBuilder newString = new StringBuilder("");
            for (int i = 0; i < value; i++) {
                newString.append(v);
            }

            return new RuntimeStringValue(newString.toString());
        }
        return super.evalMultiply(v, where);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(value + v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(value + v.getFloatValue(typeName(), where));
        }
        return super.evalAdd(v, where);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(value - v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(value - v.getFloatValue(typeName(), where));
        }
        return super.evalSubtract(v, where);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value != v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value != v.getFloatValue(typeName(), where));
        } else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        return super.evalNotEqual(v, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(value == v.getIntValue(typeName(), where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(value == v.getFloatValue(typeName(), where));
        } else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        return super.evalEqual(v, where);
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorMod(value, v.getIntValue(typeName(), where)));
        } else if (v instanceof RuntimeFloatValue) {
            double divisor = v.getFloatValue(typeName(), where);
            return new RuntimeFloatValue(value - divisor * Math.floor(value / divisor));
        }
        return super.evalModulo(v, where);
    }
}
