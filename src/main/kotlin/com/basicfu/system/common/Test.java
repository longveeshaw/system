package com.basicfu.system.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuliang
 * @date 2018/1/15
 */
public class Test {
    static class Hi{
        public String a;
        public String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
    public static void main(String[] args) {
        List<Hi> list=new ArrayList<>();
        Hi hi = new Hi();
        hi.setA("bbb");
        list.add(hi);
        String[] strings = (String[]) list.stream().map((item) -> item.a).toArray();
        for (Object o : strings) {
            System.out.println(o);
        }
//        Map<String,String> map=new LinkedHashMap<>();
//        Properties properties=System.getProperties(); //系统属性
//        map.put("Java版本",properties.getProperty("java.version"));
//        map.put("Java供应商",properties.getProperty("java.vendor"));
//        map.put("Java安装路径",properties.getProperty("java.home"));
//        map.put("临时文件路径",properties.getProperty("java.io.tmpdir"));
//        map.put("系统名称",properties.getProperty("os.name"));
//        map.put("系统版本",properties.getProperty("os.version"));
//        map.put("系统构架",properties.getProperty("os.arch"));
//        map.put("系统用户名",properties.getProperty("user.name"));
//        map.put("系统用户主目录",properties.getProperty("user.home"));
//        map.put("系统用户语言",properties.getProperty("user.language"));
//        map.put("系统用户当前工作目录",properties.getProperty("user.dir"));
//        InetAddress localHost = null;
//        try {
//            localHost = InetAddress.getLocalHost();
//            map.put("主机名",localHost.getHostName());
//            map.put("IP地址",localHost.getHostAddress());
//        } catch (UnknownHostException e) {
//        }
//        map.keySet().forEach(e->{
//            System.out.println(e+":"+map.get(e));
//        });

    }
}