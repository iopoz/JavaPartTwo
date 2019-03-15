package ru.iopoz.courseTwo.homework2;

import java.util.Random;

public class ArrayDigitalStrings {
    private int size;
    private String[][] strArray = new String[4][4];

    public ArrayDigitalStrings(int size){
        this.size = size;
        if (size > 4) throw new MySizeArrayException("Incorrect array size");
    }

    public void generateArray(boolean flag){
        for(int i=0; i < size; i++){
            if (flag) {
                for (int j = 0; j < size; j++) {
                    strArray[i][j] = getArrayElement(j+1);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    int mul = (i + j) * (j + 1);
                    strArray[i][j] = String.valueOf(mul);
                }
            }
        }
    }

    private String getArrayElement(int len){
        Random rnd = new Random();
        String dict = "abcxyz1234567890";
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( dict.charAt( rnd.nextInt(dict.length())));
        return sb.toString();
    }

    public int getElementsSum(){
        int sum = 0;
        for (int i=0; i < strArray.length; i++){
            for (int j=0; j < strArray.length; j++){
                try{
                    sum += Integer.parseInt(strArray[i][j]);
                } catch (NumberFormatException e){
                    sum = 0;
                    break;
                }
            }
        }
        return sum;
    }
}
