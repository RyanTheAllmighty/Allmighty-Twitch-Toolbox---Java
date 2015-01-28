package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.Logger;
import org.jbundle.util.jcalendarbutton.JCalendarButton;
import org.jbundle.util.jcalendarbutton.JTimeButton;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerPanel extends JPanel implements PropertyChangeListener {
    private JPanel mainPane;
    private JPanel buttonPane;

    private JPanel countdownPanel;
    private JLabel countdownLabel;
    private JTextField countdownToLabel;
    private JCalendarButton dateButton;
    private JTimeButton timeButton;

    private Date countdownDate = App.NOTIFIER.getCountdownTimer();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private JButton saveButton;

    public TimerPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupTwitchUsernamePanel();
        setupButtonPanel();
    }

    private void setupTwitchUsernamePanel() {
        this.countdownPanel = new JPanel();
        this.countdownPanel.setLayout(new FlowLayout());

        this.countdownLabel = new JLabel("Countdown To:");

        this.countdownToLabel = new JTextField(dateFormat.format(countdownDate), 16);
        this.countdownToLabel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    Date newDate = dateFormat.parse(countdownToLabel.getText());
                    setCountdownTo(newDate);
                } catch (ParseException e1) {
                    Logger.log("The date entered for countdown timer '" + countdownToLabel.getText() + "' was " +
                            "invalid!");
                    setCountdownTo(App.NOTIFIER.getCountdownTimer());
                }
            }
        });

        this.dateButton = new JCalendarButton();
        this.dateButton.addPropertyChangeListener(this);

        this.timeButton = new JTimeButton();
        this.timeButton.addPropertyChangeListener(this);

        this.countdownPanel.add(this.countdownLabel);
        this.countdownPanel.add(this.countdownToLabel);
        this.countdownPanel.add(this.dateButton);
        this.countdownPanel.add(this.timeButton);
    }

    private void setupButtonPanel() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCountdownTimer();
            }
        });
    }

    private void saveCountdownTimer() {
        App.NOTIFIER.setCountdownTimer(this.countdownDate);
    }

    private void addComponents() {
        this.mainPane.add(this.countdownPanel);

        this.buttonPane.add(this.saveButton);

        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Date) {
            Calendar curCal = Calendar.getInstance();
            curCal.setTime(countdownDate);

            Calendar newCal = Calendar.getInstance();
            newCal.setTime((Date) evt.getNewValue());

            if (evt.getSource() == dateButton) {
                curCal.set(Calendar.DAY_OF_MONTH, newCal.get(Calendar.DAY_OF_MONTH));
                curCal.set(Calendar.MONTH, newCal.get(Calendar.MONTH));
                curCal.set(Calendar.YEAR, newCal.get(Calendar.YEAR));
            } else if (evt.getSource() == timeButton) {
                curCal.set(Calendar.HOUR_OF_DAY, newCal.get(Calendar.HOUR_OF_DAY));
                curCal.set(Calendar.MINUTE, newCal.get(Calendar.MINUTE));
                curCal.set(Calendar.SECOND, 0);
                curCal.set(Calendar.MILLISECOND, 0);
            }

            setCountdownTo(curCal.getTime());
        }
    }

    public void setCountdownTo(Date countdownTo) {
        this.countdownDate = countdownTo;
        this.countdownToLabel.setText(this.dateFormat.format(this.countdownDate));
    }
}
