package com.example.maratbe.secrets;

import android.graphics.drawable.Icon;
import android.media.Image;

/**
 * Created by MARATBE on 12/15/2017.
 */

public class Item
{
    private String user;
    private String text;
    private Image image;
    private int type;
    private enum TYPE{
        SECRET (0), THOUGHT(1);

        public int value;

        TYPE(int val) {
            value = val;
        }
    }

    public void addImage()
    {
        if (type == TYPE.SECRET.value)
        {

        }
    }
}
