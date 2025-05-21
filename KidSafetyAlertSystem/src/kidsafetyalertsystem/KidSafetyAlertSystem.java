package kidsafetyalertsystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Notification {
    void update(String alertMessage);
}

// Alert Controller to manage alerts
class AlertController {
    private List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }

    public void notifyObservers(String alertMessage) {
        for (Notification notification : notifications) {
            notification.update(alertMessage);
        }
    }
}

// Event Detector to detect dangerous situations
class EventDetector {
    private AlertController alertController;

    public EventDetector(AlertController alertController) {
        this.alertController = alertController;
    }

    public void detectEvent(String dangerType) {
        alertController.notifyObservers("🚨 Urgent! " + dangerType);
    }
}

// Sensor class representing parents
class Sensor implements Notification {
    private String parentName;

    public Sensor(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void update(String alertMessage) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(parentName + " Alert");
            JOptionPane.showMessageDialog(frame, 
                "📩 Alert for " + parentName + ":\n" + alertMessage, 
                parentName + " Alert", 
                JOptionPane.WARNING_MESSAGE);
        });
    }
}

// Main Kid Safety Alert System UI
public class KidSafetyAlertSystem extends JFrame {
    private EventDetector eventDetector;

    public KidSafetyAlertSystem() {
        AlertController alertController = new AlertController();
        eventDetector = new EventDetector(alertController);

        Sensor mom = new Sensor("Mom");
        Sensor dad = new Sensor("Dad");

        alertController.addNotification(mom);
        alertController.addNotification(dad);

        JButton animalButton = new JButton("Animal Inside House");
        animalButton.addActionListener(e -> eventDetector.detectEvent("Animal detected inside the house!"));

        JButton fallButton = new JButton("Kid Fell While Playing");
        fallButton.addActionListener(e -> eventDetector.detectEvent("Kid fell while playing!"));

        JButton strangerButton = new JButton("Stranger Approaching");
        strangerButton.addActionListener(e -> eventDetector.detectEvent("A stranger is approaching the child!"));

        JButton exitButton = new JButton("Emergency Exit Required");
        exitButton.addActionListener(e -> eventDetector.detectEvent("Urgent exit required for safety!"));

        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(e -> System.exit(0));

        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(animalButton);
        buttonPanel.add(fallButton);
        buttonPanel.add(strangerButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setTitle("Kid Safety Alert System");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KidSafetyAlertSystem().setVisible(true));
    }
}

