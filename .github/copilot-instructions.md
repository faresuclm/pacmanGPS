# Instrucciones para GitHub Copilot

## Contexto del Proyecto

Este es un proyecto de **Pac-Man** desarrollado en **Java** utilizando **Swing** para la interfaz gráfica. Es un juego clásico con las siguientes características:

- Laberinto donde Pac-Man y los fantasmas se mueven
- Control de Pac-Man mediante teclas de flecha
- Fantasmas con movimiento automático
- Sistema de puntuación con puntos coleccionables
- Condiciones de victoria y derrota

## Estructura del Proyecto

```
src/
├── Game.java         # Clase principal: inicializa la ventana y el juego
├── Board.java        # Lógica y renderizado del tablero
├── Pacman.java       # Lógica y renderizado de Pac-Man
├── Ghost.java        # Lógica y renderizado de los fantasmas
└── Direction.java    # Enum para las direcciones de movimiento
```

## Directrices de Código

### Estilo de Código
- Utilizar **Java 8** o superior
- Seguir las convenciones de nomenclatura de Java (camelCase para métodos y variables, PascalCase para clases)
- Comentar el código en **español**
- Mantener los métodos cortos y con una única responsabilidad

### Arquitectura
- Mantener la separación de responsabilidades entre clases
- `Board.java` maneja la lógica del juego y el renderizado del tablero
- `Pacman.java` y `Ghost.java` manejan sus propias entidades
- `Direction.java` define las direcciones de movimiento como enum

### Interfaz Gráfica
- Utilizar **Java Swing** para todos los componentes de UI
- El renderizado se realiza mediante el método `paintComponent`
- Mantener la interfaz sencilla y modular

## Preferencias de Desarrollo

- Priorizar código limpio y legible sobre optimización prematura
- Incluir manejo de errores apropiado
- Documentar métodos públicos con JavaDoc
- Mantener compatibilidad con Java 8

## Funcionalidades Futuras a Considerar

- Mejoras en la IA de los fantasmas
- Nuevos niveles y mapas
- Efectos de sonido
- Power-ups clásicos del juego
