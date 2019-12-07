package functions;

import actions.Differentiation;

public class Sin implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Sin(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("(cos(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = new StringBuilder("");
        if(!Differentiation.difExpression(arg).toString().equals("0")) {
            difArg = Differentiation.difExpression(arg);
        }

        result.append("(" + arg + "))) * " + difArg );

        return result;
    }

}
