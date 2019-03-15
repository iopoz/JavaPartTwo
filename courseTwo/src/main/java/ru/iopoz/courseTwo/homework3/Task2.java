package ru.iopoz.courseTwo.homework3;

public class Task2 {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", 123456789);
        phoneBook.add("Petrov", 123456789);
        phoneBook.add("Ivanov", 987654321);

        System.out.println(phoneBook.get("Ivanov"));
    }
}
