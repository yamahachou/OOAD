package eCommerce;

public class loginFacade {
	private String username;
	private String password;
	public usernameCheck usernameChecker;
	public passwordCheck passwordChecker;

	public loginFacade(String un, String pwd)
	{
		username = un;
		password = pwd;
		usernameChecker = new usernameCheck();
		passwordChecker = new passwordCheck();
	}

	public String confirmLogin()
	{
		int valid = 0;
		int index = usernameChecker.isUserValid(username);
		if((index != -1))
		{
			valid = passwordChecker.isUserValid(password, index);
		}
		if(valid == 1)
		{
			return username;
		}
		else
		{
			return "-1";
		}
	}

}
