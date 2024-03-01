package Syntax_Anlayzer;

import java.util.*;


public class Follow {

    public static Map<String, Set<String>> follow = new HashMap<>(); //follow集合
    public static Map<List<String>, Set<String>> firstX = new HashMap<>(); //生成任何符号串的first

    //生成任何符号串的first
    public static void getFirstX(List<String> str0) {
        Set<String> set = (firstX.containsKey(str0)) ? firstX.get(str0) : new HashSet<>();
        //从左往右扫描该式
        int i = 0;
        while (i < str0.size()) {
            String str = str0.get(i);
            if (!First.first.containsKey(str)) First.addVNIntoFirst(str);
            Set<String> t_v = First.first.get(str);
            //将其非空first集加入左部
            for (String s : t_v)
                if (!Objects.equals(s, "$")) set.add(s);
            //若包含空串 处理下一个符号
            if (t_v.contains("$")) i++;
                //否则结束
            else break;
            //到了尾部 即所有符号的first集都包含空串 把空串加入
            if (i == str0.size()) {
                set.add("$");
            }
        }
        firstX.put(str0, set);
    }

    //生成follow集的入口（待改）
    public static void constructFollow() {

        for (String str : Init.V_N) {
            Set<List<String>> l = Init.Production.get(str);
            for (List<String> s : l)
                Follow.getFirstX(s);
        }

        for (int i = 0; i < 5; i++) {
            for (String str : Init.V_N) {
                getFollow(str);
            }
        }
    }

    //生成follow集合
    public static void getFollow(String str) {
        Set<List<String>> lists = Init.Production.get(str);
        Set<String> setA = follow.containsKey(str) ? follow.get(str) : new HashSet<>();
        //如果是开始符 添加#
        if (Objects.equals(str, "program")) {
            setA.add("#");
        }
        //查找输入的所有产生式，确定c的后跟终结符
        for (String ss : Init.V_N) {
            Set<List<String>> ls = Init.Production.get(ss);
            for (List<String> s : ls)
                for (int i = 0; i < s.size(); i++)
                    if (Objects.equals(s.get(i), str) && i + 1 < s.size() && Init.V_T.contains(s.get(i + 1)))
                        setA.add(s.get(i + 1));
        }
        follow.put(str, setA);

        //处理str的每一条产生式
        for (List<String> ls : lists) {
            int i = ls.size() - 1;
            while (i >= 0) {
                String tn = ls.get(i);
                //只处理非终结符
                if (Init.V_N.contains(tn)) {
                    //都按 A->αBβ 形式处理
                    //若β不存在 followA加入followB
                    //若β存在 把β的非空first集加入followB
                    //若β存在且first(β)包含空串 followA加入followB

                    //若β存在
                    if (ls.size() - i - 1 > 0) {
                        List<String> right = ls.subList(i + 1, ls.size());
                        //非空first集 加入followB
                        Set<String> setF;
                        if (right.size() == 1) {
                            if (!First.first.containsKey(right.get(0))) First.addVNIntoFirst(right.get(0));
                            setF = First.first.get(right.get(0));
                        } else {
                            //先找出右部的first集
                            if (!firstX.containsKey(right)) getFirstX(right);
                            setF = firstX.get(right);
                        }
                        Set<String> setX = follow.containsKey(tn) ? follow.get(tn) : new HashSet<>();
                        for (String var : setF)
                            if (!Objects.equals(var, "$")) setX.add(var);
                        follow.put(tn, setX);

                        //若first(β)包含空串 followA加入followB
                        if (setF.contains("$")) {
                            if (!Objects.equals(tn, str)) {
                                Set<String> setB = follow.containsKey(tn) ? follow.get(tn) : new HashSet<>();
                                setB.addAll(setA);
                                follow.put(tn, setB);
                            }
                        }
                    }
                    //若β不存在 followA加入followB
                    else {
                        //A和B相同不添加
                        if (!Objects.equals(tn, str)) {
                            Set<String> setB = follow.containsKey(tn) ? follow.get(tn) : new HashSet<>();
                            setB.addAll(setA);
                            follow.put(tn, setB);
                        }
                    }
                }
                //如果是终结符 往前看
                i--;
            }
        }
    }
}
