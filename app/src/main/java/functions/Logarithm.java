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

        String temp = function.substring(0, function.indexOf("("));
        // Натуральный логарфим
        if (temp.contains("ln")){
            result = new StringBuilder("1÷(");
            StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));

            result.append(arg).append(")");
        }

        // Логарифм
        if (temp.contains("log")) {
            result.append("1÷(");

            StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
            StringBuilder baseOfTheLogarithm = new StringBuilder(function.substring(function.indexOf("g") + 1, function.lastIndexOf("(")));

            result.append("(").append(arg).append(")").append("·ln").append(baseOfTheLogarithm).append(")");
        }

        return result;
    }

}
