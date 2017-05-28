package service;

import model.dao.factory.DAOFactoryImpl;
import model.entity.Fee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fees {
    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();

    public static String getMessage() {
        return "SVC:FEES:FEES_LIST";
    }

    public Map<Integer, String> getFeeNamesMap(){
        Map <Integer,String> feeNames = new HashMap<>();
        List<Fee> res = DAO.getFeesDAO().getFees();
        if (res==null) return null;
        for(Fee fee: res){
            feeNames.put(fee.getId(), fee.getName());
        }
        return feeNames;
    }




}
