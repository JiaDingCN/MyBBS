package com.jiading.model;

import java.io.Serializable;

/**
 * 分类实体类
 */
public class Block implements Serializable {

    private int bid;//分类id
    private String blockName;//分类名称
    private String logo;//logo的路径
    private String description ="这是一个版块";

    public Block() {
    }

    public Block(int bid, String blockName, String logo, String describe) {
        this.bid = bid;
        this.blockName = blockName;
        this.logo = logo;
        this.description = describe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Block{" +
                "bid=" + bid +
                ", blockName='" + blockName + '\'' +
                ", logo='" + logo + '\'' +
                ", descrption='" + description + '\'' +
                '}';
    }
}
