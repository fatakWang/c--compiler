package Syntax_Anlayzer;

import java.util.*;

public class First {
    static Init init;

    public First(Init init) {
        First.init = init;
    }

    public static Map<String, Set<String>> first = new HashMap<>();

    //生成first集合的第一步，所有终结符的首符集合即为自己,直接加进去
    private void initFirst() {
        //将所有终结符添加进去
        for (String a : Init.V_T) {
            first.put(a, new HashSet<>());
            first.get(a).add(a);
        }
    }

    //填充非终结符v的first集合
    public static void addVNIntoFirst(String v) {
        //进来先初始化,因为消除了左递归，所以理论上不会死递归
        if (!first.containsKey(v)) first.put(v, new HashSet<>());
        else return;
        //得到关于v的所有产生式vProduction
        Set<List<String>> vProduction = Init.Production.get(v);
        //遍历v的每个产生式右侧l
        for (List<String> l : vProduction) {
            //判断这个产生式右侧l的第一个符号是否为非终结符
            if (Init.V_T.contains(l.get(0))) {
                //是终结符的情况,直接将这个终结符添加进去
                first.get(v).add(l.get(0));
            } else {
                //是非终结符的情况，遍历产生式右侧l的每一个符号y
                int i;
                for (i = 0; i < l.size(); i++) {
                    String y = l.get(i);
                    if (Init.V_N.contains(y)) {
                        //first还没有添加这个键值对,说明还没有访问过，先构造它的First集合
                        if (!first.containsKey(y)) {
                            addVNIntoFirst(y);
                        }
                        //遍历y的每个first集合,把除了$的符号全部添加进去
                        for (String s : first.get(y)) {
                            if (!s.equals("$")) first.get(v).add(s);
                        }
                        //如果y的first集合不包含$,那就退出
                        if (!first.get(y).contains("$")) break;
                    } else {
                        //如果是终结符也退出
                        first.get(v).add(y);
                        break;
                    }
                }
                //如果遍历完了，说明要添加$
                if (i == l.size()) first.get(v).add("$");
            }
        }
    }

    public void constructFirst() {
        initFirst();
        for (String s : Init.V_N) {
            addVNIntoFirst(s);
        }
    }

    public void constructFollow() {
        Follow.constructFollow();
    }
}
