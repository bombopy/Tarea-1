import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// ==================== PATRÓN SINGLETON ====================
class GameManager {
    private static GameManager instance;
    private int score = 0;
    private int level = 1;
    private int lives = 3;
    private boolean gameRunning = false;
    private boolean gameOver = false;
    
    private GameManager() {
        System.out.println("GameManager inicializado");
    }
    
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    public void startGame() {
        gameRunning = true;
        gameOver = false;
        score = 0;
        level = 1;
        lives = 3;
    }
    
    public void endGame() {
        gameRunning = false;
        gameOver = true;
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public void nextLevel() {
        level++;
    }
    
    public void loseLife() {
        lives--;
        if (lives <= 0) {
            endGame();
        }
    }
    
    // Getters
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public int getLives() { return lives; }
    public boolean isGameRunning() { return gameRunning; }
    public boolean isGameOver() { return gameOver; }
}

// ==================== PATRÓN STRATEGY ====================
// Estrategias de movimiento
interface MovementStrategy {
    void move(GameObject object);
}

class LinearMovement implements MovementStrategy {
    private final int speed;
    
    public LinearMovement(int speed) {
        this.speed = speed;
    }
    
    @Override
    public void move(GameObject object) {
        object.setY(object.getY() + speed);
    }
}

class ZigzagMovement implements MovementStrategy {
    private final int speed;
    private final int horizontalSpeed;
    private boolean movingRight = true;
    
    public ZigzagMovement(int speed, int horizontalSpeed) {
        this.speed = speed;
        this.horizontalSpeed = horizontalSpeed;
    }
    
    @Override
    public void move(GameObject object) {
        object.setY(object.getY() + speed);
        
        if (movingRight) {
            object.setX(object.getX() + horizontalSpeed);
        } else {
            object.setX(object.getX() - horizontalSpeed);
        }
        
        // Cambiar dirección si llega a los bordes
        if (object.getX() <= 0 || object.getX() >= 750) {
            movingRight = !movingRight;
        }
    }
}

class PlayerMovement implements MovementStrategy {
    private final int speed;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    
    public PlayerMovement(int speed) {
        this.speed = speed;
    }
    
    @Override
    public void move(GameObject object) {
        if (moveLeft && object.getX() > 0) {
            object.setX(object.getX() - speed);
        }
        if (moveRight && object.getX() < 750) {
            object.setX(object.getX() + speed);
        }
    }
    
    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }
    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }
}

// Estrategias de disparo
interface ShootingStrategy {
    java.util.List<Bullet> shoot(int x, int y);
}

class SingleShot implements ShootingStrategy {
    @Override
    public java.util.List<Bullet> shoot(int x, int y) {
        java.util.List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(x, y, -5, Color.YELLOW));
        return bullets;
    }
}

class TripleShot implements ShootingStrategy {
    @Override
    public java.util.List<Bullet> shoot(int x, int y) {
        java.util.List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(x - 10, y, -5, Color.CYAN));
        bullets.add(new Bullet(x, y, -5, Color.CYAN));
        bullets.add(new Bullet(x + 10, y, -5, Color.CYAN));
        return bullets;
    }
}

class EnemyShot implements ShootingStrategy {
    @Override
    public java.util.List<Bullet> shoot(int x, int y) {
        java.util.List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(x, y, 3, Color.RED));
        return bullets;
    }
}

// ==================== PATRÓN FACTORY ====================
// Clase base para objetos del juego
abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Color color;
    protected MovementStrategy movementStrategy;
    
    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void move() {
        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Getters y Setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setMovementStrategy(MovementStrategy strategy) { this.movementStrategy = strategy; }
}

// Enemigos específicos
class SmallInvader extends GameObject {
    private final ShootingStrategy shootingStrategy;
    private int health = 1;
    private final int points = 10;
    
    public SmallInvader(int x, int y) {
        super(x, y, 30, 20, Color.GREEN);
        this.movementStrategy = new LinearMovement(1);
        shootingStrategy = new EnemyShot();
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // Dibujar "ojos"
        g.setColor(Color.RED);
        g.fillOval(x + 5, y + 5, 5, 5);
        g.fillOval(x + 20, y + 5, 5, 5);
    }
    
    public java.util.List<Bullet> shoot() {
        return shootingStrategy.shoot(x + width/2, y + height);
    }
    
    public boolean takeDamage() {
        health--;
        if (health <= 0) {
            GameManager.getInstance().addScore(points);
            return true; // Destruido
        }
        return false;
    }
}

class MediumInvader extends GameObject {
    private final ShootingStrategy shootingStrategy;
    private int health = 2;
    private final int points = 25;
    
    public MediumInvader(int x, int y) {
        super(x, y, 40, 25, Color.BLUE);
        this.movementStrategy = new ZigzagMovement(1, 2);
        shootingStrategy = new EnemyShot();
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // Dibujar detalles
        g.setColor(Color.YELLOW);
        g.fillRect(x + 5, y + 5, 30, 15);
        g.setColor(Color.RED);
        g.fillOval(x + 10, y + 8, 4, 4);
        g.fillOval(x + 26, y + 8, 4, 4);
    }
    
