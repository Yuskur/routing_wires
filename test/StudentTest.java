import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class StudentTest {

    @Test
    public void test(){
        testWire0();
        testWire1();
        testWire2();
        testWire3();
        testWire4();
        testWire5();
        smallInput();
        largeInput();
    }

    @Test
    public void testWire0() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire0.in");
        });
    }
    @Test
    public void testWire1() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire1.in");
        });
    }

    @Test
    public void testWire2() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire2.in");
        });
    }

    @Test
    public void testWire3() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire3.in");
        });
    }
    @Test
    public void testWire4() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire4.in");
        });
    }
    @Test
    public void testWire5() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire5.in");
        });
    }

    @Test
    public void smallInput(){
        int height = 10;
        int width = 10;
        long start = System.currentTimeMillis();
        ArrayList<Endpoints> goals = getGoals(height, width);
        Board board = new Board(height, width, goals, new ArrayList<>());
        Routing.findPaths(board, goals);
        long end = System.currentTimeMillis();
        long wall_clockTime = end - start;

        System.out.println("Wall-Clock time (small input): " + wall_clockTime + " millisecond");
    }
    @Test
    public void largeInput(){
        int height = 100;
        int width = 100;
        long start = System.currentTimeMillis();
        ArrayList<Endpoints> goals = getGoals(height, width);
        Board board = new Board(height, width, goals, new ArrayList<>());
        Routing.findPaths(board, goals);
        long end = System.currentTimeMillis();
        long wall_clockTime = end - start;

        System.out.println("Wall-Clock time (large input): " + wall_clockTime + " millisecond");
    }

    public ArrayList<Endpoints> getGoals(int height, int width){
        ArrayList<Endpoints> goals = new ArrayList<>();
        ArrayList<Coord> endpointList = new ArrayList<>();
        Random random = new Random();
        int routes = height/2;
        int i = 1;

        while(i - 1 < routes) {
            Coord start = new Coord(random.nextInt(height), random.nextInt(width));
            Coord end = new Coord(random.nextInt(height), random.nextInt(width));
            if(!endpointList.contains(start) && !endpointList.contains(end)) {
                Endpoints endpoints = new Endpoints(i, start, end);
                goals.add(endpoints);
                endpointList.add(start);
                endpointList.add(end);
                i++;
            }
        }
        return goals;
    }
}
