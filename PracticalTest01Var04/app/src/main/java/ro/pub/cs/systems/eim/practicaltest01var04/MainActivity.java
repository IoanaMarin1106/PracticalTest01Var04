package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.CloseGuard;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01var04.general.Constants;
import ro.pub.cs.systems.eim.practicaltest01var04.service.PracticalTest01Var04Service;

public class MainActivity extends AppCompatActivity {

    private Button navigateToSecondaryActivityButton, displayInformationButton;
    private TextView messageTextView;
    private EditText firstEditText, secondEditText;
    private CheckBox firstCheckBox, secondCheckBox;

    private int serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.navigateToSecondaryActivityButton:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                    intent.putExtra(Constants.FIRST_EDIT_TEXT, firstEditText.getText().toString());
                    intent.putExtra(Constants.SECOND_EDIT_TEXT, secondEditText.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQ_CODE);
                    break;
                case R.id.displayInformationButton:

                    if (firstCheckBox.isChecked() == true && secondCheckBox.isChecked() == true) {
                        if (firstEditText.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Eroare - primul edit text nu e completat", Toast.LENGTH_SHORT).show();
                        } else {
                            messageTextView.setText(messageTextView.getText().toString() + " "  + firstEditText.getText().toString() + " ");
                        }
                    }

                    if (secondCheckBox.isChecked() == true) {
                        if (secondEditText.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Eroare - al doilea edit text nu e completat", Toast.LENGTH_LONG).show();
                        } else {
                            messageTextView.setText(messageTextView.getText().toString() + " " + secondEditText.getText().toString());
                        }
                    }

                    if ((firstEditText.getText().toString().equals("") == false) && (secondEditText.getText().toString().equals("") == false)) {
                        Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                        intent2.putExtra(Constants.FIRST_EDIT_TEXT, firstEditText.getText().toString());
                        intent2.putExtra(Constants.SECOND_EDIT_TEXT, secondEditText.getText().toString());
                        getApplicationContext().startService(intent2);
                        serviceStatus = Constants.SERVICE_STARTED;
                    }
                    break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigateToSecondaryActivityButton = (Button) findViewById(R.id.navigateToSecondaryActivityButton);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        displayInformationButton = (Button) findViewById(R.id.displayInformationButton);
        displayInformationButton.setOnClickListener(buttonClickListener);

        messageTextView = (TextView) findViewById(R.id.messageTextView);
        firstEditText = (EditText) findViewById(R.id.firstEditText);
        secondEditText = (EditText) findViewById(R.id.secondEditText);
        firstCheckBox = (CheckBox) findViewById(R.id.firstCheckBox);
        secondCheckBox = (CheckBox) findViewById(R.id.secondCheckBox);

        if (savedInstanceState != null) {
            // a avut loc o stare anterioara, deci activitatea trebuie reluata
            if (savedInstanceState.containsKey(Constants.FIRST_EDIT_TEXT)) {
                firstEditText.setText(savedInstanceState.getString(Constants.FIRST_EDIT_TEXT));
            } else {
                firstEditText.setText("");
            }

            if (savedInstanceState.containsKey(Constants.SECOND_EDIT_TEXT)) {
                secondEditText.setText(savedInstanceState.getString(Constants.SECOND_EDIT_TEXT));
            } else {
                secondEditText.setText("");
            }

        } else {
           firstEditText.setText("");
           secondEditText.setText("");
        }

        messageBroadcastReceiver = new MessageBroadcastReceiver();

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.FIRST_EDIT_TEXT, firstEditText.getText().toString());
        outState.putString(Constants.SECOND_EDIT_TEXT,secondEditText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.FIRST_EDIT_TEXT)) {
            firstEditText.setText(savedInstanceState.getString(Constants.FIRST_EDIT_TEXT));
        } else {
            firstEditText.setText("");
        }

        if (savedInstanceState.containsKey(Constants.SECOND_EDIT_TEXT)) {
            secondEditText.setText(savedInstanceState.getString(Constants.SECOND_EDIT_TEXT));
        } else {
            secondEditText.setText("");
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQ_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        super.onDestroy();
    }
}