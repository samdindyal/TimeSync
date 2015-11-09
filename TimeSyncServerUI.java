/**
	Title: The "TimeSyncServerUI" class
	Date Written: November 2015
	Author: Samuel Dindyal, Balin Banh, Danel Tran
	Description: A graphical user interface for the server side of TimeSync.
*/

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TimeSyncServerUI implements MouseListener, ActionListener{

	private JFrame frame;
	private JLabel title, syncButton, lastSynchronizedLabel, lastSynchronizedDateLabel, lastSynchronizedTimeLabel;
	private TimeSyncServerRuntime runtime;
	private DateTimePanel dateTimePanel;
	private int screenWidth, screenHeight;
	private ColourScheme syncButtonActiveColourScheme;

	private Thread initRuntimeThread, runtimeThread;

	public TimeSyncServerUI(String titlebarText)
	{
		frame = new JFrame(titlebarText);
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		runtime = new TimeSyncServerRuntime(this);

		GraphicsDevice gd 				= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth 					= gd.getDisplayMode().getWidth();
		screenHeight 					= gd.getDisplayMode().getHeight();

		GridBagConstraints c 			= new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		dateTimePanel = new DateTimePanel("", "", 32, TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME);

		title = new JLabel("TimeSync Server");
		TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME.apply(title, false);
		title.setFont(TimeSyncLibrary.DEFAULT_TITLEFONT);

		syncButton = new JLabel("Synchronize");
		syncButton.setHorizontalAlignment(JLabel.CENTER);
		syncButton.setFont(TimeSyncLibrary.BODYTEXTFONT);
		syncButtonActiveColourScheme = new ColourScheme(Color.DARK_GRAY, Color.WHITE, "Sync Button Active");

		lastSynchronizedDateLabel = new JLabel("<html><i>Last Synchronized: Never</i></html>");
		lastSynchronizedDateLabel.setHorizontalAlignment(JLabel.CENTER);
		lastSynchronizedDateLabel.setFont(TimeSyncLibrary.CAPTIONFONT);
		TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME.apply(lastSynchronizedDateLabel, false);

		lastSynchronizedTimeLabel = new JLabel("");
		lastSynchronizedTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		lastSynchronizedTimeLabel.setFont(TimeSyncLibrary.CAPTIONFONT);
		TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME.apply(lastSynchronizedTimeLabel, false);

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

		TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME.apply(frame);
		frame.setSize((int)(screenWidth / 4.5), (int)(screenWidth / 4.5));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		syncButton.addMouseListener(this);

		refreshFields();

		try {
			(runtimeThread = new Thread(new Runnable() {
				@Override
				public void run() {
					runtime.openConnection();
				}
			})).start();

		}catch (Exception e){e.printStackTrace();}
	}

	public void refreshFields() {
		dateTimePanel.setTime(runtime.getTime());
		dateTimePanel.setDate(runtime.getDate());
		lastSynchronizedDateLabel.setText("<html><i>Last Synchronized: <b>" + runtime.getDate() + "<b></i></html>");
		lastSynchronizedTimeLabel.setText("<html> <i>at<b> " + runtime.getTime() + "</b></i>.</html>");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == runtime)
		{
			System.out.println("RUNTIME");
			refreshFields();
		}
			
	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			syncButtonActiveColourScheme.apply(syncButton, true);
			
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			TimeSyncLibrary.BLACK_AND_WHITE_COLOURSCHEME.apply(syncButton, false);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getSource() == syncButton)
			refreshFields();
	}
	
	@Override
	public void mouseReleased(MouseEvent e){}
	
	@Override
	public void mouseClicked(MouseEvent e){}	


}