package textManipulators;

import actions.Division;
import actions.Multiplication;
import functions.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Analyzer {
    private List<StringBuilder> functions;

    public static List<String> crush (StringBuilder expression){
        List<String> functions = new ArrayList<>();
        List<Integer> signsIndexes = new ArrayList<>();

        if (expression.charAt(0) != '−'){
            expression.insert('+', 0);
        }

        for (int i = 0, countBracket = 0; i < expression.length(); i++) {
            if((expression.charAt(i) == '+' || expression.charAt(i) == '−') && countBracket == 0){
                signsIndexes.add(i);
            }
            else if (expression.charAt(i) == '('){
                countBracket++;
            }
            else if (expression.charAt(i) == ')'){
                countBracket--;
            }
        }

        for (int i = 0; i < signsIndexes.size() - 1; i++) {
            functions.add(expression.substring(signsIndexes.get(i), signsIndexes.get(i+1)));
        }
        functions.add(expression.substring(signsIndexes.get(signsIndexes.size())-1));

        return functions;
    }

    public static List<Function> distribute (StringBuilder expression){
        List<Function> result = new ArrayList<>();
        List<String> functions = crush(expression);
        List<String> auxiliary = deleteBrackets(functions);
        for (int i = 0; i < functions.size(); i++) {
            StringBuilder temp = new StringBuilder(functions.get(i));
            String var = auxiliary.get(i);
            if(var.contains("arccos")){
                result.add(new ArcCos(temp));
            }
            else if(var.contains("arcctg")){
                result.add(new ArcCotan(temp));
            }
            else if(var.contains("arcsin")){
                result.add(new ArcSin(temp));
            }
            else if(var.contains("arctg")){
                result.add(new ArcTan(temp));
            }
            else if(var.contains("ch")){
                result.add(new Ch(temp));
            }
            else if(var.contains("cos")){
                result.add(new Cos(temp));
            }
            else if(var.contains("ctg")){
                result.add(new Cotan(temp));
            }
            else if(var.contains("cth")){
                result.add(new Cth(temp));
            }
            else if(var.contains("^x")){
                result.add(new Exponential(temp));
            }
            else if(var.contains("l")){
                result.add(new Logarithm(temp));
            }
            else if(var.contains("x^")){
                result.add(new Power(temp));
            }
            else if(var.contains("sh")){
                result.add(new Sh(temp));
            }
            else if(var.contains("sin")){
                result.add(new Sin(temp));
            }
            else if(var.contains("tg")){
                result.add(new Tan(temp));
            }
            else if(var.contains("th")){
                result.add(new Th(temp));
            }
            else if(var.contains("÷")){
                result.add(new Division(temp));
            }
            else if(var.contains("·")){
                result.add(new Multiplication(temp));
            }
            else{
                result.add(new Constant(temp));
            }

            /*switch (auxiliary.get(i)){
                case "x^":
                    result.add(new Power(temp));
                    break;
                case "^x":
                    result.add(new Exponential(temp));
                    break;
                case "cos":
                    result.add(new Cos(temp));
                    break;
                case "sin":
                    result.add(new Sin(temp));
                    break;
                case "tg":
                    result.add(new Tan(temp));
                    break;
                case "ctg":
                    result.add(new Cotan(temp));
                    break;
                case "arccos":
                    result.add(new ArcCos(temp));
                    break;
                case "arcctg":
                    result.add(new ArcCotan(temp));
                    break;
                case "arcsin":
                    result.add(new ArcSin(temp));
                    break;
                case "arctg":
                    result.add(new ArcTan(temp));
                    break;
                case "ch":
                    result.add(new Ch(temp));
                    break;
                case "cth":
                    result.add(new Cth(temp));
                    break;
                case "log":
                case "ln":
                    result.add(new Logarithm(temp));
                    break;
                case "sh":
                    result.add(new Sh(temp));
                    break;
                case "th":
                    result.add(new Th(temp));
                    break;
                default:
                    result.add(new Constant(temp));
                    break;
            }*/
        }
        return result;
    }

    public static List<String> deleteBrackets (List<String> functions){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < functions.size(); i++) {
            StringBuilder temp = new StringBuilder(functions.get(i));
            List<Integer> bracketIndexes = new ArrayList<>();
            for (int j = 0, countInnerBrackets = 0; j < temp.length(); j++) {
                if(temp.charAt(j) == '(' && countInnerBrackets == 0){
                    bracketIndexes.add(j);
                }
                else if(temp.charAt(j) == ')' && countInnerBrackets == 0){
                    bracketIndexes.add(j);
                }
                else if (temp.charAt(j) == '(') countInnerBrackets++;
                else if (temp.charAt(j) == ')') countInnerBrackets--;
            }
            for (int k = 0; k < bracketIndexes.size()-1; k+=2) {
                int open = bracketIndexes.get(k);
                int close = bracketIndexes.get(k+1);
                if(temp.substring(open, close).contains((CharSequence) "x")){
                    temp.replace(open, close + 1, "x");
                }
                else temp.delete(open, close + 1);
            }
            if(!temp.toString().contains((CharSequence)"x")){
                temp = new StringBuilder("0");
            }
            result.add(temp.toString());
        }
        return result;
    }
}
