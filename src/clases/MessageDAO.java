package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MessageDAO {
    Connection connection;
    Statement statement;

    public void createConnect() {
        {
            try {

                connection = DriverManager.getConnection("jdbc:mysql://localhost/labjava?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false", "vladuser", "3208455258Ss");
                statement = connection.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void JDBSConnector() throws SQLException {
    }
    public List<Message> getMessagesFrom(int id) {
        ArrayList<Message> list = new ArrayList<Message>();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT * FROM `messages` WHERE `messages`.`fromid` = ? ");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                    list.add(new Message(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getNString(4),rs.getTime(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void sendMessage(int from, int to, String message){
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("INSERT INTO `messages` ( `fromId`, `toId`, `message`) VALUES (? , ? , ?);");
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to);
            preparedStatement.setString(3,message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public TreeSet<Integer> getUniqMessageers(int id) throws SQLException {
        TreeSet<Integer> result = new TreeSet<>();
        PreparedStatement preparedStatementFrom = null;
        PreparedStatement preparedStatementTo = null;
        try {
            preparedStatementFrom = connection.prepareStatement("SELECT DISTINCT fromId FROM `messages` WHERE toId = ?;");
            preparedStatementTo = connection.prepareStatement("SELECT DISTINCT toId FROM `messages` WHERE fromId = ?;");
            preparedStatementFrom.setInt(1,id);
            preparedStatementTo.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = preparedStatementFrom.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt("fromId"));
        }
         rs = preparedStatementTo.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt("toId"));
        }

        return result;
    }
    public TreeSet<Message> getCorrespondence (int firstId,int secondId){
        TreeSet<Message> list = new TreeSet<Message>();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT * FROM `messages` WHERE `messages`.`fromid` = ? AND `messages`.`toId` = ?  ");
            preparedStatement.setInt(1, firstId);
            preparedStatement.setInt(2, secondId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                 list.add(new Message(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getNString(4),rs.getTime(5)));
            }
            PreparedStatement preparedStatement1 = null;
            preparedStatement1 = connection.prepareStatement("SELECT * FROM `messages` WHERE `messages`.`fromid` = ? AND `messages`.`toId` = ?  ");
            preparedStatement1.setInt(1, secondId);
            preparedStatement1.setInt(2, firstId);
             rs = preparedStatement1.executeQuery();
            while (rs.next()) {
               list.add(new Message(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getNString(4),rs.getTime(5)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return list;


    }

    public String getLastMessage(int first, int second){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT message from messages WHERE time = ( SELECT MAX(time) from messages WHERE (toId=? AND fromId =?) OR (fromId = ? AND toId = ?))  ");
            preparedStatement.setInt(1,first);
            preparedStatement.setInt(2,second);
            preparedStatement.setInt(3,first);
            preparedStatement.setInt(4,second);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return "";
    }



}
