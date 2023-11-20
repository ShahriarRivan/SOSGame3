package sosgame3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

//ertegtdyjdytukfuykr


public class GameBoard extends JPanel 
{
	private final int BOARD_SIZE;
	public GameButton[][] buttons;
	private char[][] boardValues;
	private static Color currentColor = Color.RED;
	private static String gameMode = "general"; // default mode
	//fgbdhghndgyfhndyt
	private int redScore = 0;
	private int blueScore = 0;
	private static boolean isSTurn = true;  // Start with "S" turn
	private static String currentLetter = "S"; // Default letter
	private String opponentType;
	public Color playerColor;



	public GameBoard(int boardSize) {
		
					System.out.println("7--- just entered GameBoard class-> GameBoard constructor  ");
	    resetGame(); // Reset the game variables
	    this.BOARD_SIZE = boardSize;
	    boardValues = new char[BOARD_SIZE][BOARD_SIZE];
	    setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
	    buttons = new GameButton[BOARD_SIZE][BOARD_SIZE];
	    
	 
        
        this.opponentType = SOSGame3.promptForOpponentType();
        			System.out.println(" 11---- player type prompt-1 ");
	    initializeButtons();
	    
	    if ("Computer-vs-Computer".equals(opponentType)) {
	        startComputerVsComputerGame();
	    }
	    else if ("Computer".equals(opponentType)) {
            // Set up game against computer
	    	playerColor = SOSGame3.promptForPlayerColor();
	    	setCurrentColor(playerColor);
	        Color computerColor = (playerColor == Color.RED) ? Color.BLUE : Color.RED;
	    					System.out.println("10---prompted for player color/ done with GameBoard const-1  ");
        }
	}

