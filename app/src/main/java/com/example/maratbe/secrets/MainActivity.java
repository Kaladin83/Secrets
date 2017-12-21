package com.example.maratbe.secrets;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends NavigationPanel implements Constants, View.OnClickListener
{
    private int titleHeight, scrollHeight, panelHeight, objectWidth;
    private TextView secretTxt, thoughtTxt, likesTxt, NumOfLikesTxt, commentsTxt,numOfCommentsTxt;
    private HorizontalScrollView secretSV, thoughtsSV;
    private RelativeLayout scrollRLayout, panelRLayout;
    LinearLayout objectRLayout;
    private int id;
    private RelativeLayout.LayoutParams lp;
    public int passedItem;

    public static int screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        screen = 1;
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        titleHeight = ((NavigationPanel.getScreenHeight() - 70) / 2)/8;
        scrollHeight = ((NavigationPanel.getScreenHeight() - 70) / 2) - (int)(titleHeight*3);
        panelHeight = scrollHeight/6;
        objectWidth = 550;
        Database db = new Database();
        //db.insertIntoStatisticsBunch();
        //db.insertIntoTagsBunch();
        db.selectTopTenData();
        setFields();
    }

    @Override
    public void onClick(View view)
    {
        for (int i = 0; i < 20; i++)
        {
            if (view.getId() == BUTTONS[i])
            {
                ShowItem item = new ShowItem(MainActivity.this, getArrayOfItems().get(i));
                item.show();
            }

        }
    }

    public void setFields()
    {
        scrollRLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);

        setRules(getToolbar().getId(),RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW,
                RelativeLayout.LayoutParams.MATCH_PARENT,(int)(titleHeight*1.25));
        createTitle("Top Secrets", ID_SECRET_TXT, lp);
        setRules(ID_SECRET_TXT, RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW,
                RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeight);
        createScrollView(ID_SECRET_SCROLL, SECRET_START_VALUE, lp);
        setRules(secretSV.getId(), RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW,
                RelativeLayout.LayoutParams.MATCH_PARENT, (int)(titleHeight*1.25));
        createTitle("Top Thoughts", ID_THOUGHTS_TXT, lp);
        setRules(thoughtTxt.getId(), RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.BELOW,
                RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeight);
        createScrollView(ID_THOUGHTS_SCROLL, THOUGHT_START_VALUE, lp);
    }

    public void createScrollView(int id, int type, RelativeLayout.LayoutParams scrollParams)
    {
        Button button;
        int i = 0;
        HorizontalScrollView hScroll = new HorizontalScrollView(this);
        hScroll.setId(id);
        LinearLayout objectsLLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams lineLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                       scrollHeight);
        objectsLLayout.setLayoutParams(lineLayoutParams);
        objectsLLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transperant_blue));
        for ( i = type; i < 10 + type ; i++)
        {
            objectRLayout = new LinearLayout(this);
            lineLayoutParams = new RelativeLayout.LayoutParams(objectWidth, scrollHeight - 65);
            lineLayoutParams.setMargins(10,10,10,20);
            objectRLayout.setOrientation(LinearLayout.VERTICAL);
            objectRLayout.setLayoutParams(lineLayoutParams);
            objectRLayout.setBackground(createBorder(20));

            panelRLayout = new RelativeLayout(this);
            panelRLayout.setLayoutParams(new RelativeLayout.LayoutParams(objectWidth, panelHeight ));
            panelRLayout.setBackground(createBorder(objectRLayout.getDrawingCacheBackgroundColor(), 1, 0));
            panelRLayout.setPadding(10,0,10,0);
            lineLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeight - panelHeight - 60);
            button = new Button(this);
            button.setText(getArrayOfItems().get(i).getText());
            button.setTextSize(TEXT_SIZE);
            button.setTextColor(TEXT_COLOR);
            button.setBackground(objectRLayout.getBackground());
            button.setAllCaps(false);
            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            button.setBackground(createBorder(objectRLayout.getDrawingCacheBackgroundColor(), 1, 0));
            button.setGravity(Gravity.CENTER);
            button.setOnClickListener(this);

            button.setPadding(20,10,20,10);
            button.setId(i);
            button.setLayoutParams(lineLayoutParams);

            setRules(-100,RelativeLayout.ALIGN_LEFT, (int) (panelHeight/1.5), panelHeight-20);
            createTxtVeiw(R.drawable.heart, "",ID_LIKE_TXT_TOP, lp);
            String txt = String.valueOf(getArrayOfItems().get(i).getNumOfLikes());
            setRules( ID_LIKE_TXT_TOP, RelativeLayout.RIGHT_OF, txt.length()*TEXT_SIZE*2,(panelHeight-10));
            createTxtVeiw(-100, txt, ID_NUM_OF_LIKES_TXT_TOP, lp);
            setRules(ID_NUM_OF_LIKES_TXT_TOP, RelativeLayout.RIGHT_OF, (int) (panelHeight/2),(panelHeight - 5));
            createTxtVeiw(-100, "",ID_EMPTY_TXT, lp);
            setRules(ID_EMPTY_TXT, RelativeLayout.RIGHT_OF, (int) (panelHeight/1.5),(panelHeight-25));
            createTxtVeiw(R.drawable.comments2, "",ID_COMMENTS_TXT_TOP, lp);
            txt = String.valueOf(getArrayOfItems().get(i).getNumOfComments());
            setRules(ID_COMMENTS_TXT_TOP , RelativeLayout.RIGHT_OF, txt.length()*TEXT_SIZE*2, (panelHeight-5));
            createTxtVeiw(-100, txt,ID_NUM_OF_COMMENTS_TXT_TOP, lp);
            setRules(-100, RelativeLayout.ALIGN_PARENT_RIGHT, (int) (panelHeight/1.5),(panelHeight-15));
            createTxtVeiw(R.drawable.star, String.valueOf(getArrayOfItems().get(i).getRating()),ID_RATING_TXT, lp);

            View separater = new View(this);
            separater.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2));
            separater.setBackground(createBorder(objectRLayout.getDrawingCacheBackgroundColor(), 2, 1));
            objectRLayout.addView(button);
            objectRLayout.addView(separater);
            objectRLayout.addView(panelRLayout);
            objectsLLayout.addView(objectRLayout);
        }

        hScroll.addView(objectsLLayout);
        updateField(id, hScroll);
        scrollRLayout.addView(hScroll, scrollParams);
    }

    private Drawable createBorder(int radius)
    {
        GradientDrawable gd = new GradientDrawable();
        getRandomNUmber();
        gd.setColor(getRandomNUmber()); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(radius);
        gd.setStroke(2, Color.DKGRAY);
        return gd;
    }

    private Drawable createBorder(int color,int radius, int stroke)
    {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(radius);
        if (radius > 0)
        {
            gd.setStroke(stroke, Color.DKGRAY);
        }
        return gd;
    }

    private int getRandomNUmber()
    {
        Random rand = new Random();
        return getRandomColor(rand.nextInt(10));
    }

    public void setRules(int id_, int rule1, int rule2, int width, int height)
    {
        lp = new RelativeLayout.LayoutParams(width, height);
        lp.addRule(rule1);
        lp.addRule(rule2, id_);
    }

    public void setRules(int id_, int rule, int width, int height)
    {
        lp = new RelativeLayout.LayoutParams(width, height);
        if (id_ != -100)
        {
            lp.addRule(rule, id_);
        }
        else
        {
            lp.addRule(rule);
        }
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
        scrollRLayout.addView(txtView, lp);
    }

    public void createTxtVeiw(int icon, String txt, int id, RelativeLayout.LayoutParams  lp)
    {
        TextView txtView = new TextView(this);
        txtView.setText(txt);
        txtView.setTextSize(TEXT_SIZE);
        txtView.setTextColor(Color.BLACK);
        txtView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txtView.setBackgroundColor(objectRLayout.getDrawingCacheBackgroundColor());
        if (icon > -100)
        {
            //removeBackground(icon);
            txtView.setBackgroundResource(icon);
            txtView.setBackgroundColor(Color.TRANSPARENT);
        }
        //txtView.setBackgroundColor(Color.argb(250,240,200,70));
       // txtView.setPadding(20,0,0,0);
        txtView.setId(id);
       // updateField(id, txtView);
        panelRLayout.addView(txtView, lp);
    }

   /* private void removeBackground(int icon)
    {
        mColorCode = Color.parseColor(strColorCode);
//Get the image to be changed from the drawable, drawable-xhdpi, drawable-hdpi,etc folder.
        Drawable sourceDrawable = getResources().getDrawable(icon, getTheme());
//Convert drawable in to bitmap
        Bitmap sourceBitmap = Util.(sourceDrawable);
//Pass the bitmap and color code to change the icon color dynamically.
        mFinalBitmap = Util.changeImageColor(sourceBitmap, mColorCode);
        imgIcon.setImageBitmap(mFinalBitmap);
    }*/

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
