package functions;

public class Cos implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Cos(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("(- sin(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + arg + "))) * " +difArg );

        return result;
    }

}
