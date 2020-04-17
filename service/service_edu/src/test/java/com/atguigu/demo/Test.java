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
            double result2 = test02();//拿不到返回值
            System.out.println(result1);
            System.out.println(result2);
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
