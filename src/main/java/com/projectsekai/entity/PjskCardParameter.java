package com.projectsekai.entity;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardParameter
 * @description 卡牌等级数据
 * @date 2021/6/22 0022 下午 5:08
 **/
public class PjskCardParameter {
    private int id;
    private int cardId;
    private int cardLevel;
    private String cardParameterType;
    private int power;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public String getCardParameterType() {
        return cardParameterType;
    }

    public void setCardParameterType(String cardParameterType) {
        this.cardParameterType = cardParameterType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
