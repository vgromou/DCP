package actions;

import functions.Function;
import textManipulators.Analyzer;
import tree.GenericTree;
import tree.GenericTreeNode;

import java.util.ArrayList;
import java.util.List;

public class Multiplication implements Function {
    private StringBuilder function;
    private GenericTreeNode<StringBuilder> functionNode = new GenericTreeNode<>();
    private List<StringBuilder> children = new ArrayList<>();


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

    public void setFunctionNode(GenericTreeNode<StringBuilder> functionNode) {
        this.functionNode = functionNode;
    }

    public void setChildren(){
        List<GenericTreeNode<StringBuilder>> temp = functionNode.getChildren();
        for (int i = 0; i < temp.size(); i++) {
            children.add(temp.get(i).getData());
        }
    }

    public List<StringBuilder> getChildren() {
        return children;
    }

    @Override
    public StringBuilder differentiate(){
        function.insert(0, "M");
        return function;
    }
}
