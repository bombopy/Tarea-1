import java.util.*;

// ==================== PATRÓN SINGLETON ====================
// Gestor único del juego Space Invaders
class SpaceInvadersGameManager {
    private static SpaceInvadersGameManager instance;
    private int score;
    private int level;
    private int lives;
    private boolean gameRunning;
    
    // Constructor privado para implementar Singleton
    private SpaceInvadersGameManager() {
        this.score = 0;
        this.level = 1;
        this.lives = 3;
        this.gameRunning = false;
        System.out.println("🎮 Space Invaders Game Manager inicializado");
    }
    
    // Método thread-safe para obtener la única instancia
    public static synchronized SpaceInvadersGameManager getInstance() {
        if (instance == null) {
            instance = new SpaceInvadersGameManager();
        }
        return instance;
    }
    
    // Métodos del gestor del juego
    public void startGame() {
        gameRunning = true;
        System.out.println("🚀 ¡Space Invaders iniciado! Nivel: " + level);
    }
    
    public void endGame() {
        gameRunning = false;
        System.out.println("💀 Game Over! Puntuación final: " + score);
    }
    
    public void addScore(int points) {
        score += points;
        System.out.println("⭐ +"+points+" puntos! Puntuación total: " + score);
    }
    
    public void nextLevel() {
        level++;
        System.out.println("🎯 ¡Nivel " + level + " desbloqueado!");
    }
    
    public void loseLife() {
        lives--;
        System.out.println("💔 Vida perdida! Vidas restantes: " + lives);
        if (lives <= 0) {
            endGame();
        }
    }
    
    // Getters
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public int getLives() { return lives; }
    public boolean isGameRunning() { return gameRunning; }
}

// ==================== PATRÓN STRATEGY ====================
// Interfaz para estrategias de movimiento de enemigos
interface MovementStrategy {
    void move(Enemy enemy);
    String getMovementType();
}

// Estrategia de movimiento lineal
class LinearMovementStrategy implements MovementStrategy {
    @Override
    public void move(Enemy enemy) {
        enemy.setY(enemy.getY() + 1);
        System.out.println("📍 " + enemy.getName() + " se mueve linealmente hacia abajo");
    }
    
    @Override
    public String getMovementType() {
        return "Movimiento Lineal";
    }
}

// Estrategia de movimiento zigzag
class ZigzagMovementStrategy implements MovementStrategy {
    private boolean movingRight = true;
    
    @Override
    public void move(Enemy enemy) {
        enemy.setY(enemy.getY() + 1);
        if (movingRight) {
            enemy.setX(enemy.getX() + 2);
        } else {
            enemy.setX(enemy.getX() - 2);
        }
        
        // Cambiar dirección aleatoriamente
        if (Math.random() < 0.3) {
            movingRight = !movingRight;
        }
        
        System.out.println("🔄 " + enemy.getName() + " se mueve en zigzag");
    }
    
    @Override
    public String getMovementType() {
        return "Movimiento Zigzag";
    }
}

// Estrategia de movimiento agresivo
class AggressiveMovementStrategy implements MovementStrategy {
    @Override
    public void move(Enemy enemy) {
        enemy.setY(enemy.getY() + 2); // Más rápido
        System.out.println("⚡ " + enemy.getName() + " se mueve agresivamente");
    }
    
    @Override
    public String getMovementType() {
        return "Movimiento Agresivo";
    }
}

// Interfaz para estrategias de disparo
interface ShootingStrategy {
    void shoot(Enemy enemy);
    String getShootingType();
}

// Estrategia de disparo simple
class SingleShotStrategy implements ShootingStrategy {
    @Override
    public void shoot(Enemy enemy) {
        System.out.println("💥 " + enemy.getName() + " dispara una bala");
    }
    
    @Override
    public String getShootingType() {
        return "Disparo Simple";
    }
}

// Estrategia de disparo múltiple
class MultipleShotStrategy implements ShootingStrategy {
    @Override
    public void shoot(Enemy enemy) {
        System.out.println("💥💥💥 " + enemy.getName() + " dispara ráfaga de 3 balas");
    }
    
    @Override
    public String getShootingType() {
        return "Disparo Múltiple";
    }
}

// Estrategia de disparo láser
class LaserShotStrategy implements ShootingStrategy {
    @Override
    public void shoot(Enemy enemy) {
        System.out.println("🔴 " + enemy.getName() + " dispara un láser potente");
    }
    
    @Override
    public String getShootingType() {
        return "Disparo Láser";
    }
}

// ==================== PATRÓN FACTORY ====================
// Clase base para enemigos
abstract class Enemy {
    protected String name;
    protected int health;
    protected int points;
    protected int x, y;
    protected MovementStrategy movementStrategy;
    protected ShootingStrategy shootingStrategy;
    
