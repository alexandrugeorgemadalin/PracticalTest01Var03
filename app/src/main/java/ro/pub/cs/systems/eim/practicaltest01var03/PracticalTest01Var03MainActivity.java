package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    Button plusButton;
    Button minusButton;
    EditText firstEditText;
    EditText secondEditText;
    TextView resultTestView;
    Button navigateToSecondaryActivityButton;
    private final IntentFilter intentFilter = new IntentFilter();


    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private final NavigateButtonListener navigateButtonListener = new NavigateButtonListener();

    private class NavigateButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
            String result = resultTestView.getText().toString();
            intent.putExtra(Constants.RESULT, result);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }

    private void printToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startService(String firstNumber, String secondNumber) {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        intent.putExtra(Constants.FIRST_NUMBER, firstNumber);
        intent.putExtra(Constants.SECOND_NUMBER, secondNumber);
        getApplicationContext().startService(intent);
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String firstNumber = firstEditText.getText().toString();
            String secondNumber = secondEditText.getText().toString();

            // check if number is integer
            if (firstNumber.matches("[0-9]+") && secondNumber.matches("[0-9]+")) {
                int first = Integer.parseInt(firstEditText.getText().toString());
                int second = Integer.parseInt(secondEditText.getText().toString());
                int result;
                switch (view.getId()) {
                    case R.id.plus_button:
                        result = first + second;
                        resultTestView.setText(firstNumber + " + " + secondNumber + " = " + String.valueOf(result));
                        startService(firstNumber, secondNumber);
                        break;
                    case R.id.minus_button:
                        result = first - second;
                        resultTestView.setText(firstNumber + " - " + secondNumber + " = " + String.valueOf(result));
                        startService(firstNumber, secondNumber);
                        break;
                }

            } else {
                String message = "Error, please insert integers";
                printToast(message);
            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test03_var03_main);

        plusButton = (Button) findViewById(R.id.plus_button);
        minusButton = (Button) findViewById(R.id.minus_button);
        firstEditText = (EditText) findViewById(R.id.first_edit_text);
        secondEditText = (EditText) findViewById(R.id.second_edit_text);
        resultTestView = (TextView) findViewById(R.id.result_text_view);
        navigateToSecondaryActivityButton = (Button) findViewById(R.id.navigate_to_second_activity_button);

        plusButton.setOnClickListener(buttonClickListener);
        minusButton.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivityButton.setOnClickListener(navigateButtonListener);

        intentFilter.addAction(Constants.actionTypes[0]);
//        for (int index = 0; index < Constants.actionTypes.length; index++) {
//            intentFilter.addAction(Constants.actionTypes[index]);
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("firstEditText", firstEditText.getText().toString());
        savedInstanceState.putString("secondEditText", secondEditText.getText().toString());
        savedInstanceState.putString("resultTestView", resultTestView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("firstEditText")) {
            firstEditText.setText(savedInstanceState.getString("firstEditText"));
        } else {
            firstEditText.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("secondEditText")) {
            secondEditText.setText(savedInstanceState.getString("secondEditText"));
        } else {
            secondEditText.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("resultTestView")) {
            resultTestView.setText(savedInstanceState.getString("resultTestView"));
        } else {
            resultTestView.setText(String.valueOf(0));
        }
        printToast(savedInstanceState.getString("resultTestView"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result: " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
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

}