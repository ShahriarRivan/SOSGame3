package sosgame3;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GameButtonTest {

    GameButton gameButton;

    @BeforeEach
    void setUp() {
        gameButton = new GameButton(0, 0); // Assuming a button at position (0, 0)
    }

    @Test
    void testButtonInitialization() {
        assertEquals(0, gameButton.getRow());
        assertEquals(0, gameButton.getCol());
    }

  
}
