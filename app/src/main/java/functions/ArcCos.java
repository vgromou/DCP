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
        StringBuilder result = "(-1÷(1-x)^(1/2))";

        /*StringBuilder arg = new StringBuilder(function.substring(function.indexOf(0) + 1, function.lastIndexOf(")")));
        StringBuilder difArg = new StringBuilder();
        if(arg.toString().contains("x")){
            difArg.append("(");
            StringBuilder temp = new StringBuilder(arg.toString());
            difArg.append(Differentiation.difExpression(temp));
            difArg.append(")");
            difArg.append("·");
        }
        result.insert(0, difArg);
        result.append("(").append(arg).append(")^2)^(1÷2))");*/
        return result;
    }

}
