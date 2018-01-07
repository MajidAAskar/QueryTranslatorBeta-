package querytranslatorbeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author 
 */
public class ConnectionMySql {

	final static String url = "jdbc:mysql://localhost:3306/OntologiesDB";
	final static String uid = "Translator";
	final static String pwd = "12Translator34";
	static String driver = "com.mysql.jdbc.Driver";
	final static String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
	final static String fullUrl = url + unicode;
	static Connection con;

	/**
	 *
	 */
	public static void main(String[] args) throws Exception
	{
		ConnectionMySql s = new ConnectionMySql();
		s.openBDConnection();
		//s.executeStoredProc("AddToTable",5,"","");
	}
	public  void openBDConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("driver");
			con = DriverManager.getConnection(fullUrl, uid, pwd);
			System.out.println("Connection created");
			con.setAutoCommit(true);

		} catch (ClassNotFoundException | SQLException ex) {
			JOptionPane.showMessageDialog(null,ex.getMessage() ,"Error in connection",1);
			ex.printStackTrace();
			con = null;

		} catch (IllegalAccessException ex) {
			JOptionPane.showMessageDialog(null,ex.getMessage() ,"Error in connection IllegalAccessException",1);
			// Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error in connection InstantiationException",1);
			//Logger.getLogger(ConnectionMySql.class.getName()).log(Level.SEVERE, null, ex);
		}
		//con = null;

	}
	/////////////////////////////////////////////////////////////////////////////////////////////	
	public void executeStoredProc( String procName, String s1,String s2,String s3)
	{
		String query = "";
		if(procName.equals("InsertClasses"))
			query = "call "+ procName+" ( ?, ?)";
		else
			query = "call "+ procName+" ( ?, ?, ?)";

		try {
			// set all the preparedstatement parameters
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, s1);
			st.setString(2, s2);
			if(!procName.equals("InsertClasses")) st.setString(3, s3);

			// execute the preparedstatement insert
			st.executeUpdate();
			st.close();
		} 
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "Stored Proc name "+ procName+ " "+ query +se.getMessage(),"Error in Stored Proc ",1);
			// log exception

		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public void executeStoredProc( String procName,String s1,String s2,String s3,String s4)
	{
		String query = "call "+ procName+" (?, ?, ?, ?)";

		try {
			// set all the preparedstatement parameters
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, s1);
			st.setString(2, s2);
			st.setString(3, s3);
			st.setString(4, s4);

			// execute the preparedstatement insert
			st.executeUpdate();
			st.close();
		} 
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "Stored Proc name "+ procName+se.getMessage(),"Error in Stored Proc ",1);
			// log exception

		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public ResultSet executeStoredProc( String procName)
	{
		String query = "call "+ procName;
		ResultSet RS=null;
		try {
			// set all the preparedstatement parameters
			PreparedStatement st = con.prepareStatement(query);

			// execute the preparedstatement insert
			RS = st.executeQuery();
			//st.close();
		} 
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "Stored Proc name "+ procName+se.getMessage(),"Error in Stored Proc ",1);
			// log exception

		}
		return RS;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public ResultSet executeStoredProc( String procName,String s1)
	{
		String query = "call "+ procName+" (?)";
		ResultSet RS=null;
		try {
			// set all the preparedstatement parameters
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, s1);


			// execute the preparedstatement insert
			RS = st.executeQuery();
			//st.close();
		} 
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "Stored Proc name "+ procName+se.getMessage(),"Error in Stored Proc ",1);
			// log exception

		}
		return RS;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public  void closeBDConnection() {
		try 
		{
			if (con != null) 
				con.close();
		} 
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
}
