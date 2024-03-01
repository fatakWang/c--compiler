package Syntax_Anlayzer;

import java.io.*;
import java.util.*;

public class Init {
    /*
     * 这个类是语法分析步骤的开始，用于读入LL1文法，
     * 从LL1文法中得到终结符集合V_T、非终结符集合V_N、
     * 以及对应的产生式Production
     * */

    //终结符集合
    public static Set<String> V_T = new HashSet<>();
    //非终结符集合
    public static Set<String> V_N = new HashSet<>();
    //产生式
    public static Map<String, Set<List<String>>> Production = new HashMap<>();

    //对一个从文件读出来的单个产生式行，分隔左右两侧，再去除空格得到左右两端符号，并且对多余的空格具有鲁棒性
    public void divideToLeftRight(String str, List<String> Left, List<String> Right) {
        String[] tmp = str.split("->");

        //理论上产生式左侧只能有一个符号,也这么处理是为了避免多余的空格
        String[] leftArr = tmp[0].split(" ");
        for (String a : leftArr)
            if (!a.equals("")) Left.add(a);

        String[] rightArr = tmp[1].split(" ");
        for (String a : rightArr)
            if (!a.equals("")) Right.add(a);
    }

    //B=B-A，在集合B中删除所有A中有的元素
    public void removeAFromB(Set A, Set B) {
        for (Object a : A) {
            B.remove(a);
        }
    }

    private Init(String file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                //将从文件中得到的产生式分隔成左右两边
                List<String> left = new ArrayList<>();
                List<String> right = new ArrayList<>();
                divideToLeftRight(line, left, right);

                //将左边作为非终结符、右边作为一个列表添加到产生式map里面
                if (!Production.containsKey(left.get(0))) Production.put(left.get(0), new HashSet<>());
                Production.get(left.get(0)).add(right);

                //左边肯定是非终结符，直接添加进去，右边也不判断了，全加到终结符里面，最后做一个集合的减法即可
                V_N.add(left.get(0));
                V_T.addAll(right);
            }

            //V_T=V_T-V_N
            removeAFromB(V_N, V_T);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Init init = null;

    public static Init getInstance(String file) {
        if (init == null) {
            init = new Init(file);
        }
        return init;
    }

    public void convertFile(String file, String target) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedWriter out = new BufferedWriter(new FileWriter(target));
            String line;
            while ((line = in.readLine()) != null) {
                String[] s = line.split("->");

                String[] leftArr = s[0].split(" ");
                String left = null;
                for (String a : leftArr)
                    if (!a.equals("")) left = a;

                List<String> Right = new ArrayList<>();
                String[] rightArr = s[1].split(" ");
                for (int i = 0; i < rightArr.length; i++) {
                    String a = rightArr[i];
                    if (!a.equals("")) {
                        if (!a.equals("|")) Right.add(a);
                        if (a.equals("|") || i == rightArr.length - 1) {
                            StringBuilder res = new StringBuilder();
                            res.append(left).append(" ->");
                            for (String r : Right) {
                                res.append(" ").append(r);
                            }
                            out.write(res + "\r\n");
                            out.flush();
                            Right.clear();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
