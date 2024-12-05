package com.mblip.tarotwebapp.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TarotCard {
    private String type;
    private String name_short;
    private String name;
    private String value;
    private int value_int;
    private String meaning_up;
    private String meaning_rev;
    private String desc; // description

    public TarotCard() { }

    // GETTER
    public String getType() {
        return type;
    }

    public String getName_short() {
        return name_short;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getValue_int() {
        return value_int;
    }

    public String getMeaning_up() {
        return meaning_up;
    }

    public String getMeaning_rev() {
        return meaning_rev;
    }

    public String getDesc() {
        return desc;
    }

    // SETTER

    public void setType(String type) {
        this.type = type;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue_int(int value_int) {
        this.value_int = value_int;
    }

    public void setMeaning_up(String meaning_up) {
        this.meaning_up = meaning_up;
    }

    public void setMeaning_rev(String meaning_rev) {
        this.meaning_rev = meaning_rev;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "\nTarotCard{" +
                "type='" + type + '\'' +
                ", name_short='" + name_short + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", value_int=" + value_int +
                ", meaning_up='" + meaning_up + '\'' +
                ", meaning_rev='" + meaning_rev + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
