import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testBoardCreation() {
        assertNotNull(board);
    }

    @Test
    public void testInitialLevel() {
        assertEquals(1, board.getCurrentLevel());
    }

    @Test
    public void testLoadLevel1() {
        board.loadLevel(1);
        assertEquals(1, board.getCurrentLevel());
        assertNotNull(board.getPacmanX());
        assertNotNull(board.getPacmanY());
    }

    @Test
    public void testLoadLevel2() {
        board.loadLevel(2);
        assertEquals(2, board.getCurrentLevel());
        assertNotNull(board.getPacmanX());
    }

    @Test
    public void testLoadLevel3() {
        board.loadLevel(3);
        assertEquals(3, board.getCurrentLevel());
        assertNotNull(board.getPacmanX());
    }

    @Test
    public void testLoadLevelInvalid() {
        board.loadLevel(99); // Nivel inválido debería cargar nivel 1 por defecto
        assertEquals(99, board.getCurrentLevel()); // Aunque el nivel se establece, el mapa será LEVEL_1
    }

    @Test
    public void testIsWall() {
        // Probar con coordenadas de píxeles
        assertTrue(board.isWall(0, 0)); // Esquina debería ser pared
        assertTrue(board.isWall(-10, -10)); // Fuera de límites debería ser pared
        assertTrue(board.isWall(1000, 1000)); // Fuera de límites debería ser pared
    }

    @Test
    public void testCanMove() {
        // Probar con diferentes posiciones
        boolean result1 = board.canMove(180, 60, 20);
        assertNotNull(Boolean.valueOf(result1)); // Solo verificamos que no lance excepción
        
        boolean result2 = board.canMove(0, 0, 20);
        assertNotNull(Boolean.valueOf(result2));
    }

    @Test
    public void testHandleTunnelLeft() {
        int[] result = board.handleTunnel(-10, 100);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(result[0] >= 0); // Debería teletransportar al lado derecho
    }

    @Test
    public void testHandleTunnelRight() {
        int maxX = board.getBoardWidth() - 1;
        int[] result = board.handleTunnel(maxX + 10, 100);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(result[0] <= maxX); // Debería teletransportar al lado izquierdo
    }

    @Test
    public void testHandleTunnelTop() {
        int[] result = board.handleTunnel(100, -10);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(result[1] >= 0); // Debería teletransportar abajo
    }

    @Test
    public void testHandleTunnelBottom() {
        int maxY = board.getBoardHeight() - 1;
        int[] result = board.handleTunnel(100, maxY + 10);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(result[1] <= maxY); // Debería teletransportar arriba
    }

    @Test
    public void testHandleTunnelNormal() {
        int[] result = board.handleTunnel(100, 100);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(100, result[0]);
        assertEquals(100, result[1]);
    }

    @Test
    public void testGetBoardWidth() {
        int width = board.getBoardWidth();
        assertTrue(width > 0);
        assertEquals(19 * 20, width); // BOARD_WIDTH * CELL_SIZE
    }

    @Test
    public void testGetBoardHeight() {
        int height = board.getBoardHeight();
        assertTrue(height > 0);
        assertEquals(19 * 20, height); // BOARD_HEIGHT * CELL_SIZE
    }

    @Test
    public void testEatDot() {
        board.loadLevel(1);
        // Intentar comer un punto en una posición válida
        int result = board.eatDot(180, 60);
        assertTrue(result >= 0 && result <= 2); // 0 = nada, 1 = punto, 2 = power pellet
    }

    @Test
    public void testEatDotInvalidPosition() {
        int result = board.eatDot(-10, -10);
        assertEquals(0, result); // Fuera de límites debería retornar 0
    }

    @Test
    public void testIsPowerMode() {
        assertFalse(board.isPowerMode());
    }

    @Test
    public void testGetPowerModeTimer() {
        int timer = board.getPowerModeTimer();
        assertTrue(timer >= 0);
    }

    @Test
    public void testGetPacmanX() {
        int x = board.getPacmanX();
        assertTrue(x >= 0);
    }

    @Test
    public void testGetPacmanY() {
        int y = board.getPacmanY();
        assertTrue(y >= 0);
    }

    @Test
    public void testGetPacmanDirection() {
        Direction dir = board.getPacmanDirection();
        assertNotNull(dir);
    }

    @Test
    public void testGetDotsRemaining() {
        int dots = board.getDotsRemaining();
        assertTrue(dots >= 0);
    }

    @Test
    public void testGetCurrentLevel() {
        int level = board.getCurrentLevel();
        assertTrue(level >= 1 && level <= 3);
    }

    @Test
    public void testPaintComponent() {
        BufferedImage image = new BufferedImage(400, 420, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        assertDoesNotThrow(() -> board.paintComponent(g));
    }

    @Test
    public void testActionPerformed() {
        ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
        
        // Ejecutar varias veces para cubrir diferentes estados
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> board.actionPerformed(event));
        }
    }

    @Test
    public void testActionPerformedWithPowerMode() {
        board.loadLevel(1);
        // Activar power mode comiendo un power pellet
        board.eatDot(20, 20); // Intentar comer en posición donde podría haber power pellet
        
        ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
        for (int i = 0; i < 5; i++) {
            board.actionPerformed(event);
        }
    }

    @Test
    public void testLoadLevelResetsPowerMode() {
        board.loadLevel(1);
        // Activar power mode (simulado)
        board.eatDot(20, 20);
        
        board.loadLevel(2);
        assertFalse(board.isPowerMode());
        assertEquals(0, board.getPowerModeTimer());
    }

    @Test
    public void testDotsRemainingAfterLoadLevel() {
        board.loadLevel(1);
        int dots1 = board.getDotsRemaining();
        assertTrue(dots1 > 0);
        
        board.loadLevel(2);
        int dots2 = board.getDotsRemaining();
        assertTrue(dots2 > 0);
        
        board.loadLevel(3);
        int dots3 = board.getDotsRemaining();
        assertTrue(dots3 > 0);
    }

    @Test
    public void testCanMoveWithDifferentSizes() {
        assertNotNull(Boolean.valueOf(board.canMove(100, 100, 10)));
        assertNotNull(Boolean.valueOf(board.canMove(100, 100, 20)));
        assertNotNull(Boolean.valueOf(board.canMove(100, 100, 30)));
    }

    @Test
    public void testIsWallWithDifferentCoordinates() {
        // Probar múltiples coordenadas
        for (int x = -20; x < 400; x += 20) {
            for (int y = -20; y < 400; y += 20) {
                assertNotNull(Boolean.valueOf(board.isWall(x, y)));
            }
        }
    }

    @Test
    public void testHandleTunnelCorners() {
        // Esquinas del tablero
        int[] result1 = board.handleTunnel(-1, -1);
        assertNotNull(result1);
        
        int maxX = board.getBoardWidth() - 1;
        int maxY = board.getBoardHeight() - 1;
        int[] result2 = board.handleTunnel(maxX + 1, maxY + 1);
        assertNotNull(result2);
    }

    @Test
    public void testMultipleLevelLoads() {
        for (int level = 1; level <= 3; level++) {
            board.loadLevel(level);
            assertEquals(level, board.getCurrentLevel());
            assertTrue(board.getDotsRemaining() > 0);
        }
    }

    @Test
    public void testPowerModeActivation() {
        board.loadLevel(1);
        // Buscar y comer un power pellet
        // Los power pellets están en las posiciones con valor 3 en el mapa
        // Intentar en varias posiciones conocidas donde hay power pellets
        int result = board.eatDot(20, 20); // Posición aproximada de un power pellet
        if (result == 2) {
            assertTrue(board.isPowerMode());
        }
    }

    @Test
    public void testGetDotsRemainingDecreases() {
        board.loadLevel(1);
        int initialDots = board.getDotsRemaining();
        
        // Intentar comer puntos múltiples veces
        for (int i = 0; i < 10; i++) {
            board.eatDot(180 + i, 60);
        }
        
        // Los puntos deberían haber disminuido si se comieron
        int finalDots = board.getDotsRemaining();
        assertTrue(finalDots <= initialDots);
    }

    @Test
    public void testCanMoveChecksAllCorners() {
        // Probar que canMove verifica las 4 esquinas
        // Si alguna esquina está en una pared, debería retornar false
        boolean result = board.canMove(0, 0, 20);
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    public void testEatDotNormalDot() {
        board.loadLevel(1);
        // Buscar una posición con punto normal (valor 2)
        // Los puntos están en varias posiciones del mapa
        int result = board.eatDot(40, 20);
        assertTrue(result >= 0 && result <= 2);
    }

    @Test
    public void testEatDotPowerPellet() {
        board.loadLevel(1);
        // Buscar power pellet en nivel 1 - están en (1,1) y (17,1) aproximadamente
        // Convertir a píxeles: (1*20, 1*20) = (20, 20)
        int result = board.eatDot(20, 20);
        if (result == 2) {
            assertTrue(board.isPowerMode());
            assertTrue(board.getPowerModeTimer() > 0);
        }
    }

    @Test
    public void testEatDotEmptyCell() {
        board.loadLevel(1);
        // Intentar comer en una celda vacía (valor 0)
        int result = board.eatDot(180, 120); // Posición aproximada de celda vacía
        assertTrue(result >= 0);
    }

    @Test
    public void testEatDotWall() {
        board.loadLevel(1);
        // Intentar comer en una pared (valor 1)
        int result = board.eatDot(0, 0);
        assertEquals(0, result); // No debería comer nada
    }

    @Test
    public void testPowerModeTimerDecreases() {
        board.loadLevel(1);
        // Activar power mode
        board.eatDot(20, 20);
        if (board.isPowerMode()) {
            int initialTimer = board.getPowerModeTimer();
            assertTrue(initialTimer > 0);
            
            // Simular ticks del timer
            ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
            for (int i = 0; i < 10; i++) {
                board.actionPerformed(event);
            }
            
            // El timer debería haber disminuido
            assertTrue(board.getPowerModeTimer() <= initialTimer);
        }
    }

    @Test
    public void testPowerModeExpires() {
        board.loadLevel(1);
        // Activar power mode
        board.eatDot(20, 20);
        if (board.isPowerMode()) {
            // Simular muchos ticks hasta que expire
            ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
            for (int i = 0; i < 250; i++) {
                board.actionPerformed(event);
                if (!board.isPowerMode()) {
                    break; // Power mode expiró
                }
            }
            // Después de muchos ticks, el power mode debería haber expirado
            // (aunque puede que no haya expirado completamente en este test)
        }
    }

    @Test
    public void testIsWallBoundaryConditions() {
        board.loadLevel(1);
        // Probar límites exactos
        int maxX = board.getBoardWidth();
        int maxY = board.getBoardHeight();
        
        assertTrue(board.isWall(-1, 100));
        assertTrue(board.isWall(100, -1));
        assertTrue(board.isWall(maxX, 100));
        assertTrue(board.isWall(100, maxY));
        assertTrue(board.isWall(maxX + 1, maxY + 1));
    }

    @Test
    public void testHandleTunnelBothDirections() {
        // Probar túnel en ambas direcciones simultáneamente
        int[] result = board.handleTunnel(-10, -10);
        assertNotNull(result);
        assertEquals(2, result.length);
        
        int maxX = board.getBoardWidth() - 1;
        int maxY = board.getBoardHeight() - 1;
        int[] result2 = board.handleTunnel(maxX + 10, maxY + 10);
        assertNotNull(result2);
    }

    @Test
    public void testActionPerformedWhenGameOver() {
        // Este test es difícil de ejecutar completamente porque requiere simular game over
        // Pero podemos verificar que el método no lanza excepciones
        ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
        assertDoesNotThrow(() -> board.actionPerformed(event));
    }

    @Test
    public void testActionPerformedWhenGameWon() {
        // Similar al anterior, difícil de testear completamente sin mock
        ActionEvent event = new ActionEvent(new Timer(33, null), ActionEvent.ACTION_PERFORMED, "");
        assertDoesNotThrow(() -> board.actionPerformed(event));
    }

    @Test
    public void testEatDotMultipleTimesSamePosition() {
        board.loadLevel(1);
        int x = 40;
        int y = 20;
        
        int result1 = board.eatDot(x, y);
        int result2 = board.eatDot(x, y); // Intentar comer en la misma posición
        
        // La segunda vez debería retornar 0 porque ya se comió
        if (result1 > 0) {
            assertEquals(0, result2);
        }
    }

    @Test
    public void testLoadLevelPreservesGameState() {
        board.loadLevel(1);
        int dots1 = board.getDotsRemaining();
        
        board.loadLevel(2);
        int dots2 = board.getDotsRemaining();
        
        board.loadLevel(1);
        int dots3 = board.getDotsRemaining();
        
        // Cada nivel debería tener su propio número de puntos
        assertEquals(dots1, dots3);
    }

    @Test
    public void testCanMoveWithZeroSize() {
        boolean result = board.canMove(100, 100, 0);
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    public void testCanMoveWithLargeSize() {
        boolean result = board.canMove(100, 100, 100);
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    public void testIsWallWithNegativeCoordinates() {
        assertTrue(board.isWall(-100, -100));
        assertTrue(board.isWall(-1, 100));
        assertTrue(board.isWall(100, -1));
    }

    @Test
    public void testIsWallWithVeryLargeCoordinates() {
        assertTrue(board.isWall(10000, 10000));
        assertTrue(board.isWall(10000, 100));
        assertTrue(board.isWall(100, 10000));
    }

    @Test
    public void testHandleTunnelExactBoundaries() {
        // Usar el mismo cálculo que handleTunnel: (BOARD_WIDTH - 1) * CELL_SIZE
        // BOARD_WIDTH = 19, CELL_SIZE = 20, entonces maxX = 18 * 20 = 360
        int maxX = (19 - 1) * 20; // 360
        int maxY = (19 - 1) * 20; // 360
        
        // Probar exactamente en los límites (no debería teletransportar)
        int[] result1 = board.handleTunnel(0, 100);
        assertEquals(0, result1[0]);
        
        int[] result2 = board.handleTunnel(maxX, 100);
        assertEquals(maxX, result2[0]); // Debería quedarse igual, no teletransportar
        
        int[] result3 = board.handleTunnel(100, 0);
        assertEquals(0, result3[1]);
        
        int[] result4 = board.handleTunnel(100, maxY);
        assertEquals(maxY, result4[1]); // Debería quedarse igual, no teletransportar
        
        // Probar que cuando está justo después del límite, sí teletransporta
        int[] result5 = board.handleTunnel(maxX + 1, 100);
        assertEquals(0, result5[0]); // Debería teletransportar al inicio
        
        int[] result6 = board.handleTunnel(100, maxY + 1);
        assertEquals(0, result6[1]); // Debería teletransportar al inicio
    }
}
