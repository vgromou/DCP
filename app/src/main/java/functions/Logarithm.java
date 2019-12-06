package functions;

public class Logarithm implements Function {

    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Logarithm(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate() {


        StringBuilder result = new StringBuilder();

        // Натуральный логарфим
        if (function.charAt(2) == 'n') //точно 2 символ?
        {
            result.append("(1 / ( ");

            StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
            StringBuilder difArg = complex(arg); //оставлять это?

            result.append(arg + ")) * " + difArg);
        }

        // Логарифм
        if (function.charAt(3) == 'g')
        {
            result.append("(1 /((");

            StringBuilder arg = new StringBuilder(function.substring(function.indexOf("(") + 1, function.lastIndexOf(")")));
            StringBuilder baseOfTheLogarithm = new StringBuilder(function.substring(function.indexOf("g") + 1, function.lastIndexOf("(")));
            StringBuilder difArg = complex(arg);

            result.append(arg + ") * ln" + baseOfTheLogarithm + "))");
        }

        return result;
    }

}
