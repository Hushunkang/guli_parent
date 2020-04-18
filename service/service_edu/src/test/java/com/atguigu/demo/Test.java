package com.atguigu.demo;

public class Test {

    public static void main(String[] args) {
        try {
            if (true)
                throw new RuntimeException("run wrong");
            System.out.println("after throw");
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        finally {
            System.out.println("over");
        }

        try {
            double result1 = test01();//拿不到返回值
//            double result2 = test02();//拿不到返回值
            System.out.println(result1);
//            System.out.println(result2);
            System.out.println("注释掉和不注释掉double result2 = test02();，看看能不能执行到此处！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double test01() {
        double x = 0;
        try {
            System.out.println("test01 ex before");
            x = 1 / 0;
            System.out.println("test01 ex after");
        } catch (Exception e) {
            e.printStackTrace();
            //没有在catch代码块里面手动的throw异常对象
            //相当于处理了try代码块里面的异常，吃掉了异常
            //方法的调用者是不知道这个方法原本有异常的，即调用了这个方法的方法认为这个方法是个正常的方法
            //然后调用者还是正常的往下面执行代码，test02方法与此刚好相反！！！
        } finally {
            System.out.println("test01 finally");
        }
        System.out.println("test01???");

        return x;
    }

    private static double test02() throws Exception {
        double y;
        try {
            System.out.println("test02 ex before");
            y = 1 / 0;
            System.out.println("test02 ex before");
        } catch (Exception e) {
            throw new Exception();
        } finally {
            System.out.println("test02 finally");
        }
        System.out.println("test02???");

        return y;
    }

}
