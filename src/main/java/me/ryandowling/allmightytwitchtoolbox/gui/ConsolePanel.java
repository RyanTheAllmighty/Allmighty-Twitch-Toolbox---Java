package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConsolePanel extends JPanel {
    private Console console = new Console();
    private JScrollPane scrollPane;
    private JPopupMenu contextMenu = new JPopupMenu(); // Right click menu

    private JMenuItem copy = new JMenuItem("Copy");

    public ConsolePanel() {
        super();
        this.setLayout(new BorderLayout());

        this.setupContextMenu(); // Setup the right click menu

        this.scrollPane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane
                .HORIZONTAL_SCROLLBAR_NEVER);
        this.add(this.scrollPane, BorderLayout.CENTER);

        App.NOTIFIER.setConsole(this.console);
    }

    private void setupContextMenu() {
        this.copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection text = new StringSelection(console.getSelectedText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(text, null);
            }
        });
        this.contextMenu.add(copy);

        this.console.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (console.getSelectedText() != null) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        contextMenu.show(console, e.getX(), e.getY());
                    }
                }
            }
        });
    }
}
