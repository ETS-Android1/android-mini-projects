package edu.cuhk.csci3310.fabpasscode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*****************************************
 * Name: Yeung Man Wai
 * SID: 1155126854
 ****************************************/

public class MainActivity extends AppCompatActivity {
    private String mPasscode;
    private TextView mShowPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowPasscode = (TextView) findViewById(R.id.passcodeView);
    }

    public void updatePasscode(View view) {
        Button button = (Button) view;
        // get clicked button text
        String buttonText = button.getText().toString();
        // append button text to entered passcode
        String newPasscode = mShowPasscode.getText().toString() + buttonText;
        // update entered passcode
        mPasscode = newPasscode;
        mShowPasscode.setText(newPasscode);
    }

    public void unlock(View view) {
        String message = "Incorrect Passcode";
        // if passcode is correct
        if(mPasscode.contains("6854")){
            message = "Bingo!";
            // reveal the image
            ImageView hiddenBird = (ImageView) findViewById(R.id.hidden_bird);
            hiddenBird.setVisibility(View.VISIBLE);
            // disable all buttons
            View currentView = this.findViewById(android.R.id.content);
            ArrayList<View> layoutButtons = currentView.getTouchables();
            for(View v : layoutButtons){
                if( v instanceof Button ) {
                    ((Button)v).setEnabled(false);
                }
            }
        }
        // reset TextView
        mShowPasscode.setText("");
        // toast message
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
    }

}