import java.util.Calendar;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class TimeSyncUI implements MouseListener{

	private Calendar lastSynchronizedCal;

	private JFrame	frame;
	private JLabel 	title, syncButton, lastSynchronizedDateLabel, lastSynchronizedTimeLabel;
	
	private int 	screenWidth, screenHeight;
	private String 	currentOS;

	private TimeSyncRuntime runtime;
	private DateTimePanel dateTimePanel;
	private ColourScheme syncButtonColourScheme, syncButtonHoverColourScheme;

	public TimeSyncUI(String titleBarText, TimeSyncRuntime runtime)
	{
		
		this.runtime = runtime;

		GraphicsDevice gd 				= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth 					= gd.getDisplayMode().getWidth();
		screenHeight 					= gd.getDisplayMode().getHeight();
		currentOS 						= System.getProperty("os.name");
		GridBagConstraints c 			= new GridBagConstraints();
		syncButtonColourScheme 			= new ColourScheme(Color.DARK_GRAY, Color.WHITE, "Sync Button Hover Colour Scheme");
		syncButtonHoverColourScheme 	= new ColourScheme(Color.DARK_GRAY.brighter(), TimeSyncLibrary.GREENACCENT_COLOURSCHEME.getForeground(), "Sync Button Hover Colour Scheme");
			
		c.gridx = 0;
		c.gridy = 0;
		
		if (currentOS.startsWith("Mac"))
			System.setProperty("apple.laf.useScreenMenuBar", "true");

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){ e.printStackTrace(); }

		frame = new JFrame(titleBarText);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setResizable(false);
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(frame);

		dateTimePanel = new DateTimePanel(runtime.getDate(), runtime.getTime(), 32);

		title = new JLabel("TimeSync");
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(title, false);
		title.setFont(TimeSyncLibrary.DEFAULT_TITLEFONT);

		syncButton = new JLabel("Synchronize");
		syncButton.setHorizontalAlignment(JLabel.CENTER);
		syncButton.setFont(TimeSyncLibrary.BODYTEXTFONT);
		syncButtonColourScheme.apply(syncButton, false);

		lastSynchronizedCal = Calendar.getInstance();
		lastSynchronizedDateLabel = new JLabel("<html><i>Last Synchronized: Never</i></html>");
		lastSynchronizedDateLabel.setHorizontalAlignment(JLabel.CENTER);
		lastSynchronizedDateLabel.setFont(TimeSyncLibrary.CAPTIONFONT);
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(lastSynchronizedDateLabel, false);

		lastSynchronizedTimeLabel = new JLabel("<html><i>Last Synchronized: Never</i></html>");
		lastSynchronizedTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		lastSynchronizedTimeLabel.setFont(TimeSyncLibrary.CAPTIONFONT);
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(lastSynchronizedTimeLabel, false);

		sync();

		frame.add(title, c);

		c.gridy++;
		c.ipady = 25;

		frame.add(dateTimePanel, c);


		c.gridy++;
		c.ipadx = 25;

		frame.add(syncButton, c);

		c.ipadx = 0;
		c.ipady = 0;
		c.gridy++;

		frame.add(lastSynchronizedDateLabel, c);

		c.gridy++;

		frame.add(lastSynchronizedTimeLabel, c);


		frame.setSize((int)(screenWidth / 4.5), (int)(screenWidth / 4.5));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		syncButton.addMouseListener(this);
	}

	public void sync()
	{
		lastSynchronizedCal = Calendar.getInstance();
			lastSynchronizedDateLabel.setText("<html><i>Last Synchronized: <b>" + TimeSyncLibrary.getDate(lastSynchronizedCal.get(Calendar.DAY_OF_WEEK)-1,
																									lastSynchronizedCal.get(Calendar.MONTH),
																									lastSynchronizedCal.get(Calendar.DAY_OF_MONTH),
																									lastSynchronizedCal.get(Calendar.YEAR)) + "<b></i></html>");
			lastSynchronizedTimeLabel.setText("<html> <i>at<b> "+ TimeSyncLibrary.pad((lastSynchronizedCal.get(Calendar.HOUR) == 0) ? 12 : lastSynchronizedCal.get(Calendar.HOUR)) + ":" 
																+ TimeSyncLibrary.pad(lastSynchronizedCal.get(Calendar.MINUTE)) + ":" 
																+ TimeSyncLibrary.pad(lastSynchronizedCal.get(Calendar.SECOND)) 
																+ ((lastSynchronizedCal.get(Calendar.AM_PM) == Calendar.AM) ? " AM" : " PM")  + "</b></i>.</html>");
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			syncButtonHoverColourScheme.apply(syncButton, true);
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			syncButtonColourScheme.apply(syncButton, false);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			sync();
	}
	
	@Override
	public void mouseReleased(MouseEvent e){}
	
	@Override
	public void mouseClicked(MouseEvent e){}	

}