package textManipulators;

import actions.Division;
import actions.Multiplication;
import functions.*;
import tree.GenericTree;
import tree.GenericTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Analyzer {

    public static GenericTree<StringBuilder> buildTree(StringBuilder expression){
        GenericTree<StringBuilder> functionTree = new GenericTree<>();
        GenericTreeNode<StringBuilder> root = new GenericTreeNode<>();
        root.setData(expression);
        functionTree.setRoot(root);
        build(root);
        return functionTree;
    }
    private static void build(GenericTreeNode<StringBuilder> node){
        System.out.println(node.getData().toString());
        if (!node.getData().toString().contains("(")) {
            return;
        }

        StringBuilder tempN = new StringBuilder(node.getData());
        tempN = deleteBracketsMulti(tempN);

        List<StringBuilder> children;
        if(!tempN.toString().contains("·")) {
            children = crush(node.getData());
        }
        else{
            children = crushMultiplication(node.getData());
        }

        for (StringBuilder stringBuilder : children) {
            GenericTreeNode<StringBuilder> temp = new GenericTreeNode<>(stringBuilder);
            node.addChild(temp);
            build(temp);
        }
    }

    private static List<StringBuilder> crush (StringBuilder expressionT){ //К изначальному выражению присобачить скобки!
        StringBuilder expression = new StringBuilder(expressionT.toString());
        List<StringBuilder> functions = new ArrayList<>();
        List<Integer> signsIndexes = new ArrayList<>();

        int index = expression.indexOf("(");
        expression.delete(0, index + 1); //exclusive index
        int lastIndex = expression.lastIndexOf(")");
        expression.delete(lastIndex, expression.length());

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
            StringBuilder temp = new StringBuilder(expression.substring(signsIndexes.get(i), signsIndexes.get(i+1)));
            functions.add(temp);
        }
        StringBuilder temp = new StringBuilder(expression.substring(signsIndexes.get(signsIndexes.size()-1)));
        functions.add(temp);
        return functions;
    }

    public static Function distribute (StringBuilder expression){
            StringBuilder exVar = deleteBrackets(expression);

            if(exVar.toString().contains("÷")){
                return new Division(expression);
            }
            else if(exVar.toString().contains("·")){
                return new Multiplication(expression);
            }
            else if(exVar.toString().contains("arccos")){
                return new ArcCos(expression);
            }
            else if(exVar.toString().contains("arcctg")){
                return new ArcCotan(expression);
            }
            else if(exVar.toString().contains("arcsin")){
                return new ArcSin(expression);
            }
            else if(exVar.toString().contains("arctg")){
                return new ArcTan(expression);
            }
            else if(exVar.toString().contains("ch")){
                return new Ch(expression);
            }
            else if(exVar.toString().contains("cos")){
                return new Cos(expression);
            }
            else if(exVar.toString().contains("ctg")){
                return new Cotan(expression);
            }
            else if(exVar.toString().contains("cth")){
                return new Cth(expression);
            }
            else if(exVar.toString().contains("^x")){
                return new Exponential(expression);
            }
            else if(exVar.toString().contains("l")){
                return new Logarithm(expression);
            }
            else if(exVar.toString().contains("sh")){
                return new Sh(expression);
            }
            else if(exVar.toString().contains("sin")){
                return new Sin(expression);
            }
            else if(exVar.toString().contains("tg")){
                return new Tan(expression);
            }
            else if(exVar.toString().contains("th")){
                return new Th(expression);
            }
            else if(exVar.toString().contains("x")){
                return new Power(expression);
            }
            else{
                return new Constant(expression);
            }
    }

    private static StringBuilder deleteBrackets (StringBuilder expression){
        StringBuilder temp = new StringBuilder(expression.toString());
        if(!temp.toString().contains("x")) temp = new StringBuilder("const");
        if(temp.toString().contains("(") && temp.toString().contains(")")) {
            temp.replace(temp.indexOf("("), temp.lastIndexOf(")") - 1, "x");
        }
        return temp;
    }

    private static StringBuilder deleteBracketsMulti (StringBuilder expression){
        StringBuilder temp = new StringBuilder(expression.toString());
        int beg = 0;
        int end = 0;
        for (int i = 0; i < temp.toString().length(); i++) {
            if(temp.charAt(i) == '('){
                beg = i;
            }
            if(temp.charAt(i) == ')'){
                end = i;
            }
            if(end != 0){
                temp.delete(beg + 1, end);
                i -= (end - beg);
                i += 2;
                beg = 0;
                end = 0;
            }
        }
        return temp;
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