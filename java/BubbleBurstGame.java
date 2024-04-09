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
  private JButton startButton;
  private JButton restartButton;
  private JSlider difficultySlider;
  private Timer timer;
  private int bubblesCount;
  private int currentRound;
  private ArrayList<Bubble> bubbles;

  private final int PANEL_WIDTH = 600;
  private final int PANEL_HEIGHT = 400;
  private final int BUBBLE_RADIUS = 20;
  private final int INITIAL_TIMER_DELAY = 15000;
  private final int TIMER_DECREASE_PER_ROUND = 1000;

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
    timer.restart();
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
    gamePanel.add(timerLabel, BorderLayout.NORTH);

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

  private void endRound() {
    timer.stop();
    startNextRound();
  }

  private void startNextRound() {
    currentRound++;
    if (currentRound <= 10) {
      gameFrame.setTitle("Bubble Burst Game - Round " + currentRound);
      if (currentRound > 1 && bubbles.isEmpty()) {
        spawnBubbles(); // Spawn bubbles only if there are none left for rounds 2 to 10
      }
      setupTimer();
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
