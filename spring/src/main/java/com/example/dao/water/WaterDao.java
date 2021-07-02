package com.example.dao.water;

import java.util.List;

import com.example.model.WaterModel;

public interface WaterDao {

    List<WaterModel> getWaterInfo();

    WaterModel getWaterInfoById(int id);

    String WaterInfoUpdate(WaterModel data);

    String waterInfoSave(WaterModel data);

    String waterInfoDelete(int id);

    List<WaterModel> searchWaterModels(String city);
}
