package com.goshopping.usx.goshopping.bean;

import java.util.List;

/**
 * Created by Si on 2016/10/22.
 */

public class MovieItem {


    private String movieName;
    private String time;
    private String desc;
    private String imgUrl;
    private List<String> actors;

    public MovieItem(String movieName,String imgUrl){
        this(movieName,null,null,imgUrl,null);
    }
    public MovieItem(String movieName, String time, String desc, String imgUrl, List<String> actors) {
        this.movieName = movieName;
        this.time = time;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.actors = actors;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDesc() {
        return desc;
    }


    public List<String> getActors() {
        return actors;
    }


}
