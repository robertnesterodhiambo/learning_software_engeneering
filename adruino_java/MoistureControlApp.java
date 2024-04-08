
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;+
import java.util.Enumeration;

public class MoistureControlApp extends JFrame {

    private JSlider moistureSlider;
    private JLabel moistureLabel;
    private SerialPort serialPort;
    private OutputStream outputStream;

    public MoistureControlApp() {
        // Set up the JFrame
        setTitle("Moisture Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        // Create UI components
        moistureSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        moistureSlider.setMajorTickSpacing(10);
        moistureSlider.setMinorTickSpacing(5);
        moistureSlider.setPaintTicks(true);
        moistureSlider.setPaintLabels(true);
        moistureSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateMoistureLabel();
            }
        });

        moistureLabel = new JLabel("Moisture Level: " + moistureSlider.getValue() + "%");

        JButton sendButton = new JButton("Send Moisture Level");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMoistureLevel();
            }
        });

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(moistureSlider);
        panel.add(moistureLabel);
        panel.add(sendButton);

        getContentPane().add(panel);
        setVisible(true);

        // Connect to Arduino
        connectToArduino();
    }

    private void updateMoistureLabel() {
        moistureLabel.setText("Moisture Level: " + moistureSlider.getValue() + "%");
    }

    private void connectToArduino() {
        // Get the list of available serial ports
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                try {
                    // Try to open the port
                    serialPort = (SerialPort) portId.open("MoistureControlApp", 2000);
                    outputStream = serialPort.getOutputStream();
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void sendMoistureLevel() {
        int moistureLevel = moistureSlider.getValue();
        try {
            // Send moisture level to Arduino
            outputStream.write(moistureLevel);
            System.out.println("Sent moisture level: " + moistureLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MoistureControlApp();
            }
        });
    }
}
