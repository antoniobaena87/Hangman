package hangman;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * This panel shows the letters that have been already checked, both right and wrong.
 * @author antonio
 *
 */
@SuppressWarnings("serial")
public class TriesPanel extends MyPanel {
	
	JLabel[] lettersTried;
	int currentIndex;  // keep track of the next usable index in lettersTried (see addGuess method)
	
	public TriesPanel(int longestWordSize) {
		super(Hangman.TRIES_LABEL);
		setLayout(new FlowLayout());
		
		/*the user will never be able to guess more letters than the longest word minus one plus 6
		 * Why? because any word will have a maximum of letters of the longest word. A user will be able to input 
		 * either six wrong letters (so the minimum input will be 6, all of them wrong) or a maximum of the size of the word minus one
		 * (minus one because otherwise it would mean the user won the game) plus 6 wrong guesses.
		 * I'll illustrate this with an example:
		 * Word: computer (length 8). let's suppose this is also the longest word.
		 * the user guesses c, o, m, p, u, t, e (this is the longest size minus one)
		 * then the user starts guessing all wrong till a maximum of 6 times, then the user loses.
		 * This leaves us with the maximum guesses of length of the longest word minus one plus six
		 * But to avoid hard coding the number of allowed mistakes, I get it from the static final in Hangman class.
		 * I define the maxGuesses variable locally because I will never need it again
		 * (that info will anyways be accessible through lettersTried.length)
		 */
		int maxGuesses = longestWordSize - 1 + Hangman.INITIAL_GUESSES;
		
		// now that we know the max number of guesses, create as many JLabels to show the letters
		lettersTried = new JLabel[maxGuesses];
		for(int i = 0; i < maxGuesses; i++) {
			lettersTried[i] = new JLabel();
			lettersTried[i].setText("");
			this.add(lettersTried[i]);
		}
	}
	
	/**
	 * 
	 * @param letter The letter to add as tested. Notice that this letter should already come as certainly never checked before.
	 */
	protected void addGuess(String letter) {
		lettersTried[currentIndex].setText(letter);
		lettersTried[currentIndex].setVisible(true);
		currentIndex++;
	}
	
	protected void reset() {
		for(JLabel label:lettersTried) {
			if(!label.isVisible()) break;
			label.setVisible(false);
			label.setText("");
		}
		currentIndex = 0;
	}
}
