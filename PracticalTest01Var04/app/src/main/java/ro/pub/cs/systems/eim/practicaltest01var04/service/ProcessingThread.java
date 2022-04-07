package ro.pub.cs.systems.eim.practicaltest01var04.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest01var04.R;
import ro.pub.cs.systems.eim.practicaltest01var04.general.Constants;

public class ProcessingThread extends Thread {

    private Context context;

    private String firsText, secondText;
    private Random random = new Random();

    private boolean isRunning = true;

    public ProcessingThread(Context context, String firsText, String secondText) {
        this.context = context;
        this.firsText = firsText;
        this.secondText = secondText;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.TAG, "Thread has stopped");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, firsText + " " +  secondText);
        context.sendBroadcast(intent);
    }


    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }


    public void stopThread() {
        isRunning = false;
    }
}
