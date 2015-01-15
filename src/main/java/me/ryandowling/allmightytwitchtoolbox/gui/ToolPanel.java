package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.gui.tools.CounterToolPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ToolPanel extends JPanel {
    private JPanel mainPane;

    private CounterToolPanel counterToolPanel;

    public ToolPanel() {
        super();

        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.counterToolPanel = new CounterToolPanel();
    }

    private void addComponents() {
        this.mainPane.add(this.counterToolPanel);

        add(this.mainPane, BorderLayout.CENTER);
    }
}
