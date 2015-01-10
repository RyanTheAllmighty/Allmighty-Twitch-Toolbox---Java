package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.interfaces.Donation;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;
import me.ryandowling.twitchnotifier.events.listeners.DonationListener;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DonationTable extends JTable implements DonationListener {
    private AbstractTableModel tableModel;

    public DonationTable() {
        super();

        setupTableModel();

        setModel(this.tableModel);

        getTableHeader().setReorderingAllowed(false);

        DonationManager.addListener(this);
    }

    private void setupTableModel() {
        final String[] columnNames = {"Username", "Amount", "Message", "Time"};

        this.tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return App.NOTIFIER.getDonations().size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                List<String> keys = new ArrayList<>();
                keys.addAll(App.NOTIFIER.getDonations().keySet());

                Donation donation = App.NOTIFIER.getDonations().get(keys.get(rowIndex));

                switch (columnIndex) {
                    case 0:
                        return donation.getUsername();
                    case 1:
                        return donation.getPrintableAmount();
                    case 2:
                        return donation.getNote();
                    case 3:
                        return donation.getTimeLocal();
                }

                return null;
            }
        };
    }

    @Override
    public void onNewDonation(final Donation donation) {
        this.tableModel.fireTableDataChanged();
    }
}
