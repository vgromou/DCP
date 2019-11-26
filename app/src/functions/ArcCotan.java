package functions;

public class ArcCotan implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public ArcCotan(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("(-1 / (1 + ");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + arg + ")^2)) * " + difArg );

        return result;
    }

}
