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
import java.awt.Color;
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

    private JPanel countdownPanel1;
    private JLabel countdownLabel1;
    private JTextField countdownToLabel1;
    private JCalendarButton dateButton1;
    private JTimeButton timeButton1;
    private JButton resetButton1;
    private JButton addMinuteButton1;
    private JButton addHourButton1;
    private JButton addDayButton1;

    private JPanel countdownPanel2;
    private JLabel countdownLabel2;
    private JTextField countdownToLabel2;
    private JCalendarButton dateButton2;
    private JTimeButton timeButton2;
    private JButton resetButton2;
    private JButton addMinuteButton2;
    private JButton addHourButton2;
    private JButton addDayButton2;

    private JPanel countdownPanel3;
    private JLabel countdownLabel3;
    private JTextField countdownToLabel3;
    private JCalendarButton dateButton3;
    private JTimeButton timeButton3;
    private JButton resetButton3;
    private JButton addMinuteButton3;
    private JButton addHourButton3;
    private JButton addDayButton3;

    private Date countdownDate1 = App.NOTIFIER.getCountdownTimer(1);
    private Date countdownDate2 = App.NOTIFIER.getCountdownTimer(2);
    private Date countdownDate3 = App.NOTIFIER.getCountdownTimer(3);

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private JButton saveButton;

    public TimerPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();

        checkForOldDates();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupCountdownPanel1();
        setupCountdownPanel2();
        setupCountdownPanel3();
        setupButtonPanel();
    }

    private void setupCountdownPanel1() {
        this.countdownPanel1 = new JPanel();
        this.countdownPanel1.setLayout(new FlowLayout());

        this.countdownLabel1 = new JLabel("Countdown 1:");

        this.countdownToLabel1 = new JTextField(dateFormat.format(countdownDate1), 16);
        this.countdownToLabel1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    Date newDate = dateFormat.parse(countdownToLabel1.getText());
                    setCountdownTo(1, newDate);
                } catch (ParseException e1) {
                    Logger.log("The date entered for countdown timer 1 '" + countdownToLabel1.getText() + "' was " +
                            "invalid!");
                    setCountdownTo(1, App.NOTIFIER.getCountdownTimer(1));
                }
            }
        });

        this.dateButton1 = new JCalendarButton();
        this.dateButton1.addPropertyChangeListener(this);

        this.timeButton1 = new JTimeButton();
        this.timeButton1.addPropertyChangeListener(this);

        this.resetButton1 = new JButton("Reset");
        this.resetButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCountdownTo(1, new Date());
            }
        });

        this.addMinuteButton1 = new JButton("Minute");
        this.addMinuteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate1);
                cal.add(Calendar.MINUTE, 1);
                setCountdownTo(1, cal.getTime());
            }
        });

        this.addHourButton1 = new JButton("Hour");
        this.addHourButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate1);
                cal.add(Calendar.HOUR, 1);
                setCountdownTo(1, cal.getTime());
            }
        });

        this.addDayButton1 = new JButton("Day");
        this.addDayButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate1);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                setCountdownTo(1, cal.getTime());
            }
        });

        this.countdownPanel1.add(this.countdownLabel1);
        this.countdownPanel1.add(this.countdownToLabel1);
        this.countdownPanel1.add(this.dateButton1);
        this.countdownPanel1.add(this.timeButton1);
        this.countdownPanel1.add(this.resetButton1);
        this.countdownPanel1.add(this.addMinuteButton1);
        this.countdownPanel1.add(this.addHourButton1);
        this.countdownPanel1.add(this.addDayButton1);
    }

    private void setupCountdownPanel2() {
        this.countdownPanel2 = new JPanel();
        this.countdownPanel2.setLayout(new FlowLayout());

        this.countdownLabel2 = new JLabel("Countdown 2:");

        this.countdownToLabel2 = new JTextField(dateFormat.format(countdownDate2), 16);
        this.countdownToLabel2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    Date newDate = dateFormat.parse(countdownToLabel2.getText());
                    setCountdownTo(2, newDate);
                } catch (ParseException e2) {
                    Logger.log("The date entered for countdown timer 2 '" + countdownToLabel1.getText() + "' was " +
                            "invalid!");
                    setCountdownTo(2, App.NOTIFIER.getCountdownTimer(2));
                }
            }
        });

        this.dateButton2 = new JCalendarButton();
        this.dateButton2.addPropertyChangeListener(this);

        this.timeButton2 = new JTimeButton();
        this.timeButton2.addPropertyChangeListener(this);

        this.resetButton2 = new JButton("Reset");
        this.resetButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCountdownTo(2, new Date());
            }
        });

        this.addMinuteButton2 = new JButton("Minute");
        this.addMinuteButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate2);
                cal.add(Calendar.MINUTE, 1);
                setCountdownTo(2, cal.getTime());
            }
        });

        this.addHourButton2 = new JButton("Hour");
        this.addHourButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate2);
                cal.add(Calendar.HOUR, 1);
                setCountdownTo(2, cal.getTime());
            }
        });

        this.addDayButton2 = new JButton("Day");
        this.addDayButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate2);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                setCountdownTo(2, cal.getTime());
            }
        });

        this.countdownPanel2.add(this.countdownLabel2);
        this.countdownPanel2.add(this.countdownToLabel2);
        this.countdownPanel2.add(this.dateButton2);
        this.countdownPanel2.add(this.timeButton2);
        this.countdownPanel2.add(this.resetButton2);
        this.countdownPanel2.add(this.addMinuteButton2);
        this.countdownPanel2.add(this.addHourButton2);
        this.countdownPanel2.add(this.addDayButton2);
    }

    private void setupCountdownPanel3() {
        this.countdownPanel3 = new JPanel();
        this.countdownPanel3.setLayout(new FlowLayout());

        this.countdownLabel3 = new JLabel("Countdown 3:");

        this.countdownToLabel3 = new JTextField(dateFormat.format(countdownDate3), 16);
        this.countdownToLabel3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    Date newDate = dateFormat.parse(countdownToLabel3.getText());
                    setCountdownTo(3, newDate);
                } catch (ParseException e3) {
                    Logger.log("The date entered for countdown timer 3 '" + countdownToLabel3.getText() + "' was " +
                            "invalid!");
                    setCountdownTo(3, App.NOTIFIER.getCountdownTimer(3));
                }
            }
        });

        this.dateButton3 = new JCalendarButton();
        this.dateButton3.addPropertyChangeListener(this);

        this.timeButton3 = new JTimeButton();
        this.timeButton3.addPropertyChangeListener(this);

        this.resetButton3 = new JButton("Reset");
        this.resetButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCountdownTo(3, new Date());
            }
        });

        this.addMinuteButton3 = new JButton("Minute");
        this.addMinuteButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate3);
                cal.add(Calendar.MINUTE, 1);
                setCountdownTo(3, cal.getTime());
            }
        });

        this.addHourButton3 = new JButton("Hour");
        this.addHourButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate3);
                cal.add(Calendar.HOUR, 1);
                setCountdownTo(3, cal.getTime());
            }
        });

        this.addDayButton3 = new JButton("Day");
        this.addDayButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(countdownDate3);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                setCountdownTo(3, cal.getTime());
            }
        });

        this.countdownPanel3.add(this.countdownLabel3);
        this.countdownPanel3.add(this.countdownToLabel3);
        this.countdownPanel3.add(this.dateButton3);
        this.countdownPanel3.add(this.timeButton3);
        this.countdownPanel3.add(this.resetButton3);
        this.countdownPanel3.add(this.addMinuteButton3);
        this.countdownPanel3.add(this.addHourButton3);
        this.countdownPanel3.add(this.addDayButton3);
    }

    private void setupButtonPanel() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCountdownTimers();
            }
        });
    }

    private void saveCountdownTimers() {
        App.NOTIFIER.setCountdownTimer(1, this.countdownDate1);
        App.NOTIFIER.setCountdownTimer(2, this.countdownDate2);
        App.NOTIFIER.setCountdownTimer(3, this.countdownDate3);
    }

    private void addComponents() {
        this.mainPane.add(this.countdownPanel1);
        this.mainPane.add(this.countdownPanel2);
        this.mainPane.add(this.countdownPanel3);

        this.buttonPane.add(this.saveButton);

        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Date) {
            Calendar curCal = Calendar.getInstance();

            Calendar newCal = Calendar.getInstance();
            newCal.setTime((Date) evt.getNewValue());

            int num = 0;

            if (evt.getSource() == dateButton1) {
                num = 1;
                curCal.setTime(countdownDate1);
                curCal.set(Calendar.DAY_OF_MONTH, newCal.get(Calendar.DAY_OF_MONTH));
                curCal.set(Calendar.MONTH, newCal.get(Calendar.MONTH));
                curCal.set(Calendar.YEAR, newCal.get(Calendar.YEAR));
            } else if (evt.getSource() == timeButton1) {
                num = 1;
                curCal.setTime(countdownDate1);
                curCal.set(Calendar.HOUR_OF_DAY, newCal.get(Calendar.HOUR_OF_DAY));
                curCal.set(Calendar.MINUTE, newCal.get(Calendar.MINUTE));
                curCal.set(Calendar.SECOND, 0);
                curCal.set(Calendar.MILLISECOND, 0);
            } else if (evt.getSource() == dateButton2) {
                num = 2;
                curCal.setTime(countdownDate2);
                curCal.set(Calendar.DAY_OF_MONTH, newCal.get(Calendar.DAY_OF_MONTH));
                curCal.set(Calendar.MONTH, newCal.get(Calendar.MONTH));
                curCal.set(Calendar.YEAR, newCal.get(Calendar.YEAR));
            } else if (evt.getSource() == timeButton2) {
                num = 2;
                curCal.setTime(countdownDate2);
                curCal.set(Calendar.HOUR_OF_DAY, newCal.get(Calendar.HOUR_OF_DAY));
                curCal.set(Calendar.MINUTE, newCal.get(Calendar.MINUTE));
                curCal.set(Calendar.SECOND, 0);
                curCal.set(Calendar.MILLISECOND, 0);
            } else if (evt.getSource() == dateButton3) {
                num = 3;
                curCal.setTime(countdownDate3);
                curCal.set(Calendar.DAY_OF_MONTH, newCal.get(Calendar.DAY_OF_MONTH));
                curCal.set(Calendar.MONTH, newCal.get(Calendar.MONTH));
                curCal.set(Calendar.YEAR, newCal.get(Calendar.YEAR));
            } else if (evt.getSource() == timeButton3) {
                num = 3;
                curCal.setTime(countdownDate3);
                curCal.set(Calendar.HOUR_OF_DAY, newCal.get(Calendar.HOUR_OF_DAY));
                curCal.set(Calendar.MINUTE, newCal.get(Calendar.MINUTE));
                curCal.set(Calendar.SECOND, 0);
                curCal.set(Calendar.MILLISECOND, 0);
            }

            if (num != 0) {
                setCountdownTo(num, curCal.getTime());
            }
        }
    }

    public void setCountdownTo(int num, Date countdownTo) {
        switch (num) {
            case 1:
                this.countdownDate1 = countdownTo;
                this.countdownToLabel1.setText(this.dateFormat.format(this.countdownDate1));
                break;
            case 2:
                this.countdownDate2 = countdownTo;
                this.countdownToLabel2.setText(this.dateFormat.format(this.countdownDate2));
                break;
            case 3:
                this.countdownDate3 = countdownTo;
                this.countdownToLabel3.setText(this.dateFormat.format(this.countdownDate3));
                break;
        }

        checkForOldDates();
    }

    private void checkForOldDates() {
        if (countdownDate1.before(new Date())) {
            this.countdownToLabel1.setBackground(Color.RED);
        } else {
            this.countdownToLabel1.setBackground(Color.WHITE);
        }

        if (countdownDate2.before(new Date())) {
            this.countdownToLabel2.setBackground(Color.RED);
        } else {
            this.countdownToLabel2.setBackground(Color.WHITE);
        }

        if (countdownDate3.before(new Date())) {
            this.countdownToLabel3.setBackground(Color.RED);
        } else {
            this.countdownToLabel3.setBackground(Color.WHITE);
        }
    }
}
