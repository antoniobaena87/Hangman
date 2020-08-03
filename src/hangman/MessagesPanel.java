package hangman;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MessagesPanel extends MyPanel{
	
	JLabel messageLabel;
	
	static final String WIN_MESSAGE = "Has averiguado la palabra.";
	static final String LOSE_MESSAGE = "Demasiados intentos fallidos.";

	public MessagesPanel() {
		super(Hangman.MESSAGES_LABEL);
		setLayout(new FlowLayout());
		
		messageLabel = new JLabel(LOSE_MESSAGE);
		
		this.add(messageLabel);
	}
	
	protected void showMessage(String message) {
		messageLabel.setText(message);
	}
	
	protected void reset() {
		messageLabel.setText("");
	}
}