    public java.util.List<Bullet> shoot() {
        return shootingStrategy.shoot(x + width/2, y + height);
    }
    
    public boolean takeDamage() {
        health--;
        if (health <= 0) {
            GameManager.getInstance().addScore(points);
            return true;
        }
        return false;
    }
}

class LargeInvader extends GameObject {
    private final ShootingStrategy shootingStrategy;
    private int health = 3;
    private final int points = 50;
    
    public LargeInvader(int x, int y) {
        super(x, y, 50, 35, Color.RED);
        this.movementStrategy = new LinearMovement(2);
        shootingStrategy = new EnemyShot();
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // Dibujar como boss
        g.setColor(Color.ORANGE);
        g.fillRect(x + 5, y + 5, 40, 25);
        g.setColor(Color.YELLOW);
        g.fillOval(x + 15, y + 10, 8, 8);
        g.fillOval(x + 27, y + 10, 8, 8);
    }
    
    public java.util.List<Bullet> shoot() {
        return shootingStrategy.shoot(x + width/2, y + height);
    }
    
    public boolean takeDamage() {
        health--;
        if (health <= 0) {
            GameManager.getInstance().addScore(points);
            return true;
        }
        return false;
    }
}

// Jugador
class Player extends GameObject {
    private ShootingStrategy shootingStrategy;
    private long lastShot = 0;
    private final long shootCooldown = 200; // 200ms entre disparos
    
    public Player(int x, int y) {
        super(x, y, 40, 30, Color.CYAN);
        this.movementStrategy = new PlayerMovement(5);
        shootingStrategy = new SingleShot();
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // Dibujar detalles de nave
        g.setColor(Color.WHITE);
        g.fillRect(x + 15, y + 5, 10, 20);
        g.setColor(Color.BLUE);
        g.fillOval(x + 17, y + 8, 6, 6);
    }
    
    public java.util.List<Bullet> shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShot >= shootCooldown) {
            lastShot = currentTime;
            return shootingStrategy.shoot(x + width/2, y);
        }
        return new ArrayList<>();
    }
    
    public void upgradeWeapon() {
        shootingStrategy = new TripleShot();
    }
    
    public void resetWeapon() {
        shootingStrategy = new SingleShot();
    }
}

// Bala
class Bullet extends GameObject {
    
    public Bullet(int x, int y, int speed, Color color) {
        super(x, y, 4, 8, color);
        this.movementStrategy = new LinearMovement(speed);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
    
    public boolean isOffScreen() {
        return y < 0 || y > 600;
    }
}

// Fábrica de enemigos
class InvaderFactory {
    public static GameObject createInvader(String type, int x, int y) {
        return switch (type.toLowerCase()) {
            case "small" -> new SmallInvader(x, y);
            case "medium" -> new MediumInvader(x, y);
            case "large" -> new LargeInvader(x, y);
            default -> new SmallInvader(x, y);
        };
    }
    
    public static java.util.List<GameObject> createWave(int level) {
        java.util.List<GameObject> enemies = new ArrayList<>();
        int enemyCount = Math.min(5 + level, 15);
        
        for (int i = 0; i < enemyCount; i++) {
            int x = 50 + (i % 10) * 70;
            int y = 50 + (i / 10) * 60;
            
            GameObject enemy;
            if (level <= 2) {
                enemy = createInvader("small", x, y);
            } else if (level <= 4) {
                enemy = createInvader(i % 2 == 0 ? "small" : "medium", x, y);
            } else {
                String[] types = {"small", "medium", "large"};
                enemy = createInvader(types[i % 3], x, y);
            }
            
            enemies.add(enemy);
        }
        
        return enemies;
    }
}

// ==================== PANEL PRINCIPAL DEL JUEGO ====================
class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final javax.swing.Timer gameTimer;
    private Player player;
    private java.util.List<GameObject> enemies;
    private java.util.List<Bullet> bullets;
    private java.util.List<Bullet> enemyBullets;
    private long lastEnemyShot = 0;
    private final boolean[] keys = new boolean[256];
    
    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        initGame();
        
        gameTimer = new javax.swing.Timer(16, this); // ~60 FPS
        gameTimer.start();
        
