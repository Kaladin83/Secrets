package com.example.maratbe.secrets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MARATBE on 12/16/2017.
 */

public class ShowItem extends Dialog implements View.OnClickListener, View.OnTouchListener, Constants
{
    private Button likeBtn, commentsBtn, shareBtn, commentBtn, putLikeBtn, sendCommentBtn;
    private TextView numOfCommentsTxt, numOfLikesTxt;
    private EditText addCommentEdit;
    private RelativeLayout.LayoutParams params;
    private RelativeLayout mainLayout, statusLayout, editLayout ;
    private int buttonWidth, buttonHeight;
    private static int numOfEnterClicked = 1;

    public Activity activity;

    public ShowItem() {
        super(null);
    }
    public ShowItem(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item);

        mainLayout = findViewById(R.id.ItemDialog);
        mainLayout.setLayoutParams(new FrameLayout.LayoutParams(
                NavigationPanel.getScreenWidth() - 100, NavigationPanel.getScreenHeight() - 80));
        buttonWidth = mainLayout.getLayoutParams().width /12;
        buttonHeight = mainLayout.getLayoutParams().height/ 18;
        setTagsLayout();
        setTextView();
        setStatusLayout();
        setEditLayout((int)(buttonHeight*1.25),"");
    }

    private void setTagsLayout()
    {
        setRules(-100, RelativeLayout.CENTER_HORIZONTAL, mainLayout.getLayoutParams().width, buttonHeight);
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setId(ID_TAG_LAYOUT);
        tagLayout.setGravity(Gravity.CENTER);
        for (int i = 0; i < 3; i++)
        {
            Button btn = new Button(getContext());
            btn.setText("Tag "+(i+1));
            btn.setPadding(10,10,10,10);
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            tagLayout.addView(btn);
        }

        mainLayout.addView(tagLayout,params);
    }

    private void setTextView()
    {
        setRules(ID_TAG_LAYOUT, RelativeLayout.BELOW, mainLayout.getLayoutParams().width, mainLayout.getLayoutParams().height/3);

        TextView itemVeiw = new TextView(getContext());
        itemVeiw.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperant_blue), 1));
        itemVeiw.setId(ID_ITEM_TXT);
        itemVeiw.setTextSize(TEXT_SIZE);
        itemVeiw.setPadding(40,20,40,20);
        itemVeiw.setMovementMethod(new ScrollingMovementMethod());
        itemVeiw.setText("bla bla bla\nIt's going to be a great app\ntrust me!!!\nbla bla\nbli bli\nwhat's up\nwho am I?\nI'm a goose\nNot realy...\nwhen I'll stop?\nstop already!!\ngood lord!\nbla bla bla again");
        mainLayout.setPadding(0,50,0,0);
        mainLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.top_1), 1));
        mainLayout.addView(itemVeiw, params);
        mainLayout.setOnTouchListener(this);
        mainLayout.requestFocus();
    }

    public void setStatusLayout()
    {
        Toast.makeText(this.getContext(),"new secret = " +buttonWidth, Toast.LENGTH_SHORT).show();
        setRules(ID_ITEM_TXT, RelativeLayout.BELOW, mainLayout.getLayoutParams().width, (int)(buttonHeight*1.7));
        statusLayout = new RelativeLayout(getContext());
        statusLayout.setPadding(20,30,20,10);
        statusLayout.setId(ID_STATUS_LAYOUT);
        statusLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 1));
        mainLayout.addView(statusLayout, params);

        setRules(-100, RelativeLayout.ALIGN_LEFT, buttonWidth, buttonHeight);
        likeBtn = new Button(getContext());
        likeBtn.setId(ID_LIKE_BTN);
        likeBtn.setBackgroundResource(R.drawable.heart);
        likeBtn.setOnClickListener(this);
        statusLayout.addView(likeBtn, params);

        setRules(ID_LIKE_BTN , RelativeLayout.RIGHT_OF, (int) (buttonWidth*1.25), buttonHeight);
        numOfLikesTxt = new TextView(getContext());
        numOfLikesTxt.setId(ID_NUM_OF_LIKES_TXT);
        numOfLikesTxt.setText("136K");
        numOfLikesTxt.setTextSize(TEXT_SIZE);
        numOfLikesTxt.setTextColor(Color.BLACK);
        numOfLikesTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 0));
        statusLayout.addView(numOfLikesTxt, params);

        setRules(ID_NUM_OF_LIKES_TXT , RelativeLayout.RIGHT_OF, (int) (buttonWidth * 0.5), buttonHeight);
        View emptyView = new View(getContext());
        emptyView.setId(ID_EMPTY_VIEW);
        statusLayout.addView(emptyView, params);

        setRules(ID_EMPTY_VIEW , RelativeLayout.RIGHT_OF, buttonWidth, buttonHeight);
        commentsBtn = new Button(getContext());
        commentsBtn.setBackgroundResource(R.drawable.comments2);
        commentsBtn.setId(ID_COMMENTS_BTN);
        commentsBtn.setOnClickListener(this);
        statusLayout.addView(commentsBtn, params);

        setRules(ID_COMMENTS_BTN , RelativeLayout.RIGHT_OF, (int) (buttonWidth*1.25), buttonHeight);
        numOfCommentsTxt = new TextView(getContext());
        numOfCommentsTxt.setText("166");
        numOfCommentsTxt.setTextSize(TEXT_SIZE);
        numOfCommentsTxt.setTextColor(Color.BLACK);
        numOfCommentsTxt.setId(ID_NUM_OF_COMMENTS_TXT);
        numOfCommentsTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 0));
        statusLayout.addView(numOfCommentsTxt, params);

        setRules(-100, RelativeLayout.ALIGN_PARENT_RIGHT, buttonWidth, buttonHeight);
        commentBtn = new Button(getContext());
        commentBtn.setId(ID_PUT_COMMENT_BTN);
        commentBtn.setOnClickListener(this);
        commentBtn.setBackgroundResource(R.drawable.add_comment);
        statusLayout.addView(commentBtn, params);

        setRules(ID_PUT_COMMENT_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
        shareBtn = new Button(getContext());
        shareBtn.setId(ID_SHARE_BTN);
        shareBtn.setOnClickListener(this);
        shareBtn.setBackgroundResource(R.drawable.share);
        statusLayout.addView(shareBtn, params);

        setRules(ID_SHARE_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
        putLikeBtn = new Button(getContext());
        putLikeBtn.setId(ID_PUT_LIKE_BTN);
        putLikeBtn.setOnClickListener(this);
        putLikeBtn.setBackgroundResource(R.drawable.like);
        statusLayout.addView(putLikeBtn, params);
    }

    private void setEditLayout(int editSize, String text)
    {

        if (numOfEnterClicked > 1)
        {
            Toast.makeText(this.getContext(),"Saved text = "+text+" Size = "+editSize, Toast.LENGTH_SHORT).show();
        }
        editLayout = new RelativeLayout(getContext());
        editLayout.setVisibility(View.GONE);

        setRules(-100, RelativeLayout.ALIGN_LEFT, (int) (mainLayout.getLayoutParams().width * 0.85), editSize);
        addCommentEdit = new EditText(getContext());
        addCommentEdit.setId(ID_ADD_COMMENT_EDIT);
        addCommentEdit.setTextSize(TEXT_SIZE);
        addCommentEdit.setText(text);
        addCommentEdit.setPadding(30,10,30,10);
        addCommentEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        addCommentEdit.setMovementMethod(new ScrollingMovementMethod());
        /*addCommentEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    numOfEnterClicked++;
                    TextView txtView = (TextView) view;

                    //setEditLayout((int)(buttonHeight*1.25)*numOfEnterClicked, txtView.getText().toString());
                    return true;
                }
                return false;
            }
        });*/
        addCommentEdit.setBackground(createBorder(ContextCompat.getColor(getContext(), R.color.top_3), 1));
        addCommentEdit.invalidate();
        editLayout.addView(addCommentEdit, params);
        editLayout.invalidate();

       // m.showSoftInput(addCommentEdit, InputMethodManager.RESULT_UNCHANGED_HIDDEN);
        setRules(ID_ADD_COMMENT_EDIT, RelativeLayout.RIGHT_OF, (int) (mainLayout.getLayoutParams().width * 0.15),(int)(buttonHeight*1.25));
        sendCommentBtn = new Button(getContext());
        sendCommentBtn.setId(ID_SEND_COMMENT_BTN);
        sendCommentBtn.setOnClickListener(this);
        sendCommentBtn.setBackgroundResource(R.drawable.download);
        editLayout.addView(sendCommentBtn, params);

        setRules(ID_STATUS_LAYOUT , RelativeLayout.BELOW, mainLayout.getLayoutParams().width, mainLayout.getLayoutParams().height/7);
        mainLayout.addView(editLayout,params);
    }

    public void setRules(int id_, int rule, int width, int hight)
    {
        params = new RelativeLayout.LayoutParams(width, hight);
        if (id_ != -100)
        {
            params.addRule(rule, id_);
        }
        else
        {
            params.addRule(rule);
        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    private Drawable createBorder(int color,int radius)
    {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(radius);
        if (radius > 0)
        {
            gd.setStroke(1, Color.DKGRAY);
        }
        return gd;
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case ID_PUT_COMMENT_BTN:
                if (editLayout.getVisibility() == View.VISIBLE)
                {
                    editLayout.setVisibility(View.GONE);
                }
                else
                {
                    editLayout.setVisibility(View.VISIBLE);
                }
            default:
                break;
        }
        //dismiss();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null)
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
