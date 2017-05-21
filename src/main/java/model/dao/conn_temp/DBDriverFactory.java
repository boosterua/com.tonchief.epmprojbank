package model.dao.conn_temp;

public final class DBDriverFactory {

    private static DBDriverBase driver = null;

    public static DBDriverBase createDriver(final DBTypes dbType) throws ClassNotFoundException {
        switch (dbType) {
            case MYSQL:
                driver = new DBDriverMysql();
                break;
            case ORACLE:
                throw new ClassNotFoundException("Unknown DB type: " + dbType);
            default:
                driver = new DBDriverMysql();
        }

        return driver;
    }

    private DBDriverFactory() {
    }
}
