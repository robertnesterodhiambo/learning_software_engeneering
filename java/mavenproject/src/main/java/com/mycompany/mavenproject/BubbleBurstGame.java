package com.mycompany.mavenproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
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
    private JLabel timerLabel;
    private JLabel sideTimerLabel;
    private JButton startButton;
    private JButton restartButton;
    private JSlider difficultySlider;
    private Timer timer;
    private int bubblesCount;
    private int currentRound;
    private ArrayList<Bubble> bubbles;
    private boolean roundInProgress;

    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 400;
    private final int BUBBLE_RADIUS = 20;
    private final int INITIAL_TIMER_DELAY = 15000;
    private final int TIMER_DECREASE_PER_ROUND = 1000;
    private final int MAX_ROUNDS = 10;

    public void createAndShowGUI() {
        mainFrame = new JFrame("Bubble Burst Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startRound1();
        });

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> startRound1());

        // Create custom labels for difficulty slider
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(4, new JLabel("Easy"));
        labelTable.put(5, new JLabel("Medium"));
        labelTable.put(6, new JLabel("Hard"));

        difficultySlider = new JSlider(JSlider.HORIZONTAL, 4, 6, 4);
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setLabelTable(labelTable);

        mainFrame.add(startButton);
        mainFrame.add(restartButton);
        mainFrame.add(difficultySlider);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void startRound1() {
        bubblesCount = difficultySlider.getValue();
        currentRound = 1;
        bubbles = new ArrayList<>();
        createGamePanel();
        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
        spawnBubbles();
        setupTimer();
        timer.start();
        roundInProgress = true;
    }

    private void createGamePanel() {
        gameFrame = new JFrame("Bubble Burst Game - Round " + currentRound);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        JPanel gameContainer = new JPanel(new BorderLayout());

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

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        sideTimerLabel = new JLabel();
        sideTimerLabel.setHorizontalAlignment(JLabel.CENTER);
        sideTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gameContainer.add(timerLabel, BorderLayout.NORTH);
        gameContainer.add(gamePanel, BorderLayout.CENTER);

        gamePanel.add(sideTimerLabel, BorderLayout.EAST);

        gameFrame.add(gameContainer, BorderLayout.CENTER);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (roundInProgress) {
                    checkBubbleBurst(e.getX(), e.getY());
                }
                if (bubbles.isEmpty()) {
                    endRound();
                }
            }
        });

        gameFrame.pack();
        gameFrame.setVisible(false);
    }

    private void setupTimer() {
        int initialDelay = INITIAL_TIMER_DELAY - (currentRound - 1) * TIMER_DECREASE_PER_ROUND;
        timer = new Timer(1000, new ActionListener() { // Timer ticks every second
            int timeRemaining = initialDelay / 1000;

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining >= 0) {
                    timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
                    sideTimerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
                    gamePanel.repaint();
                } else {
                    endRound();
                }
            }
        });
    }

    private void endRound() {
        timer.stop();
        if (bubbles.isEmpty()) {
            roundInProgress = false;
            startNextRound();
        } else {
            gameOver("Game Over - You failed!");
        }
    }

    private void startNextRound() {
        if (currentRound >= MAX_ROUNDS) {
            gameOver("Congratulations! You completed all rounds.");
            return;
        }
        currentRound++;
        gameFrame.setTitle("Bubble Burst Game - Round " + currentRound);
        spawnBubbles();
        setupTimer();
        timer.start(); // Start the timer for the next round
        roundInProgress = true;
    }

    private void gameOver(String message) {
        JOptionPane.showMessageDialog(gameFrame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        gameFrame.dispose();
        mainFrame.setVisible(true);
    }

    private void spawnBubbles() {
        Random random = new Random();
        boolean overlap;
        for (int i = 0; i < bubblesCount; i++) {
            do {
                overlap = false;
                int x = random.nextInt(PANEL_WIDTH - BUBBLE_RADIUS * 2);
                int y = random.nextInt(PANEL_HEIGHT - BUBBLE_RADIUS * 2);
                for (Bubble bubble : bubbles) {
                    double distance = Math.sqrt(Math.pow(x - bubble.getX(), 2) + Math.pow(y - bubble.getY(), 2));
                    if (distance <= BUBBLE_RADIUS * 2) {
                        overlap = true;
                        break;
                    }
                }
                if (!overlap) {
                    Bubble bubble = new Bubble(x, y);
                    bubbles.add(bubble);
                }
            } while (overlap);
        }
        gamePanel.repaint();
    }

    private void checkBubbleBurst(int x, int y) {
        for (Bubble bubble : bubbles) {
            double distance = Math.sqrt(Math.pow(x - (bubble.getX() + BUBBLE_RADIUS), 2) +
                    Math.pow(y - (bubble.getY() + BUBBLE_RADIUS), 2));
            if (distance <= BUBBLE_RADIUS) {
                bubbles.remove(bubble);
                gamePanel.repaint();
                return;
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

        public int getY() {
            return y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillOval(x, y, BUBBLE_RADIUS * 2, BUBBLE_RADIUS * 2);
        }
    }
}
