// ==================== IMPLEMENTACIÓN SIMPLIFICADA ====================
// Esta versión se enfoca en mostrar claramente cada patrón

// ==================== PATRÓN SINGLETON ====================
class GameSession {
    private static GameSession instance;
    private int score = 0;
    private int level = 1;
    
    private GameSession() {
        System.out.println("🎮 Nueva sesión de juego creada");
    }
    
    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }
    
    public void addScore(int points) {
        score += points;
        System.out.println("⭐ +" + points + " puntos! Total: " + score);
    }
    
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public void nextLevel() { level++; }
}

// ==================== PATRÓN STRATEGY ====================
// Estrategias de disparo
interface WeaponStrategy {
    void fire();
}

class BasicLaser implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("💥 Disparo láser básico");
    }
}

class PowerLaser implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("🔥 Disparo láser potenciado");
    }
}

class SpreadShot implements WeaponStrategy {
    @Override
    public void fire() {
        System.out.println("💥💥💥 Disparo en abanico");
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
        System.out.println("🔧 Arma cambiada");
    }
    
    public void shoot() {
        weapon.fire();
    }
}

// ==================== PATRÓN FACTORY ====================
// Productos de la fábrica
abstract class Invader {
    protected String type;
    protected int points;
    
    public Invader(String type, int points) {
        this.type = type;
        this.points = points;
    }
    
    public abstract void attack();
    public abstract void move();
    
    public void destroy() {
        System.out.println("💀 " + type + " destruido!");
        GameSession.getInstance().addScore(points);
    }
    
    public String getType() { return type; }
}

class SmallInvader extends Invader {
    public SmallInvader() {
        super("Invasor Pequeño", 10);
    }
    
    @Override
    public void attack() {
        System.out.println("👾 " + type + " dispara bala simple");
    }
    
    @Override
    public void move() {
        System.out.println("👾 " + type + " se mueve lentamente");
    }
}

class MediumInvader extends Invader {
    public MediumInvader() {
        super("Invasor Mediano", 20);
    }
    
    @Override
    public void attack() {
        System.out.println("🛸 " + type + " dispara doble bala");
    }
    
    @Override
    public void move() {
        System.out.println("🛸 " + type + " se mueve moderadamente");
    }
}

class LargeInvader extends Invader {
    public LargeInvader() {
        super("Invasor Grande", 50);
    }
    
    @Override
    public void attack() {
        System.out.println("🚀 " + type + " dispara misiles");
    }
    
    @Override
    public void move() {
        System.out.println("🚀 " + type + " se mueve rápidamente");
    }
}

// Fábrica de invasores
class InvaderFactory {
    public static Invader createInvader(String type) {
        switch (type.toLowerCase()) {
            case "small":
                return new SmallInvader();
            case "medium":
                return new MediumInvader();
            case "large":
                return new LargeInvader();
            default:
                return new SmallInvader(); // Por defecto
        }
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

// ==================== DEMOSTRACIÓN ====================
public class SpaceInvadersSimple {
    public static void main(String[] args) {
        System.out.println("🎮 SPACE INVADERS - PATRONES DE DISEÑO");
        System.out.println("=" .repeat(40));
        
        // SINGLETON: Una sola sesión de juego
        System.out.println("\n📋 PATRÓN SINGLETON:");
        GameSession session1 = GameSession.getInstance();
        GameSession session2 = GameSession.getInstance();
        System.out.println("¿Misma instancia? " + (session1 == session2));
        
        // STRATEGY: Diferentes armas para la nave
        System.out.println("\n🎯 PATRÓN STRATEGY:");
        PlayerShip player = new PlayerShip();
        
        System.out.println("Arma inicial:");
        player.shoot();
        
        System.out.println("Mejora de arma 1:");
        player.setWeapon(new PowerLaser());
        player.shoot();
        
        System.out.println("Mejora de arma 2:");
        player.setWeapon(new SpreadShot());
        player.shoot();
        
        // FACTORY: Crear diferentes enemigos
        System.out.println("\n🏭 PATRÓN FACTORY:");
        
        System.out.println("Nivel 1 - Enemigos básicos:");
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
        
        // Demostrar factory automática
        System.out.println("\n🎲 Creación automática por nivel:");
        session1.nextLevel();
        Invader autoEnemy1 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy1.getType());
        
        session1.nextLevel();
        session1.nextLevel();
        Invader autoEnemy2 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy2.getType());
        
        session1.nextLevel();
        session1.nextLevel();
        Invader autoEnemy3 = InvaderFactory.createRandomInvader(session1.getLevel());
        System.out.println("Nivel " + session1.getLevel() + ": " + autoEnemy3.getType());
        
        System.out.println("\n📊 Puntuación final: " + session1.getScore());
        System.out.println("🎯 Nivel final: " + session1.getLevel());
    }
}
