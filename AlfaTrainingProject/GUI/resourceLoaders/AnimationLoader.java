package resourceLoaders;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class AnimationLoader {

	private static final AnimationLoader ANIMATIONLOADER_SINGLETON = new AnimationLoader();

	private ArrayList<Image>[] animations;

	private AnimationLoader() {
		animations = new ArrayList[ImageName.values().length];

		// Animationen (Image-ArrayLists) ins Array laden.

		Image frame;

		ArrayList<Image> attackDiceArrayList = new ArrayList<>();
		for (int i = 0; i < 220; i++) {
			// Pfad-String zusammenbasteln. image 0 -> 0000, 15 -> 0015 etc
			StringBuilder sb = new StringBuilder();
			sb.append("dice_w10/testsequence");
			int additionalZeros = 4 - ("" + i).length();

			for (int j = 0; j < additionalZeros; j++) {
				sb.append("0");
			}
			sb.append(i);
			sb.append(".png");

			frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
			attackDiceArrayList.add(frame);
		}
		animations[AnimationName.ATTACKDICE.ordinal()] = attackDiceArrayList;

		ArrayList<Image> hideDiceArrayList = new ArrayList<>();
		for (int i = 0; i < 120; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("dice_w6/testsequence");
			int additionalZeros = 4 - ("" + i).length();

			for (int j = 0; j < additionalZeros; j++) {
				sb.append("0");
			}
			sb.append(i);
			sb.append(".png");

			frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
			hideDiceArrayList.add(frame);
		}
		animations[AnimationName.HIDEDICE.ordinal()] = hideDiceArrayList;

		ArrayList<Image> deactivatedHideoutArrayList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			deactivatedHideoutArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Gameboard/" + i + "-Deactivated.png"))
							.getImage());
		}
		animations[AnimationName.DEACTIVATED_HIDEOUTS.ordinal()] = deactivatedHideoutArrayList;

		ArrayList<Image> towerChargeArrayList = new ArrayList<>();
		for (int i = 0; i < 42; i++) {
			towerChargeArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Tower-Charge/Tower" + (i + 1) + ".png"))
							.getImage());
		}
		animations[AnimationName.TOWER_CHARGE.ordinal()] = towerChargeArrayList;

		ArrayList<Image> towerFireArrayList = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			towerFireArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Tower-Fire/Tower" + (i + 1) + ".png"))
							.getImage());
		}
		animations[AnimationName.TOWER_FIRE.ordinal()] = towerFireArrayList;

		ArrayList<Image> towerScanArrayList = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			towerScanArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Tower-Scan/Scan" + (i + 1) + ".png"))
							.getImage());
		}
		animations[AnimationName.TOWER_SCAN.ordinal()] = towerScanArrayList;
		
		ArrayList<Image> towerEliminateArrayList = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			towerEliminateArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Tower-Eliminate/Tower" + (i + 1) + ".png"))
							.getImage());
		}
		animations[AnimationName.TOWER_ELIMINATE.ordinal()] = towerEliminateArrayList;

	}

	public static AnimationLoader getInstance() {
		return ANIMATIONLOADER_SINGLETON;
	}

	public ArrayList<Image> getAnimation(AnimationName name) {

		return animations[name.ordinal()];
	}
}
