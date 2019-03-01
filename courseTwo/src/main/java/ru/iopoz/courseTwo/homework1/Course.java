package ru.iopoz.courseTwo.homework1;

import java.util.Random;

public class Course {
    private Random rnd = new Random();
    private Obstacle[] obstacleArray;
    
    public Course() {
        this.obstacleArray = new Obstacle[]{
                new Road(rnd.nextInt(30)),
                new Water(rnd.nextInt(30)),
                new Wall(rnd.nextInt(2))};
    }
    
    public void doIt(Team team){
        for (Obstacle obstacle: obstacleArray) {
            for (Animal animal: team.animals) {
                obstacle.doIt(animal);
            }
            
        }
    }
}
