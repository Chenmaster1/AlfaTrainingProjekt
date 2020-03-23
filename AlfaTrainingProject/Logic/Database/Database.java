package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Die Verbidnung zur Datenbank. Über Datenbank. aufrufen.
 */
public class Database {
	
	public static final String DATABASENAME = "heroesofthearena";
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

    /**
     * Setzt die nötigen Strings zum Aufbau der Datenbank
     */
    private Database()
    {
        driver = "com.mysql.cj.jdbc.Driver";

        dbURL = "jdbc:mysql://db4free.net:3306/heroesofthearena"
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
            cn = DriverManager.getConnection(dbURL, "heroesofthearena", "AlfaTraining");
            
            // Endgueltige Verbindung zur Datenbank erzeugen
            st = cn.createStatement();
        } catch (ClassNotFoundException | SQLException ex)
        {
            //ex.printStackTrace();
            System.out.println("FEHLER: Laeuft der Server?");
            System.out.println(ex.toString());
        }
    }

    /**
     * Gibt die aktuelle Datenbank zurück, um auf alle Funktionen auszuführen.
     * @return Die Datenbank
     */
    public static Database getInstance()
    {
        if (instance == null)
        {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Falls nicht vorhanden, werden hier die Datenbank und die Tabellen erstellt
     */
    public void connect() {
    	createDatabase();
    	createTables();
    }
    
    /**
     * Übergibt eine Query und führt diese aus.
     * Nur benutzen, wenn keine Daten erwartet werden.
     * @param sql Die Query
     * @return Die erfolgreiche Ausführung der Query
     */
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
    
    /**
     * Erstellt die Datenbank (falls nicht vorhanden) und setzt die Verbindung zu eben jener.
     */
    private void createDatabase()
    {
        String sql = "CREATE DATABASE IF NOT EXISTS "
                + DATABASENAME
                + " COLLATE utf8_german2_ci;";
        // System.out.println(sql);
        getInstance().execute(sql);

        getInstance().execute("USE " + DATABASENAME + ";");
    }
    
    /**
     * Erstellt die Tabellen, falls diese noch nicht existieren
     */
    private void createTables() {
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
