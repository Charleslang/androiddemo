package com.dysy.carttest.dto;

public class OrderDetailsDTO {
    private Integer detailsId;
    private Integer goodsId;
    private Integer orderId;
    private Float goodsPrice;
    private String goodsName;
    private String goodsImg;
    private Integer goodsNum;
    private Integer stockNum;

    public OrderDetailsDTO() {

    }

    public OrderDetailsDTO(Integer goodsId, Float goodsPrice, String goodsName, String goodsImg, Integer goodsNum, Integer stockNum) {
        this.goodsId = goodsId;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
        this.goodsImg = goodsImg;
        this.goodsNum = goodsNum;
        this.stockNum = stockNum;
    }

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }
}
