import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ColourScheme {

	private Color 	background, foreground;
	private String 	name;

	public ColourScheme(Color background, Color foreground, String name){

		this.background = background;
		this.foreground = foreground;
		this.name 		= (name != null && !name.equals("")) ? name : "Untitled";
	}

	public void apply(javax.swing.JComponent component, boolean opaque)
	{
		component.setForeground(foreground);
		component.setBackground(background);
		component.setOpaque(opaque);
	}

	public void apply(JFrame frame)
	{
		frame.getContentPane().setForeground(foreground);
		frame.getContentPane().setBackground(background);
	}

	public void preview()
	{
		JFrame frame 	= new JFrame("Preview of \"" + name + "\"");
		Font font 	 	= new Font("Arial", Font.BOLD, 32);
		JLabel content 	= new JLabel("ColorScheme", SwingConstants.CENTER);
		
		GraphicsDevice gd 	= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth 	= gd.getDisplayMode().getWidth();

		frame.setSize(screenWidth / 5, screenWidth / 5);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		content.setFont(font);

		frame.add(content);

		content.setBounds((int)((frame.getWidth() - frame.getWidth())/2.0),
						 (int)((frame.getHeight() - content.getPreferredSize().getHeight())/2.0),
						 (int)(frame.getWidth()),
						 (int)(content.getPreferredSize().getHeight())
						 );

		apply(frame);
		apply(content, false);

		frame.setVisible(true);

	}

	public Color getBackground()
	{
		return background;
	}

	public Color getForeground()
	{
		return foreground;
	}

}