        // Add key listener after construction is complete
        SwingUtilities.invokeLater(() -> addKeyListener(this));
    }
    
    private void initGame() {
        GameManager.getInstance().startGame();
        player = new Player(375, 550);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        
        // Crear primera oleada
        enemies.addAll(InvaderFactory.createWave(1));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!GameManager.getInstance().isGameRunning()) {
            drawGameOverScreen(g);
            return;
        }
        
        // Dibujar todos los objetos
        player.draw(g);
        
        for (GameObject enemy : enemies) {
            enemy.draw(g);
        }
        
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        
        for (Bullet bullet : enemyBullets) {
            bullet.draw(g);
        }
        
        // Dibujar UI
        drawUI(g);
    }
    
    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntuacion: " + GameManager.getInstance().getScore(), 10, 25);
        g.drawString("Nivel: " + GameManager.getInstance().getLevel(), 10, 45);
        g.drawString("Vidas: " + GameManager.getInstance().getLives(), 10, 65);
        g.drawString("Controles: A/D o Flechas = Mover, ESPACIO = Disparar", 10, 85);
    }
    
    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String gameOver = "GAME OVER";
        int x = (getWidth() - fm.stringWidth(gameOver)) / 2;
        g.drawString(gameOver, x, getHeight() / 2 - 50);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        fm = g.getFontMetrics();
        String score = "Puntuacion Final: " + GameManager.getInstance().getScore();
        x = (getWidth() - fm.stringWidth(score)) / 2;
        g.drawString(score, x, getHeight() / 2);
        
        String restart = "Presiona R para reiniciar";
        x = (getWidth() - fm.stringWidth(restart)) / 2;
        g.drawString(restart, x, getHeight() / 2 + 50);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!GameManager.getInstance().isGameRunning()) {
            return;
        }
        
        updateGame();
        repaint();
    }
    
    private void updateGame() {
        // Actualizar controles del jugador
        PlayerMovement playerMovement = (PlayerMovement) player.movementStrategy;
        playerMovement.setMoveLeft(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]);
        playerMovement.setMoveRight(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]);
        
        // Mover jugador
        player.move();
        
        // Mover enemigos
        for (GameObject enemy : enemies) {
            enemy.move();
        }
        
        // Mover balas
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        
        for (Bullet bullet : enemyBullets) {
            bullet.move();
        }
        
        // Disparos enemigos
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyShot > 1000 && !enemies.isEmpty()) { // Cada segundo
            int randomEnemy = (int) (Math.random() * enemies.size());
            GameObject enemy = enemies.get(randomEnemy);
            
            if (enemy instanceof SmallInvader) {
                enemyBullets.addAll(((SmallInvader) enemy).shoot());
            } else if (enemy instanceof MediumInvader) {
                enemyBullets.addAll(((MediumInvader) enemy).shoot());
            } else if (enemy instanceof LargeInvader) {
                enemyBullets.addAll(((LargeInvader) enemy).shoot());
            }
            
            lastEnemyShot = currentTime;
        }
        
        // Colisiones bala-enemigo
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            
            if (bullet.isOffScreen()) {
                bulletIterator.remove();
                continue;
            }
            
            Iterator<GameObject> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                GameObject enemy = enemyIterator.next();
                
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bulletIterator.remove();
                    boolean destroyed = false;
                    if (enemy instanceof SmallInvader smallInvader) {
                        destroyed = smallInvader.takeDamage();
                    } else if (enemy instanceof MediumInvader mediumInvader) {
                        destroyed = mediumInvader.takeDamage();
                    } else if (enemy instanceof LargeInvader largeInvader) {
                        destroyed = largeInvader.takeDamage();
                    }
                    
                    if (destroyed) {
                        enemyIterator.remove();
                    }
                    break;
                }
            }
        }
        
        // Colisiones bala enemiga-jugador
        Iterator<Bullet> enemyBulletIterator = enemyBullets.iterator();
        while (enemyBulletIterator.hasNext()) {
            Bullet bullet = enemyBulletIterator.next();
            
            if (bullet.isOffScreen()) {
                enemyBulletIterator.remove();
                continue;
            }
            
            if (bullet.getBounds().intersects(player.getBounds())) {
                enemyBulletIterator.remove();
                GameManager.getInstance().loseLife();
                break;
            }
        }
        
        // Verificar si todos los enemigos han sido destruidos
        if (enemies.isEmpty()) {
            GameManager.getInstance().nextLevel();
            enemies.addAll(InvaderFactory.createWave(GameManager.getInstance().getLevel()));
            
            // Mejorar arma cada 3 niveles
            if (GameManager.getInstance().getLevel() % 3 == 0) {
                player.upgradeWeapon();
            }
        }
        
        // Verificar si algún enemigo llegó al fondo
        for (GameObject enemy : enemies) {
            if (enemy.getY() > 500) {
                GameManager.getInstance().loseLife();
                break;
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.addAll(player.shoot());
        }
        
        if (e.getKeyCode() == KeyEvent.VK_R && GameManager.getInstance().isGameOver()) {
            initGame();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}

// ==================== CLASE PRINCIPAL ====================
public class SpaceInvadersGraphic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Space Invaders - Patrones de Diseño");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            System.out.println("Space Invaders Gráfico iniciado!");
            System.out.println("Controles:");
            System.out.println("- A/D o Flechas: Mover nave");
            System.out.println("- ESPACIO: Disparar");
            System.out.println("- R: Reiniciar (cuando termina el juego)");
            System.out.println("\nPatrones implementados:");
            System.out.println("- SINGLETON: GameManager controla estado global");
            System.out.println("- STRATEGY: Movimiento y disparo intercambiables");
            System.out.println("- FACTORY: Creación dinámica de enemigos");
        });
    }
}
