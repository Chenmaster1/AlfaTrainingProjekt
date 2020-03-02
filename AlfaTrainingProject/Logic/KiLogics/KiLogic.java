package KiLogics;

import java.util.ArrayList;
import java.util.Random;

import Actions.Action;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import Hideouts.Hideout;

/**
 * Diese Klasse dient als Grundgeruest fuer jede implementierte KI.
 * Jeder Held besitzt aufgrund seiner einzigartigkeit einee eigene
 * @author Kevin
 */
public abstract class KiLogic {

	/**
	 * Diese Methode wird von jeder KI implementiert und darueber aufgerufen
	 * 
	 * Falls nur ein Actionpoint übrig:
	 * 		-> Verzögerungsmarke abbauen
	 * Falls mehr, erst verstecken dann angreifen oder Fähigkeit benutzen
	 * 
	 * @param actions alle ausfuehrbaren Aktionen ohne Faehigkeiten
	 * @param hero der aktuelle Held
	 * @param singleplayerGame das aktuelle Spiel
         * @return die Aktion
	 */
	public abstract Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame);
	
	public int chooseAttackField(SingleplayerGame singleplayerGame){
		
		int ownPosition = -1;
		for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
			
			if(singleplayerGame.getMap().getHideoutHero().containsKey(hideout)) {
				
				if(singleplayerGame.getMap().getHideoutHero().get(hideout).equals(singleplayerGame.getCurrentHero())) {
					ownPosition = hideout.getFieldNumber();
				}
			}
		}
		
		//es wird solange ein Hideout gewaehlt, bis ein passendes getroffen wird.
		//solange mehr als 6 felder aktiv sind, wird er selber nicht getroffen bei der auswahl 
		//und ein entsprechendes Feld wird gewaehlt
		//sind weniger aktiv wird nur geschaut ob das Feld aktiv ist
		
		while(true) {
			Random random = new Random();
			int attackField = random.nextInt(21);
			int activeFields = 0;

			if(singleplayerGame.getMap().getHideouts().get(attackField).isActive()) {
				
				for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
					if(hideout.isActive())
						activeFields++;
				}
				
				if(activeFields <= 7) {
					if(attackField < ownPosition + 2 || attackField > ownPosition - 2) {
						return attackField;
					}
				}else {
					return attackField;
				}
			}															
		}
		//reduce sollte bereits in der action, bzw ability aufgerufen werde, sofern die ability einen Zug ersetzt
		//singleplayerGame.reduceCurrentActionPoints();
	}
	
	
	
}
