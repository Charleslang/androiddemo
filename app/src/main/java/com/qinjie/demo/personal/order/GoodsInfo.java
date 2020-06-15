package com.qinjie.demo.personal.order;

public class GoodsInfo {
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品图片
     */
    private String goodsImage;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private String goodsPrice;
    /**
     * 商品数量
     */
    private String goodsNum;

    public GoodsInfo(Integer goodsId, String goodsImage, String goodsName, String goodsPrice, String goodsNum) {
        this.goodsId = goodsId;
        this.goodsImage = goodsImage;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }

    public GoodsInfo() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "goodsId=" + goodsId +
                ", goodsImage='" + goodsImage + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                '}';
    }

    public void setGoodsNum(String goodsNum) {

        this.goodsNum = goodsNum;
    }
}
