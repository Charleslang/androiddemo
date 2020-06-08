package com.qinjie.demo.pojo;

public class Goods {
    private String picture;
    private String goodsName;
    private String price;
    private String number;
    private String limit;

    public Goods(String picture, String goodsName, String price, String number, String limit) {
        this.picture = picture;
        this.goodsName = goodsName;
        this.price = price;
        this.number = number;
        this.limit = limit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
