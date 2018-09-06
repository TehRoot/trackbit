import java.sql.*;
import java.util.ArrayList;

public class PsqlConnector {

    public static Connection initConnection(ArrayList<String> arguments) throws SQLException {
        String user = arguments.get(0);
        String password = arguments.get(1);
        String databaseName = arguments.get(2);
        String url = "jdbc:postgresql://localhost/"+databaseName+'?'+user+"&"+password;
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }

    public static Boolean transactDB(PreparedStatement statement) throws SQLException {
        int i = statement.executeUpdate();
        if(i == 1){
            return true;
        } else {
            return false;
        }
    }

    protected static PreparedStatement insertDB(Connection connection, int id, byte[] array, long timestamp) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT INTO route VALUES (?, ?, ?)");
        ps.setInt(1, id);
        ps.setBytes(2, array);
        ps.setLong(3, timestamp);
        return ps;
    }

    protected static PreparedStatement deleteDB(Connection connection, int id) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("");
        return ps;
    }

    protected static Integer byteArrayLength(Byte[] passedArray) {
        return passedArray.length;
    }
}