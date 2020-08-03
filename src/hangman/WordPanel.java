package hangman;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.sun.org.glassfish.external.statistics.annotations.Reset;


@SuppressWarnings("serial")
public class WordPanel extends MyPanel {
	
	ArrayList<String> wordList;  // contains all the letters in the word
	JLabel[] letterLabels;
	
	final int longestWordSize;
	int missingLetters;
	
	public WordPanel(int longestWordSize) {
		super(Hangman.WORD_LABEL);
		
		wordList = new ArrayList<>();
		letterLabels = new JLabel[longestWordSize];
		this.longestWordSize = longestWordSize;
		
		for(int i = 0; i < letterLabels.length; i++) {
			letterLabels[i] = new JLabel();
			letterLabels[i].setVisible(false);
			add(letterLabels[i]);
		}
		
	}
	
	protected void setWord(String word) {
		wordList.clear();
		missingLetters = 0;
		// set labels back to blank
		for(JLabel label:letterLabels) {
			if(!label.isVisible()) break;
			label.setVisible(false);
		}
		
		char[] array = word.toCharArray();
		for(int i = 0; i < array.length; i++) {
			wordList.add(String.valueOf(array[i]).toUpperCase());
			if(array[i] != ' ') {
				letterLabels[i].setText("_");
				letterLabels[i].setVisible(true);
				missingLetters++;
			}else if(array[i] == ' ') {
				letterLabels[i].setText(" ");
				letterLabels[i].setVisible(true);
			}
		}
	}
	
	/**
	 * 
	 * @param letter The letter to check. This letter should have already been tested that it hasn't been checked before.
	 * @return true if the letter is in the word, false otherwise.
	 */
	protected boolean updateLetter(String letter) {
		letter = letter.toUpperCase();
		if(wordList.contains(letter)) {
			for(int i = 0; i < wordList.size(); i++) {
				if(wordList.get(i).equals(letter)) {
					letterLabels[i].setText(letter);
					missingLetters--;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	protected boolean isWordCompleted() {
		return missingLetters == 0;
	}
}
