package sosgame3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

class GameBoardTest {
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(5);
    }

    @Test
    void testIsBoardFull() {
        assertFalse(gameBoard.isBoardFull(), "Board should not be full initially");
        
    }

    @BeforeEach
    void setUp1() {
        gameBoard = new GameBoard(5); 
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(gameBoard.buttons);
        assertEquals(5, gameBoard.buttons.length); 
    }

    @Test
    void testResetGame() {
        gameBoard.resetGame();
        assertEquals(Color.RED, GameBoard.getCurrentColor());
        assertEquals("S", GameBoard.getCurrentLetter());
    }

    @Test
    void testSetCurrentColor() {
        GameBoard.setCurrentColor(Color.BLUE);
        assertEquals(Color.BLUE, GameBoard.getCurrentColor());
    }

    @Test
    void testSetCurrentLetter() {
        GameBoard.setCurrentLetter("O");
        assertEquals("O", GameBoard.getCurrentLetter());
    }
}
