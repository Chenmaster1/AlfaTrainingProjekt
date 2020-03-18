package Heroes;

import enums.HeroEventType;

public interface HeroEventListener {

	public void heroEventRequest(Hero requestingHero, HeroEventType eventType);
}
