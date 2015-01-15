package me.ryandowling.allmightytwitchtoolbox.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolPanel extends JPanel {
    private JPanel mainPane;

    private JPanel counterPanel;
    private JLabel counterLabel;
    private JLabel counterValueLabel;
    private JButton counterAddButton;
    private JButton counterRemoveButton;
    private JButton counterResetButton;

    public ToolPanel() {
        super();

        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        setupCounterPanel();
    }

    private void setupCounterPanel() {
        this.counterPanel = new JPanel();
        this.counterPanel.setLayout(new FlowLayout());

        this.counterLabel = new JLabel("Counter: ");

        this.counterValueLabel = new JLabel("0");

        this.counterAddButton = new JButton("Add");
        this.counterAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(counterValueLabel.getText());

                counterValueLabel.setText("" + ++value);
            }
        });

        this.counterRemoveButton = new JButton("Remove");
        this.counterRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(counterValueLabel.getText());

                counterValueLabel.setText("" + --value);
            }
        });

        this.counterResetButton = new JButton("Reset");
        this.counterResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counterValueLabel.setText("0");
            }
        });

        this.counterPanel.add(this.counterLabel);
        this.counterPanel.add(this.counterValueLabel);
        this.counterPanel.add(this.counterAddButton);
        this.counterPanel.add(this.counterRemoveButton);
        this.counterPanel.add(this.counterResetButton);
    }

    private void addComponents() {
        this.mainPane.add(this.counterPanel);

        add(this.mainPane, BorderLayout.CENTER);
    }
}
