package com.example.experiment.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalculatorResults {

    private List<String> infixList;
    private List<String> suffixList = new ArrayList<>();
    private float result;

    public CalculatorResults(List firstList) {
        this.infixList = firstList;
    }

    public float getResult() {
        return result;
    }

    public List<String> infixParseToSuffix(){

        Stack<String> opStack = new Stack<>();
        for(int i = 0; i < infixList.size();i++){
            String item = String.valueOf(infixList.get(i));
            if(isOperator(item)) {
                if (opStack.isEmpty() || priority(item) > priority(opStack.peek())) {
                    System.out.println(item+"入栈");
                    opStack.push(item);
                }
                else {
                    while (!opStack.isEmpty()) {
                        if (priority(item) <= priority(opStack.peek())) {
                            suffixList.add(opStack.pop());
                        }
                    }
                    opStack.push(item);
                }
            }
            else
            {
                System.out.println(item+"入队");
                suffixList.add(item);
            }
        }
        while (!opStack.isEmpty())
        {
            suffixList.add(opStack.pop());
        }
        System.out.println("suffixList"+suffixList.toString());
        return suffixList;
    }

    private boolean isOperator(String op){
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    private static int priority(String op){
        if(op.equals("*") || op.equals("/")){
            return 1;
        }else if(op.equals("+") || op.equals("-")){
            return 0;
        }
        return -1;
    }

    public void calculateSuffix(){
        Stack<Float> stack = new Stack<>();
        for(String item: suffixList){
            if(!isOperator(item)){
                stack.push(Float.parseFloat(item));
            }else {
                float num2 = stack.pop();
                float num1 = stack.pop();
                float res = 0;
                if(item.equals("+")){
                    res = num1 + num2;
                }else if(item.equals("-")){
                    res = num1 - num2;
                }else if(item.equals("*")){
                    res = num1 * num2;
                }else if(item.equals("/")){
                    res = num1 / num2;
                }
                stack.push(res);
            }
        }
        result = stack.pop();
        System.out.println(result);
    }

}
