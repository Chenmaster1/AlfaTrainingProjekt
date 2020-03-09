package Heroes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Abilities.Ability;
import Database.Database;
import Database.Queries;
import KiLogics.KiLogic;

/**
 * Dies ist das Grundgeruest fuer jeden Helden
 *
 * @author Kevin
 */
public abstract class Hero {

	protected ArrayList<Ability> abilities;

	private ImageIcon avatar;

	protected KiLogic ki;

	private String name;
	private String description;
	private String artwork;

	private int currentHitPoints;
	private int maxHitPoints;

	private int currentActionPoints;
	private int maxActionPoints;
	private int delayTokens;

	private boolean isVisible;
	private boolean isPlayerControlled;

	private double power;

	/**
	 * Dies ist der Konstruktor fuer jeden Helden
	 *
	 * @param name der Name des Helden. Wird benoetigt um alle noetigen Werte aus
	 *             der Datenbank zu holen
	 * @author Kevin
	 */
	public Hero(String name) {
		// TODO ueber name werte aus Datenbank holen und attribute fuellen
		ResultSet rs = Database.getInstance().executeQuery(Queries.getHeroValues(name));
		try {
			while (rs.next()) {

			}
		} catch (SQLException e) {
		}

		// Standardwerte: KI-kontrolliert, nicht sichtbar, kein Delay, volle
		// Lebenspunkte
		setPlayerControlled(false);
		setVisible(false);
		setDelayTokens(0);
		setCurrentHitPoints(getMaxHitPoints());

	}

	public boolean equals(Hero hero) {
		return hero.getName().equals(name);
	}

	// -------------------------GETTER-------------------------//
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public int getMaxActionPoints() {
		return maxActionPoints;
	}

	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isPlayerControlled() {
		return isPlayerControlled;
	}

	public String getName() {
		return name;
	}

	public int getDelayTokens() {
		return delayTokens;

	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public String getDescription() {
		return description;
	}

	public double getPower() {
		return power;
	}

	public String getArtwork() {
		return artwork;
	}

	// -------------------------SETTER-------------------------//
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public void setMaxActionPoints(int maxActionPoints) {
		this.maxActionPoints = maxActionPoints;
	}

	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setPlayerControlled(boolean isPlayerControlled) {
		this.isPlayerControlled = isPlayerControlled;
	}

	public void setDelayTokens(int delayTokens) {
		this.delayTokens = delayTokens;
		// TODO Actions entsprechend auf enabled oder disabled setzen
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		this.currentActionPoints = currentActionPoints;
	}

}
