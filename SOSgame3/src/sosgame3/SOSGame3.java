//mewhdbfivjhv,jhvb,jhvb,jhb,jhk
//hertyhrtyhertgcfjhftyjuyfkuyfkuy
package sosgame3;

import javax.swing.*;
import java.awt.*;

public class SOSGame3
{
	 private static GameBoard gameBoard;
	 public static JRadioButton redButton = new JRadioButton("Red");
     public static JRadioButton blueButton = new JRadioButton("Blue");
     static JRadioButton sButton = new JRadioButton("S"); 
     static JRadioButton oButton = new JRadioButton("O"); 
     public static JToggleButton recordButton = new JToggleButton("Record");
     
     
	 public static void main(String[] args) 
	 {
	
		
	    SwingUtilities.invokeLater(new Runnable() 
	    {
	    	
	        @Override
	        public void run() 
	        {
	            JFrame frame = new JFrame("SOS Game");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setLayout(new BorderLayout());
	            System.out.println("1--");

	            int boardSize = promptForBoardSize();
	            System.out.println("2--after prompting for board siuze and gen/sim---1");
	            
	           
	            gameBoard = new GameBoard(boardSize); 
	            System.out.println(" after first call to gameBoard const");
	            frame.add(gameBoard, BorderLayout.CENTER);
	            System.out.println(" after 1st frame call");

	            frame.add(initializeControlPanel(), BorderLayout.NORTH);
	            System.out.println(" after 2nd frame call");

	            initializeMenu(frame);
	            System.out.println(" after initializing menu buttons");

	            frame.pack();
	            System.out.println(" after 3rd frame call");
	            frame.setVisible(true);
	            System.out.println(" after 4th frame call");
	            frame.setLocationRelativeTo(null);
	            System.out.println(" after 5th frame call");
	        }
                 

        });
    }
	 
	 
	 
	
	public static void updatePlayerSelection() 
	{
	    if (GameBoard.getCurrentColor() == Color.RED) 
	    {
	        redButton.setSelected(true);
	    } 
	    else 
	    {
	        blueButton.setSelected(true);
	    }
	}
	
	public static String promptForOpponentType() {
        String[] options = {"Human", "Computer", "Computer-vs-Computer"};
        return (String) JOptionPane.showInputDialog(null, 
                "Choose your opponent:", 
                "Select Opponent", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);
    }
	
	public static Color promptForPlayerColor() {
        Object[] options = {"Red", "Blue"};
        int choice = JOptionPane.showOptionDialog(null, "Choose your color", "Color Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        return choice == 0 ? Color.RED : Color.BLUE;
    }
	
	
	
	
	public static JPanel initializeControlPanel() 
	{
        JPanel panel = new JPanel(new GridLayout(2,1));

        // For color choice
        JPanel colorPanel = new JPanel();
        
       
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redButton);
        colorGroup.add(blueButton);
        redButton.setSelected(true);
        redButton.addActionListener(e -> GameBoard.setCurrentColor(Color.RED));
        blueButton.addActionListener(e -> GameBoard.setCurrentColor(Color.BLUE));
        
        colorPanel.add(redButton);
        colorPanel.add(blueButton);
        
        recordButton.addActionListener(e -> gameBoard.toggleRecording());
        // Choose where to add the record button. Here's an example:
        JPanel recordPanel = new JPanel();
        recordPanel.add(recordButton);
        panel.add(recordPanel);
        
        // For letter choice
        JPanel letterPanel = new JPanel();
//        JRadioButton sButton = new JRadioButton("S");
//        JRadioButton oButton = new JRadioButton("O");
        ButtonGroup letterGroup = new ButtonGroup();
        letterGroup.add(sButton);
        letterGroup.add(oButton);
        sButton.setSelected(true);  // By default, "S" will be selected
        sButton.addActionListener(e -> GameBoard.setCurrentLetter("S"));
        oButton.addActionListener(e -> GameBoard.setCurrentLetter("O"));
        letterPanel.add(sButton);
        letterPanel.add(oButton);

        panel.add(colorPanel);
        panel.add(letterPanel);
        return panel;
    }
	
	
	
	//
	public static void initializeMenu(JFrame frame) 
	{
	    JMenuBar menuBar = new JMenuBar();
	    JMenu gameMenu = new JMenu("Menu");
	    JMenuItem newGame = new JMenuItem("New Game");

	    newGame.addActionListener(e -> 
	    {
	        int boardSize = promptForBoardSize();
	        GameBoard.setCurrentLetter("S");
	        sButton.setSelected(true);
	        

	        // Create a new game board with the selected size
	        GameBoard newGameBoard = new GameBoard(boardSize);

	        // Remove the old game board from the frame
	        frame.getContentPane().remove(gameBoard);
	        gameBoard = newGameBoard; // Update the reference to the current game board
	        frame.add(gameBoard, BorderLayout.CENTER);

	        // Refresh the frame contents
	        frame.revalidate();
	        frame.repaint();

	        frame.pack();
	        frame.setLocationRelativeTo(null);
	    });

	    gameMenu.add(newGame);
	    menuBar.add(gameMenu);
	    frame.setJMenuBar(menuBar);
	}
	
	
	

	
	
	public static int promptForBoardSize() 
	{
		System.out.println("3---just entered promptForBoardSize method");
	    Object[] possibleValues = { "3", "4", "5", "6", "7", "8" };
	    int selectedOption = JOptionPane.showOptionDialog(null,
	            "Select a board size:", "New Game",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
	            possibleValues, possibleValues[5]);

	    int boardSize = 8; // default size
	    if (selectedOption >= 0 && selectedOption <= 5) 
	    {
	        boardSize = Integer.parseInt((String) possibleValues[selectedOption]);
	    } 
	    else if (selectedOption == -1) 
	    {
	        // User closed the dialog box
	        System.exit(0); // Close the application
	    }
	    else 
	    {
	        JOptionPane.showMessageDialog(null, "Invalid input. Using default size of 8x8.", "Warning", JOptionPane.WARNING_MESSAGE);
	    }
	    
	    System.out.println("4---done with the code to select size  ");

	    // Prompt for game mode
	    System.out.println("5---game mode selection start  ");
	    Object[] options = {"Simple", "General"};
	    int n = JOptionPane.showOptionDialog(null,
	            "Choose a game mode:",
	            "Game Mode",
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            options,
	            options[1]);

	    if (n == 0) 
	    {
	        GameBoard.setGameMode("simple");
	    } 
	    else 
	    {
	        GameBoard.setGameMode("general");
	    }
        System.out.println("6---about to return board size and exit promptForBoardSize method in SOSGame3 class");
	    return boardSize;
	    
	    
	}



     
	//
	
}
