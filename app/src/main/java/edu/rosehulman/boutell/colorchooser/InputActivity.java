package edu.rosehulman.boutell.colorchooser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class InputActivity extends AppCompatActivity {


    // make constants for reference
    public static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static String EXTRA_COLOR = "EXTRA_COLOR";

    private RelativeLayout mLayout;
    private EditText mEditText;
    private int mCurrentBackgroundColor;
    private String mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mLayout = (RelativeLayout) findViewById(R.id.activity_input_layout);
        mEditText = (EditText) findViewById(R.id.activity_input_message);

        //Create intent here
        Intent intent = getIntent();

        //mMessage = "Hello World"; //Change this string to be changed by the intent
        mMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //mCurrentBackgroundColor = Color.GRAY; //Change this to be changed by the intent action
             //Note that it is reference by a Integer
        mCurrentBackgroundColor = intent.getIntExtra(MainActivity.EXTRA_COLOR, Color.GRAY);
        updateUI();

        Button colorButton = (Button) findViewById(R.id.activity_input_button);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    private void updateUI() {
        mEditText.setText(mMessage);
        mLayout.setBackgroundColor(mCurrentBackgroundColor);
    }

    // From https://android-arsenal.com/details/1/1693

    private void showColorDialog() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose HSV color")
                .initialColor(mCurrentBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(6)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(InputActivity.this, "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(getString(android.R.string.ok), new ColorPickerClickListener() {
                    //listens to the color that will be selected
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        mCurrentBackgroundColor = selectedColor; //Changes the mCurrentBackgroundColor to the one selected here
                        mMessage = mEditText.getText().toString(); //Assigns the mMessage to the new string that will be entered here
                        updateUI();
						// TODO: Use an intent to send info back to activity that called this one for a result.


                        //====Create an intent and tell the intent that putExtra was normal
                           // but was expectedand ended with a good result=====
                        //Create intent that will help send information back to activity that called for a result
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.EXTRA_MESSAGE, mMessage); //Passes the message we wrote
                        intent.putExtra(MainActivity.EXTRA_COLOR, mCurrentBackgroundColor); //Passes the color selected

                        //set the result here that wil let you pass the result code
                        //It pass TWO PARAMETERS one to identify that its part of the activity call
                        // and the other to identify intent holding the data
                        setResult(Activity.RESULT_OK, intent);

                        //With the extra set and the result set then the activity is ready to finish
                        finish();

                        //After finishing it goes to the MainActivity




                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), null)
                .build()
                .show();
    }
}
