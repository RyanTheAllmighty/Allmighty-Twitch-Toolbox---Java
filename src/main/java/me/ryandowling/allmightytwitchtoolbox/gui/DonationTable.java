package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        final JPopupMenu popup = new JPopupMenu();
        final JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.NOTIFIER.removeDonation((String) tableModel.getValueAt(getSelectedRow(), 0));
                tableModel.fireTableDataChanged();
            }
        });
        popup.add(delete);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());

                    if (!source.isRowSelected(row)) {
                        source.changeSelection(row, column, false, false);
                    }

                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void setupTableModel() {
        final String[] columnNames = {"ID", "Username", "Amount", "Message", "Time"};

        this.tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return App.NOTIFIER.getDonations().size() <= 100 ? App.NOTIFIER.getDonations().size() : 100;
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
                        return donation.getID();
                    case 1:
                        return donation.getUsername();
                    case 2:
                        return donation.getPrintableAmount();
                    case 3:
                        return donation.getNote();
                    case 4:
                        return donation.getTimeLocal();
                }

                return null;
            }
        };
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int row = rowAtPoint(event.getPoint());
        int column = columnAtPoint(event.getPoint());

        if (column != 2) {
            return null;
        }

        Object value = getValueAt(row, column);
        return value == null ? null : value.toString();
    }

    @Override
    public void onNewDonation(final Donation donation) {
        this.tableModel.fireTableDataChanged();
    }
}
