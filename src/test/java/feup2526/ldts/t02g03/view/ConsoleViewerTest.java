package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsoleViewerTest{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams(){
        System.setOut(originalOut);
    }

    @Test
    void testDraw(){
        RoadLane lane1 = new RoadLane(Direction.RIGHT, 1, 1);
        lane1.addVehicle(new Bus(new Position(1, 1), Direction.RIGHT));
        RoadLane lane2 = new RoadLane(Direction.LEFT, 1, 3);
        lane2.addVehicle(new Car(new Position(3, 3), Direction.LEFT));
        RoadLane lane3 = new RoadLane(Direction.RIGHT, 1, 4);
        lane3.addVehicle(new Vehicle(new Position(4, 4), Direction.RIGHT));
        Lane dummyLane = Mockito.mock(Lane.class);

        // Mock Data
        Grid mockGrid = Mockito.mock(Grid.class);
        when(mockGrid.getW()).thenReturn(5);
        when(mockGrid.getH()).thenReturn(5);
        when(mockGrid.isInside(any(Position.class))).thenReturn(true);
        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new Position(2, 2));
        Level mockLevel = Mockito.mock(Level.class);
        when(mockLevel.getGrid()).thenReturn(mockGrid);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockLevel.getLanes()).thenReturn(Arrays.asList(lane1, lane2, lane3, dummyLane));

        ConsoleViewer viewer = new ConsoleViewer();
        viewer.draw(mockLevel);
        String output = outContent.toString();

        String expected = "\033[H\033[2J" +
                ".....\n" +
                ".B...\n" +
                "..P..\n" +
                "...C.\n" +
                "....V\n";

        assertEquals(expected, output);
    }
}
