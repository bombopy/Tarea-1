# IMPLEMENTACION DE PATRONES DE DISEÑO EN SPACE INVADERS

## DESCRIPCION DEL PROYECTO

Este proyecto universitario demuestra la implementación y aplicación de tres patrones de diseño fundamentales (Strategy, Factory, Singleton) en el contexto de un videojuego Space Invaders.

## ARCHIVOS INCLUIDOS

### Ejemplos Básicos

- `SingletonPatternExample.java` - Implementación básica del patrón Singleton
- `FactoryPatternExample.java` - Implementación básica del patrón Factory  
- `StrategyPatternExample.java` - Implementación básica del patrón Strategy

### Implementación Completa de Space Invaders

- `SpaceInvadersGame.java` - Implementación completa con todos los patrones integrados
- `SpaceInvadersClean.java` - Versión limpia sin caracteres especiales (recomendada para ejecución)
- `SpaceInvadersGraphic.java` - Versión gráfica interactiva del juego

### Documentación

- `PatronesDeDiseno_SpaceInvaders.md` - Documentación detallada de la implementación
- `GuiaJuegoGrafico.md` -  Guía completa de la versión gráfica
- `README.md` - Este archivo

## COMO EJECUTAR

### Prerrequisitos

- Java JDK 8 o superior
- Terminal o línea de comandos

### Pasos para ejecutar

1. **Compilar y ejecutar la versión limpia :**

```bash
javac SpaceInvadersClean.java
java SpaceInvadersClean
```

**NU: Compilar y ejecutar la versión gráfica:**

```bash
javac SpaceInvadersGraphic.java 
java SpaceInvadersGraphic
```

1. **Ejecutar ejemplos individuales:**

```bash
# Singleton
javac SingletonPatternExample.java
java SingletonPatternExample

# Factory
javac FactoryPatternExample.java
java FactoryPatternExample

# Strategy
javac StrategyPatternExample.java
java StrategyPatternExample
```

## PATRONES IMPLEMENTADOS

### 1. PATRON SINGLETON

**Propósito:** Garantizar que una clase tenga una sola instancia
**Implementación:** `GameSession` - Gestor único del estado del juego
**Beneficios:**

- Estado global consistente
- Control de puntuación centralizado
- Una sola instancia del gestor del juego

### 2. PATRON STRATEGY

**Propósito:** Definir algoritmos intercambiables en tiempo de ejecución
**Implementación:**

- `WeaponStrategy` - Diferentes tipos de armas
- `MovementStrategy` - Diferentes patrones de movimiento
- `ShootingStrategy` - Diferentes estrategias de disparo

**Beneficios:**

- Comportamiento dinámico
- Fácil extensión con nuevas estrategias
- Eliminación de condicionales complejas

### 3. PATRON FACTORY

**Propósito:** Crear objetos sin especificar sus clases concretas
**Implementación:** `InvaderFactory` - Crear diferentes tipos de enemigos
**Beneficios:**

- Creación flexible de enemigos
- Desacoplamiento del código cliente
- Fácil adición de nuevos tipos

## EJEMPLO DE SALIDA

```text
SPACE INVADERS - PATRONES DE DISENO
========================================

PATRON SINGLETON:
------------------
Nueva sesion de juego creada
Son la misma instancia? true

PATRON STRATEGY:
----------------
Arma inicial:
Disparo laser basico

Mejora de arma 1:
Arma cambiada
Disparo laser potenciado

PATRON FACTORY:
---------------
Nivel 1 - Enemigos basicos:
Invasor Pequeno se mueve lentamente
Invasor Pequeno dispara bala simple
Invasor Pequeno destruido!
+10 puntos! Total: 10

RESUMEN FINAL:
--------------
Puntuacion final: 160
Nivel final: 6
```

## ARQUITECTURA DEL PROYECTO

```SpaceInvaders/
├── Gestión del Estado (Singleton)
│   └── GameSession: Puntuación, nivel, estado global
├── Comportamientos (Strategy)
│   ├── WeaponStrategy: Armas intercambiables
│   ├── MovementStrategy: Patrones de movimiento
│   └── ShootingStrategy: Tipos de disparo
└── Creación de Objetos (Factory)
    └── InvaderFactory: Creación flexible de enemigos
```

## BENEFICIOS DE LA IMPLEMENTACION

### Para el Desarrollo

- **Código mantenible:** Cada patrón encapsula una responsabilidad
- **Extensibilidad:** Fácil agregar nuevas características
- **Testabilidad:** Componentes independientes y mockeable
- **Reutilización:** Estrategias y factories reutilizables

### Para el Juego

- **Variedad:** Diferentes tipos de enemigos y comportamientos
- **Balance:** Fácil ajustar dificultad
- **Consistencia:** Estado del juego coherente
- **Escalabilidad:** Fácil agregar nuevos niveles y enemigos

## EXTENSIONES

### Estrategias

- `TeleportMovementStrategy`: Enemigos que se teletransportan
- `HomingMissileStrategy`: Disparos que siguen al jugador
- `ShieldStrategy`: Enemigos con escudos

### Tipos

- `BossFactory`: Crear jefes de nivel únicos
- `PowerUpFactory`: Crear power-ups especiales
- `WeaponUpgradeFactory`: Crear mejoras de armas

### Singletons

- `AudioManager`: Gestor de sonidos
- `ConfigManager`: Gestor de configuración
- `SaveGameManager`: Gestor de guardado

## RECURSOS

### Videos de Referencia (mencionados en la especificación de la Tarea 1)

- Introducción: <https://www.youtube.com/watch?v=yRJ1rRoMnIM&list=PLF206E906175C7E07&index=3>
- Strategy Pattern: <https://www.youtube.com/watch?v=-NCgRD9-C6o&list=PLF206E906175C7E07&index=3>  
- Factory Pattern: <https://www.youtube.com/watch?v=ub0DXaeV6hA&list=PLF206E906175C7E07&index=5>

## AUTOR

Proyecto desarrollado para demostrar la aplicación práctica de patrones de diseño en el desarrollo de videojuegos.

## NOTAS TECNICAS

- Compatible con Java 8+
- Sin dependencias externas
- Codificación UTF-8 para compatibilidad
- Ejemplos ejecutables independientes
