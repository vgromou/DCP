package functions;

public interface Function {
    public StringBuilder differentiate ();
    default StringBuilder complex (StringBuilder function){
        //нужен ли этот метод?
        return new StringBuilder("temp");
    }
}
