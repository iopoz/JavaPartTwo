package ru.iopoz.courseTwo.homework1;

public class Water extends Obstacle {
    protected Water(int size) {
        super(size);
    }

    @Override
    public void doIt(Animal a) {
        if (a instanceof Swimable) {
            ((Swimable) a).swim(size);
        } else {
            a.setOnDistance(false);
        }
    }
}
