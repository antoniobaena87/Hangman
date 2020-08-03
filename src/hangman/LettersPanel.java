package hangman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LettersPanel extends MyPanel{
	
	private JComboBox<String> lettersBox;
	private ComboBoxModel<String> cbModel;
	private JButton acceptButton;
	
	private Hangman controller;
	
	public LettersPanel(Hangman controller) {
		super(Hangman.LETTERS_LABEL);
		
		this.controller = controller;
		
		lettersBox = new JComboBox<>();
		cbModel = lettersBox.getModel();
		
		acceptButton = new JButton("Aceptar");
		acceptButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLetter();
				
			}
		});
		
		reset();
		
		this.add(lettersBox);
		this.add(acceptButton);
	}
	
	protected void reset() {
		lettersBox.removeAllItems();  // clear the comboBox
		char[] lettersArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for(char letter:lettersArray) {
			lettersBox.addItem(String.valueOf(letter));
		}
		acceptButton.setEnabled(true);
	}
	
	private void selectLetter() {
		String selectedLetter = lettersBox.getSelectedItem().toString();
		lettersBox.removeItem(selectedLetter);
		controller.setChosenLetter(selectedLetter);
	}
	
	protected void disableButton() {
		acceptButton.setEnabled(false);
	}
}
