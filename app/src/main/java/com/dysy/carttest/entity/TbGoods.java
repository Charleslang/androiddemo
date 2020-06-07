package com.dysy.carttest.entity;

import java.sql.Date;

/**
 * @author: Dai Junfeng
 * @create: 2020-06-02
 **/
public class TbGoods {
    private Integer gId;
    private String gName;
    private Float gPrice;
    private Integer gNumber;
    private String gPhoto;
    private Date gAddTime;
    private TbGoodsType tbGoodsType;

    public TbGoodsType getTbGoodsType() {
        return tbGoodsType;
    }

    public void setTbGoodsType(TbGoodsType tbGoodsType) {
        this.tbGoodsType = tbGoodsType;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public Float getgPrice() {
        return gPrice;
    }

    public void setgPrice(Float gPrice) {
        this.gPrice = gPrice;
    }

    public Integer getgNumber() {
        return gNumber;
    }

    public void setgNumber(Integer gNumber) {
        this.gNumber = gNumber;
    }

    public String getgPhoto() {
        return gPhoto;
    }

    public void setgPhoto(String gPhoto) {
        this.gPhoto = gPhoto;
    }

    public Date getgAddTime() {
        return gAddTime;
    }

    public void setgAddTime(Date gAddTime) {
        this.gAddTime = gAddTime;
    }

    @Override
    public String toString() {
        return "TbGoods{" +
                "gId=" + gId +
                ", gName='" + gName + '\'' +
                ", gPrice=" + gPrice +
                ", gNumber=" + gNumber +
                ", gPhoto='" + gPhoto + '\'' +
                ", gAddTime=" + gAddTime +
                ", tbGoodsType=" + tbGoodsType +
                '}';
    }
}
