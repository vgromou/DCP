package textManipulators;

import functions.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Analyzer {
    private List<StringBuilder> functions;

    public static List<StringBuilder> crush (StringBuilder expression){
        List<StringBuilder> functions = new ArrayList<>();
        List<Character> signs = new ArrayList<>();
        int countParentheses;
        for (int i = 0; i < expression.indexOf("[+-]"); i++) {

        }
        return new ArrayList<StringBuilder>();
    }

    public static List<Function> distribute (List<StringBuilder> strFunctions){
        //код
        //либо void, либо ArrayList
        //это не нужно:
        List<Function> res = new ArrayList<>();
        StringBuilder str = new StringBuilder("arccos");
        switch (str.toString()){
            case("arccos"):
                res.add(new ArcCos(str));
        }
        return null;
    }
}
