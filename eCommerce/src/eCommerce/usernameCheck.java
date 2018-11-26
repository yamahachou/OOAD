package eCommerce;

import java.util.ArrayList;

public class usernameCheck {
	ArrayList<String> username = new ArrayList<String>();
	public usernameCheck() {
		// TODO Auto-generated constructor stub
		DBInterface db = new DBInterface();
		username = db.getUsernames();
	}
	public int isUserValid(String name)
	{
		for(String u:username)
		{
			if(u.equals(name))
			{
				return username.indexOf(u);
			}
		}
		return -1;
	}
}
