package functions;

import actions.Differentiation;

public class ArcCos implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public ArcCos(StringBuilder function){
        this.function = function;
        System.out.println(this.function + " FUNCTION");
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder("(-1÷(1-");

        StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
        System.out.println(arg + " ARG AT BEG");
        StringBuilder difArg = new StringBuilder();
        if(arg.toString().contains("x")){
            difArg.append("(");
            difArg.append(Differentiation.difExpression(arg));
            difArg.append(")");
            difArg.append("·");
        }
        result.insert(0, difArg);
        result.append("(").append(arg).append(")^2)^(1÷2))");
        System.out.println(arg + " ARG");
        return result;
    }

}
