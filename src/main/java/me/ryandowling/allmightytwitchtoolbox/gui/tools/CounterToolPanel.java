package me.ryandowling.allmightytwitchtoolbox.gui.tools;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import me.ryandowling.allmightytwitchtoolbox.App;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CounterToolPanel extends JPanel implements ActionListener {
    private JLabel counterLabel;
    private JLabel counterValueLabel;
    private JButton counterAddButton;
    private JButton counterRemoveButton;
    private JButton counterResetButton;

    private int counter = App.NOTIFIER.getSettings().getCounter();

    public CounterToolPanel() {
        super();

        setLayout(new FlowLayout());

        setupComponents();
        addComponents();

        setupHotKeys();
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
        App.NOTIFIER.setCounter(this.counter);
        counterValueLabel.setText("" + this.counter);
    }

    private void setupComponents() {
        this.counterLabel = new JLabel("Counter: ");

        this.counterValueLabel = new JLabel("" + this.counter);

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

    private void setupHotKeys() {
        App.NOTIFIER.getHotKeyProvider().register(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, KeyEvent.CTRL_DOWN_MASK),
                new HotKeyListener() {
                    @Override
                    public void onHotKey(HotKey hotKey) {
                        addToCounter(1);
                    }
                });

        App.NOTIFIER.getHotKeyProvider().register(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, KeyEvent
                .CTRL_DOWN_MASK), new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotKey) {
                removeFromCounter(1);
            }
        });

        App.NOTIFIER.getHotKeyProvider().register(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, KeyEvent
                .CTRL_DOWN_MASK), new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotKey) {
                resetCounter();
            }
        });
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
