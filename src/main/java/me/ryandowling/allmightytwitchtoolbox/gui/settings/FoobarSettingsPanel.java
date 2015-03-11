package me.ryandowling.allmightytwitchtoolbox.gui.settings;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.exceptions.SettingsException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoobarSettingsPanel extends JPanel {
    private JPanel mainPane;

    private JPanel foobarLocationPanel;
    private JLabel foobarLocationLabel;
    private JTextField foobarLocationTextField;
    private JFileChooser foobarLocationChooser;
    private JButton foobarLocationChooserButton;

    private JPanel rawNowPlayingFilePanel;
    private JLabel rawNowPlayingFileLabel;
    private JTextField rawNowPlayingFileTextField;
    private JFileChooser rawNowPlayingFileChooser;
    private JButton rawNowPlayingFileChooserButton;

    private JPanel nowPlayingFilePanel;
    private JLabel nowPlayingFileLabel;
    private JTextField nowPlayingFileTextField;
    private JFileChooser nowPlayingFileChooser;
    private JButton nowPlayingFileChooserButton;

    private JPanel nowPlayingFileFilePanel;
    private JLabel nowPlayingFileFileLabel;
    private JTextField nowPlayingFileFileTextField;
    private JFileChooser nowPlayingFileFileChooser;
    private JButton nowPlayingFileFileChooserButton;

    private JPanel autoRunFoobarNowPlayingConverterPanel;
    private JLabel autoRunFoobarNowPlayingConverterLabel;
    private JCheckBox autoRunFoobarNowPlayingConverterCheckbox;

    public FoobarSettingsPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        setupFoobarLocationPanel();

        setupRawNowPlayingFilePanel();
        setupNowPlayingFilePanel();
        setupNowPlayingFileFilePanel();

        setupAutoRunFoobarNowPlayingConverterPanel();
    }

    private void setupFoobarLocationPanel() {
        this.foobarLocationPanel = new JPanel();
        this.foobarLocationPanel.setLayout(new FlowLayout());

        this.foobarLocationLabel = new JLabel("foobar2000 Executable File:");

        this.foobarLocationTextField = new JTextField(App.NOTIFIER.getSettings().getFoobarLocation(), 16);
        this.foobarLocationTextField.setEnabled(false);

        this.foobarLocationChooser = new JFileChooser();
        this.foobarLocationChooser.setMultiSelectionEnabled(false);

        this.foobarLocationChooserButton = new JButton("Browse");
        this.foobarLocationChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foobarLocationChooser.showOpenDialog(FoobarSettingsPanel.this);
                if (foobarLocationChooser.getSelectedFile() != null) {
                    foobarLocationTextField.setText(foobarLocationChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.foobarLocationPanel.add(this.foobarLocationLabel);
        this.foobarLocationPanel.add(this.foobarLocationTextField);
        this.foobarLocationPanel.add(this.foobarLocationChooserButton);
    }

    private void setupRawNowPlayingFilePanel() {
        this.rawNowPlayingFilePanel = new JPanel();
        this.rawNowPlayingFilePanel.setLayout(new FlowLayout());

        this.rawNowPlayingFileLabel = new JLabel("Now Playing Raw File:");

        this.rawNowPlayingFileTextField = new JTextField(App.NOTIFIER.getSettings().getRawNowPlayingFilePath(), 16);
        this.rawNowPlayingFileTextField.setEnabled(false);

        this.rawNowPlayingFileChooser = new JFileChooser();
        this.rawNowPlayingFileChooser.setMultiSelectionEnabled(false);

        this.rawNowPlayingFileChooserButton = new JButton("Browse");
        this.rawNowPlayingFileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rawNowPlayingFileChooser.showOpenDialog(FoobarSettingsPanel.this);
                if (rawNowPlayingFileChooser.getSelectedFile() != null) {
                    rawNowPlayingFileTextField.setText(rawNowPlayingFileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.rawNowPlayingFilePanel.add(this.rawNowPlayingFileLabel);
        this.rawNowPlayingFilePanel.add(this.rawNowPlayingFileTextField);
        this.rawNowPlayingFilePanel.add(this.rawNowPlayingFileChooserButton);
    }

    private void setupNowPlayingFilePanel() {
        this.nowPlayingFilePanel = new JPanel();
        this.nowPlayingFilePanel.setLayout(new FlowLayout());

        this.nowPlayingFileLabel = new JLabel("Now Playing Output File:");

        this.nowPlayingFileTextField = new JTextField(App.NOTIFIER.getSettings().getNowPlayingFilePath(), 16);
        this.nowPlayingFileTextField.setEnabled(false);

        this.nowPlayingFileChooser = new JFileChooser();
        this.nowPlayingFileChooser.setMultiSelectionEnabled(false);

        this.nowPlayingFileChooserButton = new JButton("Browse");
        this.nowPlayingFileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowPlayingFileChooser.showOpenDialog(FoobarSettingsPanel.this);
                if (nowPlayingFileChooser.getSelectedFile() != null) {
                    nowPlayingFileTextField.setText(nowPlayingFileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.nowPlayingFilePanel.add(this.nowPlayingFileLabel);
        this.nowPlayingFilePanel.add(this.nowPlayingFileTextField);
        this.nowPlayingFilePanel.add(this.nowPlayingFileChooserButton);
    }

    private void setupNowPlayingFileFilePanel() {
        this.nowPlayingFileFilePanel = new JPanel();
        this.nowPlayingFileFilePanel.setLayout(new FlowLayout());

        this.nowPlayingFileFileLabel = new JLabel("Now Playing File Output File:");

        this.nowPlayingFileFileTextField = new JTextField(App.NOTIFIER.getSettings().getNowPlayingFileFilePath(), 16);
        this.nowPlayingFileFileTextField.setEnabled(false);

        this.nowPlayingFileFileChooser = new JFileChooser();
        this.nowPlayingFileFileChooser.setMultiSelectionEnabled(false);

        this.nowPlayingFileFileChooserButton = new JButton("Browse");
        this.nowPlayingFileFileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowPlayingFileFileChooser.showOpenDialog(FoobarSettingsPanel.this);
                if (nowPlayingFileFileChooser.getSelectedFile() != null) {
                    nowPlayingFileFileTextField.setText(nowPlayingFileFileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.nowPlayingFileFilePanel.add(this.nowPlayingFileFileLabel);
        this.nowPlayingFileFilePanel.add(this.nowPlayingFileFileTextField);
        this.nowPlayingFileFilePanel.add(this.nowPlayingFileFileChooserButton);
    }

    private void setupAutoRunFoobarNowPlayingConverterPanel() {
        this.autoRunFoobarNowPlayingConverterPanel = new JPanel();
        this.autoRunFoobarNowPlayingConverterPanel.setLayout(new FlowLayout());

        this.autoRunFoobarNowPlayingConverterLabel = new JLabel("Auto Run Foobar Now Playing Converter?");

        this.autoRunFoobarNowPlayingConverterCheckbox = new JCheckBox();
        this.autoRunFoobarNowPlayingConverterCheckbox.setSelected(App.NOTIFIER.getSettings().autoRunFoobarNowPlayingConverter());

        this.autoRunFoobarNowPlayingConverterPanel.add(this.autoRunFoobarNowPlayingConverterLabel);
        this.autoRunFoobarNowPlayingConverterPanel.add(this.autoRunFoobarNowPlayingConverterCheckbox);
    }

    private void addComponents() {
        this.mainPane.add(this.foobarLocationPanel);

        this.mainPane.add(this.rawNowPlayingFilePanel);
        this.mainPane.add(this.nowPlayingFilePanel);
        this.mainPane.add(this.nowPlayingFileFilePanel);

        this.mainPane.add(this.autoRunFoobarNowPlayingConverterPanel);

        add(this.mainPane, BorderLayout.CENTER);
    }

    public void saveSettings() throws SettingsException {
        if (this.foobarLocationChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setFoobarLocation(this.foobarLocationChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        if (this.rawNowPlayingFileChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setRawNowPlayingFile(this.rawNowPlayingFileChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        if (this.nowPlayingFileChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNowPlayingFile(this.nowPlayingFileChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        if (this.nowPlayingFileFileChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNowPlayingFileFile(this.nowPlayingFileFileChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        App.NOTIFIER.getSettings().setAutoRunFoobarNowPlayingConverter(this.autoRunFoobarNowPlayingConverterCheckbox.isSelected());
    }
}
