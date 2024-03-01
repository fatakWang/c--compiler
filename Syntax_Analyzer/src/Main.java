import java.util.*;

import Syntax_Anlayzer.*;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenTable;
import java.io.File;


public class Main {

    static int cnt;
    public static void output() {
        Iterator<String> it = First.first.keySet().iterator();
        cnt = 1;
        System.out.println("first集:");
        while (it.hasNext()) {
            Object key = it.next();
            Object value = First.first.get(key);
            System.out.println(cnt++ + " " + key + " " + value);
        }
        System.out.println();

        cnt = 1;
        System.out.println("V_N集:");
        for (String str : Init.V_N) {
            System.out.println(cnt++ + " " + str);
        }
        System.out.println();

        cnt = 1;
        System.out.println("V_T集:");
        for (String str : Init.V_T) {
            System.out.println(cnt++ + " " + str);
        }
        System.out.println();

        Iterator<String> it2 = Init.Production.keySet().iterator();
        cnt = 1;
        System.out.println("Production集:");
        while (it2.hasNext()) {
            Object key = it2.next();
            Object value = Init.Production.get(key);
            System.out.println(cnt++ + " " + key + " " + value);
        }
        System.out.println();

        Iterator<List<String>> it3 = Follow.firstX.keySet().iterator();
        cnt = 1;
        System.out.println("firstX集:");
        while (it3.hasNext()) {
            Object key = it3.next();
            Object value = Follow.firstX.get(key);
            System.out.println(cnt++ + " " + key + " " + value);
        }
        System.out.println();

        Iterator<String> it4 = Follow.follow.keySet().iterator();
        cnt = 1;
        System.out.println("follow集:");
        while (it4.hasNext()) {
            Object key = it4.next();
            Object value = Follow.follow.get(key);
            System.out.println(cnt++ + " " + key + " " + value);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TokenTable tokenTable = new TokenTable();
        File srcFile = new File(System.getProperty("user.dir") + "/examples/05_var_defn.sy");
        //File srcFile = new File("C:\\Users\\19143\\大三上\\编译原理\\c--compiler-master\\Syntax_Analyzer\\test");
        Lexer lexer = new Lexer(srcFile, tokenTable);
        lexer.work();
        int size=tokenTable.getSize();
        for(int i=0;i<size;i++){
            Token token=tokenTable.tokens.get(i);
            System.out.println(token.getLexeme()+ " "+token.getTag()+" "+token.getPointer());
        }
        System.out.println("");
        First f = new First(Init.getInstance("./Syntax_Analyzer/grammar.txt"));
        f.constructFirst();
        f.constructFollow();
        output();
        ForecastAnalysisTable forecastAnalysisTable=new ForecastAnalysisTable();
        forecastAnalysisTable.buildTable();
        System.out.println();
        Iterator iterator1=ForecastAnalysisTable.forecastAnalysisTable.keySet().iterator();
        while (iterator1.hasNext()){
            String V_N=(String) iterator1.next();
            Map map=ForecastAnalysisTable.forecastAnalysisTable.get(V_N);
            Iterator iterator2=map.keySet().iterator();
            while (iterator2.hasNext()){
                String V_T=(String) iterator2.next();
                List list=(List) map.get(V_T);
                System.out.println(V_N+" "+V_T+" "+list);
            }
        }
        ForecastAnalysis forecastAnalysis=new ForecastAnalysis(tokenTable);
        forecastAnalysis.forecastAnalysis();

    }
}