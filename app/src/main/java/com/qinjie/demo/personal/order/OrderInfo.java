package com.qinjie.demo.personal.order;
/**
  * 保存订单信息
  *
  * @author: 秦杰
 **/
public class OrderInfo {
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 店铺名
     */
    private String storeName;
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
    /**
     * 总价
     */
    private String totalPrice;

    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 用户id
     */
    private String userId;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OrderInfo(Integer orderId, String storeName, String goodsImages, String goodsName, String goodsPrice, String goodsNum, String totalPrice, String orderStatus) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.goodsImage = goodsImages;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public void setOrderId(Integer orderId) {

        this.orderId = orderId;
    }

    public OrderInfo(Integer orderId, String storeName, String goodsImage, String goodsName, String goodsPrice, String goodsNum, String totalPrice, String orderStatus, String userId) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.goodsImage = goodsImage;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public OrderInfo() {
    }
}
