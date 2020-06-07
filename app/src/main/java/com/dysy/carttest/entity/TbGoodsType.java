package com.dysy.carttest.entity;

import com.dysy.carttest.dto.GoodsDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Dai Junfeng
 * @create: 2020-06-02
 **/
public class TbGoodsType implements Serializable {
    private Integer typeId;
    private String typeName;
    private List<GoodsDTO> tbGoodsList;

    public List<GoodsDTO> getTbGoodsList() {
        return tbGoodsList;
    }

    public void setTbGoodsList(List<GoodsDTO> tbGoodsList) {
        this.tbGoodsList = tbGoodsList;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TbGoodsType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", tbGoodsList=" + tbGoodsList +
                '}';
    }
}
