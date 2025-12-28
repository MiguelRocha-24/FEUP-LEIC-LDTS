package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;

class SpriteViewerTest {

    @Test
    void testConvertToGUIImage() {
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);
        Mockito.when(mockGUI.createOffScreenImage(anyInt(), anyInt())).thenReturn(mockImage);

        BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        
        GUIImage result = SpriteViewer.convertToGUIImage(mockGUI, bufferedImage);
        
        assertNotNull(result);
        Mockito.verify(mockGUI).createOffScreenImage(10, 10);
        Mockito.verify(mockImage).setTransparency(true); // Default image has 0 alpha, so transparency true
    }
}
