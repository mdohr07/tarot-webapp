package com.mblip.tarotwebapp.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TarotCard {
    private String type;
    @JsonProperty("name_short")
    private String nameShort;
    private String name;
    private String value;
    @JsonProperty("value_int")
    private int valueInt;
    @JsonProperty("meaning_up")
    private String meaningUp;
    @JsonProperty("meaning_rev")
    private String meaningRev;
    private String desc; // description
    private String imageUrl;

    public TarotCard() { }

    // GETTER
    public String getType() {
        return type;
    }

    public String getNameShort() {
        return nameShort;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getValueInt() {
        return valueInt;
    }

    public String getMeaningUp() {
        return meaningUp;
    }

    public String getMeaningRev() {
        return meaningRev;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // SETTER

    public void setType(String type) {
        this.type = type;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public void setMeaningUp(String meaningUp) {
        this.meaningUp = meaningUp;
    }

    public void setMeaningRev(String meaningRev) {
        this.meaningRev = meaningRev;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "\nTarotCard{" +
                "type='" + type + '\'' +
                ", nameShort='" + nameShort + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", valueInt=" + valueInt +
                ", meaningUp='" + meaningUp + '\'' +
                ", meaningRev='" + meaningRev + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
