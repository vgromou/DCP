package functions;

public class Cotan implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Cotan(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("(-1 / (sin^(2) * ( ");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + arg + "))) * " + difArg );
        return result;
    }

}
