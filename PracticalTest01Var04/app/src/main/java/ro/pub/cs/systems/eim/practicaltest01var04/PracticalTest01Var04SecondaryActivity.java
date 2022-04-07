package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;

import ro.pub.cs.systems.eim.practicaltest01var04.general.Constants;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    private EditText firstEditText, secondEditText;
    private Button okButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        firstEditText = (EditText) findViewById(R.id.firstEditText);
        secondEditText = (EditText) findViewById(R.id.secondEditText);

        okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(buttonClickListener);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.FIRST_EDIT_TEXT) && intent.getExtras().containsKey(Constants.SECOND_EDIT_TEXT)) {
            String firstText = intent.getStringExtra(Constants.FIRST_EDIT_TEXT);
            String secondText = intent.getStringExtra(Constants.SECOND_EDIT_TEXT);
            if (firstText != null) {
                firstEditText.setText(firstText);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }

            if (secondText != null) {
                secondEditText.setText(secondText);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }
}