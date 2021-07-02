package com.example.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "water_info")
public class WaterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer wid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    @Column(name = "pressure", nullable = false)
    private String waterPressure;

    @Column(name = "water_desc", nullable = false)
    private String waterDesc;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String duration;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Cascade(value = { org.hibernate.annotations.CascadeType.DELETE })
    private List<FeedbackModel> feedback;

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public UserModel getUserId() {
        return userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
    }

    public String getWaterPressure() {
        return waterPressure;
    }

    public void setWaterPressure(String waterPressure) {
        this.waterPressure = waterPressure;
    }

    public String getWaterDesc() {
        return waterDesc;
    }

    public void setWaterDesc(String waterDesc) {
        this.waterDesc = waterDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<FeedbackModel> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<FeedbackModel> feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "WaterModel [city=" + city + ", duration=" + duration + ", feedback=" + feedback + ", location="
                + location + ", userId=" + userId + ", waterDesc=" + waterDesc + ", waterPressure=" + waterPressure
                + ", wid=" + wid + "]";
    }

}
