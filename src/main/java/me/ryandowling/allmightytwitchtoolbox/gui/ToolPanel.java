package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.gui.tools.CounterToolPanel;
import me.ryandowling.allmightytwitchtoolbox.gui.tools.FoobarNowPlayingConverter;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ToolPanel extends JPanel {
    private JPanel mainPane;

    private CounterToolPanel counterToolPanel;
    private FoobarNowPlayingConverter foobarNowPlayingConverter;

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
        this.foobarNowPlayingConverter = new FoobarNowPlayingConverter();
    }

    private void addComponents() {
        this.mainPane.add(this.counterToolPanel);
        this.mainPane.add(this.foobarNowPlayingConverter);

        add(this.mainPane, BorderLayout.CENTER);
    }
}
