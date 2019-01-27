package notation.android.com.polishreversonotation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    /**
     * Fields of TextView for display input, reverse Polish notation
     * and result of calculating
     */
    private TextView mTextInput;
    private TextView mTextReverse;
    private TextView mTextResult;

    /**
     * Collection which records entered numbers and operators
     * then gives it to calculate
     */
    private LinkedList<String> mList;

    /**
     * This StringBuilder serves to write to itself single and
     * more numbers, then gives it to a collection that contains expressions
     */
    private StringBuilder mStringBuilder;

    /**
     * Overriding Android method. Used in this app
     * for initialization all fields in class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* allow only portrait orientation
           because I'm too lazy to override onSaveInstanceBundle  */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // initialization all fields in class
        mTextInput = findViewById(R.id.textInput);
        mTextReverse = findViewById(R.id.textReverse);
        mTextResult = findViewById(R.id.textResult);

        mList = new LinkedList<>();
        mStringBuilder = new StringBuilder();
    }


    /**
     * The method responds to the AC button. It clears
     * LinkedList with expression, StringBuilder for numbers
     * and sets null for TextView
     */
    public void onClearClick(View view) {
        while (!mList.isEmpty())
            mList.removeLast();

        mStringBuilder = new StringBuilder();

        mTextInput.setText(null);
        mTextReverse.setText(null);
        mTextResult.setText(null);
    }

    /**
     * The method is responsible for the response to pressing
     * the operation or ( ) button. It checks if pressed buttons for number
     * and writes them into the collection, if necessary, then it
     * gets pressed button text and record it to textView
     */
    public void onOperationClick(View view) {
        Button button = (Button) view;
        String pressedButton = button.getText().toString();

        /* check if there are unrecorded numbers in the collection
           and record it to collection if true */
        if (mStringBuilder.length() != 0)
            mList.add(mStringBuilder.toString());

        mTextInput.setText(getString(R.string.expression,
                mTextInput.getText(), pressedButton));
        mStringBuilder = new StringBuilder();
        mList.add(pressedButton);
    }

    /**
     * The method is responsible for the response to pressing
     * the number button.
     */
    public void onNumberClick(View view) {
        Button button = (Button) view;
        String pressedButton = button.getText().toString();

        mStringBuilder.append(pressedButton);

        mTextInput.setText(getString(R.string.expression,
                mTextInput.getText(), pressedButton));
    }

    /**
     * The main method that responds to the button =. It create object
     * of class Calculate, then gives the collection with the expression
     * into method transferIntoPolish, then calculate it and sets text into
     * textView. All calculation code is processed in try catch block.
     * The user will be notified if there is an error.
     */
    public void onResultClick(View view) {
        Calculate calc = new Calculate();

        /* check if there are unrecorded numbers in the collection
           and record it to collection if true */
        if (mStringBuilder.length() != 0)
            mList.add(mStringBuilder.toString());

        try {
            mList = calc.transferIntoPolish(mList);

            mTextReverse.setText(mList.toString()
                    .replaceAll("^\\[|]$", "")
                    .replace(',', Character.MIN_VALUE));

            mTextResult.setText(calc.calculateFromPolish(mList).
                    replaceAll("^\\[|]$", ""));
        } catch (Exception e) {
            e.printStackTrace();

            mTextReverse.setText(null);
            mTextResult.setText(R.string.error);
        }
    }
}
