package actions;

import functions.Function;
import textManipulators.Analyzer;

import java.util.ArrayList;
import java.util.List;

public class Multiplication implements Function {
    private StringBuilder function;

    @Override
    public StringBuilder getFunction() {
        return function;
    }

    @Override
    public void setFunction(StringBuilder newFunction) {
        this.function = newFunction;
    }

    public Multiplication(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        StringBuilder result = new StringBuilder();
        List<StringBuilder> functions = Analyzer.crushMultiplication(function);
        List<StringBuilder> difFunctions = new ArrayList<>();

        for (int i = 0; i < functions.size(); i++) {
            StringBuilder temp = new StringBuilder(functions.get(i).toString());
            difFunctions.add(Differentiation.difExpression(temp));
        }
        for (int i = 0; i < difFunctions.size(); i++) {
            for (int j = 0; j < functions.size(); j++) {
                if (j != i) {
                    if (functions.get(i).charAt(0) == '-') {
                        functions.get(i).insert(0, '(');
                        functions.get(i).append(")");
                    }
                    result.append(functions.get(j));
                }
                else {
                    result.append(difFunctions.get(i));
                }
                if (j != functions.size() - 1) {
                    result.append("·");
                }
            }
            if(i != functions.size()-1) {
                result.append("+");
            }
        }
        return result;
    }
}
