package com.example.mateup;

public class UsersPosts {

    String imgUrl;
    String description;
    String date;

public UsersPosts(String imageUrl, String postDescription,String postDate)
{
 imgUrl = imageUrl;
 description = postDescription;
 date = postDate;

}

    public String getDate() {
        return date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }
}



