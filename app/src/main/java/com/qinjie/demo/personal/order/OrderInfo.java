package com.qinjie.demo.personal.order;

import java.util.List;

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
    /**
     * 商品信息
     */
    private List<GoodsInfo> goodsInfos;
    /**
     * 商品数量
     */
    private String goodsNum;

    public OrderInfo(Integer orderId, String storeName, String totalPrice, String orderStatus, String userId, List<GoodsInfo> goodsInfos, String goodsNum) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.goodsInfos = goodsInfos;
        this.goodsNum = goodsNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public OrderInfo() {
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId=" + orderId +
                ", storeName='" + storeName + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", userId='" + userId + '\'' +
                ", goodsInfos=" + goodsInfos +
                ", goodsNum='" + goodsNum + '\'' +
                '}';
    }

    public void setGoodsInfos(List<GoodsInfo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }
}
