package com.example.mateup;

public class UsersPosts {

    String imgUrl;
    String description;
    String date;
    String userName;
    String userLastName;
    String userProfession;

public UsersPosts(String imageUrl, String postDescription,String postDate,String name, String lastName)
{
 imgUrl = imageUrl;
 description = postDescription;
 date = postDate;
 userName = name;
 userLastName = lastName;


}

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
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



