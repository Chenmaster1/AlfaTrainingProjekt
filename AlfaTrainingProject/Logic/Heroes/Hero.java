package Heroes;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Abilities.Ability;
import Database.Database;
import Database.Queries;
import GameLogic.KiLogic;

public abstract class Hero {
	private ArrayList<Ability> abilities;
	
	private ImageIcon avatar;
	
	private KiLogic ki;
	
	private String name;
	private String description;
	private String artwork;
	
	private int maxHealth;
	private int currentHealth;
	private int actionPoints;
	private int delayTokens;
	
	private boolean isVisible;
	
	private double power;
	
	
	
	public Hero(String name) {
		//TODO ueber name werte aus Datenbank holen und attribute fuellen
		ResultSet rs = Database.getInstance().executeQuery(Queries.getHeroValues + name);
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void setDelayTokens(int delayTokens) {
		this.delayTokens = delayTokens;
	}
	
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public ArrayList<Ability> getAbilities(){
		return abilities;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDelayTokens() {
		return delayTokens;
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
}
