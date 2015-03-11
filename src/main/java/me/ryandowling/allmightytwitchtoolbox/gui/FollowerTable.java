package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;

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

public class FollowerTable extends JTable implements FollowerListener {
    private AbstractTableModel tableModel;

    public FollowerTable() {
        super();

        setupTableModel();

        setModel(this.tableModel);

        getTableHeader().setReorderingAllowed(false);

        FollowerManager.addListener(this);

        final JPopupMenu popup = new JPopupMenu();
        final JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.NOTIFIER.removeFollower((String) tableModel.getValueAt(getSelectedRow(), 0));
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
        final String[] columnNames = {"Username", "Time Followed"};

        this.tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return App.NOTIFIER.getFollowersTotal() <= 100 ? App.NOTIFIER.getFollowersTotal() : 100;
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
                keys.addAll(App.NOTIFIER.getFollowers().keySet());

                Follower follower = App.NOTIFIER.getFollowers().get(keys.get(rowIndex));

                switch (columnIndex) {
                    case 0:
                        return follower.getDisplayName();
                    case 1:
                        return follower.getTimeLocal();
                }

                return null;
            }
        };
    }

    @Override
    public void onNewFollow(final Follower follower) {
        this.tableModel.fireTableDataChanged();
    }

    @Override
    public void onFollowersNumberChanged(final int newTotal) {

    }
}
