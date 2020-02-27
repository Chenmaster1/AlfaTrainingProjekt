package Heroes;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import Abilities.Ability;
import GameLogic.KiLogic;

public abstract class Hero {
	
	private ArrayList<Ability> abilities;
	private boolean isVisible;
	private int maxHealth;
	private int currentHealth;
	private String name;
	private int actionPoints;
	private int delayTokens;
	private ImageIcon avatar;
	private String description;
	private double power;
	private String artwork;
	private KiLogic ki;
	
	public Hero(String name) {
		//TODO ueber name werte aus Datenbank holen und attribute fuellen
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
