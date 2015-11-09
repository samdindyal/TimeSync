import java.awt.Color;
import java.awt.Font;

import java.io.InputStream;

public class TimeSyncLibrary {
	public static final ColourScheme DEFAULT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.WHITE, "TimeSync Default");
	public static final ColourScheme GREENACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.GREEN.darker(), "Green Accent");
	public static final ColourScheme BLUEACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, new Color(0, 120, 255), "Blue Accent");
	public static final ColourScheme REDACCENT_COLOURSCHEME = new ColourScheme(Color.DARK_GRAY, Color.RED.darker(), "TimeSync Default");
	public static final ColourScheme BLACK_AND_WHITE_COLOURSCHEME = new ColourScheme(Color.WHITE, Color.BLACK, "Black and White");

	public static final String DAYS[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
	MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October,", "November", "December"},
	SUFFIX[] = {"<sup>th</sup>", "<sup>st</sup>", "<sup>nd</sup>", "<sup>rd</sup>"};

	public static Font DEFAULT_TITLEFONT 	= compileFont("res/OpenSans-Light.ttf", 48);
	public static Font BODYTEXTFONT 		= compileFont("res/OpenSans-Light.ttf", 32);
	public static Font CAPTIONFONT 			= compileFont("res/OpenSans-Regular.ttf", 12);

	public static final int VK_COMMAND 			= 157;
	public static final int TCP_SERVER_SOCKET 	= 6789;

	public static final boolean RUNNING_OSX 		= getOS().startsWith("Mac");
	public static final boolean RUNNING_WINDOWS 	= getOS().startsWith("Mac");

	public static Font compileFont(String path, float size) 
	{
		try{
			InputStream input = TimeSyncLibrary.class.getResourceAsStream(path);
			return Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(size);
		}catch(Exception e){
			System.err.println("Cold not load \"" + path + "\".");
			return new Font ("Arial", Font.BOLD, (int)size);
		}
	}

	public static String pad(int i)
	{
		return (i < 10) ? "0" + i : i + "";
	}

	public static String getOS()
	{
		return System.getProperty("os.name");
	}

}