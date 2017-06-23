package querytranslatorbeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lts
 */
public class ConnectionMySql {


    final static String url = "jdbc:mysql://db4free.net:3306/recommender";
    final static String uid = "eelu";
    final static String pwd = "eelueelu";
    static String driver = "com.mysql.jdbc.Driver";
    final static String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
    final static String fullUrl = url + unicode;
    static Connection con;

    /**
     *
     */
    /* public static void main(String[] args) throws Exception
    {
    	ConnectionMySql s = new ConnectionMySql();
    	 s.connection();
    }*/
    public  Connection openBDConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("driver");
            con = DriverManager.getConnection(fullUrl, uid, pwd);
            System.out.println("Connection created");
            con.setAutoCommit(true);
            return con;

        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error in connection");
            ex.printStackTrace();
            return null;

        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public  void closeBDConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
