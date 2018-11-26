package eCommerce;

import java.util.ArrayList;

public class passwordCheck {
	ArrayList<String> password = new ArrayList<String>();
	public passwordCheck() {
		// TODO Auto-generated constructor stub
		DBInterface db = new DBInterface();
		password = db.getPasswords();
	}
	public int isUserValid(String pwd,int index)
	{
		if(password.get(index).equals(pwd))
			return 1;
		return -1;
	}
}
