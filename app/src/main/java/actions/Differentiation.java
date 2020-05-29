package actions;

import textManipulators.Analyzer;
import functions.*;
import textManipulators.Simplifier;
import tree.GenericTree;
import tree.GenericTreeNode;

import java.util.*;

public abstract class Differentiation {
    
    public static StringBuilder difExpression(StringBuilder expression) {
        expression.append(")");
        expression.insert(0, "(");

        /*
        Следующее условие добавляет к первой функции знак "+", если она не отрицательная
        Нужно для того, чтобы все функции были одного шаблона.

        Меняет кастомные минусы на обычные
         */

        for (int i = 0; i < expression.length() - 1; i++) {
            if(expression.charAt(i) == '(' && expression.charAt(i+1) != '−'){
                expression.insert(i+1, "+");
            }
            if(expression.charAt(i) == '−'){
                expression.setCharAt(i, '-');
            }
        }

        GenericTree<StringBuilder> tree = Analyzer.buildTree(expression);

        //Проверка
        System.out.println(tree.getRoot().getParent());
        System.out.println(tree.getNumberOfNodes());
        System.out.println(tree.toStringWithDepth());
        System.out.println(expression);

        ArrayList<Multiplication> multiList = new ArrayList<>();
        GenericTree<Function> treeFunc = buildTree(tree, multiList);
        //Проверка
        System.out.println(treeFunc.toStringWithDepth());

        GenericTree<StringBuilder> treeRes = new GenericTree<>();
        treeRes.setRoot(new GenericTreeNode<>(new StringBuilder(" ")));
        /* Проверка
        treeRes.getRoot().addChild(new GenericTreeNode<>(new StringBuilder("ln(x)")));
        treeRes.getRoot().addChild(new GenericTreeNode<>(new StringBuilder("ctg(tg(x + 1))")));
        treeRes.getRoot().getChildren().get(1).addChild(new GenericTreeNode<>(new StringBuilder("tg(x + 1)")));
        treeRes.getRoot().getChildren().get(1).getChildren().get(0).addChild(new GenericTreeNode<>(new StringBuilder("x")));
        treeRes.getRoot().getChildren().get(1).getChildren().get(0).addChild(new GenericTreeNode<>(new StringBuilder("1")));
        System.out.println(treeRes.toStringWithDepth());
        System.out.println(treeRes.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(0).getDepth());
        */
        difTree(treeFunc.getRoot(), treeRes.getRoot());
        System.out.println(treeRes.toStringWithDepth());

        System.out.println(createAnswer(treeRes, treeFunc, multiList));

        StringBuilder answer = createAnswer(treeRes, treeFunc, multiList);

        for (int i = 0; i < answer.length() - 1; i++) {
            if(answer.charAt(i) == '-'){
                answer.setCharAt(i, '−');
            }
        }
        Simplifier.simplify(answer);
        return answer;
    }

    private static GenericTree<Function> buildTree(GenericTree<StringBuilder> tree, ArrayList<Multiplication> multiList){
        GenericTree<Function> treeFunc = new GenericTree<>();
        GenericTreeNode<Function> rootFunc = new GenericTreeNode<>(Analyzer.distribute(tree.getRoot().getData()));
        treeFunc.setRoot(rootFunc);
        build(treeFunc.getRoot(), tree.getRoot(), tree, multiList);

        return treeFunc;
    }

    private static void build(GenericTreeNode<Function> node, GenericTreeNode<StringBuilder> nodeStr, GenericTree<StringBuilder> treeStr, ArrayList<Multiplication> multiList){
        List<GenericTreeNode<StringBuilder>> children = nodeStr.getChildren();

        for (GenericTreeNode<StringBuilder> child : children) {
            GenericTreeNode<Function> temp = new GenericTreeNode<>(Analyzer.distribute(child.getData()));
            node.addChild(temp);
            if(temp.getData() instanceof Multiplication){
                ((Multiplication) temp.getData()).setFunctionNode(child);
                multiList.add((Multiplication) temp.getData());
            }
            build(temp, child, treeStr, multiList);
        }
    }

    private static void difTree(GenericTreeNode<Function> node, GenericTreeNode<StringBuilder> nodeRes){

        if(!node.hasChildren()){
            return;
        }

        List<GenericTreeNode<Function>> children = node.getChildren();
        for (int i = 0; i < node.getNumberOfChildren(); i++) {
            boolean isX = children.get(i).getData().getFunction().toString().equals("+x");
            GenericTreeNode<StringBuilder> temp;
            if(!isX) {
                temp = new GenericTreeNode<>(children.get(i).getData().differentiate());
            }
            else{
                temp = new GenericTreeNode<>(new StringBuilder("1"));
            }
            nodeRes.addChild(temp);
            difTree(children.get(i), temp);
        }
    }

