package com.sp.customviewdemo;

/**
 * Created by songpeng on 2017/8/31.
 * <p>
 * Date 2017/8/31
 * <p>
 * Description
 */

public class PieDate {
    private String pieName;//扇形的名称

    private float pieValue;//扇形的数值

    private float piePercent;//扇形的百分比

    private int pieColor;//扇形的颜色

    private float pieAngle;//扇形的角度


    public PieDate(String pieName, float pieValue) {
        this.pieName = pieName;
        this.pieValue = pieValue;
    }

    public String getPieName() {
        return pieName;
    }

    public void setPieName(String pieName) {
        this.pieName = pieName;
    }

    public float getPieValue() {
        return pieValue;
    }

    public void setPieValue(float pieValue) {
        this.pieValue = pieValue;
    }

    public float getPiePercent() {
        return piePercent;
    }

    public void setPiePercent(float piePercent) {
        this.piePercent = piePercent;
    }

    public int getPieColor() {
        return pieColor;
    }

    public void setPieColor(int pieColor) {
        this.pieColor = pieColor;
    }

    public float getPieAngle() {
        return pieAngle;
    }

    public void setPieAngle(float pieAngle) {
        this.pieAngle = pieAngle;
    }
}
