package com.junshijia.k_project_v3.domain;

import java.io.Serializable;

public class TenMsData implements Serializable {
    private Integer id;
    private Float HMI_IReg106;
    private Float HMI_IReg110;
    private Float HMI_IReg210;
    private Float HMI_IReg211;
    private Float HMI_IReg282;
    private Float HMI_IReg168;
    private Boolean HMI_Disc912;
    private Boolean HMI_Disc919;

    @Override
    public String toString() {
        return "TenMsData{" +
                "id=" + id +
                ", HMI_IReg106=" + HMI_IReg106 +
                ", HMI_IReg110=" + HMI_IReg110 +
                ", HMI_IReg210=" + HMI_IReg210 +
                ", HMI_IReg211=" + HMI_IReg211 +
                ", HMI_IReg282=" + HMI_IReg282 +
                ", HMI_IReg168=" + HMI_IReg168 +
                ", HMI_Disc912=" + HMI_Disc912 +
                ", HMI_Disc919=" + HMI_Disc919 +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getHMI_IReg106() {
        return HMI_IReg106;
    }

    public void setHMI_IReg106(Float HMI_IReg106) {
        this.HMI_IReg106 = HMI_IReg106;
    }

    public Float getHMI_IReg110() {
        return HMI_IReg110;
    }

    public void setHMI_IReg110(Float HMI_IReg110) {
        this.HMI_IReg110 = HMI_IReg110;
    }

    public Float getHMI_IReg210() {
        return HMI_IReg210;
    }

    public void setHMI_IReg210(Float HMI_IReg210) {
        this.HMI_IReg210 = HMI_IReg210;
    }

    public Float getHMI_IReg211() {
        return HMI_IReg211;
    }

    public void setHMI_IReg211(Float HMI_IReg211) {
        this.HMI_IReg211 = HMI_IReg211;
    }

    public Float getHMI_IReg282() {
        return HMI_IReg282;
    }

    public void setHMI_IReg282(Float HMI_IReg282) {
        this.HMI_IReg282 = HMI_IReg282;
    }

    public Float getHMI_IReg168() {
        return HMI_IReg168;
    }

    public void setHMI_IReg168(Float HMI_IReg168) {
        this.HMI_IReg168 = HMI_IReg168;
    }

    public Boolean getHMI_Disc912() {
        return HMI_Disc912;
    }

    public void setHMI_Disc912(Boolean HMI_Disc912) {
        this.HMI_Disc912 = HMI_Disc912;
    }

    public Boolean getHMI_Disc919() {
        return HMI_Disc919;
    }

    public void setHMI_Disc919(Boolean HMI_Disc919) {
        this.HMI_Disc919 = HMI_Disc919;
    }
}
