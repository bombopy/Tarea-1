# 🎮 SPACE INVADERS GRÁFICO - PATRONES DE DISEÑO


## 🚀 CÓMO EJECUTAR EL JUEGO GRÁFICO

### Compilar y ejecutar:
```bash
javac SpaceInvadersGraphic.java
java SpaceInvadersGraphic
```

### Controles del juego:
- **A / Flecha Izquierda**: Mover nave a la izquierda
- **D / Flecha Derecha**: Mover nave a la derecha  
- **ESPACIO**: Disparar
- **R**: Reiniciar juego (cuando termina la partida)

## 🎯 CARACTERÍSTICAS DEL JUEGO

### Mecánicas de Juego:
- **Objetivo**: Destruir todos los enemigos de cada oleada
- **Vidas**: Comienzas con 3 vidas
- **Progresión**: Los niveles se vuelven más difíciles
- **Mejoras**: Tu arma se mejora cada 3 niveles (disparo triple)
- **Puntuación**: Diferentes enemigos dan diferentes puntos

### Tipos de Enemigos:
1. **Invasor Pequeño** (Verde)
   - Vida: 1 punto
   - Puntos: 10
   - Movimiento: Lineal descendente

2. **Invasor Mediano** (Azul)
   - Vida: 2 puntos
   - Puntos: 25
   - Movimiento: Zigzag

3. **Invasor Grande** (Rojo)
   - Vida: 3 puntos
   - Puntos: 50
   - Movimiento: Agresivo rápido

## 🔧 PATRONES DE DISEÑO EN ACCIÓN

### 🎯 PATRÓN SINGLETON - GameManager
**¿Cómo se ve en el juego?**
- La puntuación se mantiene consistente en toda la partida
- El nivel y las vidas se gestionan globalmente
- Solo existe una instancia del gestor del juego

**Implementación Visual:**
```java
// El UI siempre muestra el estado actual del único GameManager
g.drawString("Puntuacion: " + GameManager.getInstance().getScore(), 10, 25);
g.drawString("Nivel: " + GameManager.getInstance().getLevel(), 10, 45);
g.drawString("Vidas: " + GameManager.getInstance().getLives(), 10, 65);
```

### 🎯 PATRÓN STRATEGY - Comportamientos Dinámicos
**¿Cómo se ve en el juego?**
- Diferentes enemigos se mueven de manera distinta
- La nave cambia de arma automáticamente cada 3 niveles
- Los enemigos disparan con diferentes patrones

**Estrategias de Movimiento Visibles:**
1. **LinearMovement**: Enemigos que bajan en línea recta
2. **ZigzagMovement**: Enemigos que se mueven en zigzag
3. **PlayerMovement**: Control suave de la nave del jugador

**Estrategias de Disparo Visibles:**
1. **SingleShot**: Disparo simple (amarillo)
2. **TripleShot**: Disparo triple mejorado (cian)
3. **EnemyShot**: Disparos enemigos (rojo)

### 🎯 PATRÓN FACTORY - Creación Dinámica
**¿Cómo se ve en el juego?**
- Cada nivel genera diferentes tipos y cantidades de enemigos
- Los enemigos se crean automáticamente según el nivel actual
- La fábrica decide qué tipo de enemigo crear basándose en la dificultad

**Implementación Visual:**
```java
// Niveles 1-2: Solo enemigos pequeños
// Niveles 3-4: Mezcla de pequeños y medianos  
// Nivel 5+: Todos los tipos incluyendo grandes
public static List<GameObject> createWave(int level) {
    // La fábrica decide dinámicamente qué crear
}
```

## 🎮 PROGRESIÓN DEL JUEGO

### Niveles 1-2: Aprendizaje
- Solo enemigos pequeños verdes
- Movimiento lineal simple
- Velocidad moderada

### Niveles 3-4: Intermedio
- Aparecen enemigos medianos azules
- Movimiento zigzag más complejo
- Aumento de velocidad

### Nivel 5+: Avanzado
- Todos los tipos de enemigos
- Enemigos grandes rojos resistentes
- Arma mejorada del jugador (disparo triple)
- Mayor velocidad y densidad

## 🎨 ELEMENTOS VISUALES

### Diseño de Naves:
- **Jugador**: Nave cian con detalles blancos y azules
- **Enemigos Pequeños**: Rectángulos verdes con "ojos" rojos
- **Enemigos Medianos**: Rectángulos azules con detalles amarillos
- **Enemigos Grandes**: Rectángulos rojos con diseño de "boss"

### Sistema de Balas:
- **Balas del Jugador**: Óvalos amarillos/cian
- **Balas Enemigas**: Óvalos rojos
- **Cooldown**: Previene spam de disparos

### Interfaz de Usuario:
- Puntuación en tiempo real
- Contador de nivel actual
- Indicador de vidas restantes
- Controles mostrados en pantalla

## 📈 VERSIÓN GRÁFICA

### Para el Aprendizaje:
1. **Visualización Clara**: Los patrones son visibles en acción
2. **Interactividad**: Se experimenta directamente con los patrones
3. **Feedback Inmediato**: Ver cómo los cambios afectan el comportamiento
4. **Engagement**: Más divertido que ejemplos de consola

### Para la Demostración:
1. **Impacto Visual**: Para presentaciones
2. **Comprensión Intuitiva**: Los conceptos se entienden mejor visualmente
3. **Casos de Uso Reales**: Muestra aplicaciones prácticas
4. **Escalabilidad**: Fácil agregar nuevas características

## 🛠️ ARQUITECTURA TÉCNICA

### Componentes Principales:
```
SpaceInvadersGraphic/
├── GameManager (Singleton)
│   ├── Estado global del juego
│   ├── Puntuación y nivel
│   └── Control de vidas
├── Strategies (Strategy Pattern)
│   ├── MovementStrategy
│   │   ├── LinearMovement
│   │   ├── ZigzagMovement
│   │   └── PlayerMovement
│   └── ShootingStrategy
│       ├── SingleShot
│       ├── TripleShot
│       └── EnemyShot
├── Factory (Factory Pattern)
│   └── InvaderFactory
│       ├── createInvader()
│       └── createWave()
└── Game Engine
    ├── GamePanel (Rendering)
    ├── Collision Detection
    └── Input Handling
```

### Tecnologías Utilizadas:
- **Java Swing**: Interfaz gráfica
- **Java AWT**: Gráficos y eventos
- **Timer**: Loop principal del juego (~60 FPS)
- **KeyListener**: Manejo de entrada del teclado

## 🎯 COMPARACIÓN: VERSIÓN CONSOLA vs GRÁFICA

| Aspecto | Versión Consola | Versión Gráfica |
|---------|----------------|-----------------|
| **Singleton** | Texto informativo | UI en tiempo real |
| **Strategy** | Mensajes de texto | Comportamiento visual |
| **Factory** | Listado de tipos | Enemigos en pantalla |
| **Interactividad** | Ejecución única | Juego completo |
| **Aprendizaje** | Conceptos teóricos | Aplicación práctica |
| **Demostración** | Académica | Profesional |


## 🎓 CONCLUSIÓN

La versión gráfica de Space Invaders demuestra que los patrones de diseño no son solo conceptos teóricos, sino herramientas prácticas que:

1. **Resuelven problemas reales** en el desarrollo de juegos
2. **Facilitan el mantenimiento** y la extensión del código
3. **Mejoran la experiencia** tanto del desarrollador como del usuario
4. **Proporcionan flexibilidad** para futuras mejoras


