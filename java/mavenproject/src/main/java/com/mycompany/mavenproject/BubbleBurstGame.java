    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.mycompany.mavenproject;

    /**
     *
     * @author oem
     */
    
import javax.swing.*;
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
    private JLabel timerLabel; // Label to display countdown
    private JButton startButton;
    private JButton restartButton;
    private JSlider difficultySlider;
    private Timer timer;
    private Timer countdownTimer; // New countdown timer
    private int bubblesCount;
    private int currentRound;
    private ArrayList<Bubble> bubbles;

    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 400;
    private final int BUBBLE_RADIUS = 20;
    private final int INITIAL_TIMER_DELAY = 15000;
    private final int TIMER_DECREASE_PER_ROUND = 1000;
    private final int COUNTDOWN_DURATION = 60000; // 60 seconds countdown

    private boolean round1Completed = false;

    public void createAndShowGUI() {
        mainFrame = new JFrame("Bubble Burst Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            if (!round1Completed) {
                startRound1();
            } else {
                startNextRound();
            }
        });

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> startRound1());

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

    private void startRound1() {
        bubblesCount = difficultySlider.getValue();
        currentRound = 1;
        bubbles = new ArrayList<>();
        createGamePanel();
        mainFrame.setVisible(false);
        gameFrame.setVisible(true);
        round1Positioning();
    }

    private void round1Positioning() {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bubbles.size() < bubblesCount) {
                    int x = e.getX();
                    int y = e.getY();
                    if (isWithinPanel(x, y)) {
                        Bubble bubble = new Bubble(x - BUBBLE_RADIUS, y - BUBBLE_RADIUS);
                        bubbles.add(bubble);
                        gamePanel.repaint();
                        if (bubbles.size() == bubblesCount) {
                            round1Completed = true;
                            startNextRound();
                        }
                    } else {
                        JOptionPane.showMessageDialog(gameFrame, "Please select a position within the panel dimensions.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void startNextRound() {
        currentRound++;
        if (currentRound <= 10) {
            gameFrame.setTitle("Bubble Burst Game - Round " + currentRound);
            bubbles.clear();
            spawnBubbles();
            setupTimer();
            setupCountdownTimer(); // Setup countdown timer for each round
            timer.restart();
            countdownTimer.restart(); // Start the countdown timer
        } else {
            gameOver("Congratulations! You completed all rounds.");
        }
    }

    private boolean isWithinPanel(int x, int y) {
        return x >= BUBBLE_RADIUS && x <= PANEL_WIDTH - BUBBLE_RADIUS &&
                y >= BUBBLE_RADIUS && y <= PANEL_HEIGHT - BUBBLE_RADIUS;
    }

    private void createGamePanel() {
        gameFrame = new JFrame("Bubble Burst Game - Round " + currentRound);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

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

        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(timerLabel, BorderLayout.NORTH); // Add timer label to game panel

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkBubbleBurst(e.getX(), e.getY());
            }
        });
        
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
    }

    private void setupCountdownTimer() {
        countdownTimer = new Timer(COUNTDOWN_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOver("Time's up! You ran out of time.");
            }
        });
        countdownTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the timer label with the remaining time
                int remainingTime = (int) Math.ceil((double) countdownTimer.getDelay() / 1000);
                timerLabel.setText("Time Left: " + remainingTime + " seconds");
            }
        });
    }

    private void endRound() {
        timer.stop();
        countdownTimer.stop(); // Stop the countdown timer
        if (currentRound < 10) {
            startNextRound();
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
        for (int i = 0; i < bubblesCount; i++) {
            int x = random.nextInt(PANEL_WIDTH - BUBBLE_RADIUS * 2);
            int y = random.nextInt(PANEL_HEIGHT - BUBBLE_RADIUS * 2);
            Bubble bubble = new Bubble(x, y);
            bubbles.add(bubble);
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
                if (bubbles.isEmpty()) {
                    endRound();
                }
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
