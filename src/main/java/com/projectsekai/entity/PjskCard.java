package com.projectsekai.entity;


import java.util.List;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCard
 * @description 卡牌
 * @date 2021/6/22 0022 下午 5:06
 **/
public class PjskCard {
    private int id;
    private int seq;
    private int characterId;
    private int rarity;
    private int specialTrainingPower1BonusFixed;
    private int specialTrainingPower2BonusFixed;
    private int specialTrainingPower3BonusFixed;
    private String attr;
    private String supportUnit;
    private int skillId;
    private String cardSkillName;
    private String prefix;
    private String assetbundleName;
    private String gachaPhrase;
    private String flavorText;
    private long releaseAt;
    private String cardCharacterName;
    private List<PjskCardParameter> cardParameters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getSpecialTrainingPower1BonusFixed() {
        return specialTrainingPower1BonusFixed;
    }

    public void setSpecialTrainingPower1BonusFixed(int specialTrainingPower1BonusFixed) {
        this.specialTrainingPower1BonusFixed = specialTrainingPower1BonusFixed;
    }

    public int getSpecialTrainingPower2BonusFixed() {
        return specialTrainingPower2BonusFixed;
    }

    public void setSpecialTrainingPower2BonusFixed(int specialTrainingPower2BonusFixed) {
        this.specialTrainingPower2BonusFixed = specialTrainingPower2BonusFixed;
    }

    public int getSpecialTrainingPower3BonusFixed() {
        return specialTrainingPower3BonusFixed;
    }

    public void setSpecialTrainingPower3BonusFixed(int specialTrainingPower3BonusFixed) {
        this.specialTrainingPower3BonusFixed = specialTrainingPower3BonusFixed;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getSupportUnit() {
        return supportUnit;
    }

    public void setSupportUnit(String supportUnit) {
        this.supportUnit = supportUnit;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getCardSkillName() {
        return cardSkillName;
    }

    public void setCardSkillName(String cardSkillName) {
        this.cardSkillName = cardSkillName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAssetbundleName() {
        return assetbundleName;
    }

    public void setAssetbundleName(String assetbundleName) {
        this.assetbundleName = assetbundleName;
    }

    public String getGachaPhrase() {
        return gachaPhrase;
    }

    public void setGachaPhrase(String gachaPhrase) {
        this.gachaPhrase = gachaPhrase;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public long getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(long releaseAt) {
        this.releaseAt = releaseAt;
    }

    public String getCardCharacterName() {
        return cardCharacterName;
    }

    public void setCardCharacterName(String cardCharacterName) {
        this.cardCharacterName = cardCharacterName;
    }

    public List<PjskCardParameter> getCardParameters() {
        return cardParameters;
    }

    public void setCardParameters(List<PjskCardParameter> cardParameters) {
        this.cardParameters = cardParameters;
    }
}
