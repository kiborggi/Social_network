package clases;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        JDBSConnector jdbsConnector = null;
        try {
            jdbsConnector = new JDBSConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbsConnector.createConnect();
        jdbsConnector.show();

    }
}
