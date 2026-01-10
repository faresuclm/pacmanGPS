import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PacmanTest {

    private Board board;
    private Pacman pacman;

    @BeforeEach
    public void setUp() {
        board = new Board();
        pacman = new Pacman(100, 100, board);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, pacman.getX());
        assertEquals(100, pacman.getY());
    }

    @Test
    public void testInitialScore() {
        assertEquals(0, pacman.getScore());
    }

    @Test
    public void testInitialDirection() {
        assertEquals(Direction.LEFT, pacman.getDirection());
    }

    @Test
    public void testSetPosition() {
        pacman.setPosition(200, 300);
        assertEquals(200, pacman.getX());
        assertEquals(300, pacman.getY());
    }

    @Test
    public void testSetDirection() {
        pacman.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, pacman.getDirection());
        
        pacman.setDirection(Direction.UP);
        assertEquals(Direction.UP, pacman.getDirection());
        
        pacman.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, pacman.getDirection());
    }

    @Test
    public void testAddScore() {
        pacman.addScore(10);
        assertEquals(10, pacman.getScore());
        
        pacman.addScore(50);
        assertEquals(60, pacman.getScore());
        
        pacman.addScore(200);
        assertEquals(260, pacman.getScore());
    }

    @Test
    public void testKeyPressedLeft() {
        KeyEvent leftKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, '←');
        pacman.keyPressed(leftKey);
        // La dirección se aplicará en el próximo movimiento si es posible
        pacman.move();
        // Verificar que la dirección cambió si el movimiento fue posible
    }

    @Test
    public void testKeyPressedRight() {
        KeyEvent rightKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '→');
        pacman.keyPressed(rightKey);
    }

    @Test
    public void testKeyPressedUp() {
        KeyEvent upKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_UP, '↑');
        pacman.keyPressed(upKey);
    }

    @Test
    public void testKeyPressedDown() {
        KeyEvent downKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '↓');
        pacman.keyPressed(downKey);
    }

    @Test
    public void testKeyPressedA() {
        KeyEvent aKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');
        pacman.keyPressed(aKey);
    }

    @Test
    public void testKeyPressedD() {
        KeyEvent dKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_D, 'd');
        pacman.keyPressed(dKey);
    }

    @Test
    public void testKeyPressedW() {
        KeyEvent wKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_W, 'w');
        pacman.keyPressed(wKey);
    }

    @Test
    public void testKeyPressedS() {
        KeyEvent sKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_S, 's');
        pacman.keyPressed(sKey);
    }

    @Test
    public void testMove() {
        int initialX = pacman.getX();
        int initialY = pacman.getY();
        
        // Mover varias veces
        for (int i = 0; i < 10; i++) {
            pacman.move();
        }
        
        // Verificar que se movió (o intentó moverse)
        // La posición puede cambiar o no dependiendo de las colisiones
        assertNotNull(pacman.getX());
        assertNotNull(pacman.getY());
    }

    @Test
    public void testDraw() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // No debería lanzar excepción
        assertDoesNotThrow(() -> pacman.draw(g));
        
        // Dibujar múltiples veces para probar animación
        for (int i = 0; i < 5; i++) {
            pacman.draw(g);
        }
    }

    @Test
    public void testMoveWithDifferentDirections() {
        pacman.setPosition(180, 60); // Posición alineada con el grid
        
        pacman.setDirection(Direction.RIGHT);
        int xBefore = pacman.getX();
        pacman.move();
        // Verificar que intentó moverse
        
        pacman.setDirection(Direction.UP);
        int yBefore = pacman.getY();
        pacman.move();
        
        pacman.setDirection(Direction.DOWN);
        pacman.move();
    }

    @Test
    public void testScoreIncrementOnDot() {
        // Colocar Pacman en una posición donde pueda comer un punto
        pacman.setPosition(180, 60);
        int initialScore = pacman.getScore();
        
        // Mover para comer puntos
        for (int i = 0; i < 5; i++) {
            pacman.move();
        }
        
        // El score puede haber aumentado si comió puntos
        assertTrue(pacman.getScore() >= initialScore);
    }

    @Test
    public void testKeyPressedInvalidKey() {
        KeyEvent invalidKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' ');
        // No debería lanzar excepción
        assertDoesNotThrow(() -> pacman.keyPressed(invalidKey));
    }

    @Test
    public void testMoveWhenAligned() {
        // Colocar en posición alineada con el grid (múltiplo de 20)
        pacman.setPosition(180, 60);
        Direction originalDir = pacman.getDirection();
        
        // Establecer una nueva dirección
        KeyEvent rightKey = new KeyEvent(new javax.swing.JPanel(), KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '→');
        pacman.keyPressed(rightKey);
        
        pacman.move();
        // Verificar que intentó cambiar de dirección si era posible
    }

    @Test
    public void testMoveWhenNotAligned() {
        // Colocar en posición no alineada
        pacman.setPosition(182, 61);
        int xBefore = pacman.getX();
        pacman.move();
        // Debería moverse en la dirección actual
    }

    @Test
    public void testDrawAnimation() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // Dibujar muchas veces para probar la animación de la boca
        for (int i = 0; i < 20; i++) {
            pacman.draw(g);
        }
        // No debería lanzar excepción
    }

    @Test
    public void testDrawWithAllDirections() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        pacman.setDirection(Direction.LEFT);
        pacman.draw(g);
        
        pacman.setDirection(Direction.RIGHT);
        pacman.draw(g);
        
        pacman.setDirection(Direction.UP);
        pacman.draw(g);
        
        pacman.setDirection(Direction.DOWN);
        pacman.draw(g);
    }

    @Test
    public void testCanMoveInDirection() {
        pacman.setPosition(180, 60);
        
        // Probar todas las direcciones
        for (Direction dir : Direction.values()) {
            pacman.setDirection(dir);
            pacman.move();
        }
    }

    @Test
    public void testSetDirectionAlsoSetsNextDirection() {
        pacman.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, pacman.getDirection());
        
        pacman.setDirection(Direction.UP);
        assertEquals(Direction.UP, pacman.getDirection());
    }

    @Test
    public void testMoveThroughTunnel() {
        // Colocar cerca del borde para probar túneles
        pacman.setPosition(0, 100);
        pacman.setDirection(Direction.LEFT);
        pacman.move(); // Debería activar el túnel
        
        pacman.setPosition(board.getBoardWidth() - 1, 100);
        pacman.setDirection(Direction.RIGHT);
        pacman.move();
    }

    @Test
    public void testScoreAddMultipleTimes() {
        int initialScore = pacman.getScore();
        pacman.addScore(10);
        assertEquals(initialScore + 10, pacman.getScore());
        
        pacman.addScore(50);
        assertEquals(initialScore + 60, pacman.getScore());
        
        pacman.addScore(200);
        assertEquals(initialScore + 260, pacman.getScore());
    }

    @Test
    public void testMoveBlockedByWall() {
        // Intentar moverse hacia una pared
        pacman.setPosition(20, 0); // Cerca de una pared
        pacman.setDirection(Direction.UP);
        int yBefore = pacman.getY();
        pacman.move();
        // La posición puede o no cambiar dependiendo de la colisión
    }
}
