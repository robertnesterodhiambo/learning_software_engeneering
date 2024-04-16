package main.java.com.mycompany.mavenproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BubbleBurstFinal extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NUM_BUBBLES = 20;
    private static final int BUBBLE_RADIUS = 30;
    private static final int GAME_DURATION_SECONDS = 60;

    private ArrayList<Bubble> bubbles;
    private Timer timer;
    private int score;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private int timeRemaining;

    public BubbleBurstFinal() {
        setTitle("Bubble Burst Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        bubbles = new ArrayList<>();
        score = 0;
        timeRemaining = GAME_DURATION_SECONDS;

        scoreLabel = new JLabel("Score: " + score);
        timerLabel = new JLabel("Time: " + timeRemaining + "s");

        add(scoreLabel, BorderLayout.NORTH);
        add(timerLabel, BorderLayout.SOUTH);

        generateBubbles();

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining + "s");
                if (timeRemaining <= 0) {
                    endGame();
                }
            }
        };
        timer = new Timer(1000, taskPerformer);
        timer.start();

        BubbleMouseListener listener = new BubbleMouseListener();
        addMouseListener(listener);
    }

    private void generateBubbles() {
        Random random = new Random();
        for (int i = 0; i < NUM_BUBBLES; i++) {
            int x = random.nextInt(WIDTH - 2 * BUBBLE_RADIUS) + BUBBLE_RADIUS;
            int y = random.nextInt(HEIGHT - 2 * BUBBLE_RADIUS) + BUBBLE_RADIUS;
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            bubbles.add(new Bubble(x, y, BUBBLE_RADIUS, color));
        }
    }

    private void endGame() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score is " + score);
    }

    private class Bubble {
        int x, y, radius;
        Color color;

        public Bubble(int x, int y, int radius, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }

        public boolean contains(int px, int py) {
            return Math.sqrt((px - x) * (px - x) + (py - y) * (py - y)) <= radius;
        }
    }

    private class BubbleMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int mouseX = e.getX();
            int mouseY = e.getY();
            Iterator<Bubble> iterator = bubbles.iterator();
            while (iterator.hasNext()) {
                Bubble bubble = iterator.next();
                if (bubble.contains(mouseX, mouseY)) {
                    iterator.remove();
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                    break;
                }
            }
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Bubble bubble : bubbles) {
            bubble.draw(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BubbleBurstFinal game = new BubbleBurstFinal();
            game.setVisible(true);
        });
    }
}
