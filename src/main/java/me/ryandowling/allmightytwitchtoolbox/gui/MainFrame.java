package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Allmighty Twitch Toolbox");
        setSize(App.NOTIFIER.getSettings().getGuiSize());
        setLocation(App.NOTIFIER.getSettings().getGuiPosition());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        add(new TabbedPanel(), BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                exit();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                App.NOTIFIER.getSettings().setGuiSize(getSize());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                App.NOTIFIER.getSettings().setGuiPosition(getLocation());
            }
        });

        setVisible(true);
    }

    private void exit() {
        System.out.println("Exiting the application!");
        App.NOTIFIER.saveSettings();
        App.NOTIFIER.stopServer();
        System.exit(0);
    }
}
