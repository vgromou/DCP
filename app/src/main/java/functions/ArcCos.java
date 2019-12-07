package functions;

import actions.Differentiation;

public class ArcCos implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public ArcCos(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("(-1 / (1 - ");
        char sign = function.charAt(0);

        function.deleteCharAt(0);

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = new StringBuilder("");
        if(!Differentiation.difExpression(arg).toString().equals("0")) {
            difArg = Differentiation.difExpression(arg);
        }

        result.append("(" + arg + ")^2)^(1/2)) * " + difArg );
        return result;
    }

}
