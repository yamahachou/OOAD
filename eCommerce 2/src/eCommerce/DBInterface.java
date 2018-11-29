package eCommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
// This is the class for Database connectivity and result set generation
public class DBInterface {
	ArrayList<ArrayList<ArrayList<String>>> data = new ArrayList<ArrayList<ArrayList<String>>>();
	ArrayList<String> usernames = new ArrayList<String>();
	ArrayList<String> passwords = new ArrayList<String>();
	public DBInterface()
	{
		ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();


		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/ecommerce","root","");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from products");
			while(rs.next())
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				products.add(temp);
			}
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from user");
			while(rs.next())
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				users.add(temp);
				usernames.add(rs.getString(1));
				passwords.add(rs.getString(2));
			}
			con.close();
			}
		catch(Exception e)
		{
			System.out.println(e);
		}
		data.add(users);
		data.add(products);
	}
	public ArrayList<ArrayList<ArrayList<String>>> getData()
	{
		return data;
	}
	public ArrayList<String> getUsernames()
	{
		return usernames;
	}
	public ArrayList<String> getPasswords()
	{
		return passwords;
	}
}
