package Arenacards;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Actions.Action;
import Actions.ActionHide;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.ArenacardType;

public class Arenacards {

	private static ArrayList<Arenacards> arenacards;
	private String name;
	private String description;
	private String commentary;
	private String author;
	private Image image;
	private ArenacardType arenacardType;
	private int delayTokens;
	private int cardNumber;
	
	
	public Arenacards(int cardNumber) {
		this.name = MyFrame.bundle.getString("ArenacardName" + cardNumber);
		this.description = MyFrame.bundle.getString("ArenacardDescription" + cardNumber);
		this.commentary = MyFrame.bundle.getString("ArenacardCommentary" + cardNumber);
		this.author = MyFrame.bundle.getString("ArenacardAuthor" + cardNumber);
		//"Images/BackGround_FullScreenBlurred.png"
		this.image = new ImageIcon(getClass().getClassLoader().getResource(MyFrame.bundle.getString("ArenacardImage" + cardNumber))).getImage();		
		switch(MyFrame.bundle.getString("ArenacardType" + cardNumber)) {
			case "ACTIONCARD":
				arenacardType = ArenacardType.ACTIONCARD;
				break;
			case "EVENTCARD":
				arenacardType = ArenacardType.EVENTCARD;
				break;
		}
		
		if(cardNumber == 8 || cardNumber == 9 || cardNumber == 14)
			delayTokens = 3;
		
		this.cardNumber = cardNumber;

	}

	/**
	 * erstellt die Liste der Arenakarten
	 * @return
	 */
	public static ArrayList<Arenacards> createNewArenacards(){
		for(int count = 0; count <= 16; count++) {
			arenacards.add(new Arenacards(count));
		}
		return arenacards;
	}
	
	/**
	 * 
	 * @param singleplayerGame
	 * @param decision nur bei entscheidungen uebergeben
	 */
	public void useCard(SingleplayerGame singleplayerGame, int... decision) {
		switch(cardNumber) {
			case 0:
				//nichts passiert
				break;
			case 1:
				useMedicinalHerb(singleplayerGame);
				break;
			case 2:
				useMysteriousFruit(singleplayerGame);
				break;
			case 3:
				useRustyBearTrap(singleplayerGame);
				break;
			case 4:
				useHiddenPit(singleplayerGame);
				break;
			case 5:
				useStrangeRedShrooms(singleplayerGame);
				break;
			case 6:
				useAncientPendant(singleplayerGame);
				break;
			case 7:
				useForestHut(singleplayerGame);
				break;
			case 8:
				useMysteriousIdol1(singleplayerGame);
				break;
			case 9:
				useMysteriousIdol2(singleplayerGame);
				break;
			case 10:
				useTreacherousCrow(singleplayerGame);
				break;
			case 11:
				useImpassableTerrain(singleplayerGame);
				break;
			case 12:
				useWispyLights(singleplayerGame);
				break;
			case 13:
				useWrongWay(singleplayerGame);
				break;
			case 14:
				useSeismicActivities(singleplayerGame);
				break;
			case 15:
				useNorthernSpiders(singleplayerGame);
				break;
			case 16:
				useFallingRocks(singleplayerGame);
				break;
 		}
	}

	private void useFallingRocks(SingleplayerGame singleplayerGame) {
		// TODO Auto-generated method stub
		
	}

	private void useNorthernSpiders(SingleplayerGame singleplayerGame) {
		// TODO Auto-generated method stub
		
	}

	private void useSeismicActivities(SingleplayerGame singleplayerGame) {
		//3 Verzoegerungsmarken. verzoegerungsmarken werden abgebaut bei speziellem wuerfelwurf (in singleplayergame abfangen und aufrufen)
		
		if(delayTokens > 0) {
			delayTokens--;
			if(delayTokens == 0) {
				//TODO alle spieler im ödland aufdecken und ausdauer um 1 verringern
			}
		}
		//TODO boolean setzen
		
	}

	private void useWrongWay(SingleplayerGame singleplayerGame) {
		// TODO ueberlegen, wie zu iomplementieren. ansonsten jeder held eine verzögerungsmarke?
		
	}

	private void useWispyLights(SingleplayerGame singleplayerGame) {
		//auch verzoegerungsmarken aufnehmen
		singleplayerGame.getCurrentHero().addDelayTokens(2);
	}

	private void useImpassableTerrain(SingleplayerGame singleplayerGame) {

		singleplayerGame.getCurrentHero().addDelayTokens(1);
		
	}

	private void useTreacherousCrow(SingleplayerGame singleplayerGame) {

		singleplayerGame.setCurrentActionPointsToZero();
		singleplayerGame.getCurrentHero().addDelayTokens(1);
		
	}

	private void useMysteriousIdol2(SingleplayerGame singleplayerGame) {
		//TODO wuerfelwurf und aufruf ueber singleplayergame?
		//Verzögerungsmarken auf dieser Karte. können abgebaut werden
		if(delayTokens > 0) {
			delayTokens--;
			if(delayTokens == 0)
				//boolean in singleplayerGame setzen
				singleplayerGame.setMysteriousIdol2(true);
		}
	}
	
	private void useMysteriousIdol1(SingleplayerGame singleplayerGame) {
		//TODO wuerfelwurf und aufruf ueber singleplayergame?
		//erst Verzögerungsmarken entfernen. also held würfelt, falls klappt, dann verringern	
		if(delayTokens > 0) {
			delayTokens--;
			if(delayTokens == 0)
				//boolean in singleplayerGame setzen
				singleplayerGame.setMysteriousIdol1(true);
		}
		
	}

	private void useForestHut(SingleplayerGame singleplayerGame) {
		// TODO Helden und Karten verbinden (über hashmap????)
		
		//falls held sichtbar, karte entfernen
		//ansonsten currentactionpoints um 1 erhoehen
		
	}

	private void useAncientPendant(SingleplayerGame singleplayerGame) {
		// TODO Nachfragen wie Uralter Anhänger funktioniert
		
	}

	private void useStrangeRedShrooms(SingleplayerGame singleplayerGame) {
		//beim ablegen, direkt verstecken und 3 verzögerungsmarken aufnehmen
		for(Action action : singleplayerGame.getActions()) {
			if(action instanceof ActionHide) {
				action.useAction(singleplayerGame);
				singleplayerGame.getCurrentHero().addDelayTokens(3);
				break;
			}
		}
	}

	private void useHiddenPit(SingleplayerGame singleplayerGame) {
		//zwei verzögerungs hinzufügen
		singleplayerGame.getCurrentHero().addDelayTokens(2);
	}

	private void useRustyBearTrap(SingleplayerGame singleplayerGame) {
		//eine verzögerungs hinzufügen
		singleplayerGame.getCurrentHero().addDelayTokens(1);
		
	}

	private void useMysteriousFruit(SingleplayerGame singleplayerGame) {
		//Wenn abgelegt, im aktuellen Zug eine Aktion mehr
		singleplayerGame.increaseCrurrentActionPointsBy(1);
		
	}

	private void useMedicinalHerb(SingleplayerGame singleplayerGame) {
		//heilt ausdauer beim aktuellen Helden
		singleplayerGame.getCurrentHero().setCurrentHitPoints(singleplayerGame.getCurrentHero().getCurrentHitPoints() + 1);	
	}
}
