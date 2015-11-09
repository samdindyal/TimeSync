import javax.swing.JFrame;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class TimeSyncServerUI {

	private JFrame frame;
	private TimeSyncServerRuntime runtime;
	private int screenWidth, screenHeight;

	public TimeSyncServerUI(String titlebarText, TimeSyncServerRuntime runtime)
	{
		frame = new JFrame();
		this.runtime = runtime;

		GraphicsDevice gd 				= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth 					= gd.getDisplayMode().getWidth();
		screenHeight 					= gd.getDisplayMode().getHeight();

		frame.setSize((int)(screenWidth / 4.5), (int)(screenWidth / 4.5));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

}