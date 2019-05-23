package ru.iopoz.courseTwo.homework5;

public class MainTask {
    static final int listSize = 10000000;
    static final int h = listSize / 2;
    float[] arr = new float[listSize];

    public static void main(String[] args) throws InterruptedException {
        MainTask obj = new MainTask();
        obj.runOneThread();
        obj.runTwoThreads();
    }

    private float[] calcArray(float[] arr){
        for (int i = 0; i < arr.length; i ++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }



    private void runOneThread(){
        for(int i = 0; i < arr.length; i ++) arr[i] = 1.0f;
        long a = System.currentTimeMillis();
        calcArray(arr);
        calcExecutionTime(1, a);
    }

    private void runTwoThreads() throws InterruptedException {
        float[] firstArrHalf = new float[h];
        float[] secondArrHalf = new float[h];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, firstArrHalf, 0, h);
        System.arraycopy(arr, h, secondArrHalf, 0, h);

        Thread threadOne = new Thread(){
           public void execute(){
               float[] aOne = calcArray(firstArrHalf);
               System.arraycopy(aOne,0, firstArrHalf, 0, aOne.length);
           }
        };

        Thread threadTwo = new Thread(){
            public void execute(){
                float[] aTwo = calcArray(secondArrHalf);
                System.arraycopy(aTwo,0, secondArrHalf, 0, aTwo.length);
            }
        };

        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();

        System.arraycopy(firstArrHalf, 0, arr, 0, h);
        System.arraycopy(secondArrHalf, 0, arr, h, h);

        calcExecutionTime(2, a);
    }

    private void calcExecutionTime(int thread, long sTime){
        System.out.println(thread + " thread: "+ (System.currentTimeMillis()- sTime));
    }
}
