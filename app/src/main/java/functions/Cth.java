package functions;

public class Cth implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Cth(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder result = new StringBuilder("-1 / (sh^(2)*(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = complex(arg);

        result.append("(" + difArg + ") ) )" );

        return result;
    }

}
