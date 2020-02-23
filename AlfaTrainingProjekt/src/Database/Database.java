package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	
	public static final String DATABASENAME = "AlfaTrainingProjekt";
    private static Database instance = null;

    /**
     * Der Treiber, der genutzt werden soll, um auf die Datenbank zugreifen zu
     * koennen.
     */
    private String driver;

    /**
     * Die Adresse des Servers. Er kann im Internet oder auch lokal liegen.
     * Ebenfalls ist ein eingebetteter SQLite-Server moeglich.
     */
    private String dbURL;

    /**
     * Verbindung zur Datenbank
     */
    private Connection cn;

    /**
     * Das Statement, mit dem alle Datenbankbefehle ausgefuehrt werden.
     */
    private Statement st;

    // --------------------------------------------------------------
    private Database()
    {
        driver = "com.mysql.cj.jdbc.Driver";

        dbURL = "jdbc:mysql://localhost:3306"
                + "?useUnicode=true"
                + "&useJDBCCompliantTimezoneShift=true"
                + "&useLegacyDatetimeCode=false"
                + "&serverTimezone=UTC";

        try
        {
            /*
             * Bindet den Treiber ein: holt Klasse und legt Objekt in der
             * virtuellen Maschine ab
             */
            Class.forName(driver);

            // bereite mit dem Treiber eine DB-Verbindung vor (URL, Username, Passwort)
            cn = DriverManager.getConnection(dbURL, "root", "");

            // Endgueltige Verbindung zur Datenbank erzeugen
            st = cn.createStatement();
        } catch (ClassNotFoundException | SQLException ex)
        {
            //ex.printStackTrace();
            System.out.println("FEHLER: Laeuft der Server?");
        }
    }

    // --------------------------------------------------------------
    public static Database getInstance()
    {
        if (instance == null)
        {
            instance = new Database();
        }

        return instance;
    }

    // --------------------------------------------------------------
    public boolean execute(String sql)
    {
        boolean back = false;

        try
        {
            back = st.execute(sql);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return back;
    }

    // --------------------------------------------------------------
    /**
     * Hier werden Daten aus SQL-Tabellen ausgelesen. Das Ergebnis ist in einer
     * Klasse, die <code>ResultSet</code> heisst. Im SQL-Jargon wird es oft als
     * "Cursor" bezeichnet.
     *
     * @param sql Das auszufuehrende Kommando.
     * @return Ergebnismenge als <code>ResultSet</code>
     */
    public ResultSet executeQuery(String sql)
    {
        ResultSet back = null;

        try
        {
            back = st.executeQuery(sql);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return back;
    }
    
    public void createDatabase()
    {
        String sql = "CREATE DATABASE IF NOT EXISTS "
                + DATABASENAME
                + " COLLATE utf8_german2_ci;";
        // System.out.println(sql);
        getInstance().execute(sql);

        getInstance().execute("USE " + DATABASENAME + ";");
    }
    
    public void createTables() {
    	//TODO Tabellen erstellen
    	/*
    	String sql = "CREATE TABLE IF NOT EXISTS Tiere ("
                + "Tiere_ID INTEGER AUTO_INCREMENT, "
                + "Tier VARCHAR(255), "
                + "Beine INTEGER, "
                + "PRIMARY KEY (Tiere_ID));";
        // System.out.println(sql);
        getInstance().execute(sql);
        */
    }
}
