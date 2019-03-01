package ru.iopoz.courseTwo.homework1;

public class Main {
    public static void main(String[] args) {
        Course c = new Course();
        Team team = new Team();
        c.doIt(team);
        team.showResults();
    }

}
