package functions;

import actions.Differentiation;

public class Ch implements Function {
    private StringBuilder function;
    private boolean isNegative;


    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }
    public Ch(StringBuilder function){
        this.function = function;
        isNegative = (function.charAt(0) == '-');
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("sh(");

       StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
       result.append(arg).append(")");

        if(isNegative){
            result.insert(0, '-');
        }

        return result;
    }

}
