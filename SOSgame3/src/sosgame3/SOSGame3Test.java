package sosgame3;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

import javax.swing.JPanel;

class SOSGame3Test {

    @Test
    void testUpdatePlayerSelectionRed() {
        SOSGame3.updatePlayerSelection();
        assertTrue(SOSGame3.redButton.isSelected());
    }

    @Test
    void testUpdatePlayerSelectionBlue() {
        GameBoard.setCurrentColor(Color.BLUE);
        SOSGame3.updatePlayerSelection();
        assertTrue(SOSGame3.blueButton.isSelected());
    }
}

  

