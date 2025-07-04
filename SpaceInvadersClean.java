// ==================== IMPLEMENTACION SIMPLIFICADA ====================
// Version sin caracteres especiales para mejor compatibilidad

// ==================== PATRON SINGLETON ====================
class GameSession {
    private static GameSession instance;
    private int score = 0;
    private int level = 1;
    
    private GameSession() {
        System.out.println("Nueva sesion de juego creada");
    }
    
    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }
    
    public void addScore(int points) {
        score += points;
        System.out.println("+" + points + " puntos! Total: " + score);
    }
    
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public void nextLevel() { level++; }
}

// ==================== PATRON STRATEGY ====================
// Estrategias de movimiento
interface MovementStrategy {
    void move();
}

class LinearMovement implements MovementStrategy {
    @Override
    public void move() {
        System.out.println("Movimiento lineal descendente");
    }
}

class ZigzagMovement implements MovementStrategy {
    @Override
    public void move() {
        System.out.println("Movimiento zigzag");
    }
}

// Estrategias de disparo
interface ShootingStrategy {
    void shoot();
}

class SingleShot implements ShootingStrategy {
    @Override
    public void shoot() {
        System.out.println("Disparo simple");
    }
}

class TripleShot implements ShootingStrategy {
    @Override
    public void shoot() {
        System.out.println("Disparo triple");
    }
}

// Estrategias de disparo
interface WeaponStrategy {
    void fire();
}

class BasicLaser implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("Disparo laser basico");
    }
}

class PowerLaser implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("Disparo laser potenciado");
    }
}

class SpreadShot implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("Disparo en abanico - tres balas");
    }
}

// Nave del jugador que usa Strategy
class PlayerShip {
    private WeaponStrategy weapon;
    
    public PlayerShip() {
        weapon = new BasicLaser(); // Arma por defecto
    }
    
    public void setWeapon(WeaponStrategy weapon) {
        this.weapon = weapon;
        System.out.println("Arma cambiada");
    }
    
    public void shoot() {
        weapon.fire();
    }
}

// ==================== PATRON FACTORY ====================
// Productos de la fabrica
abstract class Invader {
    protected String type;
    protected int points;
    protected MovementStrategy movementStrategy;
    protected ShootingStrategy shootingStrategy;
    
    public Invader(String type, int points) {
        this.type = type;
        this.points = points;
    }
    
    public abstract void attack();
    public abstract void move();
    
    public void destroy() {
        System.out.println(type + " destruido!");
        GameSession.getInstance().addScore(points);
    }
    
    public String getType() { return type; }
    
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
    
    public void setShootingStrategy(ShootingStrategy strategy) {
        this.shootingStrategy = strategy;
    }
}

class SmallInvader extends Invader {
    public SmallInvader() {
        super("Invasor Pequeno", 10);
        this.movementStrategy = new LinearMovement();
        this.shootingStrategy = new SingleShot();
    }
    
    @Override
    public void attack() {
        System.out.println(type + " dispara bala simple");
        if (shootingStrategy != null) {
            shootingStrategy.shoot();
        }
    }
    
    @Override
    public void move() {
        System.out.println(type + " se mueve lentamente");
        if (movementStrategy != null) {
            movementStrategy.move();
        }
    }
}

class MediumInvader extends Invader {
    public MediumInvader() {
        super("Invasor Mediano", 20);
        this.movementStrategy = new ZigzagMovement();
        this.shootingStrategy = new SingleShot();
    }
    
    @Override
    public void attack() {
        System.out.println(type + " dispara doble bala");
        if (shootingStrategy != null) {
            shootingStrategy.shoot();
        }
    }
    
    @Override
    public void move() {
        System.out.println(type + " se mueve moderadamente");
        if (movementStrategy != null) {
            movementStrategy.move();
        }
    }
}

class LargeInvader extends Invader {
    public LargeInvader() {
        super("Invasor Grande", 50);
        this.movementStrategy = new ZigzagMovement();
        this.shootingStrategy = new TripleShot();
    }
    
    @Override
    public void attack() {
        System.out.println(type + " dispara misiles");
        if (shootingStrategy != null) {
            shootingStrategy.shoot();
        }
    }
    
    @Override
    public void move() {
        System.out.println(type + " se mueve rapidamente");
        if (movementStrategy != null) {
            movementStrategy.move();
        }
    }
}

