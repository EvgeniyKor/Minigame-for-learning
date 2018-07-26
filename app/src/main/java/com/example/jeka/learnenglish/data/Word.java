package com.example.jeka.learnenglish.data;

public class Word {

    private int id;
    private String text;
    private boolean target;
    private boolean conceal;
    private int colorId;

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public boolean isConceal() {
        return conceal;
    }

    public void setConceal(boolean conceal) {
        this.conceal = conceal;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isTarget() {
        return target;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }
}
