package com.example.dao.water;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.model.WaterModel;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WaterDaoImpl implements WaterDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<WaterModel> getWaterInfo() {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "FROM WaterModel";
        Query<WaterModel> query = currentSession.createQuery(hql, WaterModel.class);
        List<WaterModel> list = query.getResultList();

        return list;
    }

    @Override
    public WaterModel getWaterInfoById(int id) {

        Session currentSession = entityManager.unwrap(Session.class);
        WaterModel waterModel = currentSession.get(WaterModel.class, id);

        return waterModel;
    }

    @Override
    public String WaterInfoUpdate(WaterModel data) {

        Session currentSession = entityManager.unwrap(Session.class);
        // WaterModel waterModel = currentSession.get(WaterModel.class, id);
        // waterModel.setCity(data.getCity());
        // waterModel.setDuration(data.getDuration());
        // waterModel.setLocation(data.getLocation());
        // waterModel.setWaterDesc(data.getWaterDesc());
        // waterModel.setWaterPressure(data.getWaterPressure());
        // System.out.println(data);
        currentSession.update(data);
        return "Changes Saved";

    }

    @Override
    public String waterInfoSave(WaterModel data) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(data);

        return "Water Info Added";

    }

    @Override
    public String waterInfoDelete(int id) {
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            WaterModel waterModel = currentSession.get(WaterModel.class, id);
            currentSession.delete(waterModel);
            return "WaterInfo deleted";
        } catch (Exception e) {
            return "Requested WaterModel Does Not Exist!";
        }

    }

    @Override
    public List<WaterModel> searchWaterModels(String city) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "FROM WaterModel w WHERE w.city LIKE :c";
        Query<WaterModel> query = currentSession.createQuery(hql, WaterModel.class);
        query.setParameter("c", "%" + city + "%");
        List<WaterModel> list = query.getResultList();

        return list;
    }

}