    private static StringBuilder createAnswer(GenericTree<StringBuilder> tree, GenericTree<Function> treeFunc, ArrayList<Multiplication> multiList){
        /*
         * Ответ формируется как сумма детей * продифференцированное выражение
         * Избавляемся от листьев, перекидывая их как сомножитель к родителю
         */

        StringBuilder answer = new StringBuilder();
        GenericTreeNode<StringBuilder> root = tree.getRoot();
        /*
         * Происходят следующие действия:
         * 1) Находятся все крайние листья (самые дальние)
         * 2) Из них вылавливаются те, у кого один родитель
         * 3) Те, у кого один родитель, обрабатываются (соединяются и присоединяются к родителю)
         * 4) Их родитель заменяется на "родитель * сумма детей"
         * 5) Дети удаляются
         * 6) Если изначально получили детей от разных родителей, то итерация повторяется для остальных родителей
         * 7) Все подобное происходит для всех уровней, кроме корня и его детей:
         *    тут он не заменяется, а дети не удаляются; просто остается дерево с корнем и двумя детьми
         * 8) Формируется ответ как сумма детей корня.
         */
        do{
            Set<GenericTreeNode<StringBuilder>> leafs = root.getAllLeafsWithMaxDepth();
            Iterator<GenericTreeNode<StringBuilder>> it = leafs.iterator();
            GenericTreeNode<StringBuilder> leaf = it.next();
            GenericTreeNode<StringBuilder> key = leaf;
            if(key.getParent().getData().toString().contains("M")){
                StringBuilder childFactor = key.getParent().getData();
                childFactor.deleteCharAt(childFactor.indexOf("M"));
                Multiplication thisFunc = new Multiplication(new StringBuilder(""));
                for (int i = 0; i < multiList.size(); i++) {
                    thisFunc = multiList.get(i);
                    if(thisFunc.getFunction().toString().contains(childFactor.toString())){
                        break;
                    }
                }
                thisFunc.setChildren();
                List<StringBuilder> children = thisFunc.getChildren();
                List<GenericTreeNode<StringBuilder>> tempChildren = key.getParent().getChildren();
                childFactor.delete(0, childFactor.length());
                for (int i = 0; i < tempChildren.size(); i++) {
                    childFactor.append("(").append(tempChildren.get(i)).append(")");
                    for (int j = 0; j < children.size(); j++) {
                        if(i != j) {
                            childFactor.append("·(").append(children.get(j)).append(")");
                        }
                    }
                    if(i != tempChildren.size()-1){
                        childFactor.append("+");
                    }
                }

            }
            else {
                key.getParent().setData(key.getParent().getData().insert(0, "(").append(")"));
                StringBuilder childFactor = key.getParent().getData();
                List<GenericTreeNode<StringBuilder>> tempChildren = key.getParent().getChildren();
                childFactor.append("·(");
            /*
            for (; it.hasNext(); ) {
                leaf = it.next();
                if(key.getParent().equals(leaf.getParent())){
                    childFactor.append("+").append(leaf.getData());
                }
            }
             */
                for (int i = 0; i < tempChildren.size(); i++) {
                    childFactor.append(tempChildren.get(i).getData());
                    if (i != tempChildren.size() - 1) {
                        childFactor.append("+");
                    }
                }
                childFactor.append(")");
            }
            if(key.getParent().equals(root)){
                break;
            }

            key.getParent().removeChildren();
        } while(true);

        List<GenericTreeNode<StringBuilder>> rootChildren = root.getChildren();

        if(!(treeFunc.getRoot().getData() instanceof Multiplication)) {
            answer.append(rootChildren.get(0));
            for (int i = 1; i < rootChildren.size(); i++) {
                answer.append("+").append(rootChildren.get(i));
            }
        }
        else{
            List<GenericTreeNode<Function>> funcRootChildren = treeFunc.getRoot().getChildren();
            for (int i = 0; i < rootChildren.size(); i++) {
                answer.append(rootChildren.get(i));
                for (int j = 0; j < funcRootChildren.size(); j++) {
                    if(j == i) continue;
                    answer.append("·(").append(funcRootChildren.get(j).getData().getFunction().toString()).append(")");
                }
                if(i != rootChildren.size()-1) {
                    answer.append("+");
                }
            }

        }
        System.out.println("/*****" + answer);
        return answer;
    }

}
