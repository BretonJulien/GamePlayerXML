package model;

public class User {

	private String login;
	private static User INSTANCE = null;
	
	public User()
	{
		
	}
	
	/** Point d'accès pour l'instance unique du singleton */
	public static User getInstance()
	{			
		if (INSTANCE == null)
		{ 	INSTANCE = new User();	
		}
		return INSTANCE;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public String getLogin()
	{
		return login;
	}
}
