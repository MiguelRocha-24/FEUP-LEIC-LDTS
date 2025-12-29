package feup2526.ldts.t02g03.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LanternaGUIImageTest {

    @Test
    void testDimensions() {
        LanternaGUIImage image = new LanternaGUIImage(10, 5);
        assertEquals(10, image.getWidth());
        assertEquals(5, image.getHeight());
    }

    @Test
    void testTransparency() {
        LanternaGUIImage image = new LanternaGUIImage(10, 5);
        assertFalse(image.hasTransparency());
        
        image.setTransparency(true);
        assertTrue(image.hasTransparency());
    }

    @Test
    void testSetPixel() {
        LanternaGUIImage image = new LanternaGUIImage(10, 5);
        image.setPixel(0, 0, "#FFFFFF");
        
        com.googlecode.lanterna.graphics.TextImage textImage = image.getTextImage();
        com.googlecode.lanterna.TextCharacter tc = textImage.getCharacterAt(0, 0);
        
        assertNotNull(tc);
        assertNotEquals(com.googlecode.lanterna.TextColor.ANSI.DEFAULT, tc.getBackgroundColor());
    }
}
