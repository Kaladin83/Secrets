package com.example.maratbe.secrets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class ShowItem extends Dialog implements View.OnClickListener, Constants
{
    private Button likeBtn, commentsBtn, shareBtn, commentBtn, putLikeBtn, sendCommentBtn;
    private TextView numOfCommentsTxt, numOfLikesTxt;
    private EditText addCommentEdit;
    private RelativeLayout.LayoutParams params;
    private RelativeLayout mainLayout, statusLayout, editLayout ;
    private int buttonWidth, buttonHeight;

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
        setEditLayout();
    }

    private void setTagsLayout()
    {
        setRules(-100, RelativeLayout.CENTER_HORIZONTAL, mainLayout.getLayoutParams().width, buttonHeight);
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setId(TAG_LAYOUT);
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
        setRules(TAG_LAYOUT, RelativeLayout.BELOW, mainLayout.getLayoutParams().width, mainLayout.getLayoutParams().height/3);

        TextView itemVeiw = new TextView(getContext());
        itemVeiw.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperant_blue), 1));
        itemVeiw.setId(ITEM_TXT);
        itemVeiw.setPadding(40,20,40,20);
        itemVeiw.setMovementMethod(new ScrollingMovementMethod());
        itemVeiw.setText("bla bla bla\nIt's going to be a great app\ntrust me!!!\nbla bla\nbli bli\nwhat's up\nwho am I?\nI'm a goose\nNot realy...\nwhen I'll stop?\nstop already!!\ngood lord!\nbla bla bla again");
        mainLayout.setPadding(0,50,0,0);
        mainLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 1));
        mainLayout.addView(itemVeiw, params);
    }

    public void setStatusLayout()
    {
        Toast.makeText(this.getContext(),"new secret = " +buttonWidth, Toast.LENGTH_SHORT).show();
        setRules(ITEM_TXT, RelativeLayout.BELOW, mainLayout.getLayoutParams().width, (int)(buttonHeight*1.7));
        statusLayout = new RelativeLayout(getContext());
        statusLayout.setPadding(20,30,20,10);
        statusLayout.setId(STATUS_LAYOUT);
        statusLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 1));
        mainLayout.addView(statusLayout, params);

        setRules(-100, RelativeLayout.ALIGN_LEFT, buttonWidth, buttonHeight);
        likeBtn = new Button(getContext());
        likeBtn.setId(LIKE_BTN);
        likeBtn.setBackgroundResource(R.drawable.heart);
        likeBtn.setOnClickListener(this);
        statusLayout.addView(likeBtn, params);

        setRules(LIKE_BTN , RelativeLayout.RIGHT_OF, buttonWidth, buttonHeight);
        numOfLikesTxt = new TextView(getContext());
        numOfLikesTxt.setId(NUM_OF_LIKES_TXT);
        numOfLikesTxt.setText("  ");
        numOfLikesTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperant_blue), 20));
        statusLayout.addView(numOfLikesTxt, params);

        setRules(NUM_OF_LIKES_TXT , RelativeLayout.RIGHT_OF, (int) (buttonWidth * 0.5), buttonHeight);
        View emptyView = new View(getContext());
        emptyView.setId(EMPTY_VIEW);
        statusLayout.addView(emptyView, params);

        setRules(EMPTY_VIEW , RelativeLayout.RIGHT_OF, buttonWidth, buttonHeight);
        commentsBtn = new Button(getContext());
        commentsBtn.setBackgroundResource(R.drawable.comments2);
        commentsBtn.setId(COMMENTS_BTN);
        commentsBtn.setOnClickListener(this);
        statusLayout.addView(commentsBtn, params);

        setRules(COMMENTS_BTN , RelativeLayout.RIGHT_OF, buttonWidth, buttonHeight);
        numOfCommentsTxt = new TextView(getContext());
        numOfCommentsTxt.setText("  ");
        numOfCommentsTxt.setId(NUM_OF_COMMENTS_TXT);
        numOfCommentsTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperant_blue), 20));
        statusLayout.addView(numOfCommentsTxt, params);

        setRules(-100, RelativeLayout.ALIGN_PARENT_RIGHT, buttonWidth, buttonHeight);
        commentBtn = new Button(getContext());
        commentBtn.setId(PUT_COMMENT_BTN);
        commentBtn.setOnClickListener(this);
        commentBtn.setBackgroundResource(R.drawable.add_comment);
        statusLayout.addView(commentBtn, params);

        setRules(PUT_COMMENT_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
        shareBtn = new Button(getContext());
        shareBtn.setId(SHARE_BTN);
        shareBtn.setOnClickListener(this);
        shareBtn.setBackgroundResource(R.drawable.share);
        statusLayout.addView(shareBtn, params);

        setRules(SHARE_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
        putLikeBtn = new Button(getContext());
        putLikeBtn.setId(PUT_LIKE_BTN);
        putLikeBtn.setOnClickListener(this);
        putLikeBtn.setBackgroundResource(R.drawable.like);
        statusLayout.addView(putLikeBtn, params);
    }

    private void setEditLayout()
    {

        editLayout = new RelativeLayout(getContext());
        editLayout.setVisibility(View.GONE);

        setRules(-100, RelativeLayout.ALIGN_LEFT, (int) (mainLayout.getLayoutParams().width * 0.85), mainLayout.getLayoutParams().height/7);
        addCommentEdit = new EditText(getContext());
        addCommentEdit.setId(ADD_COMMENT_EDIT);
        //params.setMargins(0,20,0,0);
        addCommentEdit.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.top_3), 1));
        editLayout.addView(addCommentEdit, params);

        setRules(ADD_COMMENT_EDIT, RelativeLayout.RIGHT_OF, (int) (mainLayout.getLayoutParams().width * 0.15),buttonHeight);
        sendCommentBtn = new Button(getContext());
        sendCommentBtn.setId(SEND_COMMENT_BTN);
        sendCommentBtn.setOnClickListener(this);
        sendCommentBtn.setBackgroundResource(R.drawable.download);
        editLayout.addView(sendCommentBtn, params);

        setRules(STATUS_LAYOUT , RelativeLayout.BELOW, mainLayout.getLayoutParams().width, mainLayout.getLayoutParams().height/7);
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

    private Drawable createBorder(int color,int radius)
    {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(radius);
        gd.setStroke(1, Color.DKGRAY);
        return gd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case PUT_COMMENT_BTN:
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
}