    public Enemy(String name, int health, int points) {
        this.name = name;
        this.health = health;
        this.points = points;
        this.x = 0;
        this.y = 0;
    }
    
    // Métodos abstractos
    public abstract void display();
    public abstract void specialAbility();
    
    // Métodos comunes
    public void move() {
        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }
    
    public void shoot() {
        if (shootingStrategy != null) {
            shootingStrategy.shoot(this);
        }
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("🎯 " + name + " recibe " + damage + " de daño. Vida: " + health);
        if (health <= 0) {
            destroy();
        }
    }
    
    public void destroy() {
        System.out.println("💀 " + name + " destruido!");
        SpaceInvadersGameManager.getInstance().addScore(points);
    }
    
    // Getters y Setters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getPoints() { return points; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setMovementStrategy(MovementStrategy strategy) { this.movementStrategy = strategy; }
    public void setShootingStrategy(ShootingStrategy strategy) { this.shootingStrategy = strategy; }
}

// Enemigo básico: Alien simple
class BasicAlien extends Enemy {
    public BasicAlien() {
        super("Alien Básico", 1, 10);
        this.movementStrategy = new LinearMovementStrategy();
        this.shootingStrategy = new SingleShotStrategy();
    }
    
    @Override
    public void display() {
        System.out.println("👾 " + name + " aparece en pantalla");
    }
    
    @Override
    public void specialAbility() {
        System.out.println("⚡ " + name + " aumenta velocidad temporalmente");
    }
}

// Enemigo intermedio: Alien Soldado
class SoldierAlien extends Enemy {
    public SoldierAlien() {
        super("Alien Soldado", 2, 25);
        this.movementStrategy = new ZigzagMovementStrategy();
        this.shootingStrategy = new MultipleShotStrategy();
    }
    
    @Override
    public void display() {
        System.out.println("👾⚔️ " + name + " aparece armado");
    }
    
    @Override
    public void specialAbility() {
        System.out.println("🛡️ " + name + " activa escudo defensivo");
        health += 1;
    }
}

// Enemigo avanzado: UFO Boss
class UFOBoss extends Enemy {
    public UFOBoss() {
        super("UFO Boss", 5, 100);
        this.movementStrategy = new AggressiveMovementStrategy();
        this.shootingStrategy = new LaserShotStrategy();
    }
    
    @Override
    public void display() {
        System.out.println("🛸👑 " + name + " aparece como jefe de nivel");
    }
    
    @Override
    public void specialAbility() {
        System.out.println("💫 " + name + " invoca refuerzos enemigos");
        // Lógica para crear más enemigos
    }
}

// Fábrica de enemigos
class EnemyFactory {
    public enum EnemyType {
        BASIC_ALIEN,
        SOLDIER_ALIEN,
        UFO_BOSS
    }
    
    public static Enemy createEnemy(EnemyType type) {
        return switch (type) {
            case BASIC_ALIEN -> new BasicAlien();
            case SOLDIER_ALIEN -> new SoldierAlien();
            case UFO_BOSS -> new UFOBoss();
        };
    }
    
    // Factory method que crea enemigos según el nivel
    public static Enemy createEnemyByLevel(int level) {
        if (level <= 2) {
            return createEnemy(EnemyType.BASIC_ALIEN);
        } else if (level <= 5) {
            return createEnemy(EnemyType.SOLDIER_ALIEN);
        } else {
            return createEnemy(EnemyType.UFO_BOSS);
        }
    }
    
    // Factory method que crea oleadas de enemigos
    public static List<Enemy> createWave(int level, int enemyCount) {
        List<Enemy> wave = new ArrayList<>();
        
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = createEnemyByLevel(level);
            
            // Asignar estrategias basadas en el nivel y posición
            if (level >= 3) {
                // Niveles avanzados tienen movimientos más complejos
                if (i % 2 == 0) {
                    enemy.setMovementStrategy(new ZigzagMovementStrategy());
                } else {
                    enemy.setMovementStrategy(new AggressiveMovementStrategy());
                }
            }
            
            if (level >= 4) {
                // Niveles más altos tienen disparos más potentes
                enemy.setShootingStrategy(new LaserShotStrategy());
            }
            
            wave.add(enemy);
        }
        
        System.out.println("🌊 Oleada de " + enemyCount + " enemigos creada para nivel " + level);
        return wave;
    }
}

// ==================== CLASE PRINCIPAL ====================
public class SpaceInvadersGame {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("🎮 SPACE INVADERS - DEMOSTRACIÓN DE PATRONES DE DISEÑO");
        System.out.println("=".repeat(60));
        
