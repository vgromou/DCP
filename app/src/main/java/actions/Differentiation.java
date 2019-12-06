package actions;

import textManipulators.Analyzer;
import functions.*;

import java.util.List;

public abstract class Differentiation {
    
    public static StringBuilder difExpression(StringBuilder expression){

        /*
        Удаляет все пробелы в выражении
         */
        for (int iSpace = expression.indexOf(" "), jSpace = expression.lastIndexOf(" ");
             iSpace <= jSpace; iSpace = expression.indexOf(" ")){
            expression = expression.deleteCharAt(iSpace);
        }

        List<StringBuilder> strFunctions = Analyzer.crush(expression);

        /*
        Следующее условие добавляет к первой функции знак "+", если она не отрицательная
        Нужно для того, чтобы все функции были одного шаблона.
         */
        boolean isFirstPositive = (strFunctions.get(0).charAt(0) != '-');
        if(isFirstPositive){
            strFunctions.get(0).insert(0, '+');
        }

        List<Function> function = Analyzer.distribute(strFunctions);
        
        StringBuilder answer = new StringBuilder("");

        /*
        Формирование ответа. Каждый раз к ответу присоединяется результат от дифференцирования каждой функции.
        Так же присутствует удаление знака "+" у первого элемента (если он, конечно, положительный)
         */
        for (int i = 0; i < function.size(); i++) {
            StringBuilder currentFunction = function.get(i).differentiate();
            if(i == 0 && isFirstPositive){
                currentFunction.deleteCharAt(0);
            }
            answer.append(currentFunction);
        }

        return answer;
    }
}
