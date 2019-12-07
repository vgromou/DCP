package functions;

import actions.Differentiation;

import java.util.Arrays;

public class Power implements Function {
    private StringBuilder function;
    private boolean isNegative;

    public StringBuilder getFunction() {
        return function;
    }

    public Power(StringBuilder function){
        this.isNegative = function.charAt(0) == '-';
        System.out.println(function);
        this.function = function.deleteCharAt(0);
        System.out.println(this.function);
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder();
        StringBuilder power;

        StringBuilder arg;

        if(function.toString().contains("^")){
            StringBuilder temp = new StringBuilder(function.substring(function.lastIndexOf("^") + 1));
            if(temp.charAt(0) == '('){
                temp.deleteCharAt(0);
                temp.deleteCharAt(temp.length()-1);
            }
            power = temp;

            power.insert(0, "(");
            power.append(")");

            arg = new StringBuilder(function.substring(0, function.indexOf("^")));
            arg.deleteCharAt(0);
            arg.deleteCharAt(arg.length()-1);
        }
        else{
            power = new StringBuilder("1");
            arg = function;
        }

        StringBuilder newPower = new StringBuilder();

        if(power.toString().contains("÷")){
            int numerator = 0;
            int denominator = 0;
            try {
                numerator = Integer.parseInt(power.substring(1, power.indexOf("÷")));
                denominator = Integer.parseInt(power.substring(power.indexOf("÷") + 1, power.length()-1));
            }
            catch (NumberFormatException e){
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
            int newNumerator = numerator - denominator;
            newPower.append("(").append(newNumerator).append("÷").append(denominator).append(")");
        }
        else{
            int n = 0;
            try {
                if(power.charAt(0) == '(') {
                    n = Integer.parseInt(power.toString().substring(1, power.length() - 1));
                }
                else {
                    n = Integer.parseInt(power.toString());
                }
            }
            catch (NumberFormatException e){

            }
            if(n != 1) {
                newPower.append(n - 1);
                if (Integer.parseInt(newPower.toString()) < 0){
                    newPower.append(")");
                    newPower.insert(0, "(");
                }
            }
            else {
                arg.setCharAt(arg.indexOf("x"), '1');
            }
        }

        StringBuilder difArg = new StringBuilder();
        if(arg.toString().contains("x")){
            difArg.append("(");
            difArg.append(Differentiation.difExpression(arg));
            difArg.append(")");
            difArg.append("·");
        }
        else{
            difArg = new StringBuilder();
        }

        if(arg.toString().contains("x")){
            result.append(difArg).append(power).append("·(x)^").append(newPower);
        }
        else {
            result.append(arg);
        }
        if(isNegative){
            result.insert(0, "-");
        }

        return result;
    }

}
