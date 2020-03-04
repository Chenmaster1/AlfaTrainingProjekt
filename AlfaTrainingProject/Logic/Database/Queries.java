package Database;

/**
 * Hier die Queries entsprechend dem Beispiel String aufbauen.
 * über Queries.String aufrufen
 */
public class Queries {
	//querie für hero	
	public static String getHeroValues(String heroName) {
		return "SELECT * FROM HEROES WHERE NAME = '" + heroName + "'";
	}
	
	
	
	//login
	
	public static String loginUser(String userName) {
		return "SELECT Password FROM UserLogin WHERE UserName = '" + userName + "'";
	}
	
	//register
	public static String registerUser(String userName, String password) {
		return "INSERT INTO  UserLogin VALUES('" + userName + "', '" + password + "')";
	};
	
}
