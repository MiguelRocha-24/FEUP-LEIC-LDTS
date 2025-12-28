package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ShopViewerTest {

    @Test
    void testDrawElements() throws IOException {
        Shop mockShop = mock(Shop.class);
        User mockUser = mock(User.class);
        Skin mockSkin = mock(Skin.class);
        GUI mockGUI = mock(GUI.class);

        // Setup User
        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockUser.getCoins()).thenReturn(100);
        when(mockUser.getOwnedSkins()).thenReturn(new ArrayList<>(Arrays.asList("chicken")));
        when(mockUser.getEquippedSkin()).thenReturn("chicken");

        // Setup Skin
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        when(mockSkin.getName()).thenReturn("fox");
        when(mockSkin.getPrice()).thenReturn(200);

        // Setup GUI dimensions
        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);

        ShopViewer viewer = new ShopViewer(mockShop);
        
        viewer.draw(mockGUI);

        // skin name
        verify(mockGUI, atLeastOnce()).drawText(anyInt(), anyInt(), eq("FOX"), anyString());
        
        // "200 COINS" because user has 100, checking for red color?
        // Logic: if (100 >= 200) -> green else red.
        // Expecting red.
        verify(mockGUI, atLeastOnce()).drawText(anyInt(), anyInt(), contains("200 COINS"), eq("#FF0000"));

        // Button: Should say "UNLOCK" (white) because not owned
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("UNLOCK"), eq("#FFFFFF"));
        
        // Coins display
        verify(mockGUI).drawText(eq(1), eq(1), eq("COINS: 100"), eq("#FFD700"));
    }
}
