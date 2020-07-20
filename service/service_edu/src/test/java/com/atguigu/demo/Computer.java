package com.atguigu.demo;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年06月29日
 */
public class Computer {

    private String name;
    private String brand;

    public Computer() {
        System.out.println("创建Computer对象。。。无参的构造器被调用了");
    }

    public Computer(String name, String brand) {
        System.out.println("有参的构造器被调用了。。。");
        this.name = name;
        this.brand = brand;
    }

    protected void init() {
        System.out.println("Init方法被调用... ...");
    }

    protected void destroy() {
        System.out.println("Destroy方法被调用... ...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Computer [name="+name+",brand="+ brand +"]";
    }

}
