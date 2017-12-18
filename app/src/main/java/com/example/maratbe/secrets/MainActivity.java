package com.example.maratbe.secrets;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends NavigationPanel implements Constants
{
    private int titleHeight, scrollHeight;
    private TextView secretTxt, thoughtTxt;
    private HorizontalScrollView secretSV, thoughtsSV;
    private RelativeLayout rLayout;
    private int id;
    private RelativeLayout.LayoutParams lp;

    public static int screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        screen = 1;
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        titleHeight = ((NavigationPanel.getScreenHeight() - 70) / 2)/8;
        scrollHeight = ((NavigationPanel.getScreenHeight() - 70) / 2) - (int)(titleHeight*3);
        Log.d("Log", "Activity Width: " +  NavigationPanel.getScreenWidth());
        setFields();
    }



    public void setFields()
    {
        rLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);

        setRules(getToolbar().getId(),RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW, (int)(titleHeight*1.25));
        createTitle("Top Secrets", ID_SECRET_TXT, lp);
        setRules(ID_SECRET_TXT, RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW, scrollHeight);
        createScrollView(ID_SECRET_SCROLL);
        setRules(secretSV.getId(), RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW, (int)(titleHeight*1.25));
        createTitle("Top Thoughts", ID_THOUGHTS_TXT, lp);
        setRules(thoughtTxt.getId(), RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW, scrollHeight);
        createScrollView(ID_THOUGHTS_SCROLL);
        //setRules(thoughtsSV.getId(), RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW, true);
    }

    public void createScrollView(int id)
    {
        Button button;
        HorizontalScrollView hScroll = new HorizontalScrollView(this);
        hScroll.setId(id);
        LinearLayout linscrollview = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                scrollHeight);
        linscrollview.setLayoutParams(params);
        linscrollview.setPadding(0,0,0,40);
        linscrollview.setBackgroundColor(ContextCompat.getColor(this, R.color.transperant_blue));

        for (int i = 0; i < 10; i++)
        {
            params = new RelativeLayout.LayoutParams(
                   600, RelativeLayout.LayoutParams.MATCH_PARENT);
            button = new Button(this);
            button.setText("screen Height " + getScreenHeight()+ "\nnew title Height " + titleHeight+ "\nnew scroll Height " + scrollHeight+ "\ntext size in PT" + secretTxt.getTextSize() + "\ntext size " + (secretTxt.getTextSize()+ secretTxt.getTextSize()*0.25) + "\nlinscrollview size" + linscrollview.getMeasuredHeight()+ "\n");
            button.setTextSize(TEXT_SIZE);
            button.setTextColor(TEXT_COLOR);
            button.setAllCaps(false);
            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            button.setBackground(createBorder());
            button.setGravity(Gravity.CENTER);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vw) {
                    ShowItem item = new ShowItem(MainActivity.this);
                    item.show();
                }
            });

            button.setPadding(20,10,20,10);
            button.setId(i);
            params.setMargins(10,20,10,20);
            button.setLayoutParams(params);
            linscrollview.addView(button);
        }
        hScroll.addView(linscrollview);
        updateField(id, hScroll);
        rLayout.addView(hScroll, lp);
    }

    private Drawable createBorder()
    {
        GradientDrawable gd = new GradientDrawable();
        getRandomNUmber();
        gd.setColor(getRandomNUmber()); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(20);
        gd.setStroke(2, Color.DKGRAY);
        return gd;
    }

    private int getRandomNUmber()
    {
        Random rand = new Random();
        return getRandomColor(rand.nextInt(10));
    }

    public void setRules(int id_, int rule1, int rule2, int height)
    {
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                height);
        lp.addRule(rule1);
        lp.addRule(rule2, id_);
    }

    public void createTitle(String txt, int id, RelativeLayout.LayoutParams lp)
    {
        TextView txtView = new TextView(this);
        txtView.setText(txt);
        txtView.setTextSize(TITLE_SIZE);
        txtView.setTextColor(TITLE_COLOR);
        txtView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //txtView.setBackgroundColor(Color.argb(250,240,200,70));
        txtView.setPadding(0,40,0,0);
        txtView.setId(id);
        updateField(id, txtView);
        rLayout.addView(txtView, lp);
    }

    private void updateField(int id, Object obj)
    {
        switch (id)
        {
            case ID_SECRET_TXT:
                secretTxt = (TextView) obj;    break;
            case ID_THOUGHTS_TXT:
                thoughtTxt = (TextView) obj;   break;
            case ID_SECRET_SCROLL:
                secretSV = (HorizontalScrollView) obj;   break;
            default:
                thoughtsSV = (HorizontalScrollView) obj; break;
        }
    }
}
