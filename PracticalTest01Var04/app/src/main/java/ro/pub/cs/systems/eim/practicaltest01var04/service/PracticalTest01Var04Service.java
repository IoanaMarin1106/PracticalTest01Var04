package ro.pub.cs.systems.eim.practicaltest01var04.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import ro.pub.cs.systems.eim.practicaltest01var04.general.Constants;

public class PracticalTest01Var04Service extends Service {

    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String firstText = intent.getStringExtra(Constants.FIRST_EDIT_TEXT);
        String secondText = intent.getStringExtra(Constants.SECOND_EDIT_TEXT);
        processingThread = new ProcessingThread(this, firstText, secondText);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }

}
