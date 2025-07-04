// Clase Singleton para el gestor del juego
class GameSession {
    private static GameSession instance;

    
    private GameSession() {
        System.out.println("Nueva sesion de juego creada");
    }

    // Método para obtener la única instancia
    public static synchronized GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
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
        GameSession manager = GameSession.getInstance();
        manager.startGame();

        // Comprobar que siempre es la misma instancia
        GameSession manager2 = GameSession.getInstance();
        System.out.println("¿Es la misma instancia? " + (manager == manager2));
    }
}
