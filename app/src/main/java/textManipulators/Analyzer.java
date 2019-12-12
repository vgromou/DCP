package textManipulators;

import actions.Division;
import actions.Multiplication;
import functions.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Analyzer {

    private static List<String> crush (StringBuilder expression){
        List<String> functions = new ArrayList<>();
        List<Integer> signsIndexes = new ArrayList<>();

        for (int i = 0, countBracket = 0; i < expression.length(); i++) {
            if((expression.charAt(i) == '+' || expression.charAt(i) == '-') && countBracket == 0){
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
        functions.add(expression.substring(signsIndexes.get(signsIndexes.size()-1)));

        return functions;
    }

    public static List<Function> distribute (StringBuilder expression){
        List<Function> result = new ArrayList<>();
        List<String> functions = crush(expression);
        List<String> auxiliary = deleteBrackets(functions);
        for (int i = 0; i < functions.size(); i++) {
            StringBuilder temp = new StringBuilder(functions.get(i));
            String var = auxiliary.get(i);

            if(var.contains("÷")){
                result.add(new Division(temp));
            }
            else if(var.contains("·")){
                result.add(new Multiplication(temp));
            }
            else if(var.contains("arccos")){
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
            else if(var.contains("x")){
                result.add(new Power(temp));
            }
            else{
                result.add(new Constant(temp));
            }
        }
        return result;
    }

    private static List<String> deleteBrackets (List<String> functions){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < functions.size(); i++) {
            StringBuilder temp = new StringBuilder(functions.get(i));
            List<Integer> bracketIndexes = new ArrayList<>();
            for (int j = 0, countInnerBrackets = 0, countOuterBrackets = 0; j < temp.length(); j++) {

                if (temp.charAt(j) == '(' && countOuterBrackets != 0) countInnerBrackets++;
                else if (temp.charAt(j) == ')' && countOuterBrackets != 0 && countInnerBrackets != 0) countInnerBrackets--;

                if(temp.charAt(j) == '(' && countInnerBrackets == 0){
                    bracketIndexes.add(j);
                    countOuterBrackets++;
                }
                else if(temp.charAt(j) == ')' && countInnerBrackets == 0){
                    bracketIndexes.add(j);
                    countOuterBrackets--;
                }
            }

            int shiftSymbols = 0;

            for (int k = 0; k < bracketIndexes.size()-1; k+=2) {
                int open = bracketIndexes.get(k);
                int close = bracketIndexes.get(k+1);
                if(temp.substring(open - shiftSymbols, close - shiftSymbols).contains("x")){
                    temp.replace(open - shiftSymbols, close + 1 - shiftSymbols, "x");
                }
                else temp.delete(open, close + 1);
                shiftSymbols += close;
            }
            if(!temp.toString().contains("x")){
                temp = new StringBuilder("0");
            }
            result.add(temp.toString());
        }
        return result;
    }

    public static List<StringBuilder> crushMultiplication (StringBuilder expression){
        expression.insert(0, "·");
        List<StringBuilder> functions = new ArrayList<>();
        List<Integer> signIndexes = new ArrayList<>();
        List<StringBuilder> temp = new ArrayList<>();

        for (int i = 0, countBracket = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '·' && countBracket == 0){
                signIndexes.add(i);
            }
            else if (expression.charAt(i) == '('){
                countBracket++;
            }
            else if (expression.charAt(i) == ')'){
                countBracket--;
            }
        }

        for (int i = 0; i < signIndexes.size(); i++) {
            if(i != signIndexes.size() - 1) {
                temp.add(new StringBuilder(expression.substring(signIndexes.get(i) + 1, signIndexes.get(i + 1))));
            }
            else {
                temp.add(new StringBuilder(expression.substring(signIndexes.get(signIndexes.size() - 1) + 1)));
            }
            if(temp.get(i).charAt(0) == '('){
                temp.get(i).deleteCharAt(0);
                temp.get(i).deleteCharAt(temp.get(i).length()-1);
            }
            functions.add(temp.get(i));
        }

        return functions;
    }
}