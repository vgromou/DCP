package functions;

public class Ch implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Ch(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("(sh(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + arg + "))) * " + difArg );

        return result;
    }

}
