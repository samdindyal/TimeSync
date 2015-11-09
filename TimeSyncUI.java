/**
	Title: The "TimeSyncUI" class
	Date Written: November 2015
	Author: Samuel Dindyal, Balin Banh, Danel Tran
	Description: A graphical user interface for the client side of TimeSync.
*/

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class TimeSyncUI implements MouseListener, ActionListener, KeyEventDispatcher{

	private Calendar lastSynchronizedCal;

	private JFrame		frame;
	private JLabel 		title, syncButton, lastSynchronizedDateLabel, lastSynchronizedTimeLabel;
	private JMenuBar	menuBar;
	private JMenuItem 	syncDateMenuItem, syncTimeMenuItem, syncAllMenuItem, toolsMenu, viewMenu, dateAndTimeMenuItem, dateMenuItem, timeMenuItem;
	
	private int 	screenWidth, screenHeight;
	private Color 	glassPaneTest;
 	private String[] date, time;

	private TimeSyncRuntime runtime;
	private DateTimePanel dateTimePanel;
	private ColourScheme syncButtonColourScheme, syncButtonHoverColourScheme;

	public TimeSyncUI(String titleBarText, TimeSyncRuntime runtime)
	{
		
		this.runtime = runtime;

		GraphicsDevice gd 				= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth 					= gd.getDisplayMode().getWidth();
		screenHeight 					= gd.getDisplayMode().getHeight();
		GridBagConstraints c 			= new GridBagConstraints();
		syncButtonColourScheme 			= new ColourScheme(Color.DARK_GRAY, Color.WHITE, "Sync Button Hover Colour Scheme");
		syncButtonHoverColourScheme 	= new ColourScheme(Color.DARK_GRAY.brighter(), TimeSyncLibrary.GREENACCENT_COLOURSCHEME.getForeground(), "Sync Button Hover Colour Scheme");
			
		c.gridx = 0;
		c.gridy = 0;
		
		if (TimeSyncLibrary.RUNNING_OSX)
			System.setProperty("apple.laf.useScreenMenuBar", "true");

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){ e.printStackTrace(); }

		glassPaneTest = new Color(0, 0, 0, 0.7f);

		frame = new JFrame(titleBarText);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setResizable(false);
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(frame);

		dateTimePanel = new DateTimePanel(runtime.getDate(), runtime.getTime(), 32, TimeSyncLibrary.GREENACCENT_COLOURSCHEME);

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

		lastSynchronizedTimeLabel = new JLabel("");
		lastSynchronizedTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		lastSynchronizedTimeLabel.setFont(TimeSyncLibrary.CAPTIONFONT);
		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(lastSynchronizedTimeLabel, false);

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

		createMenuBar();

		frame.setJMenuBar(menuBar);


		sync("DATE_AND_TIME");
		frame.setSize((int)(screenWidth / 4.5), (int)(screenWidth / 4.5));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		syncButton.addMouseListener(this);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	private void sync(String str)
	{
		runtime.sync(str);
	}

	private void createMenuBar()
	{
		menuBar = new JMenuBar();

		// Create tools menu
		toolsMenu = new JMenu("Tools");

		syncAllMenuItem = new JMenuItem("Sync Date and Time");
		syncDateMenuItem = new JMenuItem("Sync Date");
		syncTimeMenuItem = new JMenuItem("Sync Time");

		// Create view menu
		viewMenu = new JMenu("View");

		dateMenuItem = new JMenuItem("Date");
		timeMenuItem = new JMenuItem("Time");
		dateAndTimeMenuItem = new JMenuItem("Date and Time");

		viewMenu.add(dateMenuItem);
		viewMenu.add(timeMenuItem);
		viewMenu.add(dateAndTimeMenuItem);

		toolsMenu.add(syncDateMenuItem);
		toolsMenu.add(syncTimeMenuItem);
		toolsMenu.add(syncAllMenuItem);

		menuBar.add(viewMenu);
		menuBar.add(toolsMenu);

		syncAllMenuItem.addActionListener(this);
		syncDateMenuItem.addActionListener(this);
		syncTimeMenuItem.addActionListener(this);

		runtime.addActionListener(this);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e)
	{
		return false;
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == runtime)
		{
			dateTimePanel.setTime(runtime.getTime());
			dateTimePanel.setDate(runtime.getDate());
			lastSynchronizedDateLabel.setText("<html><i>Last Synchronized: <b>" + runtime.getDate() + "<b></i></html>");
			lastSynchronizedTimeLabel.setText("<html> <i>at<b> " + runtime.getTime() + "</b></i>.</html>");
		}
		else if (e.getSource() == syncDateMenuItem)
			sync("DATE");
		else if (e.getSource() == syncTimeMenuItem)
			sync("TIME");
		else if (e.getSource() == syncAllMenuItem)
			sync("DATE_AND_TIME");
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
			sync("DATE_AND_TIME");
	}
	
	@Override
	public void mouseReleased(MouseEvent e){}
	
	@Override
	public void mouseClicked(MouseEvent e){}	

}