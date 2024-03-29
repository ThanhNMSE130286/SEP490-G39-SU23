package com.teachsync.utils.enums;

public enum CenterType {
    ENGLISH("ENGLISH", "English", "Tiếng Anh");

    private final String stringValue;
    private final String stringValueEng;
    private final String stringValueVie;

    CenterType(String stringValue, String stringValueEng, String stringValueVie) {
        this.stringValue = stringValue;
        this.stringValueEng = stringValueEng;
        this.stringValueVie = stringValueVie;
    }

    public String getStringValue() {
        return stringValue;
    }

    public String getStringValueEng() {
        return stringValueEng;
    }

    public String getStringValueVie() {
        return stringValueVie;
    }
}
