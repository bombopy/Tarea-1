// Interfaz para Naves
interface Enemy {
    void display();
}

// Clase concreta para un tipo de nave
class Alien implements Enemy {
    @Override
    public void display() {
        System.out.println("Alien creado");
    }
}

// Clase concreta para otro tipo de nave
class UFO implements Enemy {
    @Override
    public void display() {
        System.out.println("UFO creado");
    }
}

// Fábrica para crear naves enemigas
class EnemyFactory {
    public static Enemy createEnemy(String type) {
        switch (type) {
            case "Alien":
                return new Alien();
            case "UFO":
                return new UFO();
            default:
                throw new IllegalArgumentException("Tipo de enemigo no soportado");
        }
    }
}

// Uso del patrón Factory
public class FactoryPatternExample {
    public static void main(String[] args) {
        // Crear enemigos usando la fábrica
        Enemy alien = EnemyFactory.createEnemy("Alien");
        alien.display();

        Enemy ufo = EnemyFactory.createEnemy("UFO");
        ufo.display();
    }
}