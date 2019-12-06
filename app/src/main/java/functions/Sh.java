package functions;

public class Sh implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Sh(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("(ch(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + arg + "))) * " + difArg );

        return result;
    }

}
