package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    Button correctButton;
    Button incorrectButton;
    TextView resultSumTextView;

    private ButtonListener buttonListener = new ButtonListener();

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.correct_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.incorrect_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        correctButton = (Button) findViewById(R.id.correct_button);
        incorrectButton = (Button) findViewById(R.id.incorrect_button);
        resultSumTextView = (TextView) findViewById(R.id.sumResult_text_view);

        correctButton.setOnClickListener(buttonListener);
        incorrectButton.setOnClickListener(buttonListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.RESULT)) {
            String result = intent.getStringExtra(Constants.RESULT);
            resultSumTextView.setText(result);
        }

    }
}