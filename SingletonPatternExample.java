// Clase Singleton para el gestor del juego
class GameManager {
    private static GameManager instance;

    // Constructor privado para evitar instancias externas
    private GameManager() {
        System.out.println("Gestor del juego inicializado");
    }

    // Método para obtener la única instancia
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Método de ejemplo
    public void startGame() {
        System.out.println("¡Juego iniciado!");
    }
}

// Uso del patrón Singleton
public class SingletonPatternExample {
    public static void main(String[] args) {
        // Obtener la única instancia del gestor del juego
        GameManager manager = GameManager.getInstance();
        manager.startGame();

        // Comprobar que siempre es la misma instancia
        GameManager manager2 = GameManager.getInstance();
        System.out.println("¿Es la misma instancia? " + (manager == manager2));
    }
}
