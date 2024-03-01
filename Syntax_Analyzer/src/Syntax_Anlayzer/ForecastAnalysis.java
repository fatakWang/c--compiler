package Syntax_Anlayzer;

import Syntax_Anlayzer.Init;
import lexer.Token;
import lexer.TokenTable;

import java.util.*;

public class ForecastAnalysis {
    public TokenTable tokenTable;
    public ForecastAnalysis(TokenTable tokenTable){
        this.tokenTable=tokenTable;
    }
    public void forecastAnalysis(){
        Stack<String> stack=new Stack<>();
        stack.push("#");
        stack.push("program");
        List<String> inputString=new ArrayList<>();


        Iterator iterator=tokenTable.tokens.iterator();
        while (iterator.hasNext()){
            Token token=(Token)iterator.next();
            if(token.getTag().equals("Ident")||token.getTag().equals("INT")) {
                inputString.add(token.getTag());
            }
            else {
                if(token.getLexeme().equals("main"))
                    inputString.add("Ident");
                else
                    inputString.add(token.getLexeme());
            }
        }

        inputString.add("#");
        int ip=0;
        int count=0;
//        System.out.println(count+"\t"+stack+"\t"+inputString.subList(ip,inputString.size()));
//        System.out.println(count+"\t"+stack+"\t"+inputString.subList(ip,inputString.size()));
        while (stack.lastElement().equals("#")==false){

            String x=stack.lastElement();
            String a=inputString.get(ip);
            count++;
            if(Init.V_T.contains(x)==true||x.equals("#")==true){

                if(x.equals(a)==true&&x.equals("#")==true){


                    break;
                }
                else if (x.equals(a)==true&&x.equals("#")==false){
//                    System.out.println(count+"\t"+stack+"\t"+inputString.subList(ip,inputString.size())+"\t"+"move");

                    stack.pop();
                    ip++;

                    System.out.println(count+"\t"+x+"#"+a+"\t"+"move");

                }
                else {
                    System.out.println(count+"\t"+x+"#"+a+"\t"+"error_a");
                    break;
                }
            }else{
                if(ForecastAnalysisTable.forecastAnalysisTable.containsKey(x)){
                    Map<String,List<String>> map= ForecastAnalysisTable.forecastAnalysisTable.get(x);
                    if(map.containsKey(a)){

//                        System.out.println(count+"\t"+stack+"\t"+inputString.subList(ip,inputString.size())+"\t"+x+"->"+map.get(a));

                        stack.pop();
                        List<String> list= map.get(a);
                        int length=list.size()-1;
                        while(length>=0){
                            if(list.get(length).equals("$")==false){
                                stack.push(list.get(length));
                            }
                            length=length-1;
                        }
                        if(x.equals("compUnit")&&a.equals("#")){
                            System.out.println(count+"\t"+"EOF"+"#"+"EOF"+"\t"+"accept");
                        }else{
                            System.out.println(count+"\t"+x+"#"+a+"\t"+"reduction");
                        }


                    }
                    else {
                        System.out.println(count+"\t"+x+"#"+a+"\t"+"error");
                        break;
                    }
                }else {
                    System.out.println(count+"\t"+x+"#"+a+"\t"+"error");
                    break;
                }
            }
        }

    }
}
