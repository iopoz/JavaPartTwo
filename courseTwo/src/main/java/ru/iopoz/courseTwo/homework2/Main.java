package ru.iopoz.courseTwo.homework2;

public class Main {
    public static void main(String[] args) {
        ArrayDigitalStrings array = new ArrayDigitalStrings(4);
        array.generateArray(false);
        System.out.println(String.valueOf(array.getElementsSum()));
        array.generateArray(true);
        //if (array.getElementsSum() == 0 ) throw new MyArrayDataException("incorrect Value");
        ArrayDigitalStrings arrayTwo = new ArrayDigitalStrings(5);

    }
}
