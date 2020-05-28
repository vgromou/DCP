package functions;

import actions.Differentiation;

public class ArcCos implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }

  public ArcCos(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new Stringbuilder ("-1÷(1-");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        result.append("(").append(arg).append(")^2)^(1÷2)");
        return result;
    }

}
