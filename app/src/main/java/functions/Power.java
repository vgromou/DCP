package functions;

import actions.Differentiation;

import java.util.Arrays;

public class Power implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }


    public Power(StringBuilder function){
        this.function = function;
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
            double numerator = 0;
            double denominator = 0;
            try {
                numerator = Double.parseDouble(power.substring(1, power.indexOf("÷")));
                denominator = Double.parseDouble(power.substring(power.indexOf("÷") + 1, power.length()-1));
            }
            catch (NumberFormatException e){
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
            double newNumerator = numerator - denominator;

            String denominatorStr = isInteger(denominator) ? "" + ((int) denominator) : "" + denominator;

            newPower.append("(").append(newNumerator).append("÷").append(denominatorStr).append(")");
        }
        else{
            double n = 0;
            try {
                if(power.charAt(0) == '(') {
                    n = Double.parseDouble(power.toString().substring(1, power.length() - 1));
                }
                else {
                    n = Double.parseDouble(power.toString());
                }
            }
            catch (NumberFormatException e){

            }
            if(n != 1) {
                newPower.append(n - 1.00d);
                if (Double.parseDouble(newPower.toString()) < 0){
                    newPower.append(")");
                    newPower.insert(0, "(");
                }

                newPower = isInteger(n) ? newPower.delete(newPower.indexOf("."), newPower.length()) : newPower;
            }
            else {
                arg.setCharAt(arg.indexOf("x"), '1');
            }
        }

        if(arg.toString().contains("x")){
            result.append(power).append("·(").append(arg).append(")^").append(newPower);
        }
        else {
            result.append(arg);
        }

        return result;
    }

    private boolean isInteger (Double number){
        String fraction = number.toString().substring(number.toString().indexOf("."));
        return fraction.length() == 2;
    }

}