	private void startComputerVsComputerGame() 
	{
		System.out.println("GameBoard -> startComputerVsComputerGame(");
		
		
		Timer timer = new Timer(1000, new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
		    	System.out.println("after 1 second timer");
		    	
		    
		    
		    	makeComputerMove();
		    	
		        if (isBoardFull()) 
		        {
		            ((Timer)e.getSource()).stop();
		            displayEndOfGamePopup((JFrame) SwingUtilities.getWindowAncestor(buttons[0][0]));
		        }
		    }
		});
		timer.setRepeats(false); // Ensure it only runs once
		timer.start();
	}
	
	
	
	public void resetGame() {
		System.out.println("GameBoard -> resetGame");
	    currentColor = Color.RED;
	    currentLetter = "S";
	    isSTurn = true;
	    redScore = 0;
	    blueScore = 0;
	    System.out.println("8---entered and exiting resetGame method ");
	}
	
	public void setRedScore(int score)
	{
		System.out.println("GameBoard -> setRedScore");
		redScore=score;
	}
	
	public void setBlueScore(int score)
	{System.out.println("GameBoard -> setBlueScore");
		blueScore=score;
	}
	
	public void resetScores()
	{System.out.println("GameBoard -> ResetScore");
		blueScore=0;
		redScore=0;
	}
	

	
	public static void setCurrentColor(Color color) 
	{
	    currentColor = color;
	}

	public static Color getCurrentColor() 
	{
	    return currentColor;
	}
	
	public static void setCurrentLetter(String letter) 
	{
	    currentLetter = letter;
	}

	public static String getCurrentLetter() 
	{
	    return currentLetter;
	}
	
	public static void setGameMode(String mode) 
	{
	    gameMode = mode;
	}

	
	public void displayEndOfGamePopup(JFrame frame) {
		System.out.println("GameBoard -> displayEndOfGamePopup(JFrame frame)");
	    String winner;
	    if ("simple".equals(gameMode)) {
	        winner = redScore > 0 ? "Red" : "Blue";
	    } else {
	        winner = redScore > blueScore ? "Red" : "Blue";
	        if (redScore == blueScore) {
	            winner = "It's a tie!";
	        }
	    }
	    String message = "Scores:\nRed: " + redScore + "\nBlue: " + blueScore + "\nWinner: " + winner;

	    Object[] options = {"Start New Game", "Close"};

	    int choice = JOptionPane.showOptionDialog(frame, message, "Game Over",
	            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

	    if (choice == JOptionPane.YES_OPTION) { // Start New Game
	        int boardSize = SOSGame3.promptForBoardSize();
	        frame.getContentPane().removeAll();
	        frame.add(new GameBoard(boardSize), BorderLayout.CENTER);
	        frame.add(SOSGame3.initializeControlPanel(), BorderLayout.NORTH);
	        SOSGame3.initializeMenu(frame);
	        frame.validate(); // Refresh the frame to display the new components
	        frame.repaint();
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	    } else if (choice == JOptionPane.NO_OPTION) { // Close the game
	        frame.dispose();
	    }
	}



	
	public boolean isBoardFull() 
	{
		System.out.println("GameBoard -> isBoardFull()");
	    for (int i = 0; i < BOARD_SIZE; i++) 
	    {
	        for (int j = 0; j < BOARD_SIZE; j++) 
	        {
	            if ("".equals(buttons[i][j].getText())) 
	            {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	

	public int getRedScore() 
	{
	    return redScore;
	}

	public int getBlueScore() 
	{
	    return blueScore;
	}
	
	
	private void makeComputerMove() 
	{
		Timer timer = new Timer(500, new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
		
		
		
		System.out.println("GameBoard -> makeComputerMove()");
	    // Save the current letter to restore it later
	    String savedLetter = currentLetter;

	    // Randomly choose between 'S' and 'O' for the computer's move
	    Random random = new Random();
	    currentLetter = random.nextBoolean() ? "S" : "O";

	    // First, try to make an SOS sequence if possible
	    if (!attemptToMakeSOS()) {
	        // If not possible, place a letter in a position that doesn't allow the opponent to create SOS
//            placeLetterSafely();
	    	 makeRandomMove();
	    	
	    }

	    // Update scores and check if the game is over
	    if (isBoardFull()) 
	    {
	        displayEndOfGamePopup((JFrame) SwingUtilities.getWindowAncestor(buttons[0][0]));
	    } 
	    else 
	    {
	        // Toggle the player for the next turn
	        togglePlayer();

	        // In Computer-vs-Computer mode, continue playing
	        if ("Computer-vs-Computer".equals(opponentType)) 
	        {
	            SwingUtilities.invokeLater(() -> {
	                if (!isBoardFull()) {
	                    makeComputerMove();
	                }
	            });
	        }
	    }

	    // Restore the original letter
	    currentLetter = savedLetter;
	    
	    
		    }
		});
		timer.setRepeats(false); // Ensure it only runs once
		timer.start();
	    
	    
	    
	    
	    
	}



	private boolean attemptToMakeSOS() {
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        for (int j = 0; j < BOARD_SIZE; j++) {
	            if ("".equals(buttons[i][j].getText())) {
	                // Temporarily set the letter to check for potential SOS
	                buttons[i][j].setText(currentLetter);
	                if (canMakeSOS(i, j)) {
	                    buttons[i][j].setForeground(currentColor);
	                    checkForSOS(i, j);
	                    return true;
	                    
	                } else {
	                    // Reset if no SOS can be made
	                    buttons[i][j].setText("");
	                }
	            }
	        }
	    }
	  
	       
	       
	    return false;
	}
	
	private void placeLetterSafely() {
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        for (int j = 0; j < BOARD_SIZE; j++) {
	            if ("".equals(buttons[i][j].getText())) {
	                buttons[i][j].setText(currentLetter);
	                if (!canOpponentMakeSOS(i, j)) {
	                    buttons[i][j].setForeground(currentColor);
	                    checkForSOS(i, j);
	                    return;
	                } else {
	                    // Reset if the opponent can make SOS
	                    buttons[i][j].setText("");
	                }
	            }
	        }
	    }
	    // If no safe move is found, just make a random move
	    makeRandomMove();
	}
	
	private void makeRandomMove() {
	    Random rand = new Random();
	    while (true) 
	    {
	        int row = rand.nextInt(BOARD_SIZE);
	        int col = rand.nextInt(BOARD_SIZE);
	        if ("".equals(buttons[row][col].getText())) {
	            buttons[row][col].setText(currentLetter);
	            buttons[row][col].setForeground(currentColor);
	            break;
	        }
	    }
	}
	
	private boolean canMakeSOS(int row, int col) {
	    String[] potentialMatches = {
	        getStringValue(row, col - 1) + getStringValue(row, col) + getStringValue(row, col + 1), // Horizontal
	        getStringValue(row - 1, col) + getStringValue(row, col) + getStringValue(row + 1, col), // Vertical
	        getStringValue(row - 1, col - 1) + getStringValue(row, col) + getStringValue(row + 1, col + 1), // Diagonal
	        getStringValue(row - 1, col + 1) + getStringValue(row, col) + getStringValue(row + 1, col - 1)  // Diagonal
	    };

	    for (String sequence : potentialMatches) {
	        if ("SOS".equals(sequence)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private boolean canOpponentMakeSOS(int row, int col) {
	    // Temporarily set the letter to the opponent's letter
	    String originalLetter = currentLetter;
	    currentLetter = currentLetter.equals("S") ? "O" : "S";

	    boolean canMake = canMakeSOS(row, col);

	    // Reset the letter to the original
	    currentLetter = originalLetter;

	    return canMake;
	}







	private void initializeButtons() 
	{
		System.out.println("GameBoard -> initializeButtons() ");
		
	    for (int i = 0; i < BOARD_SIZE; i++) 
	    {System.out.println("GameBoard -> initializeButtons() -> for loop i");
	        for (int j = 0; j < BOARD_SIZE; j++) 
	        {System.out.println("GameBoard -> initializeButtons() -> for loop j");
	            GameButton btn = new GameButton(i, j);
	            btn.addActionListener(new ActionListener() 
	            {
	                
	            	@Override
	            	public void actionPerformed(ActionEvent e) 
	            	{System.out.println("GameBoard -> initializeButtons() -> action performed");
	            	    GameButton source = (GameButton) e.getSource();
	            	    if ("".equals(source.getText())) 
	            	    {System.out.println("GameBoard -> initializeButtons() -> if (\"\".equals(source.getText())) ");
	            	        source.setText(GameBoard.getCurrentLetter());
	            	        source.setForeground(GameBoard.getCurrentColor());
	            	        
	            	       
	            	        checkForSOS(source.getRow(), source.getCol());
	            	        togglePlayer();
	            	        SOSGame3.updatePlayerSelection();


	            	        if (isBoardFull()) 
	            	        {
	            	            displayEndOfGamePopup((JFrame) SwingUtilities.getWindowAncestor(source));
	            	        }
	   
	            	        if ("Computer".equals(opponentType) && !currentColor.equals(playerColor)) {
	            	        	System.out.println("GameBoard -> initializeButtons() ->  if computer");
	            	            SwingUtilities.invokeLater(() -> makeComputerMove());
	            	        }
	            	    }
	            	}


	            });
	            buttons[i][j] = btn;
	            add(btn);
	        }
	    }
	}
	
	public void checkForSOS(int row, int col) 
	{
	    // Potential sequences
	    String[] potentialMatches = {
	        getStringValue(row, col - 1) + getStringValue(row, col) + getStringValue(row, col + 1), // Horizontal
	        getStringValue(row - 1, col) + getStringValue(row, col) + getStringValue(row + 1, col), // Vertical
	        getStringValue(row - 1, col - 1) + getStringValue(row, col) + getStringValue(row + 1, col + 1), // Diagonal top-left to bottom-right
	        getStringValue(row - 1, col + 1) + getStringValue(row, col) + getStringValue(row + 1, col - 1), // Diagonal top-right to bottom-left
	        
	        getStringValue(row, col - 2) + getStringValue(row, col-1) + getStringValue(row, col), // Horizontal-left
	        getStringValue(row , col) + getStringValue(row, col+1) + getStringValue(row, col+2), // Horizontal-right
	        
	        getStringValue(row-2, col) + getStringValue(row-1, col) + getStringValue(row, col), // Vertical-top
	        getStringValue(row, col) + getStringValue(row+1, col) + getStringValue(row + 2, col), // Vertical-bottom
	        
	        getStringValue(row-2, col - 2) + getStringValue(row-1, col-1) + getStringValue(row, col), // D-T-L
	        getStringValue(row , col) + getStringValue(row+1, col+1) + getStringValue(row + 2, col+2), // D-B-R
	        
	        getStringValue(row+2, col-2) + getStringValue(row+1, col-1) + getStringValue(row, col), // D-B-L
	        getStringValue(row, col) + getStringValue(row-1, col+1) + getStringValue(row-2, col+2) // D-T-R
	    };
	    for (String sequence : potentialMatches) 
	    {
	        if ("SOS".equals(sequence)) 
	        {
	            if (getCurrentColor() == Color.RED) 
	            {
	                redScore++;
	            } 
	            else 
	            {
	                blueScore++;
	            }

	            if ("simple".equals(gameMode) && (redScore > 0 || blueScore > 0)) 
	            {
	                displayEndOfGamePopup((JFrame) SwingUtilities.getWindowAncestor(buttons[row][col]));
	                return; // Exit the method early since the game is over
	            }
	        }
	    }

	    
	    System.out.println("H   : "+potentialMatches[0]);
	    System.out.println("V   : "+potentialMatches[1]);
	    System.out.println("DL  : "+potentialMatches[2]);
	    System.out.println("DR  : "+potentialMatches[3]);
	}


	private String getStringValue(int i, int j) 
	{
	    if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE) 
	    {
	        return buttons[i][j].getText();
	    }
	    return "";
	}
	
	public void togglePlayer() 
	{
	    if (currentColor == Color.RED) 
	    {
	        currentColor = Color.BLUE;
	    } 
	    else 
	    {
	        currentColor = Color.RED;
	    }
	}


}


