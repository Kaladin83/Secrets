package com.example.maratbe.secrets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MARATBE on 12/14/2017.
 */

public class MyAccount extends NavigationPanel
{
    private TextView titleTxt;
    private LinearLayout lLayout;

    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.screen = 2;
        super.onCreate(savedInstanceState);

        //setFields();
    }

    public void setFields()
    {
        titleTxt = new TextView(this);
        titleTxt.setText("My Account");
        titleTxt.setTextSize(20);
        titleTxt.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lLayout = (LinearLayout) findViewById(R.id.main_line_layout);
        lLayout.addView(titleTxt);
    }
}
