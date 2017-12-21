package com.example.maratbe.secrets;

import android.graphics.drawable.Icon;
import android.media.Image;

import java.util.ArrayList;

/**
 * Created by MARATBE on 12/15/2017.
 */

public class Item
{
    private String user;
    private String text;
    private char type;
    private int itemId;
    private int date;
    private int rating;
    private int numOfLikes;
    private int numOfComments;
    private ArrayList<String> comments = new ArrayList<>();
    private String[] arrayOfTags;

    public Item(String user, String text, char type, int itemId, int date, int rating, int numOfLikes, int numOfComments, String[] tags)
    {
        setUser(user);
        setText(text);
        setType(type);
        setItemId(itemId);
        setDate(date);
        setRating(rating);
        setNumOfLikes(numOfLikes);
        setNumOfComments(numOfComments);
        setArrayOfTags(tags);
    }

    public Item()
    {

    }

    public void setUser(String user) {

        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void setArrayOfTags(String[] arrayOfTags) {
        this.arrayOfTags = arrayOfTags;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public char getType() {
        return type;
    }

    public int getItemId() {
        return itemId;
    }

    public int getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public int getNumOfLikes() {

        return numOfLikes;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public String[] getArrayOfTags() {
        return arrayOfTags;
    }

}