// Fabrica de invasores
class InvaderFactory {
    public static Invader createInvader(String type) {
        return switch (type.toLowerCase()) {
            case "small" -> new SmallInvader();
            case "medium" -> new MediumInvader();
            case "large" -> new LargeInvader();
            default -> new SmallInvader(); // Por defecto
        };
    }
    
    // Factory method basado en nivel
    public static Invader createRandomInvader(int level) {
        if (level <= 2) {
            return createInvader("small");
        } else if (level <= 4) {
            return createInvader("medium");
        } else {
            return createInvader("large");
        }
    }
}

// ==================== DEMOSTRACION ====================
public class SpaceInvadersClean {
    public static void main(String[] args) {
        System.out.println("SPACE INVADERS - PATRONES DE DISENO");
        System.out.println("========================================");
        
        // SINGLETON: Una sola sesion de juego
        System.out.println("\nPATRON SINGLETON:");
        System.out.println("------------------");
        GameSession session1 = GameSession.getInstance();
        GameSession session2 = GameSession.getInstance();
        System.out.println("Son la misma instancia? " + (session1 == session2));
        
        // STRATEGY: Diferentes armas para la nave
        System.out.println("\nPATRON STRATEGY:");
        System.out.println("----------------");
        PlayerShip player = new PlayerShip();
        
        System.out.println("Arma inicial:");
        player.shoot();
        
        System.out.println("\nMejora de arma 1:");
        player.setWeapon(new PowerLaser());
        player.shoot();
        
        System.out.println("\nMejora de arma 2:");
        player.setWeapon(new SpreadShot());
        player.shoot();
        
        // Demostrar estrategias de movimiento y disparo
        System.out.println("\nEstrategias de movimiento y disparo:");
        System.out.println("Estrategia lineal:");
        MovementStrategy linear = new LinearMovement();
        linear.move();
        
        System.out.println("Estrategia zigzag:");
        MovementStrategy zigzag = new ZigzagMovement();
        zigzag.move();
        
        System.out.println("Disparo simple:");
        ShootingStrategy single = new SingleShot();
        single.shoot();
        
        System.out.println("Disparo triple:");
        ShootingStrategy triple = new TripleShot();
        triple.shoot();
        
        // FACTORY: Crear diferentes enemigos
        System.out.println("\nPATRON FACTORY:");
        System.out.println("---------------");
        
        System.out.println("\nNivel 1 - Enemigos basicos:");
        Invader enemy1 = InvaderFactory.createInvader("small");
        enemy1.move();
        enemy1.attack();
        enemy1.destroy();
        
        System.out.println("\nNivel 3 - Enemigos medianos:");
        Invader enemy2 = InvaderFactory.createInvader("medium");
        enemy2.move();
        enemy2.attack();
        enemy2.destroy();
        
        System.out.println("\nNivel 5 - Enemigos grandes:");
        Invader enemy3 = InvaderFactory.createInvader("large");
        enemy3.move();
        enemy3.attack();
        enemy3.destroy();
        
        // Demostrar factory automatica
        System.out.println("\nCreacion automatica por nivel:");
        System.out.println("-------------------------------");
        
        session1.nextLevel();
        Invader autoEnemy1 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy1.getType());
        autoEnemy1.destroy();
        
        session1.nextLevel();
        session1.nextLevel();
        Invader autoEnemy2 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy2.getType());
        autoEnemy2.destroy();
        
        session1.nextLevel();
        session1.nextLevel();
        Invader autoEnemy3 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy3.getType());
        autoEnemy3.destroy();
        
        // RESUMEN FINAL
        System.out.println("\nRESUMEN FINAL:");
        System.out.println("--------------");
        System.out.println("Puntuacion final: " + session1.getScore());
        System.out.println("Nivel final: " + session1.getLevel());
        
        System.out.println("\nPATRONES DEMOSTRADOS:");
        System.out.println("- SINGLETON: GameSession como instancia unica");
        System.out.println("- STRATEGY: WeaponStrategy, MovementStrategy, ShootingStrategy");
        System.out.println("- FACTORY: InvaderFactory para creacion flexible de enemigos");
        
        System.out.println("\n========================================");
        System.out.println("Demostracion completada exitosamente!");
    }
}
