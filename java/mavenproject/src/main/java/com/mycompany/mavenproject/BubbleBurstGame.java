    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.mycompany.mavenproject;

    /**
     *
     * @author oem
     */import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.ArrayList;
    import java.util.Random;

    public class BubbleBurstGame {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new BubbleBurstGame().createAndShowGUI();
            });
        }

        private JFrame mainFrame;
        private JFrame gameFrame;
        private JPanel gamePanel;
        private JButton startButton;
        private JButton restartButton;
        private JSlider difficultySlider;
        private JLabel roundLabel;
        private Timer timer;
        private int bubblesCount;
        private int currentRound;
        private ArrayList<Bubble> bubbles;

        private final int PANEL_WIDTH = 600;
        private final int PANEL_HEIGHT = 400;
        private final int BUBBLE_RADIUS = 20;
        private final int INITIAL_TIMER_DELAY = 15000;
        private final int TIMER_DECREASE_PER_ROUND = 1000;
        private final int NEIGHBORHOOD_SIZE_START = 50;
        private final int NEIGHBORHOOD_SIZE_INCREMENT = 18;

        public void createAndShowGUI() {
            mainFrame = new JFrame("Bubble Burst Game");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLayout(new FlowLayout());

            startButton = new JButton("Start");
            startButton.addActionListener(e -> startGame());

            restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> startGame());

            difficultySlider = new JSlider(JSlider.HORIZONTAL, 4, 6, 4);
            difficultySlider.setMajorTickSpacing(1);
            difficultySlider.setPaintTicks(true);
            difficultySlider.setPaintLabels(true);

            mainFrame.add(startButton);
            mainFrame.add(restartButton);
            mainFrame.add(difficultySlider);

            mainFrame.pack();
            mainFrame.setVisible(true);
        }

      private void startGame() {
        bubblesCount = difficultySlider.getValue();
        currentRound = 1;
        bubbles = new ArrayList<>();
        createGamePanel(); // Call createGamePanel here
        setupTimer();
        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
        spawnBubbles(); // Call spawnBubbles after gameFrame is made visible
    }


        private void createGamePanel() {
            gameFrame = new JFrame("Bubble Burst Game - Round " + currentRound);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamePanel = new JPanel() {
                @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Bubble bubble : bubbles) {
            bubble.draw(g);
        }
    }


                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
                }
            };
            gamePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    checkBubbleBurst(e.getX(), e.getY());
                }
            });
            gameFrame.add(gamePanel);
            gameFrame.pack();
            gameFrame.setVisible(false);
        }

        private void setupTimer() {
            int delay = INITIAL_TIMER_DELAY - (currentRound - 1) * TIMER_DECREASE_PER_ROUND;
            timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    endRound();
                }
            });
            timer.start();
        }

        private void endRound() {
            timer.stop();
            if (currentRound < 10) {
                currentRound++;
                gameFrame.setTitle("Bubble Burst Game - Round " + currentRound);
                // Respawn bubbles
                bubbles.clear();
                spawnBubbles();
                timer.setInitialDelay(timer.getInitialDelay() - TIMER_DECREASE_PER_ROUND);
                timer.restart();
            } else {
                gameOver("Congratulations! You completed all rounds.");
            }
        }

        private void gameOver(String message) {
            JOptionPane.showMessageDialog(gameFrame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            gameFrame.dispose();
            mainFrame.setVisible(true);
        }

    private void spawnBubbles() {
        Random random = new Random();
        int neighborhoodSize = NEIGHBORHOOD_SIZE_START + NEIGHBORHOOD_SIZE_INCREMENT * (currentRound - 1);
        for (int i = 0; i < bubblesCount; i++) {
            int x = random.nextInt(PANEL_WIDTH - BUBBLE_RADIUS * 2);
            int y = random.nextInt(PANEL_HEIGHT - BUBBLE_RADIUS * 2);
            Bubble bubble = new Bubble(x, y);
            while (checkCollision(bubble) || !isWithinPanel(bubble)) {
                x = random.nextInt(PANEL_WIDTH - BUBBLE_RADIUS * 2);
                y = random.nextInt(PANEL_HEIGHT - BUBBLE_RADIUS * 2);
                bubble.setX(x);
                bubble.setY(y);
            }
            bubbles.add(bubble);
        }
    }


        private boolean isWithinPanel(Bubble bubble) {
            return bubble.getX() >= BUBBLE_RADIUS && bubble.getX() <= PANEL_WIDTH - BUBBLE_RADIUS * 2 &&
                    bubble.getY() >= BUBBLE_RADIUS && bubble.getY() <= PANEL_HEIGHT - BUBBLE_RADIUS * 2;
        }

        private boolean checkCollision(Bubble newBubble) {
            for (Bubble bubble : bubbles) {
                double distance = Math.sqrt(Math.pow(newBubble.getX() - bubble.getX(), 2) +
                        Math.pow(newBubble.getY() - bubble.getY(), 2));
                if (distance < BUBBLE_RADIUS * 2) {
                    return true; // Collision detected
                }
            }
            return false; // No collision
        }

       private void checkBubbleBurst(int x, int y) {
        System.out.println("Mouse clicked at: (" + x + ", " + y + ")");
        for (Bubble bubble : bubbles) {
            // Calculate the distance from the center of the bubble
            double distance = Math.sqrt(Math.pow(x - (bubble.getX() + BUBBLE_RADIUS), 2) + 
                                         Math.pow(y - (bubble.getY() + BUBBLE_RADIUS), 2));
            System.out.println("Distance: " + distance);
            if (distance <= BUBBLE_RADIUS) {
                System.out.println("Bubble clicked at: (" + bubble.getX() + ", " + bubble.getY() + ")");
                bubbles.remove(bubble);
                gamePanel.repaint();
                if (bubbles.isEmpty()) {
                    endRound();
                }
                return; // Return after bubble is burst
            }
        }
    }



        private class Bubble {
            private int x;
            private int y;

            public Bubble(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public void draw(Graphics g) {
                g.setColor(Color.BLUE);
                g.fillOval(x, y, BUBBLE_RADIUS * 2, BUBBLE_RADIUS * 2);
            }
        }
    }
