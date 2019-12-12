package functions;

import actions.Differentiation;

public class Ch implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }
    public Ch(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("(sh(");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        StringBuilder difArg = new StringBuilder();
        if(arg.toString().contains("x")){
            difArg.append("(");
            StringBuilder temp = new StringBuilder(arg.toString());
            difArg.append(Differentiation.difExpression(temp));
            difArg.append(")");
            difArg.append("·");
        }
        result.insert(0, difArg);
        result.append(arg).append("))");

        return result;
    }

}
