package com.example.maratbe.secrets;

/**
 * Created by MARATBE on 12/19/2017.
 */

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

//import com.mysql.jdbc.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Database  {
    public String ip = "sql11.freesqldatabase.com:3306";
    public String classs = "com.mysql.jdbc.Driver";
    public String dbName = "sql11211228";
    public String userName = "sql11211228";
    public String password = "3N8weuq9PZ";
    private  StringBuffer query;
    private PreparedStatement stmt = null;
    private Connection conn = null;

    @SuppressLint("NewApi")

    public Database()
    {
        connect();
    }
    public void connect() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:mysql://" + ip + "/"
                    +dbName+ "?useUnicode=true&characterEncoding=UTF-8" ;
            //ConnURL = "jdbc:mysql://" + ip + "/"+dbName;
            conn = DriverManager.getConnection(ConnURL, userName, password);
        } catch ( SQLException se) {
            Log.e("ERROR", se.getErrorCode()+": "+se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            //SQLException | ClassNotFoundException |
        }
    }
    public int insertUser(String password, int date, char type, String user)
    {
        int returnCode = 0;
        query = new StringBuffer();

        createSelectUserCheckQuery(user);
        if (fetchData("fetchCheckUserData") ==  0)
        {
            createInsertUserQuery(password, date, type, user);
            returnCode = insertData(query);
        }

        return returnCode;
    }

    public int insertTags(String tagName, int tagId, int itemId)
    {
        query = new StringBuffer();

        createInsertTagsQuery(tagName, tagId, itemId);
        return insertData(query);
    }
    
    public int insertLike(int itemId)
    {
        query = new StringBuffer();

        createInsertLikeStatQuery(itemId);
        return insertData(query);
    }


    public int insertComment(int itemId, String text, int parentId)
    {
        int returnCode = 0;
        query = new StringBuffer();

        createInsertCommentQuery(itemId, text, parentId);
        if (insertData(query) == 0)
        {
            createInsertCommentStatQuery(itemId);
            returnCode = insertData(query);
        }

        return returnCode;
    }

    public int selectTopTenData()
    {
        int returnCode;
        query = new StringBuffer();

        createSelectTopTenQuery();
        returnCode = fetchData("fetchTopTenData");

        return returnCode;
    }

    public int selectTagsData(Item item)
    {
        int returnCode;
        query = new StringBuffer();

        createSelectTagsQuery(item);
        returnCode = fetchData("fetchTagsData", item);

        return returnCode;
    }

    private int selectstatisticsData(Item item)
    {
        int returnCode;
        query = new StringBuffer();

        createSelectStatisticsQuery(item);
        returnCode = fetchData("fetchStatisticsData", item);

        return returnCode;
    }

    private void createInsertUserQuery(String password, int date, char type, String user)
    {
        query.append("INSERT INTO users (user_name, password, type, registration_date) VALUES('");
        query.append(user);
        query.append("', '");
        query.append(password);
        query.append("', '");
        query.append(type);
        query.append("', ");
        query.append(date);
        query.append(");");
    }

    private void createInsertTagsQuery(String tagName, int tagId, int itemId)
    {
        query.append("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES(");
        query.append(tagName);
        query.append("', ");
        query.append(tagId);
        query.append(", ");
        query.append(itemId);
        query.append(");");
    }

    private void createInsertCommentQuery(int itemId, String text, int parentId)
    {
        query.append("INSERT INTO comments (text, itemId, parentId) VALUES(");
        query.append(text);
        query.append("', ");
        query.append(itemId);
        query.append(", ");
        query.append(parentId);
        query.append(");");
    }

    private void createInsertLikeStatQuery(int itemId)
    {
        query.append("INSERT INTO statistics (item_id, likes, comments) VALUES(");
        query.append(itemId);
        query.append(", 1, 0) ON DUPLICATE KEY UPDATE ");
        query.append("likes = likes + 1;");
    }

    private void createInsertCommentStatQuery(int itemId)
    {
        query.append("INSERT INTO statistics (item_id, likes, comments) VALUES(");
        query.append(itemId);
        query.append(", 0, 1) ON DUPLICATE KEY UPDATE ");
        query.append("comments = comments + 1;");
    }

    private void createUpdateStatQuery(String itemId)
    {
        query.append("UPDATE statistics ");
        query.append("SET comments = comments + 1 ");
        query.append("WHERE item_id = '");
        query.append(itemId);
        query.append("';");
    }

    private void createSelectUserCheckQuery(String user)
    {
        query.append("SELECT 1 FROM users");
        query.append("WHERE user_name = '");
        query.append(user);
        query.append("';");
    }

    private void createSelectTopTenQuery()
    {
        query.append("SELECT item.*, IFNULL(tag_name,'') tag_name, IFNULL(likes,0) likes, IFNULL(comments,0) comments ");
        query.append("FROM item left JOIN tags_and_items tags ON ");
        query.append("(item.item_id = tags.item_id) left JOIN ");
        query.append("statistics stat ON ");
        query.append("(item.item_id = stat.item_id) ");
        query.append("WHERE rating <= 10 order by type, rating;");
    }

    /*private void createSelectTopTenQuery()
    {
        query.append("SELECT * FROM item");
        query.append(" WHERE rating <= 10 order by type, rating");
    }*/

    private void createSelectTagsQuery(Item item)
    {
        query.append("SELECT tag_name FROM tags_and_items");
        query.append("WHERE itemId = ");
        query.append(item.getItemId());
        query.append(";");
    }

    private void createSelectStatisticsQuery(Item item)
    {
        query.append("SELECT likes, comments FROM statistics");
        query.append("WHERE itemId = ");
        query.append(item.getItemId());
        query.append(";");
    }

    private int insertData(StringBuffer whatToInsert)
    {
        int rc = 0;

        try
        {
            stmt = conn.prepareStatement(query.toString());
            stmt.executeUpdate();
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getMessage();
            try
            {
                conn.close();
            }catch (SQLException se)
            {
                Log.e("ERROR", "The Fetch action did not succeed.\nThe reason is: " +se.getErrorCode()+": "+se.getMessage());
            }
            rc = -1;
        }
        return rc;
    }

    private int fetchData(String whatToQuery)
    {
        int rc;

        try
        {
            stmt = conn.prepareStatement(query.toString());
            ResultSet rset = stmt.executeQuery();

            switch (whatToQuery)
            {
                case "fetchTopTenData":
                    rc = fetchTopTenData(rset); break;
                case "fetchCheckUserData":
                    rc = fetchUserCheckData(rset); break;
               default:
                   rc = 0;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getMessage();
            try
            {
                conn.close();
            }catch (SQLException se)
            {
                Log.e("ERROR", "The Fetch action did not succeed.\nThe reason is: " +se.getErrorCode()+": "+se.getMessage());
            }
            rc = -1;
        }
        return rc;
   /*     finally
        {
            try
            {
                 conn.close();
            }catch (SQLException e)
            {
                Log.e("ERROR", "The Fetch action did not succeed.\nThe reason is: " +e.getErrorCode()+": "+e.getMessage());
                rc =  -1;
            }
            return rc;
        }*/

    }

    private int fetchData(String whatToQuery, Item item)
    {
        int rc;

        try
        {
            stmt = conn.prepareStatement(query.toString());
            ResultSet rset = stmt.executeQuery();

            switch (whatToQuery)
            {
                case "fetchTagsData":
                    rc = fetchTagsData(rset, item); break;
                case "fetchStatisticsData":
                    rc = fetchStatisticsData(rset, item); break;
                default:
                    rc = 0;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getMessage();
            try
            {
                conn.close();
            }catch (SQLException se)
            {
                Log.e("ERROR", "The Fetch action did not succeed.\nThe reason is: " +se.getErrorCode()+": "+se.getMessage());
            }
            rc = -1;
        }
        return rc;

    }

    private int fetchUserCheckData(ResultSet rset) throws SQLException
    {
        int exists = 0;
        while (rset.next())
        {
            exists = 1;
        }
        return exists;
    }

    private int fetchTagsData(ResultSet rset, Item item) throws SQLException
    {
        int rc = -1, index = 0;

        while (rset.next())
        {
            item.getArrayOfTags()[index] = rset.getString("tag_name");
            rc = 0;
            index ++;
        }
        return rc;
    }

    private int fetchStatisticsData(ResultSet rset, Item item) throws SQLException
    {
        int rc = -1, index = 0;

        while (rset.next())
        {
            item.setNumOfLikes(Integer.parseInt(rset.getString("likes")));
            item.setNumOfComments(Integer.parseInt(rset.getString("comments")));
            rc = 0;
            index ++;
        }
        return rc;
    }

    private int fetchTopTenData(ResultSet rset) throws SQLException
    {
        String user, text;
        char type;
        int itemId, prevItemId = 0, date, rating, comments, likes, arrayInx = 0;
        int rc = -1, index = 0;
        String[] tagsArray = new String[3];
        Item item = new Item();

        while (rset.next())
        {
            itemId = Integer.parseInt(rset.getString("item_id"));
            user = rset.getString("user_name");
            text = rset.getString("text");
            type = rset.getString("type").charAt(0);
            date = rset.getInt("date");
            rating = rset.getInt("rating");
            comments = rset.getInt("comments");
            likes = rset.getInt("likes");
            //tagsArray[arrayInx] = rset.getString("tag_name");

            if (NavigationPanel.getArrayOfItems().size() > 17)
            {
                Log.d("Items :", "***************************** HERE ****************************************");
            }

            if ((itemId != prevItemId && index > 0) )
            {

                NavigationPanel.setArrayOfItems(item);
                int itemNum = (NavigationPanel.getArrayOfItems().size() - 1);
                Log.d("Items ["+itemNum+"] :", "User Name = "+NavigationPanel.getArrayOfItems().get(itemNum).getUser());
                Log.d("Items ["+itemNum+"] :", "Item Id = "+NavigationPanel.getArrayOfItems().get(itemNum).getItemId());
                Log.d("Items ["+itemNum+"] :", "Type = "+NavigationPanel.getArrayOfItems().get(itemNum).getType());
                Log.d("Items ["+itemNum+"] :", "Text = "+NavigationPanel.getArrayOfItems().get(itemNum).getText());
                Log.d("Items ["+itemNum+"] :", "Num Of Comments = "+NavigationPanel.getArrayOfItems().get(itemNum).getNumOfComments());
                Log.d("Items ["+itemNum+"] :", "Num of Likes = "+NavigationPanel.getArrayOfItems().get(itemNum).getNumOfLikes());
                Log.d("Items ["+itemNum+"] :", "Date = "+NavigationPanel.getArrayOfItems().get(itemNum).getDate());
                Log.d("Items ["+itemNum+"] :", "Rating = "+NavigationPanel.getArrayOfItems().get(itemNum).getRating());
                for (int j = 0; j < arrayInx+1; j++)
                {
                    Log.d("Items ["+itemNum+"] :", "Tag ("+j+") = "+NavigationPanel.getArrayOfItems().get(itemNum).getArrayOfTags()[j]);
                }
                arrayInx = 0;
                tagsArray = new String[3];
                // tagsArray[arrayInx] = rset.getString("tag_name");
                tagsArray[arrayInx] = rset.getString("tag_name");
                item = new Item(user, text, type, prevItemId, date, rating, likes, comments, tagsArray);
                if (rset.isLast())
                {
                    NavigationPanel.setArrayOfItems(item);
                }

               // fillUpFields(prevItemId, arrayInx, tagsArray, rset);

                arrayInx = 0;
            }
            else
            {
               // fillUpFields(prevItemId, arrayInx, tagsArray, rset);
                tagsArray[arrayInx] = rset.getString("tag_name");
                item = new Item(user, text, type, prevItemId, date, rating, likes, comments, tagsArray);
                arrayInx++;

            }


            prevItemId = itemId;
         //   selectTagsData(item);
         //   selectStatisticsData(item);
            index ++;
            rc = 0;
        }
        return rc;
    }

    private void fillUpFields(int prevItemId, int arrayInx, String[] tagsArray, ResultSet rset) throws SQLException
    {
        String user = "", text = "";
        char type = ' ';
        int date = 0, rating = 0, comments = 0, likes = 0;
        Item item;



        item = new Item(user, text, type, prevItemId, date, rating, likes, comments, tagsArray);
        NavigationPanel.setArrayOfItems(item);
    }


    /*private int fetchTopTenData(ResultSet rset) throws SQLException
    {
        String user = "", text = "";
        char type = ' ';
        int itemId = 0, prevItemId = 0, date = 0, rating = 0, comments = 0, likes = 0, rc = -1, index = 0, arrayInx = 0;
        String[] tagsArray = new String[3];
        Item item;

        while (rset.next())
        {
            user = rset.getString("user_name");
            itemId = Integer.parseInt(rset.getString("item_id"));
            text = rset.getString("text");
            type = rset.getString("type").charAt(0);
            date = Integer.parseInt(rset.getString("date"));
            rating = Integer.parseInt(rset.getString("rating"));

            item = new Item(user, text, type, itemId, date, rating, likes, comments, tagsArray);


            index ++;
            rc = 0;
        }
        return rc;
    }*/


    public void insertIntoStatisticsBunch()
    {
        query = new StringBuffer("delete from statistics;");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(13, 21, 1)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(1, 50, 2)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(2, 150, 55)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(3, 73, 7)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(4, 12, 1)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(5, 7, 9)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(6, 130, 66)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(7, 13, 4)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(8, 133, 12)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(9, 35, 8)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(10, 55, 32)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(11, 43, 22)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(12, 89, 0)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(14, 43, 7)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(15, 250, 25)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(16, 44, 3)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(17, 65, 9)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(18, 81, 30)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(19, 29, 0)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(20, 78, 8)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(21, 37, 13)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(22, 68, 22)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(23, 177, 62)");
        insertData(query);
        query = new StringBuffer("INSERT INTO statistics (item_id, likes, comments) VALUES(24, 101, 20)");
        insertData(query);
    }

    public void insertIntoTagsBunch(){
        query = new StringBuffer("delete from tags_and_items;");
        insertData(query);

        query = new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('fantasy', 1, 1)");
        insertData(query);
        query = new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sports', 2, 1)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('love', 3, 1)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('romantics', 4, 2)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('love', 3, 2)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sports', 2, 3)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('football', 5, 3)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('food', 6, 4)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sex', 7, 5)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sex', 7, 6)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('teacher', 8, 6)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('work-place', 9, 7)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sex', 7, 7)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('dream', 10, 7)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('drugs', 11, 8)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('weed', 12, 8)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('movies', 13, 9)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('book', 14, 10)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('library', 15, 10)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('relationship', 16, 11)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('LGBT', 17, 11)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('lesbian', 18, 11)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('gay', 19, 12)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('menstruation', 20, 13)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('school', 21, 13)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('book', 14, 14)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('fantasy', 1, 14)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('menstruation', 20, 15)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('work-place', 9, 15)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('weapons', 22, 16)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('kill', 23, 16)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('dream', 10, 16)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('car', 24, 17)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('car', 24, 18)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('money', 25, 18)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('transport', 26, 19)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('concert', 27, 19)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('rock-band', 28, 19)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('dream', 10, 20)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('car', 24, 20)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('dream', 10, 21)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('bike', 29, 21)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('money', 25, 21)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sex', 7, 22)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('money', 25, 22)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('prostitution', 30, 22)");
        insertData(query);
       query= new StringBuffer("INSERT INTO tags_and_items (tag_name, tag_id, item_id) VALUES('sickness', 31, 22)");
        insertData(query);
    }
}
