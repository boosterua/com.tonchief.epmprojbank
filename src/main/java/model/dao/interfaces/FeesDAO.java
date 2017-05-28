package model.dao.interfaces;

import model.entity.Fee;

import java.util.List;

public interface FeesDAO extends EntityDAO {
    List<Fee> getFees();

}
