package me.ryandowling.allmightytwitchtoolbox.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class DonationPanel extends JPanel {
    private final DonationTable donationTable = new DonationTable();
    private JScrollPane scrollPane;

    public DonationPanel() {
        super();
        
        setLayout(new BorderLayout());

        this.scrollPane = new JScrollPane(this.donationTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane
                .HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }
}
