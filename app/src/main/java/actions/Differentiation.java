package actions;

import textManipulators.Analyzer;
import functions.*;

import java.util.List;

public abstract class Differentiation {
    
    public static StringBuilder difExpression(StringBuilder expression) {

        /*
        Меняет все кстомные минусы на обычные
        */

        for (int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '−'){
                expression.setCharAt(i, '-');
            }
        }

        /*
        Следующее условие добавляет к первой функции знак "+", если она не отрицательная
        Нужно для того, чтобы все функции были одного шаблона.
         */


        boolean isFirstPositive = (expression.charAt(0) != '-');
        if(isFirstPositive){
           expression.insert(0, '+');
        }

        List<Function> function = Analyzer.distribute(expression);
        
        StringBuilder answer = new StringBuilder();

        /*
        Формирование ответа. Каждый раз к ответу присоединяется результат от дифференцирования каждой функции.
        Так же присутствует удаление знака "+" у первого элемента (если он, конечно, положительный)
         */

        for (int i = 0; i < function.size(); i++) {
            StringBuilder currentFunction = function.get(i).differentiate();
            if(currentFunction.toString().equals("0")){
                continue;
            }
            answer.append(currentFunction);
        }

        if (answer.length() == 0){
            answer.append(0);
        }
        if (answer.charAt(0) == '+'){
            answer.deleteCharAt(0);
        }

        for (int i = 0; i < answer.length(); i++) {
            if(answer.charAt(i) == '-'){
                answer.setCharAt(i, '−');
            }
        }

        return answer;
    }
}
