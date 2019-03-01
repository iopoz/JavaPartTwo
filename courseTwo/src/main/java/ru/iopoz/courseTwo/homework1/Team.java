package ru.iopoz.courseTwo.homework1;

import java.util.Random;

public class Team {
    public Animal[] animals;
    public Team() {
        Random rnd = new Random();
        this.animals = new Animal[]{
                new Cat(rnd.nextInt(100), rnd.nextInt(10)),
                new Dog(rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(5)),
                new Duck(rnd.nextInt(10), rnd.nextInt(1000))};
    }

    public void showResults(){
        for (Animal animal: this.animals) {
            if(animal.isOnDistance()){
                System.out.println(animal.getName() + " passed");
            } else {
                System.out.println(animal.getName() + " failed");
            }
        }
    }
}
