package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ShopViewerTest {

    private static class TestableShopViewer extends ShopViewer {
        private final GUIImage mockImage;

        public TestableShopViewer(Shop shop, GUIImage mockImage) {
            super(shop);
            this.mockImage = mockImage;
        }

        @Override
        protected GUIImage getSprite(GUI gui, String skinName) {
            return mockImage;
        }
    }

    @Test
    void testDrawElements_OwnedAndEquipped() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        User mockUser = Mockito.mock(User.class);
        Skin mockSkin = Mockito.mock(Skin.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        // Setup User interactions
        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        
        // Setup Skin interactions
        when(mockSkin.getName()).thenReturn("NiceSkin");
        when(mockSkin.getPrice()).thenReturn(100);

        // Setup User state
        when(mockUser.getCoins()).thenReturn(500);
        when(mockUser.getOwnedSkins()).thenReturn(new java.util.ArrayList<>(java.util.List.of("NiceSkin")));
        when(mockUser.getEquippedSkin()).thenReturn("NiceSkin");

        // Setup GUI interactions
        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);
        when(mockImage.getWidth()).thenReturn(10);


        ShopViewer viewer = new TestableShopViewer(mockShop, mockImage);
        viewer.draw(mockGUI);


        // Check Name Drawing
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("NICESKIN"), eq("#FFFFFF"));
        
        // Check Sprite Drawing
        verify(mockGUI).drawImage(anyInt(), anyInt(), eq(mockImage));

        // Check Status/Button Text for Owned & Equipped
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("UNLOCKED"), eq("#00FF00"));
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("SELECTED"), eq("#FFD700"));
        
        // Check Coins
        verify(mockGUI).drawText(eq(1), eq(1), eq("COINS: 500"), eq("#FFD700"));
    }

    @Test
    void testDrawElements_NotOwned_Affordable() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        User mockUser = Mockito.mock(User.class);
        Skin mockSkin = Mockito.mock(Skin.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        when(mockSkin.getName()).thenReturn("ExpensiveSkin");
        when(mockSkin.getPrice()).thenReturn(200);

        when(mockUser.getCoins()).thenReturn(300); // Can afford
        when(mockUser.getOwnedSkins()).thenReturn(new java.util.ArrayList<>()); // Not owned
        when(mockUser.getEquippedSkin()).thenReturn("Default"); // Not equipped

        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);

        // Act
        ShopViewer viewer = new TestableShopViewer(mockShop, mockImage);
        viewer.draw(mockGUI);

        // Assert
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("200 COINS"), eq("#00FF00")); // Green because affordable
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("UNLOCK"), eq("#FFFFFF"));
    }

    @Test
    void testDrawElements_NotOwned_NotAffordable() throws IOException {

        Shop mockShop = Mockito.mock(Shop.class);
        User mockUser = Mockito.mock(User.class);
        Skin mockSkin = Mockito.mock(Skin.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        when(mockSkin.getName()).thenReturn("SuperSkin");
        when(mockSkin.getPrice()).thenReturn(1000);

        when(mockUser.getCoins()).thenReturn(50); // Cannot afford
        when(mockUser.getOwnedSkins()).thenReturn(new java.util.ArrayList<>());
        when(mockUser.getEquippedSkin()).thenReturn("Default");

        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);

        ShopViewer viewer = new TestableShopViewer(mockShop, mockImage);
        viewer.draw(mockGUI);

        verify(mockGUI).drawText(anyInt(), anyInt(), eq("1000 COINS"), eq("#FF0000")); // Red because not affordable
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("UNLOCK"), eq("#FFFFFF"));
    }

    @Test
    void testDrawElements_Owned_NotEquipped() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        User mockUser = Mockito.mock(User.class);
        Skin mockSkin = Mockito.mock(Skin.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        when(mockSkin.getName()).thenReturn("OwnedSkin");
        when(mockSkin.getPrice()).thenReturn(100);

        when(mockUser.getCoins()).thenReturn(500);
        when(mockUser.getOwnedSkins()).thenReturn(new java.util.ArrayList<>(java.util.List.of("OwnedSkin")));
        when(mockUser.getEquippedSkin()).thenReturn("OtherSkin"); // Owned but not equipped

        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);

        ShopViewer viewer = new TestableShopViewer(mockShop, mockImage);
        viewer.draw(mockGUI);

        verify(mockGUI).drawText(anyInt(), anyInt(), eq("UNLOCKED"), eq("#00FF00"));
        verify(mockGUI).drawText(anyInt(), anyInt(), eq("SELECT"), eq("#FFFFFF"));
    }


    @Test
    void testDrawElements_ImageException() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        User mockUser = Mockito.mock(User.class);
        Skin mockSkin = Mockito.mock(Skin.class);
        GUI mockGUI = Mockito.mock(GUI.class);

        when(mockShop.getUser()).thenReturn(mockUser);
        when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
        when(mockSkin.getName()).thenReturn("BrokenSkin");
        when(mockSkin.getPrice()).thenReturn(100);
        when(mockUser.getOwnedSkins()).thenReturn(new java.util.ArrayList<>());
        when(mockUser.getEquippedSkin()).thenReturn("Default");

        when(mockGUI.getTerminalWidth()).thenReturn(40);
        when(mockGUI.getTerminalHeight()).thenReturn(20);

        ShopViewer viewer = new ShopViewer(mockShop) {
            @Override
            protected GUIImage getSprite(GUI gui, String skinName) {
                throw new RuntimeException("Load failed");
            }
        };

        viewer.draw(mockGUI);

        verify(mockGUI).drawText(anyInt(), anyInt(), eq("Image not found"), eq("#FF0000"));
    }
}
