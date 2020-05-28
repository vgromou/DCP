package textManipulators;

public abstract class Simplifier {

    /*
    Метод simplify этого класса очень важен. Его надо будет вызывать в самом начале программы, чтобы упростить исходное
    выражаение, если это возможно, и в конце, чтобы ответ выглядел приемлимо. Вызывать в начале необходимо для более
    рационального использования ресурсов. Например, пользователь может ввести выражение "cos(x) - cos(x)", где вместо
    x может стоять сколько угодно длинное и сложное выражение. Без этого метода программа начнет высчитывать производную
    от двух сложных функций, но в итоге все равно получит 0. Метод simplify изначально приведет это выражение к нулевому
    значению, сэкономит время пользователя и ресурсы компьютера

    Это поясняющая записка на будущее

    Удалить по завершении написания класса
     */

    public static void simplify (StringBuilder expression){
        reduceMinuses(expression);
        deleteUnnecessaryPluses(expression);
        reduceOnes(expression);
        reduceDoubleBrackets(expression);
    }

    private static void reduceConst (StringBuilder expression){
        //Метод, совмещающий константы
    }

    private static void reduceMinuses (StringBuilder expression){
        //Метод, убирающий лишние минусы
        while(expression.toString().contains("−−")){
            int index = expression.indexOf("−−");
            expression.replace(index, index + 2, "+");
        }
    }

    private static void combineTerms (StringBuilder expression){
        //Метод, приводящий подобные слагаемые
    }

    private static void deleteUnnecessaryPluses(StringBuilder expression){
        if(expression.toString().charAt(0) == '+'){
            expression.deleteCharAt(0);
        }
        while(expression.toString().contains("(+")){
            int index = expression.indexOf("(+");
            expression.replace(index, index + 2, "(");
        }
    }

    private static void reduceOnes (StringBuilder expression){
        while(expression.toString().contains("·(1)")){
            int index = expression.indexOf("·(1)");
            expression.replace(index, index + 4, "");
        }
        while(expression.toString().contains("(1)·")){
            int index = expression.indexOf("(1)·");
            expression.replace(index, index + 4, "");
        }
    }

    private static void reduceDoubleBrackets (StringBuilder expression){
        boolean isDouble = false;
        int indexOpen = 0;
        for (int i = 0, count = 0; i < expression.length() - 1; i++) {
            if(expression.charAt(i) == '(' && expression.charAt(i + 1) == '(' && !isDouble){
                indexOpen = i;
                isDouble = true;
                count++;
                i++;
            }
            else if(expression.charAt(i) == '('){
                count++;
            }
            if(expression.charAt(i) == ')'){
                count--;
                if(expression.charAt(i + 1) == ')' && count == 0 && isDouble){
                    expression.deleteCharAt(i);
                    expression.deleteCharAt(indexOpen);
                    i-=2;
                    isDouble = false;
                }
            }


        }
    }
}
