package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.interfaces.Follower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
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
}
