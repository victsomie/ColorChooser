package edu.rosehulman.boutell.colorchooser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //make constants to carry the message and color
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_COLOR = "EXTRA_COLOR";
    private static final int REQUEST_CODE_INPUT = 1; //This helps identetify the specific code being passed into this activity

    private RelativeLayout mLayout;
    private TextView mTextView;
    private String mMessage = "This is your phone. Please rescue me!";
    private int mBackgroundColor = Color.GREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //This fab is the floating action in the main page
        //Inside here I set it to help send mails using different clients
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send an email with the message field as the body
                //Write/call a method called send email here (ensure you have that method)
                sendEmail();


            }
        });
        // Capture
        mLayout = (RelativeLayout) findViewById(R.id.content_main_layout);
        mTextView = (TextView) findViewById(R.id.content_main_message);

        // Set color and text
        updateUI();
    }

    //This is the sendEmail method
    private void sendEmail(){
        //Create intent here to call a method to send email
        Intent emailIntent  = new Intent(Intent.ACTION_SENDTO); //This calls an inbuilt method
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "xyx@gmail.com"); //This defines where to send the message. Make sure the @ is included in the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From me");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mMessage);
        if(emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_info:
                // TODO: Launch a new Info Activity that is a ScrollingActivity.
                Intent infoIntent = new Intent(this, InfoActivity.class);
                startActivity(infoIntent);
                return true;

            case R.id.action_change_color:
                // TODO: Launch the InputActivity to get a result

                //Create intent for the action when the element is pressed
                //use putExtra()

                Intent inputIntent = new Intent(this, InputActivity.class);
                inputIntent.putExtra(EXTRA_MESSAGE, mMessage);
                inputIntent.putExtra(EXTRA_COLOR,mBackgroundColor);
                //startActivity(inputIntent);//Change this startActivityForResult
                   //This requires a "request code" to distinguish between different activities it could call
                startActivityForResult(inputIntent, REQUEST_CODE_INPUT); //Note this takes a
                    // second parameter to help differentiate which activity finish

                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Creating the callback for the startActivityForResult here
    //When you call startActivityForResult stub the result here


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Here you can check for the conditions of the request code i.e if it is the one we are creating
        if (requestCode == REQUEST_CODE_INPUT && resultCode == Activity.RESULT_OK){
            //Here we check if the request code is equal to the one we created and
            // if the result code  is equal to the one in the input activity

            //If it is so it assign the mMessage and mBackground Color to
            // the new one selected in the input by using data.gertStringExtra and data.getIntExtra
            //Then update the UI using updateUI()

            mMessage = data.getStringExtra(MainActivity.EXTRA_MESSAGE);
            mBackgroundColor = data.getIntExtra(MainActivity.EXTRA_COLOR,Color.GRAY);
            updateUI();//This is the line that update the UI after it finishes
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI() {
        mTextView.setText(mMessage);
        mLayout.setBackgroundColor(mBackgroundColor);
    }


}
