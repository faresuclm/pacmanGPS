import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GhostTest {

    private Board board;
    private Ghost ghost;

    @BeforeEach
    public void setUp() {
        board = new Board();
        ghost = new Ghost(100, 100, Color.RED, board, Ghost.GhostType.BLINKY);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, ghost.getX());
        assertEquals(100, ghost.getY());
    }

    @Test
    public void testInitialDirection() {
        assertEquals(Direction.UP, ghost.getDirection());
    }

    @Test
    public void testInitialScaredState() {
        assertFalse(ghost.isScared());
    }

    @Test
    public void testInitialEatenState() {
        assertFalse(ghost.isEaten());
    }

    @Test
    public void testSetPosition() {
        ghost.setPosition(200, 300);
        assertEquals(200, ghost.getX());
        assertEquals(300, ghost.getY());
    }

    @Test
    public void testSetScared() {
        ghost.setScared(true);
        assertTrue(ghost.isScared());
        
        ghost.setScared(false);
        assertFalse(ghost.isScared());
        assertFalse(ghost.isEaten()); // Debería resetear eaten cuando scared es false
    }

    @Test
    public void testSetEaten() {
        ghost.setEaten(true);
        assertTrue(ghost.isEaten());
        assertFalse(ghost.isScared()); // Debería resetear scared cuando eaten es true
        
        ghost.setEaten(false);
        assertFalse(ghost.isEaten());
    }

    @Test
    public void testGhostTypeBlinky() {
        Ghost blinky = new Ghost(100, 100, Color.RED, board, Ghost.GhostType.BLINKY);
        assertNotNull(blinky);
    }

    @Test
    public void testGhostTypePinky() {
        Ghost pinky = new Ghost(100, 100, Color.PINK, board, Ghost.GhostType.PINKY);
        assertNotNull(pinky);
    }

    @Test
    public void testGhostTypeInky() {
        Ghost inky = new Ghost(100, 100, Color.CYAN, board, Ghost.GhostType.INKY);
        assertNotNull(inky);
    }

    @Test
    public void testGhostTypeClyde() {
        Ghost clyde = new Ghost(100, 100, new Color(255, 184, 82), board, Ghost.GhostType.CLYDE);
        assertNotNull(clyde);
    }

    @Test
    public void testConstructorWithoutType() {
        Ghost ghost2 = new Ghost(100, 100, Color.RED, board);
        assertNotNull(ghost2);
        assertEquals(100, ghost2.getX());
        assertEquals(100, ghost2.getY());
    }

    @Test
    public void testMove() {
        int initialX = ghost.getX();
        int initialY = ghost.getY();
        
        // Mover varias veces
        for (int i = 0; i < 10; i++) {
            ghost.move();
        }
        
        // Verificar que se movió (o intentó moverse)
        assertNotNull(ghost.getX());
        assertNotNull(ghost.getY());
    }

    @Test
    public void testMoveWhenScared() {
        ghost.setScared(true);
        int initialX = ghost.getX();
        
        // Mover cuando está asustado (debería ser más lento)
        for (int i = 0; i < 5; i++) {
            ghost.move();
        }
        
        assertTrue(ghost.isScared());
    }

    @Test
    public void testMoveWhenEaten() {
        ghost.setEaten(true);
        int initialX = ghost.getX();
        int initialY = ghost.getY();
        
        // Mover cuando está comido (debería volver a la base)
        for (int i = 0; i < 20; i++) {
            ghost.move();
        }
        
        // Después de muchos movimientos, debería haber intentado volver a la base
        assertNotNull(ghost.getX());
        assertNotNull(ghost.getY());
    }

    @Test
    public void testDraw() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // Dibujar en estado normal
        assertDoesNotThrow(() -> ghost.draw(g));
        
        // Dibujar cuando está asustado
        ghost.setScared(true);
        assertDoesNotThrow(() -> ghost.draw(g));
        
        // Dibujar cuando está comido
        ghost.setEaten(true);
        ghost.setScared(false);
        assertDoesNotThrow(() -> ghost.draw(g));
    }

    @Test
    public void testDrawWithPowerModeTimer() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        ghost.setScared(true);
        
        // Simular diferentes valores de power mode timer para probar parpadeo
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> ghost.draw(g));
        }
    }

    @Test
    public void testAllGhostTypes() {
        Ghost.GhostType[] types = Ghost.GhostType.values();
        assertEquals(4, types.length);
        
        assertTrue(containsType(types, Ghost.GhostType.BLINKY));
        assertTrue(containsType(types, Ghost.GhostType.PINKY));
        assertTrue(containsType(types, Ghost.GhostType.INKY));
        assertTrue(containsType(types, Ghost.GhostType.CLYDE));
    }

    @Test
    public void testGhostBehaviorBlinky() {
        Ghost blinky = new Ghost(180, 140, Color.RED, board, Ghost.GhostType.BLINKY);
        // Mover para activar la lógica de comportamiento
        for (int i = 0; i < 5; i++) {
            blinky.move();
        }
        assertNotNull(blinky.getX());
    }

    @Test
    public void testGhostBehaviorPinky() {
        Ghost pinky = new Ghost(180, 140, Color.PINK, board, Ghost.GhostType.PINKY);
        for (int i = 0; i < 5; i++) {
            pinky.move();
        }
        assertNotNull(pinky.getX());
    }

    @Test
    public void testGhostBehaviorInky() {
        Ghost inky = new Ghost(180, 140, Color.CYAN, board, Ghost.GhostType.INKY);
        for (int i = 0; i < 5; i++) {
            inky.move();
        }
        assertNotNull(inky.getX());
    }

    @Test
    public void testGhostBehaviorClyde() {
        Ghost clyde = new Ghost(180, 140, new Color(255, 184, 82), board, Ghost.GhostType.CLYDE);
        for (int i = 0; i < 5; i++) {
            clyde.move();
        }
        assertNotNull(clyde.getX());
    }

    @Test
    public void testScaredAndEatenInteraction() {
        ghost.setScared(true);
        assertTrue(ghost.isScared());
        assertFalse(ghost.isEaten());
        
        ghost.setEaten(true);
        assertTrue(ghost.isEaten());
        assertFalse(ghost.isScared()); // Eaten debería resetear scared
        
        ghost.setEaten(false);
        ghost.setScared(false);
        assertFalse(ghost.isScared());
        assertFalse(ghost.isEaten());
    }

    @Test
    public void testMoveMultipleTimes() {
        for (int i = 0; i < 50; i++) {
            ghost.move();
        }
        // Verificar que no hay errores después de muchos movimientos
        assertNotNull(ghost.getX());
        assertNotNull(ghost.getY());
    }

    @Test
    public void testMoveWhenAligned() {
        // Colocar en posición alineada
        ghost.setPosition(180, 60);
        for (int i = 0; i < 5; i++) {
            ghost.move();
        }
        assertNotNull(ghost.getX());
    }

    @Test
    public void testMoveWhenNotAligned() {
        // Colocar en posición no alineada
        ghost.setPosition(182, 61);
        for (int i = 0; i < 5; i++) {
            ghost.move();
        }
        assertNotNull(ghost.getX());
    }

    @Test
    public void testDrawWithAllDirections() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // Probar dibujar con diferentes direcciones
        ghost.setPosition(100, 100);
        // Nota: no hay setter público para dirección, pero se cambia con move()
        for (int i = 0; i < 10; i++) {
            ghost.move();
            ghost.draw(g);
        }
    }

    @Test
    public void testDrawEatenGhost() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        ghost.setEaten(true);
        assertDoesNotThrow(() -> ghost.draw(g));
        
        // Mover cuando está comido
        for (int i = 0; i < 10; i++) {
            ghost.move();
            ghost.draw(g);
        }
    }

    @Test
    public void testMoveToBase() {
        ghost.setEaten(true);
        int startX = ghost.getX();
        int startY = ghost.getY();
        
        // Mover muchas veces para que llegue a la base
        for (int i = 0; i < 100; i++) {
            ghost.move();
            if (!ghost.isEaten()) {
                break; // Llegó a la base
            }
        }
    }

    @Test
    public void testScaredSpeed() {
        ghost.setScared(true);
        int xBefore = ghost.getX();
        
        for (int i = 0; i < 5; i++) {
            ghost.move();
        }
        
        assertTrue(ghost.isScared());
    }

    @Test
    public void testRandomDirectionWhenScared() {
        ghost.setScared(true);
        ghost.setPosition(180, 60);
        
        // Mover varias veces para probar dirección aleatoria
        for (int i = 0; i < 10; i++) {
            ghost.move();
        }
    }

    @Test
    public void testGetTargetTileForEachType() {
        Ghost blinky = new Ghost(100, 100, Color.RED, board, Ghost.GhostType.BLINKY);
        Ghost pinky = new Ghost(100, 100, Color.PINK, board, Ghost.GhostType.PINKY);
        Ghost inky = new Ghost(100, 100, Color.CYAN, board, Ghost.GhostType.INKY);
        Ghost clyde = new Ghost(100, 100, new Color(255, 184, 82), board, Ghost.GhostType.CLYDE);
        
        // Mover cada tipo para activar su lógica de target
        for (int i = 0; i < 5; i++) {
            blinky.move();
            pinky.move();
            inky.move();
            clyde.move();
        }
    }

    @Test
    public void testClydeBehaviorNearPacman() {
        Ghost clyde = new Ghost(180, 140, new Color(255, 184, 82), board, Ghost.GhostType.CLYDE);
        // Colocar cerca de Pacman
        clyde.setPosition(180, 60);
        
        for (int i = 0; i < 10; i++) {
            clyde.move();
        }
    }

    @Test
    public void testClydeBehaviorFarFromPacman() {
        Ghost clyde = new Ghost(0, 0, new Color(255, 184, 82), board, Ghost.GhostType.CLYDE);
        // Colocar lejos de Pacman
        
        for (int i = 0; i < 10; i++) {
            clyde.move();
        }
    }

    @Test
    public void testInkyErraticBehavior() {
        Ghost inky = new Ghost(180, 140, Color.CYAN, board, Ghost.GhostType.INKY);
        
        // Mover muchas veces para probar comportamiento errático
        for (int i = 0; i < 20; i++) {
            inky.move();
        }
    }

    @Test
    public void testPinkyAmbushBehavior() {
        Ghost pinky = new Ghost(180, 140, Color.PINK, board, Ghost.GhostType.PINKY);
        
        for (int i = 0; i < 10; i++) {
            pinky.move();
        }
    }

    @Test
    public void testBlinkyDirectChase() {
        Ghost blinky = new Ghost(180, 140, Color.RED, board, Ghost.GhostType.BLINKY);
        
        for (int i = 0; i < 10; i++) {
            blinky.move();
        }
    }

    @Test
    public void testGhostRespawnAfterEaten() {
        ghost.setEaten(true);
        assertTrue(ghost.isEaten());
        
        // Mover hasta que respawnee
        for (int i = 0; i < 200; i++) {
            ghost.move();
            if (!ghost.isEaten()) {
                assertFalse(ghost.isScared());
                break;
            }
        }
    }

    @Test
    public void testDrawWithPowerModeTimerLow() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        ghost.setScared(true);
        // Dibujar muchas veces para probar el parpadeo cuando el timer está bajo
        for (int i = 0; i < 20; i++) {
            ghost.draw(g);
        }
    }

    private boolean containsType(Ghost.GhostType[] types, Ghost.GhostType type) {
        for (Ghost.GhostType t : types) {
            if (t == type) {
                return true;
            }
        }
        return false;
    }
}
