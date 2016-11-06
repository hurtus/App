package com.goshopping.usx.goshopping.bean;

import java.io.Serializable;

/**
 * 商家信息实体类
 */
public class MerchantBean implements Serializable{

    //商家名称
    private String name;

    //商家地址
    private String address;

    //商家所在的城市
    private String city;

    //商家地址经度
    private double latitude;

    //商家地址纬度
    private double longitude;

    //商家的介绍
    private String introduce;

    public String getName() {
        return name;
    }

    public MerchantBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public MerchantBean setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public MerchantBean setCity(String city) {
        this.city = city;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public MerchantBean setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public MerchantBean setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public MerchantBean setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }
}
