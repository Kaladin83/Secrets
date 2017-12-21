package com.example.maratbe.secrets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
    private Item passedItem;

    private Activity activity;

    public ShowItem() {
        super(null);
    }

    public ShowItem(Activity activity, Item item) {
        super(activity);
        this.activity = activity;
        passedItem = item;
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
        String txt;
        setRules(-100, RelativeLayout.CENTER_HORIZONTAL, mainLayout.getLayoutParams().width, buttonHeight);
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setId(ID_TAG_LAYOUT);
        tagLayout.setGravity(Gravity.CENTER);
        for (int i = 0; i < 3; i++)
        {
            if (passedItem.getArrayOfTags()[i] != null)
            {
                txt = passedItem.getArrayOfTags()[i];
                Button btn = new Button(getContext());
                btn.setText(txt);
                btn.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 20));
                btn.setPadding(0,0,0,0);
                btn.setAllCaps(false);
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(txt.length()*TEXT_SIZE*2,(int)(buttonHeight*0.7));
                p.setMargins(10,0,10,10);
                btn.setLayoutParams(p);

                tagLayout.addView(btn);
            }
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
        itemVeiw.setText(passedItem.getText());
        mainLayout.setPadding(0,50,0,0);
        mainLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.top_1), 1));
        mainLayout.addView(itemVeiw, params);
        mainLayout.setOnTouchListener(this);
        mainLayout.requestFocus();
    }

    private void setStatusLayout()
    {
       // Toast.makeText(this.getContext(),"new secret = " +buttonWidth, Toast.LENGTH_SHORT).show();
        setRules(ID_ITEM_TXT, RelativeLayout.BELOW, mainLayout.getLayoutParams().width, (int)(buttonHeight*1.7));
        statusLayout = new RelativeLayout(getContext());
        statusLayout.setPadding(20,30,20,10);
        statusLayout.setId(ID_STATUS_LAYOUT);
        statusLayout.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 1));
        mainLayout.addView(statusLayout, params);

        setRules(-100, RelativeLayout.ALIGN_LEFT, buttonWidth, buttonHeight);
        likeBtn = new Button(getContext());
        likeBtn.setId(ID_LIKE_BTN);
        likeBtn.setBackgroundResource(R.drawable.heart2);
        likeBtn.setOnClickListener(this);
        statusLayout.addView(likeBtn, params);

        String txt = passedItem.getNumOfLikes()+"";
        setRules(ID_LIKE_BTN , RelativeLayout.RIGHT_OF, txt.length()*TEXT_SIZE*2, buttonHeight);
        numOfLikesTxt = new TextView(getContext());
        numOfLikesTxt.setId(ID_NUM_OF_LIKES_TXT);
        numOfLikesTxt.setText(txt);
        numOfLikesTxt.setTextSize(TEXT_SIZE);
        numOfLikesTxt.setTextColor(Color.BLACK);
        numOfLikesTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 0));
        statusLayout.addView(numOfLikesTxt, params);

        setRules(ID_NUM_OF_LIKES_TXT , RelativeLayout.RIGHT_OF, buttonWidth, buttonHeight);
        commentsBtn = new Button(getContext());
        commentsBtn.setBackgroundResource(R.drawable.comments3);
        commentsBtn.setId(ID_COMMENTS_BTN);
        commentsBtn.setOnClickListener(this);
        params.setMargins(10,0,0,0);
        statusLayout.addView(commentsBtn, params);

        txt = passedItem.getNumOfComments()+"";
        setRules(ID_COMMENTS_BTN , RelativeLayout.RIGHT_OF, txt.length()*TEXT_SIZE*2, buttonHeight);
        numOfCommentsTxt = new TextView(getContext());
        numOfCommentsTxt.setText(txt);
        numOfCommentsTxt.setTextSize(TEXT_SIZE);
        numOfCommentsTxt.setTextColor(Color.BLACK);
        numOfCommentsTxt.setId(ID_NUM_OF_COMMENTS_TXT);
        numOfCommentsTxt.setBackground(createBorder(ContextCompat.getColor(getContext(),R.color.transperent_green), 0));
        statusLayout.addView(numOfCommentsTxt, params);

        setRules(-100, RelativeLayout.ALIGN_PARENT_RIGHT, buttonWidth, buttonHeight);
        shareBtn = new Button(getContext());
        shareBtn.setId(ID_SHARE_BTN);
        shareBtn.setOnClickListener(this);

       // shareBtn.setG
       // shareBtn = (ImageView) findViewById(R.id.imageview);
        params.setMargins(10,0,0,0);
        statusLayout.addView(shareBtn, params);

        setRules(ID_SHARE_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
        commentBtn = new Button(getContext());
        commentBtn.setId(ID_PUT_COMMENT_BTN);
        commentBtn.setOnClickListener(this);
        commentBtn.setBackgroundResource(R.drawable.add_comment);
        params.setMargins(10,0,0,0);
        statusLayout.addView(commentBtn, params);

        setRules(ID_PUT_COMMENT_BTN , RelativeLayout.LEFT_OF, buttonWidth, buttonHeight);
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

        setRules(ID_ADD_COMMENT_EDIT, RelativeLayout.RIGHT_OF, (int) (mainLayout.getLayoutParams().width * 0.15),(int)(buttonHeight*1.25));
        sendCommentBtn = new Button(getContext());
        sendCommentBtn.setId(ID_SEND_COMMENT_BTN);
        sendCommentBtn.setOnClickListener(this);
        sendCommentBtn.setBackgroundResource(R.drawable.download);
        editLayout.addView(sendCommentBtn, params);

        setRules(ID_STATUS_LAYOUT , RelativeLayout.BELOW, mainLayout.getLayoutParams().width, mainLayout.getLayoutParams().height/7);
        mainLayout.addView(editLayout,params);
    }

    private void setRules(int id_, int rule, int width, int hight)
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
        if (radius > 0)
        {
            gd.setStroke(1, Color.DKGRAY);
        }
        return gd;
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
