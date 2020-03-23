package Heroes;

import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import Abilities.Ability;
import Database.Database;
import Database.Queries;
import KiLogics.KiLogic;
import SoundThread.SoundController;
import enums.HeroEventType;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

/**
 * Dies ist das Grundgeruest fuer jeden Helden
 *
 * @author Kevin
 */
public abstract class Hero {

	protected Set<HeroEventListener> listeners;
	protected ArrayList<Ability> abilities;

	private Image avatar;
	private Image avatarDeactivated;
	private Image mapIcon;
	private Image gravestone;

	private KiLogic ki;

	private String name;
	private String description;
	private String artwork;

	private String hitSound;
	private String dieSound;

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
	 * @param name der Name des Helden. Wird benoetigt um alle noetigen Werte aus
	 *             der Datenbank zu holen
	 * @author Kevin
	 */
	public Hero(String name, String description, String artwork, String hitSound, String dieSound, int maxHitPoints,
			int maxActionPoints, double power, KiLogic ki, ImageName avatarName, ImageName avatarDeactivatedName,
			ImageName mapIconName, ImageName gravestoneName) {

		this.name = name;
		this.description = description;
		this.artwork = artwork;
		this.hitSound = hitSound;
		this.dieSound = dieSound;
		this.maxHitPoints = maxHitPoints;
		this.maxActionPoints = maxActionPoints;
		this.power = power;
		this.ki = ki;
		this.avatar = ImageLoader.getInstance().getImage(avatarName);
		this.avatarDeactivated = ImageLoader.getInstance().getImage(avatarDeactivatedName);
		this.mapIcon = ImageLoader.getInstance().getImage(mapIconName);
		this.gravestone = ImageLoader.getInstance().getImage(gravestoneName);
		this.abilities = new ArrayList<Ability>();
		this.listeners = new HashSet<>();
		this.isAttackable = true;
		// Standardwerte: KI-kontrolliert, nicht sichtbar, kein Delay, volle
		// Lebenspunkte
		setPlayerControlled(false);
		setVisible(false);
		setDelayTokens(0);
		setCurrentHitPoints(getMaxHitPoints());

	}

	// equals sollte glaube ich besser equivalent mit == sein, falls wir mal
	// denselben Helden mehrmals in einem Spiel haben

//    /**
//     * vergleicht zwei helden miteinander
//     * @param hero
//     * @return
//     */
//    public boolean equals(Hero hero) {
//        return hero.getName().equals(name);
//    }

	public void addHeroListener(HeroEventListener listener) {
		listeners.add(listener);
	}

	public void removeHeroListener(HeroEventListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Benachrichtigt alle Listener (üblicherweise nur das SingleplayerGame), dass
	 * der Hero ein Event beantragt. In speziellen Heros muss diese Methode
	 * aufgerufen werden, wenn die jeweiligen Bedingungen gegeben sind, z.B. wenn er
	 * HP verliert o.ä.
	 * 
	 * 
	 * @param requestingHero Der beantragende Held
	 * @param eventType      Der Typ des Events
	 */
	protected void notifyAllHeroEventListeners(Hero requestingHero, HeroEventType eventType) {
		for (HeroEventListener hel : listeners) {
			hel.heroEventRequest(requestingHero, eventType);
		}
	}

	// -------------------------GETTER-------------------------//
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public KiLogic getKiLogic() {
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

	public Image getAvatarDeactivated() {
		return avatarDeactivated;
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

	public boolean isDead() {
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
		if (currentHitPoints < this.currentHitPoints && currentHitPoints > 0) {

			SoundController.playSound(hitSound);
		}
		if (currentHitPoints <= 0) {
			isDead = true;
			setCurrentActionPoints(0);
			SoundController.playSound(dieSound);
		}
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

	public void setAttackable(boolean isAttackable) {
		this.isAttackable = isAttackable;
	}

	public Image getGravestone() {

		return gravestone;
	}

}
