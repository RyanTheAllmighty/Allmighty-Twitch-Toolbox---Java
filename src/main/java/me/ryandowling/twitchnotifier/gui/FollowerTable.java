package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.utils.Utils;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class FollowerTable extends JTable {
    private DefaultTableModel tableModel;

    public FollowerTable() {
        super();

        setupTableModel();

        setModel(this.tableModel);

        getTableHeader().setReorderingAllowed(false);
    }

    private void setupTableModel() {
        String[] columnNames = {"Username", "Time Followed", "How Long Ago?"};

        this.tableModel = new DefaultTableModel(columnNames, App.NOTIFIER.getFollowers().size()) {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                List<String> keys = new ArrayList<>();
                keys.addAll(App.NOTIFIER.getFollowers().keySet());

                TwitchFollower follower = App.NOTIFIER.getFollowers().get(keys.get(rowIndex));

                switch (columnIndex) {
                    case 0:
                        // Name
                        return follower.getUser().getName();
                    case 1:
                        return follower.getCreatedAt();
                    case 2:
                        return Utils.timeConversion((int) ((System.currentTimeMillis() / 1000) - (follower.getCreatedAtTimestamp() / 1000)));
                }

                return null;
            }
        };
    }
}
