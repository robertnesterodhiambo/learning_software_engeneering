import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SpaceInvaders extends JFrame {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PLAYER_SPEED = 10;
    private final int ALIEN_ROWS = 5;
    private final int ALIEN_COLS = 10;
    private final int ALIEN_WIDTH = 40;
    private final int ALIEN_HEIGHT = 30;
    private final int ALIEN_SPEED = 5;
    private final int LASER_SPEED = 10;
    private final int MISSILE_SPEED = 8;
    private final int MAX_LIVES = 3;

    private int playerX = WIDTH / 2;
    private final int playerY = HEIGHT - 100;
    private final int playerWidth = 80;
    private final int playerHeight = 40;
    private int playerLives = MAX_LIVES;
    private int level = 1;

    private ArrayList<Alien> aliens;
    private ArrayList<Laser> lasers;
    private ArrayList<Missile> missiles;
    private boolean isGameOver = false;
    private boolean isNewLevel = false;

    public SpaceInvaders() {
        setTitle("Space Invaders");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Set focusable for keyboard events
        setFocusable(true);

        initializeAliens();
        lasers = new ArrayList<>();
        missiles = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    if (playerX > 0) {
                        playerX -= PLAYER_SPEED;
                    }
                } else if (key == KeyEvent.VK_RIGHT) {
                    if (playerX < WIDTH - playerWidth) {
                        playerX += PLAYER_SPEED;
                    }
                } else if (key == KeyEvent.VK_SPACE) {
                    shootLaser();
                }
                repaint();
            }
        });

        // Start the game loop
        new Thread(this::gameLoop).start();

        setVisible(true);
    }

    private void initializeAliens() {
        aliens = new ArrayList<>();
        int startY = 50;
        int numAliens = 5 + (level - 1) * 5; // Increase number of aliens with each level
        for (int row = 0; row < ALIEN_ROWS; row++) {
            int startX = 50;
            for (int col = 0; col < numAliens; col++) { // Adjust the number of aliens
                Alien alien = new Alien(startX, startY, ALIEN_WIDTH, ALIEN_HEIGHT);
                aliens.add(alien);
                startX += ALIEN_WIDTH + 20; // Gap between aliens
            }
            startY += ALIEN_HEIGHT + 20; // Gap between rows
        }
    }

    private void gameLoop() {
        while (!isGameOver) {
            moveAliens();
            shootAliens();
            moveLasers();
            moveMissiles();
            checkCollisions();
            repaint();
            if (isNewLevel) {
                initializeAliens();
                isNewLevel = false;
            }
            try {
                Thread.sleep(50); // Adjust game speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game Over");
    }

    private void moveAliens() {
        for (Alien alien : aliens) {
            alien.move();
            if (alien.getY() + ALIEN_HEIGHT >= playerY) {
                isGameOver = true;
                break;
            }
            if (alien.getX() <= 0 || alien.getX() + ALIEN_WIDTH >= WIDTH) {
                for (Alien a : aliens) {
                    a.reverseDirection();
                    a.moveDown();
                }
                break;
            }
        }

        // Increase alien speed with each level
        for (Alien alien : aliens) {
            alien.setSpeed(ALIEN_SPEED + level);
        }
    }

    private void shootAliens() {
        Random rand = new Random();
        for (Alien alien : aliens) {
            if (rand.nextDouble() < 0.02) { // Adjust shooting frequency
                // Create and start a new thread for each alien's missile
                new Thread(() -> {
                    int x = alien.getX() + ALIEN_WIDTH / 2;
                    int y = alien.getY() + ALIEN_HEIGHT;
                    Missile missile = new Missile(x, y);
                    missiles.add(missile);
                    while (y < HEIGHT) {
                        y += MISSILE_SPEED;
                        repaint();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    missiles.remove(missile);
                }).start();
            }
        }
    }

    private void moveLasers() {
        Iterator<Laser> iterator = lasers.iterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.move();
            if (laser.getY() < 0) {
                iterator.remove();
            }
        }
    }

    private void moveMissiles() {
        Iterator<Missile> iterator = missiles.iterator();
        while (iterator.hasNext()) {
            Missile missile = iterator.next();
            missile.move();
            if (missile.getY() > HEIGHT) {
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        // Create a copy of the aliens list to avoid ConcurrentModificationException
        ArrayList<Alien> aliensCopy = new ArrayList<>(aliens);

        // Check collisions between lasers and aliens
        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            Iterator<Alien> alienIterator = aliensCopy.iterator(); // Iterate over the copy
            while (alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                if (laser.intersects(alien)) {
                    laserIterator.remove();
                    alienIterator.remove();
                    break;
                }
            }
        }

        // Check collisions between missiles and ship
        Iterator<Missile> missileIterator = missiles.iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            if (missile.intersects(playerX, playerY, playerWidth, playerHeight)) {
                missileIterator.remove();
                playerLives--;
                if (playerLives <= 0) {
                    isGameOver = true;
                }
                break;
            }
        }

        // Check if all aliens are killed
        if (aliensCopy.isEmpty()) {
            level++; // Increase level
            isNewLevel = true;
        }
    }

    private void shootLaser() {
        lasers.add(new Laser(playerX + playerWidth / 2, playerY));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw player
        g.setColor(Color.white);
        int[] xPoints = {playerX, playerX + playerWidth / 2, playerX + playerWidth, playerX + playerWidth / 2};
        int[] yPoints = {playerY + playerHeight, playerY, playerY + playerHeight, playerY + playerHeight};
        g.fillPolygon(xPoints, yPoints, 4);

        // Draw aliens
        g.setColor(Color.green);
        for (Alien alien : aliens) {
            g.fillPolygon(alien.getXPoints(), alien.getYPoints(), 4);
        }

        // Draw lasers
        g.setColor(Color.red);
        for (Laser laser : lasers) {
            g.fillRect(laser.getX() - 1, laser.getY(), 2, 10);
        }

        // Draw missiles
        g.setColor(Color.yellow);
        for (Missile missile : missiles) {
            g.fillRect(missile.getX() - 1, missile.getY(), 2, 10);
        }

        // Draw player lives
        g.setColor(Color.white);
        g.drawString("Lives: " + playerLives, 20, 20);

        // Draw game over message
        if (isGameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2);
        }
    }

    private class Alien {
        private int x;
        private int y;
        private int width;
        private int height;
        private int dx = ALIEN_SPEED; // Horizontal movement speed
        private boolean moveRight = true;

        public Alien(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void move() {
            if (moveRight) {
                x += dx;
            } else {
                x -= dx;
            }
        }

        public void moveDown() {
            y += ALIEN_HEIGHT;
        }

        public void reverseDirection() {
            moveRight = !moveRight;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setSpeed(int speed) {
            dx = speed;
        }

        public int[] getXPoints() {
            return new int[]{x, x + width / 2, x + width, x + width / 2};
        }

        public int[] getYPoints() {
            return new int[]{y + height, y, y + height, y + height};
        }
    }

    private class Laser {
        private int x;
        private int y;

        public Laser(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move() {
            y -= LASER_SPEED;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean intersects(Alien alien) {
            return x >= alien.getX() && x <= alien.getX() + ALIEN_WIDTH &&
                    y >= alien.getY() && y <= alien.getY() + ALIEN_HEIGHT;
        }
    }

    private class Missile {
        private int x;
        private int y;

        public Missile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move() {
            y += MISSILE_SPEED;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean intersects(int x, int y, int width, int height) {
            return this.x >= x && this.x <= x + width &&
                    this.y >= y && this.y <= y + height;
        }
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}
