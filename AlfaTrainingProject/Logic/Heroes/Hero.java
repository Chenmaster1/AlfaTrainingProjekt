package Heroes;

import java.awt.Image;
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

    private Image avatar;
    private Image mapIcon;

    private KiLogic ki;

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
    private boolean isDead;
    private boolean isAttackable;
    
    private double power;

    /**
     * Dies ist der Konstruktor fuer jeden Helden
     *
     * @param name der Name des Helden. Wird benoetigt um alle noetigen Werte
     * aus der Datenbank zu holen
     * @author Kevin
     */
    public Hero(String name, String description, String artwork, int maxHitPoints, int maxActionPoints, double power, KiLogic ki, String avatarPath, String mapIconPath) {

        this.name = name;
        this.description = description;
        this.artwork = artwork;
        this.maxHitPoints = maxHitPoints;
        this.maxActionPoints = maxActionPoints;
        this.power = power;
        this.ki = ki;
        this.avatar = new ImageIcon(getClass().getClassLoader().getResource(avatarPath)).getImage();
        this.mapIcon = new ImageIcon(getClass().getClassLoader().getResource(mapIconPath)).getImage();
        this.abilities = new ArrayList<Ability>();
        this.isAttackable = true;
        // Standardwerte: KI-kontrolliert, nicht sichtbar, kein Delay, volle
        // Lebenspunkte
        setPlayerControlled(false);
        setVisible(false);
        setDelayTokens(0);
        setCurrentHitPoints(getMaxHitPoints());

    }

    /**
     * vergleicht zwei helden miteinander
     * @param hero
     * @return
     */
    public boolean equals(Hero hero) {
        return hero.getName().equals(name);
    }

    /**
     * nur einmal pro runde angreifbar
     */
    public void heroGotHit() {
    	currentHitPoints--;
    	isAttackable = false;
    	if(currentHitPoints <=0)
            isDead = true;
    }
    // -------------------------GETTER-------------------------//
    public int getMaxHitPoints() {
        return maxHitPoints;
    }
    
    public KiLogic getKiLogic()
    {
        return ki;
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

    public Image getAvatar() {
        return avatar;
    }

    public Image getMapIcon() {
        return mapIcon;
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


    public boolean isDead()
    {
        return isDead;
    }

    public boolean isAttackable() {
    	return isAttackable;
    }
    // -------------------------SETTER-------------------------//
    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public void setMaxActionPoints(int maxActionPoints) {
        this.maxActionPoints = maxActionPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        if(currentHitPoints <=0)
            isDead = true;
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
    }

    public void setCurrentActionPoints(int currentActionPoints) {
        this.currentActionPoints = currentActionPoints;
    }
    
    public void addDelayTokens(int number) {
    	delayTokens += number;
    }
    
    public void setIsAttackable(boolean isAttackable) {
    	this.isAttackable = isAttackable;
    }

}
