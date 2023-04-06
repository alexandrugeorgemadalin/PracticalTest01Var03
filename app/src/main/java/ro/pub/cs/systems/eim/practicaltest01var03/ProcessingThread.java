package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Process;
import java.util.Date;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private int first;
    private int second;
    private int sum;
    private int difference;

    public ProcessingThread(Context context, int first, int second) {
        this.context = context;
        this.first = first;
        this.second = second;
        this.sum = first + second;
        this.difference = first - second;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sleep();
            sendMessage(0, this.sum);
            sleep();
            sendMessage(1, this.difference);
        }
    }

    // type 0 = sum
    // type 1 = difference
    private void sendMessage(int type, int result) {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[type]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + String.valueOf(result));
        context.sendBroadcast(intent);
    }


    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
