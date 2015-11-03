import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.InputStream;

public class DateTimePanel extends JPanel implements ActionListener {

	private boolean twelveHourClock;
	private int size, second, minute, hour, dayOfWeek, month, dayOfMonth, year;
	private String date;

	private Font timeFont, dateFont_small, dateFont_large, alt_timeFont;

	private JTextField timeFields[], a_p, m;
	private JLabel timeSeparators[], dateLabel;
	private JPanel clockPanel;
	private JTextArea am_pm;

	private Timer timer;

	public DateTimePanel(int[] date, int[] time, int size)
	{
		setLayout(new GridBagLayout());
		setOpaque(false);
		this.size 		= size;
		second 	 		= time[0];
		minute			= time[1];
		hour 			= time[2];
		dayOfWeek 		= date[0];
		month 			= date[1];
		dayOfMonth 		= date[2];
		year 		  	= date[3];

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
		dateLabel 	= new JLabel(TimeSyncLibrary.getDate(dayOfWeek, month, dayOfMonth, year));
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
			TimeSyncLibrary.GREENACCENT_COLOURSCHEME.apply(timeFields[i], false);

			constraints.gridx++;

			add(timeFields[i], constraints);


			if (i < timeSeparators.length)
			{
				timeSeparators[i] = new JLabel(":", SwingConstants.CENTER);
				timeSeparators[i].setFont(timeFont);

				TimeSyncLibrary.GREENACCENT_COLOURSCHEME.apply(timeSeparators[i], false);
				constraints.gridx++;
				add(timeSeparators[i], constraints);
			}
		}

		constraints.gridx++;
		TimeSyncLibrary.GREENACCENT_COLOURSCHEME.apply(am_pm, false);
		am_pm.setEditable(false);
		am_pm.setFocusable(false);
		am_pm.setFont(alt_timeFont);
		add(am_pm, constraints);

		TimeSyncLibrary.DEFAULT_COLOURSCHEME.apply(dateLabel, false);

		dateLabel.setText(TimeSyncLibrary.getDate(dayOfWeek, month, dayOfMonth, year));
		dateLabel.setFont(dateFont_small);

		updateTime();
		constraints.ipady = 15;
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.gridwidth = 8;

		add(dateLabel, constraints);

		timer.start();
	}

	public void updateTime()
	{
		second 	%= 60;
		minute 	%= 60;
		hour 	%= 24;

		timeFields[0].setText(TimeSyncLibrary.pad((hour == 0) ? 12 : hour));
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
			dayOfMonth++;

		updateTime();


	}

}