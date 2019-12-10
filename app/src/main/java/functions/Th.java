package functions;

import actions.Differentiation;

public class Th implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }
    public Th(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("1 / (ch^(2)*(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = new StringBuilder("");
        if(!Differentiation.difExpression(arg).toString().equals("0")) {
            difArg = Differentiation.difExpression(arg);
        }

        result.append("(" + difArg + ") ) )" );

        return result;
    }

}
