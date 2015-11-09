/**
	Title: The "DateTimePanel" class
	Date Written: November 2015
	Author: Samuel Dindyal, Balin Banh, Danel Tran
	Description: A time panel which display the date and time.
*/

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DateTimePanel extends JPanel implements ActionListener {

	private boolean twelveHourClock;
	private int size, second, minute, hour, dayOfMonth, month, year;
	private String date;

	private Font timeFont, dateFont_small, dateFont_large, alt_timeFont;

	private JTextField timeFields[], a_p, m;
	private JLabel timeSeparators[], dateLabel;
	private JPanel clockPanel;
	private JTextArea am_pm;

	private Timer timer;

	public DateTimePanel(String dateString, String timeString, int size, ColourScheme colourScheme)
	{
		setLayout(new GridBagLayout());
		setOpaque(false);
		this.size 		= size;

		setTime(timeString);
		setDate(dateString);
		

		twelveHourClock = true;

		timeFields 		= new JTextField[3];
		timeSeparators 	= new JLabel[2];

		timeFont 		= TimeSyncLibrary.compileFont("res/OpenSans-Regular.ttf", size);
		alt_timeFont 	= TimeSyncLibrary.compileFont("res/square_sans_serif_7.ttf", size);
		dateFont_large 	= TimeSyncLibrary.compileFont("res/OpenSans-Regular.ttf", size);
		dateFont_small 	= TimeSyncLibrary.compileFont("res/OpenSans-Regular.ttf", size/2);

		timeFields 	= new JTextField[3];
		am_pm 		= new JTextArea();
		clockPanel 	= new JPanel();
		dateLabel 	= new JLabel(date);
		timer 		= new Timer(1000, this);

		am_pm.setText(((hour > 12) ? "P" : "A") + "M");

		dateLabel.setHorizontalAlignment(JLabel.CENTER);

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 20;

		for (int i = 0; i < timeFields.length; i++)
		{
			timeFields[i] = new JTextField("001");
			timeFields[i].setHorizontalAlignment(JTextField.CENTER);
			timeFields[i].setFont(timeFont);
			timeFields[i].setEditable(false);
			colourScheme.apply(timeFields[i], false);

			constraints.gridx++;

			add(timeFields[i], constraints);


			if (i < timeSeparators.length)
			{
				timeSeparators[i] = new JLabel(":", SwingConstants.CENTER);
				timeSeparators[i].setFont(timeFont);

				colourScheme.apply(timeSeparators[i], false);
				constraints.gridx++;
				add(timeSeparators[i], constraints);
			}
		}

		constraints.gridx++;
		colourScheme.apply(am_pm, false);
		am_pm.setEditable(false);
		am_pm.setFocusable(false);
		am_pm.setFont(alt_timeFont);
		add(am_pm, constraints);

		colourScheme.apply(dateLabel, false);

		dateLabel.setText(date);
		dateLabel.setFont(dateFont_small);

		updateTime();
		constraints.ipady = 15;
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.gridwidth = 8;

		add(dateLabel, constraints);

		timer.start();
	}

	public void setTime(String timeString)
	{
		try {

		String[] time = timeString.split(":");
		hour 	 		= Integer.parseInt(time[0]);
		minute			= Integer.parseInt(time[1]);
		second 			= Integer.parseInt(time[2]);

		} catch (Exception e) {
			e.printStackTrace();
			second 	 		= 0;
			minute			= 0;
			hour 			= 0;
		}
	}

	public void setDate(String dateString)
	{
		date = dateString;

		try {
			String[] date = dateString.split("/");
			month 			= Integer.parseInt(date[1]);
			dayOfMonth 		= Integer.parseInt(date[0]);
			year 		  	= Integer.parseInt(date[2]);

		} catch (Exception e) {
			e.printStackTrace();
			month 			= 0;
			dayOfMonth 		= 0;
			year 		  	= 0;
		}
	}

	public void updateDate() {
		date = dayOfMonth + "/" + month + "/" + year;
		dateLabel.setText(date);
	}

	public void updateTime()
	{
		second 	%= 60;
		minute 	%= 60;
		hour 	%= 24;

		timeFields[0].setText(TimeSyncLibrary.pad((hour%12 == 0) ? 12 : hour%12));
		timeFields[1].setText(TimeSyncLibrary.pad(minute));
		timeFields[2].setText(TimeSyncLibrary.pad(second));
		am_pm.setText(((hour > 12) ? "P" : "A") + "M");
	}

	public boolean toggle24HourClock(){
		twelveHourClock = !twelveHourClock;
		return twelveHourClock;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		second++;
		if (second == 60)
			minute++;
		if (minute == 60)
			hour++;
		if (hour == 24)
		{
			dayOfMonth++;
			updateDate();
		}

		updateTime();


	}

}