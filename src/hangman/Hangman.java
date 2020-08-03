package hangman;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Hangman {
	
	JFrame frame;
	JPanel leftPanel, rightPanel;
	WordPanel wordPanel;
	TriesPanel triesPanel;
	LettersPanel lettersPanel;
	MessagesPanel messagesPanel;
	DrawingPanel drawingPanel;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem restartItem, exitItem;
	
	final String[] words = {
		"voodoo",
		"scarlett johansson",
		"chorizo",
		"icebox",
		"thumbscrew",
		"walter white",
		"hullaballoo"
	};
	int longestWordSize;
	
	String selectedWord;
	
	protected final static int INITIAL_GUESSES = 6;
	private int guesses = INITIAL_GUESSES;
	
	static final String TITLE = "Juego del Ahorcado";
	
	static final String WORD_LABEL = "Palabra:";
	static final String TRIES_LABEL = "Intentos:";
	static final String LETTERS_LABEL = "Letras:";
	static final String MESSAGES_LABEL = "Mensajes:";
	
	static final String MENU_LABEL = "Menu";
	static final String MENU_RESTART_LABEL = "Reiniciar";
	static final String MENU_EXIT_LABEL = "Salir";
	
	public Hangman() {
		initFrame();
		initMenu();
		initLeftSide();
		initRightSide();
		
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1, 1));
		menuPanel.add(menuBar);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(1, 2));
		gamePanel.add(leftPanel);
		gamePanel.add(rightPanel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.2;
		frame.add(menuPanel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.8;
		frame.add(gamePanel, gbc);
		frame.pack();
		frame.setVisible(true);
		/*frame.add(menuBar);
		frame.add(leftPanel);
		frame.add(rightPanel);
		frame.pack();
		frame.setVisible(true);*/
		
		restart();
	}
	
	private void initFrame() {
		frame = new JFrame(TITLE);
		frame.setLocation(250, 250);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(getClass().getResource("/assets/icono.gif")).getImage());
		
		//frame.setLayout(new GridLayout(1, 2));
		frame.setLayout(new GridBagLayout());
	}
	
	private void initMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu(MENU_LABEL);
		
		restartItem = new JMenuItem(MENU_RESTART_LABEL);
		restartItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		
		exitItem = new JMenuItem(MENU_EXIT_LABEL);
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		menu.add(restartItem);
		menu.add(exitItem);
		
		menuBar.add(menu);
	}
	
	private void initLeftSide() {
		int longestWordSize = getLongestWordSize(words);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4, 1));
		
		wordPanel = new WordPanel(longestWordSize);
		triesPanel = new TriesPanel(longestWordSize);
		lettersPanel = new LettersPanel(this);
		messagesPanel = new MessagesPanel();
		
		leftPanel.add(wordPanel);
		leftPanel.add(triesPanel);
		leftPanel.add(lettersPanel);
		leftPanel.add(messagesPanel);
	}
	
	private void initRightSide() {
		rightPanel = new JPanel();
		drawingPanel = new DrawingPanel();
		
		rightPanel.add(drawingPanel);
	}
	
	private void restart() {
		guesses = INITIAL_GUESSES;
		selectWord();
		wordPanel.setWord(selectedWord);
		triesPanel.reset();
		lettersPanel.reset();
		messagesPanel.reset();
		drawingPanel.reset();
	}
	
	private void selectWord() {
		int randomIndex = ThreadLocalRandom.current().nextInt(0, words.length);
		selectedWord = words[randomIndex];
	}
	
	/**
	 * Call this class whenever a wrong choice has been made.
	 */
	protected void decreaseGuess() {
		guesses -= 1;
		drawingPanel.showNextImage();
		if(guesses == 0) {
			endGame(MessagesPanel.LOSE_MESSAGE);
		}
		// update the drawing
	}
	
	/**
	 * Call this method any time a new letter is chosen as a guess. From here, all the classes (panels)
	 * will be updated accordingly.
	 * @param letter
	 * @see WordPanel#updateLetter(String)
	 */
	protected void setChosenLetter(String letter) {
		triesPanel.addGuess(letter);
		
		// it's important to call wordPanel.updateLetter the last cause, if called before and the
		// game ends, after the ending of the game triesPanel.addGuess will be executed, adding
		// that guess to the new game.
		if(!wordPanel.updateLetter(letter)) decreaseGuess();
		else {
			if(wordPanel.isWordCompleted()) endGame(MessagesPanel.WIN_MESSAGE);
		}
	}
	
	private int getLongestWordSize(String[] wordsArray) {
		int longestSize = 0;
		for(String word:wordsArray) {
			if(word.length() > longestSize) longestSize = word.length();
		}
		
		return longestSize;
	}
	
	private void endGame(String endMessage) {
		// disable accept button from lettersPanel
		lettersPanel.disableButton();
		messagesPanel.showMessage(endMessage);
	}
}
