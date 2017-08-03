package com.javarush.task.task21.task2113;

/**
 * Created by FILIP on 24.02.2017.
 */
public class Horse {
    private String name;
    private double speed;
    private double distance;

    public String getName() {
        return name;
    }

    public Horse(String name, double speed, double distance) {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void print(){
        for (int i = 0; i < (int) distance; i++)
        {
            System.out.print(".");
        }
        System.out.println(getName());
    }

    public void move(){
        distance += speed * Math.random();
    }
}
