package me.ryandowling.allmightytwitchtoolbox.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class FollowerPanel extends JPanel {
    private final FollowerTable followerTable = new FollowerTable();
    private JScrollPane scrollPane;

    public FollowerPanel() {
        super();
        
        setLayout(new BorderLayout());

        this.scrollPane = new JScrollPane(this.followerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane
                .HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }
}
