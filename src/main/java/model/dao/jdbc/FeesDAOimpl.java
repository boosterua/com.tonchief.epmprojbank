package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.interfaces.FeesDAO;
import model.entity.Entity;
import model.entity.Fee;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeesDAOimpl implements FeesDAO {
    //TODO full instantiation
    private static FeesDAOimpl instance = null; // Lazy instantiation
    private final Logger logger = Logger.getLogger(FeesDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();

    private static final String ID  = "id_fee";
    private static final String NAM = "name"; // 2
    private static final String TRF = "trans_fee"; // 3
    private static final String NCF = "newcard_fee"; // 4
    private static final String APR = "apr"; // 5
    //Checked for fields equality b/w dao and db(v2), 2017-05-27

    public static FeesDAO getInstance() {
        if (instance == null) instance = new FeesDAOimpl();
        return instance;
    }

    public List<Fee> getFees() {
        List<Fee> feeList = new ArrayList<>();
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("fees.getAll"), 1);) {
            logger.info("Got connection. Trying PS:" + ps.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Fee fee = new Fee(rs.getInt(ID), rs.getString(NAM));
                    fee.setTransferFee(rs.getDouble(TRF));
                    fee.setNewCardFee(rs.getDouble(NCF));
                    fee.setAPR(rs.getDouble(APR));
                    feeList.add(fee);
                    logger.info(fee.toString());
                }
                rs.close();
                return feeList;
            }

        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public Fee getFeeById(Integer feeId) {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("fees.getById"), 1)){
            logger.info("Got connection. Trying PS:" + ps.toString());
            ps.setInt(1, feeId);
            try (ResultSet rs = ps.executeQuery()) {
                Fee fee = null;
                if (rs.next()) {
                    fee = new Fee(rs.getInt(ID), rs.getString(NAM));
                    fee.setTransferFee(rs.getDouble(TRF));
                    fee.setNewCardFee(rs.getDouble(NCF));
                    fee.setAPR(rs.getDouble(APR));
                    logger.info(fee);
                }
                rs.close();
                return fee;
            }

        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }


    public Integer insert(Object tdata) {
        return 0;
    }

    public boolean update(int id, Entity data) {
        return false;
    }

    public boolean delete(long id) {
        return false;
    }

    public Entity getById(Integer id) {
        return null;
    }


}
