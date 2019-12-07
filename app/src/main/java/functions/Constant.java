package functions;

public class Constant implements Function {
    private StringBuilder function;

    public StringBuilder getFunction() {
        return function;
    }

    public Constant(StringBuilder function){
        this.function = function;
    }

    @Override
    public StringBuilder differentiate(){
        return new StringBuilder("0");
    }

}
