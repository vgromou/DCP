package functions;

import actions.Differentiation;

public class Logarithm implements Function {

    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }
    public Logarithm(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){


        StringBuilder result = new StringBuilder();

        // Натуральный логарфим
        if (function.charAt(1) == 'n'){
            Stringbuilder result = "(1÷(x))";
           /* StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
            StringBuilder difArg = new StringBuilder();
            if(arg.toString().contains("x")){
                difArg.append("(");
                StringBuilder temp = new StringBuilder(arg.toString());
                difArg.append(Differentiation.difExpression(temp));
                difArg.append(")");
                difArg.append("·");
            }
            result.insert(0, difArg);
            result.append(arg).append("))"); */
        }

        // Логарифм
        if (function.charAt(2) == 'g') {
            result.append("(1÷(x·ln(";

          //  StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
            StringBuilder baseOfTheLogarithm = new StringBuilder(function.substring(function.indexOf("g") + 1, function.lastIndexOf("(")));
           /* StringBuilder difArg = new StringBuilder("");
            if(arg.toString().contains("x")){
                difArg.append("(");
                StringBuilder temp = new StringBuilder(arg.toString());
                difArg.append(Differentiation.difExpression(temp));
                difArg.append(")");
                difArg.append("·");
            }
            result.insert(0, difArg); */
            result.append(baseOfTheLogarithm).append("))");
        }

        return result;
    }

}
