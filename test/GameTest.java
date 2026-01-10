import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JFrame;

public class GameTest {

    @Test
    public void testGameCreation() {
        Game game = new Game();
        assertNotNull(game);
    }

    @Test
    public void testGameIsJFrame() {
        Game game = new Game();
        assertTrue(game instanceof JFrame);
    }

    @Test
    public void testGameTitle() {
        Game game = new Game();
        assertEquals("Pac-Man", game.getTitle());
    }

    @Test
    public void testGameDefaultCloseOperation() {
        Game game = new Game();
        assertEquals(JFrame.EXIT_ON_CLOSE, game.getDefaultCloseOperation());
    }

    @Test
    public void testGameSize() {
        Game game = new Game();
        assertEquals(400, game.getWidth());
        assertEquals(420, game.getHeight());
    }

    @Test
    public void testGameIsNotResizable() {
        Game game = new Game();
        assertFalse(game.isResizable());
    }

    @Test
    public void testMainMethod() {
        // Verificar que el método main existe y no lanza excepción
        assertDoesNotThrow(() -> {
            // No ejecutamos realmente main porque crearía una ventana visible
            // Solo verificamos que el método existe
            Game.class.getMethod("main", String[].class);
        });
    }

    @Test
    public void testGameHasBoard() {
        Game game = new Game();
        // Verificar que tiene componentes (el Board debería estar agregado)
        assertTrue(game.getContentPane().getComponentCount() > 0);
    }

    @Test
    public void testGameSetVisible() {
        Game game = new Game();
        // No debería lanzar excepción
        assertDoesNotThrow(() -> {
            game.setVisible(false); // Establecer invisible para no mostrar ventana en tests
        });
    }

    @Test
    public void testGameMultipleInstances() {
        Game game1 = new Game();
        Game game2 = new Game();
        
        assertNotNull(game1);
        assertNotNull(game2);
        assertNotSame(game1, game2);
        
        // Limpiar
        game1.dispose();
        game2.dispose();
    }
}
