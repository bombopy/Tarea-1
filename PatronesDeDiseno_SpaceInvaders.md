# IMPLEMENTACIÓN DE PATRONES DE DISEÑO EN SPACE INVADERS

## 📋 RESUMEN

Esta implementación demuestra el uso de tres patrones de diseño fundamentales en el contexto de un videojuego Space Invaders:

1. **Singleton Pattern** - Gestor único del juego
2. **Strategy Pattern** - Diferentes estrategias de comportamiento
3. **Factory Pattern** - Creación flexible de objetos enemigos

---

## 🎯 PATRÓN SINGLETON

### ¿Qué es el Patrón Singleton?

El patrón Singleton garantiza que una clase tenga una sola instancia y proporciona un punto de acceso global a ella.

### Implementación del Patrón Strategy en Space Invaders: Estrategias de Movimiento

```java
class SpaceInvadersGameManager {
    private static SpaceInvadersGameManager instance;
    
    private SpaceInvadersGameManager() { /* Constructor privado */ }
    
    public static synchronized SpaceInvadersGameManager getInstance() {
        if (instance == null) {
            instance = new SpaceInvadersGameManager();
        }
        return instance;
    }
}
```

### ¿Por qué usar Factory aca?

- **Gestor único del juego**: Solo debe existir una instancia que controle el estado global
- **Puntuación centralizada**: Todos los enemigos deben reportar a la misma sesión
- **Configuración global**: Un solo punto para manejar nivel, vidas, etc.

### Ventajas del Patrón Singleton

✅ Acceso controlado a la instancia única
✅ Espacio de nombres reducido
✅ Estado global consistente
✅ Inicialización diferida (lazy initialization)

---

## 🎯 PATRÓN STRATEGY

### ¿Qué es el Patrón Strategy?

El patrón Strategy define una familia de algoritmos, los encapsula y los hace intercambiables. Permite que el algoritmo varíe independientemente de los clientes que lo usan.

### Implementación del Patrón Strategy en Space Invaders

#### Estrategias de Movimiento

```java
interface MovementStrategy {
    void move(Enemy enemy);
}

class LinearMovementStrategy implements MovementStrategy { /* ... */ }
class ZigzagMovementStrategy implements MovementStrategy { /* ... */ }
class AggressiveMovementStrategy implements MovementStrategy { /* ... */ }
```

#### Estrategias de Disparo

```java
interface ShootingStrategy {
    void shoot(Enemy enemy);
}

class SingleShotStrategy implements ShootingStrategy { /* ... */ }
class MultipleShotStrategy implements ShootingStrategy { /* ... */ }
class LaserShotStrategy implements ShootingStrategy { /* ... */ }
```

### ¿Por qué usar el Patrón Factory aquí?

- **Comportamiento dinámico**: Los enemigos pueden cambiar su forma de moverse y disparar
- **Flexibilidad**: Fácil agregar nuevos tipos de comportamiento
- **Reutilización**: Las estrategias pueden ser compartidas entre diferentes tipos de enemigos
- **Mantenimiento**: Cambios en una estrategia no afectan las demás

### Ventajas del Patrón Strategy

✅ Algoritmos intercambiables en tiempo de ejecución
✅ Elimina declaraciones condicionales complejas
✅ Fácil extensión con nuevas estrategias
✅ Principio abierto/cerrado cumplido

---

## 🎯 PATRÓN FACTORY

### ¿Qué es?

El patrón Factory proporciona una interfaz para crear objetos sin especificar sus clases concretas. Permite la creación de objetos de forma flexible y extensible.

### Implementación en Space Invaders

```java
class EnemyFactory {
    public static Enemy createEnemy(EnemyType type) {
        switch (type) {
            case BASIC_ALIEN: return new BasicAlien();
            case SOLDIER_ALIEN: return new SoldierAlien();
            case UFO_BOSS: return new UFOBoss();
        }
    }
    
    public static Enemy createEnemyByLevel(int level) {
        // Lógica basada en nivel
    }
    
    public static List<Enemy> createWave(int level, int count) {
        // Crear oleadas de enemigos
    }
}
```

### ¿Por qué usarlo aquí?

- **Creación compleja**: Diferentes tipos de enemigos con configuraciones específicas
- **Desacoplamiento**: El código cliente no necesita conocer las clases concretas
- **Flexibilidad**: Fácil agregar nuevos tipos de enemigos
- **Lógica centralizada**: Toda la creación de enemigos en un lugar

### Ventajas

✅ Desacopla el código de creación del código de uso
✅ Fácil mantenimiento y extensión
✅ Cumple el principio de responsabilidad única
✅ Permite crear objetos basados en parámetros dinámicos

---

## 🎮 INTEGRACIÓN DE LOS PATRONES

### Flujo del Juego

1. **Singleton**: `GameManager.getInstance()` controla el estado del juego
2. **Factory**: Crea enemigos apropiados para el nivel actual
3. **Strategy**: Cada enemigo usa estrategias específicas de movimiento y disparo
4. **Singleton**: Reporta puntuaciones y maneja transiciones de nivel

### Ejemplo de Uso Conjunto

```java
// 1. Obtener gestor único del juego
GameManager manager = GameManager.getInstance();

// 2. Crear enemigos usando Factory
List<Enemy> enemies = EnemyFactory.createWave(manager.getLevel(), 5);

// 3. Cada enemigo usa Strategy para comportamiento
for (Enemy enemy : enemies) {
    enemy.move();        // Strategy: MovementStrategy
    enemy.shoot();       // Strategy: ShootingStrategy
    enemy.destroy();     // Singleton: reporta puntuación
}
```

---

## 📊 ANÁLISIS DE BENEFICIOS

### Para el Desarrollo

- **Modularidad**: Cada patrón encapsula una responsabilidad específica
- **Mantenibilidad**: Cambios localizados y controlados
- **Extensibilidad**: Fácil agregar nuevas características
- **Testabilidad**: Componentes independientes y mockeable

### Para el Juego

- **Variedad**: Diferentes tipos de enemigos y comportamientos
- **Balance**: Fácil ajustar dificultad modificando las factories
- **Consistencia**: Estado del juego siempre coherente
- **Performance**: Reutilización de estrategias y control de instancias

---

## 🚀 POSIBLES EXTENSIONES

### Nuevas Estrategias

- `TeleportMovementStrategy`: Enemigos que se teletransportan
- `HomingMissileStrategy`: Disparos que siguen al jugador
- `ShieldStrategy`: Enemigos con escudos temporales

### Nuevos Tipos de Enemigos

- `MiniSwarmFactory`: Crear enjambres de enemigos pequeños
- `BossFactory`: Crear jefes de nivel únicos
- `PowerUpFactory`: Crear elementos especiales

### Mejoras al Singleton

- `AudioManager`: Gestor único de sonidos
- `ConfigManager`: Gestor único de configuración
- `SaveGameManager`: Gestor único de guardado

---

## 📝 CONCLUSIONES

Esta implementación demuestra cómo los patrones de diseño:

1. **Resuelven problemas reales** en el desarrollo de videojuegos
2. **Mejoran la arquitectura** del código haciéndolo más limpio y mantenible
3. **Facilitan la colaboración** en equipos de desarrollo
4. **Permiten evolución** del juego sin reescribir código base

Los patrones no son solo teoría académica, sino **herramientas prácticas** que mejoran significativamente la calidad del software en proyectos reales como videojuegos.
