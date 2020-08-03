package hangman;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class MyPanel extends JPanel{
	
	TitledBorder border;
	
	public MyPanel(String title) {
		border = BorderFactory.createTitledBorder(title);
		setBorder(border);
	}

}
