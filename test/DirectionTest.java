import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testLeftDirection() {
        Direction left = Direction.LEFT;
        assertEquals(180, left.getAngle());
    }

    @Test
    public void testRightDirection() {
        Direction right = Direction.RIGHT;
        assertEquals(0, right.getAngle());
    }

    @Test
    public void testUpDirection() {
        Direction up = Direction.UP;
        assertEquals(90, up.getAngle());
    }

    @Test
    public void testDownDirection() {
        Direction down = Direction.DOWN;
        assertEquals(270, down.getAngle());
    }

    @Test
    public void testAllDirectionsExist() {
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
        assertTrue(containsDirection(directions, Direction.LEFT));
        assertTrue(containsDirection(directions, Direction.RIGHT));
        assertTrue(containsDirection(directions, Direction.UP));
        assertTrue(containsDirection(directions, Direction.DOWN));
    }

    @Test
    public void testValueOf() {
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
        assertEquals(Direction.UP, Direction.valueOf("UP"));
        assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
    }

    private boolean containsDirection(Direction[] directions, Direction dir) {
        for (Direction d : directions) {
            if (d == dir) {
                return true;
            }
        }
        return false;
    }
}
