package Syntax_Anlayzer;

import Syntax_Anlayzer.First;
import Syntax_Anlayzer.Follow;
import Syntax_Anlayzer.Init;

import java.util.*;

public class ForecastAnalysisTable {
    public static Map<String,Map<String, List<String>>> forecastAnalysisTable=new HashMap<>();
    public void buildTable(){
        Iterator iterator= Init.Production.keySet().iterator();
        while (iterator.hasNext()){
            String leftSide=(String) iterator.next();
            Set<List<String>> set=Init.Production.get(leftSide);
            Iterator itSet=set.iterator();
            while(itSet.hasNext()){
                List<String> list=(List<String>) itSet.next();
                Set<String> first=new HashSet<>();
                boolean flag=false;
                Iterator itList=list.listIterator();
                while(itList.hasNext()){
                    String x=(String) itList.next();
                    Set<String> myfirst= First.first.get(x);
                    if(myfirst.contains("$")==false){
                        first.addAll(myfirst);
                        flag=true;
                        break;
                    }
                    first.addAll(myfirst);
                    first.remove("$");
                }
                if(flag==false) {
                    first.add("$");
                }
                Iterator itset=first.iterator();
                while (itset.hasNext()) {
                    String a = (String) itset.next();
                    if(a.equals("$")==true)
                        continue;
                    if (Init.V_T.contains(a)) {
                        if (forecastAnalysisTable.containsKey(leftSide)) {
                            Map myMap = forecastAnalysisTable.get(leftSide);
                            List<String> mylist = new ArrayList<>();
                            mylist.addAll(list);
                            myMap.put(a, mylist);
                            forecastAnalysisTable.put(leftSide, myMap);
                        } else {
                            Map map = new HashMap<>();
                            map.put(a, list);
                            forecastAnalysisTable.put(leftSide, map);
                        }
                    }
                }
                if(first.contains("$")){
                    Set follow= Follow.follow.get(leftSide);
                    Iterator followIter=follow.iterator();
                    while (followIter.hasNext()){
                        String b=(String) followIter.next();
                        if(forecastAnalysisTable.containsKey(leftSide)){
                            Map myMap=forecastAnalysisTable.get(leftSide);
                            if(myMap.containsKey(b)){
                                List<String> mylist=(List<String>) myMap.get(b);
                                if(mylist.contains("$")==false) {
                                    mylist.add("$");
                                    myMap.put(b, mylist);
                                    forecastAnalysisTable.put(leftSide, myMap);
                                }
                            }
                            else {
                                List<String> mylist=new ArrayList<>();
                                mylist.add("$");
                                myMap.put(b,mylist);
                                forecastAnalysisTable.put(leftSide,myMap);
                            }
                        }
                        else {
                            Map map = new HashMap<>();
                            List<String> mylist=new ArrayList<>();
                            mylist.add("$");
                            map.put(b,mylist);
                            forecastAnalysisTable.put(leftSide, map);
                        }
                    }
                }
            }
        }
    }
}