        // ==================== PATRÓN SINGLETON ====================
        System.out.println("\n📋 DEMOSTRACIÓN DEL PATRÓN SINGLETON:");
        System.out.println("-".repeat(50));
        
        // Obtener la única instancia del gestor del juego
        SpaceInvadersGameManager gameManager = SpaceInvadersGameManager.getInstance();
        gameManager.startGame();
        
        // Verificar que siempre es la misma instancia
        SpaceInvadersGameManager gameManager2 = SpaceInvadersGameManager.getInstance();
        System.out.println("✅ ¿Es la misma instancia? " + (gameManager == gameManager2));
        
        // ==================== PATRÓN FACTORY ====================
        System.out.println("\n🏭 DEMOSTRACIÓN DEL PATRÓN FACTORY:");
        System.out.println("-".repeat(50));
        
        // Crear diferentes tipos de enemigos usando la fábrica
        Enemy basicAlien = EnemyFactory.createEnemy(EnemyFactory.EnemyType.BASIC_ALIEN);
        basicAlien.display();
        
        Enemy soldierAlien = EnemyFactory.createEnemy(EnemyFactory.EnemyType.SOLDIER_ALIEN);
        soldierAlien.display();
        
        Enemy ufoBoss = EnemyFactory.createEnemy(EnemyFactory.EnemyType.UFO_BOSS);
        ufoBoss.display();
        
        // Crear enemigos según el nivel actual
        System.out.println("\n🎯 Creando enemigos para el nivel actual:");
        Enemy levelEnemy = EnemyFactory.createEnemyByLevel(gameManager.getLevel());
        levelEnemy.display();
        
        // ==================== PATRÓN STRATEGY ====================
        System.out.println("\n🎯 DEMOSTRACIÓN DEL PATRÓN STRATEGY:");
        System.out.println("-".repeat(50));
        
        // Demostrar diferentes estrategias de movimiento
        System.out.println("🏃 Estrategias de Movimiento:");
        basicAlien.move();
        soldierAlien.move();
        ufoBoss.move();
        
        System.out.println("\n💥 Estrategias de Disparo:");
        basicAlien.shoot();
        soldierAlien.shoot();
        ufoBoss.shoot();
        
        // Cambiar estrategias dinámicamente
        System.out.println("\n🔄 Cambiando estrategias dinámicamente:");
        basicAlien.setMovementStrategy(new AggressiveMovementStrategy());
        basicAlien.setShootingStrategy(new LaserShotStrategy());
        System.out.println("⚡ " + basicAlien.getName() + " ahora tiene nuevas estrategias:");
        basicAlien.move();
        basicAlien.shoot();
        
        // ==================== SIMULACIÓN DE JUEGO ====================
        System.out.println("\n🎮 SIMULACIÓN DE JUEGO:");
        System.out.println("-".repeat(50));
        
        // Simular oleadas de enemigos
        List<Enemy> wave1 = EnemyFactory.createWave(1, 3);
        List<Enemy> wave2 = EnemyFactory.createWave(3, 4);
        List<Enemy> wave3 = EnemyFactory.createWave(6, 2);
        
        // Simular combate
        System.out.println("\n⚔️ Simulando combate:");
        for (Enemy enemy : wave1) {
            enemy.move();
            enemy.shoot();
            enemy.specialAbility();
            enemy.takeDamage(1);
        }
        
        // Subir de nivel
        gameManager.nextLevel();
        
        // Simular combate más intenso
        System.out.println("\n🔥 Combate de nivel avanzado:");
        for (Enemy enemy : wave2) {
            enemy.move();
            enemy.shoot();
            enemy.takeDamage(2);
        }
        
        // Combate con jefe
        System.out.println("\n👑 Combate contra jefe:");
        for (Enemy boss : wave3) {
            boss.move();
            boss.shoot();
            boss.specialAbility();
            boss.takeDamage(3);
            boss.takeDamage(2);
        }
        
        // ==================== RESUMEN ====================
        System.out.println("\n📊 RESUMEN DE LA DEMOSTRACIÓN:");
        System.out.println("-".repeat(50));
        System.out.println("🎯 Puntuación final: " + gameManager.getScore());
        System.out.println("🎮 Nivel alcanzado: " + gameManager.getLevel());
        System.out.println("❤️ Vidas restantes: " + gameManager.getLives());
        
        System.out.println("\n✅ PATRONES IMPLEMENTADOS:");
        System.out.println("🔹 SINGLETON: GameManager como instancia única del juego");
        System.out.println("🔹 STRATEGY: Diferentes estrategias de movimiento y disparo");
        System.out.println("🔹 FACTORY: Creación flexible de diferentes tipos de enemigos");
        
        gameManager.endGame();
        System.out.println("\n" + "=".repeat(60));
    }
}
