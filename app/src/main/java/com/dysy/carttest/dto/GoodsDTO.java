package com.dysy.carttest.dto;

import com.dysy.carttest.entity.TbGoodsType;

import java.io.Serializable;
import java.sql.Date;

public class GoodsDTO implements Serializable {
    private Integer gId;
    private String gName;
    private Float gPrice;
    private Integer selectNum;
    private String gPhoto;
    private TbGoodsType tbGoodsType;

    public GoodsDTO() {

    }

    public GoodsDTO(Integer gId, String gName, Float gPrice, Integer selectNum, String gPhoto, TbGoodsType tbGoodsType) {
        this.gId = gId;
        this.gName = gName;
        this.gPrice = gPrice;
        this.selectNum = selectNum;
        this.gPhoto = gPhoto;
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

    public Integer getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(Integer selectNum) {
        this.selectNum = selectNum;
    }

    public String getgPhoto() {
        return gPhoto;
    }

    public void setgPhoto(String gPhoto) {
        this.gPhoto = gPhoto;
    }

    public TbGoodsType getTbGoodsType() {
        return tbGoodsType;
    }

    public void setTbGoodsType(TbGoodsType tbGoodsType) {
        this.tbGoodsType = tbGoodsType;
    }
}
