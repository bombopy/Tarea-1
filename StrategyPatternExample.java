// Interfaz para la estrategia de disparo
interface ShootingStrategy {
    void shoot();
}

// Estrategia 1: Disparo simple
class SimpleShootingStrategy implements ShootingStrategy {
    @Override
    public void shoot() {
        System.out.println("Disparo simple ejecutado");
    }
}

// Estrategia 2: Disparo múltiple
class MultipleShootingStrategy implements ShootingStrategy {
    @Override
    public void shoot() {
        System.out.println("Disparo múltiple ejecutado");
    }
}

// Clase Nave Enemiga que usa una estrategia de disparo
class EnemyShip {
    private ShootingStrategy shootingStrategy;

    // Constructor
    public EnemyShip(ShootingStrategy shootingStrategy) {
        this.shootingStrategy = shootingStrategy;
    }

    // Cambiar estrategia en tiempo de ejecución
    public void setShootingStrategy(ShootingStrategy shootingStrategy) {
        this.shootingStrategy = shootingStrategy;
    }

    // Ejecutar disparo
    public void performShoot() {
        shootingStrategy.shoot();
    }
}

// Uso del patrón Strategy
public class StrategyPatternExample {
    public static void main(String[] args) {
        // Crear nave enemiga con estrategia de disparo simple
        EnemyShip ship = new EnemyShip(new SimpleShootingStrategy());
        ship.performShoot();

        // Cambiar a estrategia de disparo múltiple
        ship.setShootingStrategy(new MultipleShootingStrategy());
        ship.performShoot();
    }
}