package com.example.services.water;

import java.util.List;

import com.example.dao.login.LoginDao;
import com.example.dao.water.WaterDao;
import com.example.model.UserModel;
import com.example.model.WaterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WaterServiceImpl implements WaterService {

    @Autowired
    private WaterDao waterDao;
    @Autowired
    private LoginDao loginDao;

    @Override
    @Transactional
    public List<WaterModel> getWaterInfo() {

        return waterDao.getWaterInfo();
    }

    @Override
    @Transactional
    public WaterModel getWaterInfoById(String id) {

        return waterDao.getWaterInfoById(Integer.parseInt(id));
    }

    @Override
    @Transactional
    public String WaterInfoUpdate(WaterModel data) {
        return waterDao.WaterInfoUpdate(data);

    }

    @Override
    @Transactional
    public String waterInfoSave(WaterModel data, String email) {

        UserModel user = loginDao.getUserModel(email);

        if (user == null) {
            return "Bad Request";
        }
        // System.out.println(user);
        data.setUserId(user);

        String resp = waterDao.waterInfoSave(data);
        return resp;

    }

    @Override
    @Transactional
    public String waterInfoDelete(String id) {

        return waterDao.waterInfoDelete(Integer.parseInt(id));

    }

    @Override
    public List<WaterModel> searchWaterModels(String city) {
        return waterDao.searchWaterModels(city);
    }

}
