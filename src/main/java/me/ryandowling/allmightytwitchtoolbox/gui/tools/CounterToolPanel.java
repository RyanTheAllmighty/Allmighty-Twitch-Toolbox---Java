package me.ryandowling.allmightytwitchtoolbox.gui.tools;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterToolPanel extends JPanel implements ActionListener {
    private JLabel counterLabel;
    private JLabel counterValueLabel;
    private JButton counterAddButton;
    private JButton counterRemoveButton;
    private JButton counterResetButton;

    private int counter;

    public CounterToolPanel() {
        super();

        setLayout(new FlowLayout());

        setupComponents();
        addComponents();

        resetCounter();
    }

    private int resetCounter() {
        this.counter = 0;

        updateCounter();

        return this.counter;
    }

    private int addToCounter(int toAdd) {
        this.counter += toAdd;

        updateCounter();

        return this.counter;
    }

    private int removeFromCounter(int toRemove) {
        this.counter -= toRemove;

        updateCounter();

        return this.counter;
    }

    private void updateCounter() {
        counterValueLabel.setText("" + this.counter);
    }

    private void setupComponents() {
        this.counterLabel = new JLabel("Counter: ");

        this.counterValueLabel = new JLabel("0");

        this.counterAddButton = new JButton("Add");
        this.counterAddButton.addActionListener(this);

        this.counterRemoveButton = new JButton("Remove");
        this.counterRemoveButton.addActionListener(this);

        this.counterResetButton = new JButton("Reset");
        this.counterResetButton.addActionListener(this);
    }

    private void addComponents() {
        add(this.counterLabel);
        add(this.counterValueLabel);
        add(this.counterAddButton);
        add(this.counterRemoveButton);
        add(this.counterResetButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.counterAddButton) {
            addToCounter(1);
        } else if (e.getSource() == this.counterRemoveButton) {
            removeFromCounter(1);
        } else if (e.getSource() == this.counterResetButton) {
            resetCounter();
        }
    }
}
