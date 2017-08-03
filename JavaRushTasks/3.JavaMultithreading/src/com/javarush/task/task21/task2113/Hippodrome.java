package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FILIP on 24.02.2017.
 */
public class Hippodrome {
    private List<Horse> horses;

    static Hippodrome game;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {

        return horses;
    }

    public void run() throws InterruptedException {
        for(int i = 0; i < 100; i++) {
            move();
            print();
            Thread.sleep(200);
        }
    }
    public void print(){
        for (Horse h : getHorses())
        {
            h.print();
        }
        for (int i = 0; i < 10; i++)
            System.out.println();
    }
    public void move(){
        for (Horse h : getHorses())
        {
            h.move();
        }

    }

    public Horse getWinner()
    {
        double max = 0;
        Horse winner = getHorses().get(0);
        for (Horse h : getHorses())
        {
            if (h.getDistance() > max) {
                max = h.getDistance();
                winner = h;
            }
        }
        return winner;
    }

    public void printWinner()
    {
        System.out.println("Winner is " + getWinner().getName() + "!");
    }

    public static void main(String[] args) throws InterruptedException {
        game = new Hippodrome(new ArrayList<>());
        Horse a = new Horse("a", 3, 0);
        Horse b = new Horse("b", 3, 0);
        Horse c = new Horse("c", 3, 0);
        game.getHorses().add(a);
        game.getHorses().add(b);
        game.getHorses().add(c);
        game.run();
        game.printWinner();
    }
}
