package MenuGUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Settings
{
	final public static Settings INSTANCE = new Settings();
	
	private Integer volume = 50;
	private Integer effectVolume = 60;
	private String language = "german";
	
	private Set<SettingsListener> listeners = new HashSet<SettingsListener>();
	
	final String pathStr = System.getProperty("user.home") + File.separator + "HeroesOfTheArena" + File.separator;
	
	private Settings() {
	}
	
	public void setVolume(final Integer volume) {
		this.volume = volume;
		listeners.forEach((l) -> l.propertyChanged("volume", volume));
	}
	
	public void setEffectVolume(final Integer effectVolume) {
		this.effectVolume = effectVolume;
		listeners.forEach((l) -> l.propertyChanged("effectVolume", effectVolume));
	}
	
	public void setLanguage(final String language) {
		this.language = language;
		listeners.forEach((l) -> l.propertyChanged("language", language));
	}

	public final Integer getVolume() {
		return volume;
	}
	
	public final Integer getEffectVolume() {
		return effectVolume;
	}

	public final String getLanguage() {
		return language;
	}
	
	void load() {
    	try {
	    	final File settingsFile = new File( pathStr, "hota_setting.txt" );
	    	
	    	BufferedReader br = new BufferedReader(new FileReader(settingsFile));
	    	
            setVolume(Integer.parseInt(br.readLine()));
            setLanguage(br.readLine());
            setEffectVolume(Integer.parseInt(br.readLine()));
            
            br.close();
    	}
    	catch (Exception e) {
    	}
	}
	
	void save() {
		
    	try {
	    	final File settingsFile = new File( pathStr, "hota_setting.txt" );
	    	
	    	if(!settingsFile.exists()) {
	    		new File(pathStr).mkdirs();
	    	}
	    	
	    	PrintWriter pw = new PrintWriter(settingsFile);
	    	
	    	pw.println(volume);
	    	pw.println(language);
	    	pw.println(effectVolume);
	    	
            pw.close();
    	}
    	catch (Exception e) {
    	}
	}
	
	public void subscribe(SettingsListener listener) {
		listeners.add(listener);
	}

}
