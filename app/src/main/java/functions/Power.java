package functions;

import actions.Differentiation;

public class Power implements Function {
    private StringBuilder function;
    private char sign;

    public StringBuilder getFunction() {
        return function;
    }

    public Power(StringBuilder function){
        this.sign = function.charAt(0);
        this.function = function.deleteCharAt(0);
    }

    @Override
    public StringBuilder differentiate() {
        StringBuilder power = new StringBuilder();
        /*
        Следующий if-else нужен для возможности введения степени в разной форме: со скобками и без
        Например, пользователь может ввести как x^1/2, так и x^(1/2)
         */
        if (function.charAt(function.lastIndexOf("^") + 1) == '(') {
            power.append(function.substring(function.lastIndexOf("^") + 2, function.length() - 1));
        }
        else {
            power.append(function.substring(function.lastIndexOf("^") + 1));
        }
        StringBuilder newPower;
        /*
        Следующий if-else нужен для работы с разными видами степени: целочисленными и дробными
         */
        if (power.toString().contains("/")){
            String numerator = power.substring(0, power.indexOf("/"));
            String denominator = power.substring(power.indexOf("/") + 1);

            Integer n = Integer.parseInt(numerator);
            Integer m = Integer.parseInt(denominator);

            int newNumerator = n - m;
            newPower = new StringBuilder(newNumerator + "/" + denominator);
        }
        else {
            int n = Integer.parseInt(power.toString()) - 1;
            newPower = new StringBuilder(Integer.toString(n));
        }

        StringBuilder arg = new StringBuilder(function.substring(0, function.lastIndexOf("^")));

        if (arg.charAt(0) == '('){
            arg = arg.deleteCharAt(0);
            arg = arg.deleteCharAt(arg.length()-1);
        }

        StringBuilder difArg = Differentiation.difExpression(arg);

        StringBuilder result = new StringBuilder("(" + power + ")" + "*(" + arg + ")^(" + newPower + ")");
        if (!difArg.toString().equals("1")) {
            result.append("*");
            result.append(difArg);
        }
        result.insert(0, sign);
        return result;
    }

}
