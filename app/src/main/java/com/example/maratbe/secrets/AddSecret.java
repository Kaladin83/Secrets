package com.example.maratbe.secrets;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by MARATBE on 12/14/2017.
 */

public class AddSecret extends NavigationPanel
{
    private TextView titleTxt;
    private RelativeLayout rLayout;

    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.screen = 3;
        super.onCreate(savedInstanceState);

        setFields();
    }

    public void setFields()
    {
        titleTxt = new TextView(this);
        titleTxt.setText("Add your secret");;
        titleTxt.setTextSize(20);
        titleTxt.setTextColor(Color.BLUE);
        titleTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleTxt.setPadding(0,40,0,0);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.addRule(RelativeLayout.BELOW,RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.BELOW, getToolbar().getId());
        rLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);
        rLayout.addView(titleTxt, lp);
    }
}
