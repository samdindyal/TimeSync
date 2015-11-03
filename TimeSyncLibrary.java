import java.awt.Color;
import java.awt.Font;

import java.io.InputStream;

public class TimeSyncLibrary {
	public static ColourScheme DEFAULT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.WHITE, "TimeSync Default");
	public static ColourScheme GREENACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.GREEN.darker(), "Green Accent");
	public static ColourScheme BLUEACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, new Color(0, 120, 255), "Blue Accent");
	public static ColourScheme REDACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.RED.darker(), "TimeSync Default");

	public static final String DAYS[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
	MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October,", "November", "December"},
	SUFFIX[] = {"<sup>th</sup>", "<sup>st</sup>", "<sup>nd</sup>", "<sup>rd</sup>"};

	public static Font DEFAULT_TITLEFONT = compileFont("res/OpenSans-Light.ttf", 48);
	public static Font BODYTEXTFONT = compileFont("res/OpenSans-Light.ttf", 32);
	public static Font CAPTIONFONT = compileFont("res/OpenSans-Regular.ttf", 12);

	public static Font compileFont(String path, float size) 
	{
		try{
			InputStream input = TimeSync.class.getResourceAsStream(path);
			return Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(size);
		}catch(Exception e){
			System.err.println("Cold not load \"" + path + "\".");
			return new Font ("Arial", Font.BOLD, (int)size);
		}
	}

	public static String getDate(int dayOfWeek, int month, int dayOfMonth, int year)
	{
		return "<html>" + DAYS[dayOfWeek] + ", " + MONTHS[month] + " " + dayOfMonth + (((dayOfMonth%10) > 3 && dayOfMonth != 0) ? SUFFIX[0] : SUFFIX[dayOfMonth%10]) + ", " + year + "</html>";
	}

	public static String pad(int i)
	{
		return (i < 10) ? "0" + i : i + "";
	}

}