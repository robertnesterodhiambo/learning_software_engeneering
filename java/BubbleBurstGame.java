import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BubbleBurstGame extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int NUM_BUBBLES = 20;
    private static final int BUBBLE_SIZE = 40;

    private ArrayList<Point> bubbles;
    private Timer timer;
    private int timeLeft;

    public BubbleBurstGame() {
        setTitle("Bubble Burst Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        bubbles = new ArrayList<>();
        generateBubbles();

        timeLeft = 30; // 30 seconds

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(BubbleBurstGame.this, "Time's up! You lose!");
                }
            }
        });
        timer.start();

        BubblePanel bubblePanel = new BubblePanel();
        add(bubblePanel);

        setVisible(true);
    }

    private void generateBubbles() {
        Random random = new Random();
        for (int i = 0; i < NUM_BUBBLES; i++) {
            int x = random.nextInt(WIDTH - BUBBLE_SIZE);
            int y = random.nextInt(HEIGHT - BUBBLE_SIZE);
            bubbles.add(new Point(x, y));
        }
    }

    private class BubblePanel extends JPanel {
        public BubblePanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.WHITE);

            addMouseListener(new BubbleMouseListener());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Point bubble : bubbles) {
                g.setColor(Color.BLUE);
                g.fillOval(bubble.x, bubble.y, BUBBLE_SIZE, BUBBLE_SIZE);
            }

            g.setColor(Color.BLACK);
            g.drawString("Time Left: " + timeLeft + " seconds", 10, 20);
        }
    }

    private class BubbleMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Point mousePoint = e.getPoint();
            for (Point bubble : bubbles) {
                if (bubble.contains(mousePoint)) {
                    bubbles.remove(bubble);
                    if (bubbles.isEmpty()) {
                        timer.stop();
                        JOptionPane.showMessageDialog(BubbleBurstGame.this, "Congratulations! You burst all bubbles!");
                    }
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BubbleBurstGame();
            }
        });
    }
}
