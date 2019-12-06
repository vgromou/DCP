package functions;

public class Th implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Th(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("1 / (ch^(2)*(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + difArg + ") ) )" );

        return result;
    }

}
