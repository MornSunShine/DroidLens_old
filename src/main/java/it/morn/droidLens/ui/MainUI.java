package it.morn.droidLens.ui;

import it.morn.droidLens.process.RunAndroidSmellDetection;
import it.morn.droidLens.process.Utilities;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:07
 * Description:
 */
public class MainUI extends JFrame {
    /**
     * Fifteen Android Code Smell types checkbox,
     * used to choose the code smell detected
     */
    private JCheckBox[] codeSmells;
    /**
     * Locate target android app package and the result output file
     */
    private JLabel inputLabel, outputLabel;
    private JTextField inputFolderField, outputFileField;
    private JButton inputFolderButton, outputFolderButton;
    /**
     * Execute the detection,show execution status
     */
    private JButton startProcessButton, viewResults;
    private JTextArea statusLabel;

    public MainUI() {
        this.initComponents();
        this.initLayout();
        PrintStream out = new PrintStream(new TextAreaOutputStream(this.statusLabel));
        System.setOut(out);
        // Set the Window Centered
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - (double) this.getWidth()) / 2.0D);
        int y = (int) ((dimension.getHeight() - (double) this.getHeight()) / 2.0D);
        this.setLocation(x, y);
    }

    private void initComponents() {
        final ImageIcon titleIcon = new ImageIcon(this.getClass().getResource("/DroidLens.png")),
                inputIcon = new ImageIcon(this.getClass().getResource("/folder.png")),
                processIcon = new ImageIcon(this.getClass().getResource("/play-button.png")),
                resultIcon = new ImageIcon(this.getClass().getResource("/results.png"));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("DroidLens");
        this.setIconImage(titleIcon.getImage());
        this.setResizable(false);

        this.inputLabel = new JLabel("Input Folder");
        this.inputFolderField = new JTextField();
        this.inputFolderField.setEditable(false);
        //测试代码
        this.inputFolderField.setText("C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal");
        this.inputFolderField.setToolTipText("Path of the Android SDK Platform Tools folder.");
        this.inputFolderButton = new JButton("Open", inputIcon);
        this.inputFolderButton.setActionCommand("input");
        this.inputFolderButton.addActionListener(MainUI.this::folderButtonActionPerformed);

        this.outputLabel = new JLabel("Output File");
        this.outputFileField = new JTextField();
        this.outputFileField.setEditable(false);
        //测试代码
        this.outputFileField.setText("C:\\Users\\MaoMorn\\Desktop\\result.csv");
        this.outputFileField.setToolTipText("Device power profile (see https://source.android.com/devices/tech/power/).");
        this.outputFolderButton = new JButton("Open", inputIcon);
        this.outputFolderButton.addActionListener(MainUI.this::folderButtonActionPerformed);

        this.codeSmells = new JCheckBox[15];
        for (int i = 0; i < Utilities.CODES_SMELL.length; i++) {
            this.codeSmells[i] = new JCheckBox(Utilities.CODES_SMELL[i], true);
            this.codeSmells[i].setToolTipText(Utilities.DESCRIPTION[i]);
        }
        this.statusLabel = new JTextArea();
        this.statusLabel.setEditable(false);
        this.statusLabel.setColumns(20);
        this.statusLabel.setDisabledTextColor(new Color(1, 1, 1));


        this.startProcessButton = new JButton("Start Process", processIcon);
        this.startProcessButton.addActionListener(MainUI.this::startProcessButtonActionPerformed);
        this.startProcessButton.getAccessibleContext().setAccessibleName("");

        this.viewResults = new JButton("Results", resultIcon);
        this.viewResults.setEnabled(false);
        this.viewResults.setFocusPainted(false);
        this.viewResults.addActionListener(MainUI.this::viewResultsActionPerformed);
    }

    private void initLayout() {
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setToolTipText("");
        jScrollPane1.setEnabled(false);
        jScrollPane1.setViewportView(this.statusLabel);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup().
                addGroup(true, layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                        addGroup(layout.createSequentialGroup().
                                addContainerGap().
                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                        addComponent(jScrollPane1, GroupLayout.Alignment.LEADING).
                                        addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.inputLabel).
                                                        addComponent(this.outputLabel)).
                                                addGap(18).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.inputFolderField).
                                                        addComponent(this.outputFileField)).
                                                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.inputFolderButton, -2, 90, -2).
                                                        addComponent(this.outputFolderButton, -2, 90, -2))).
                                        addGroup(layout.createSequentialGroup().
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.codeSmells[0], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[5], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[10], -2, 99, -2)).
                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.codeSmells[1], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[6], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[11], -2, 99, -2)).
                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.codeSmells[2], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[7], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[12], -2, 99, -2)).
                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.codeSmells[3], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[8], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[13], -2, 99, -2)).
                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE).
                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                                        addComponent(this.codeSmells[4], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[9], -2, 99, -2).
                                                        addGap(18).
                                                        addComponent(this.codeSmells[14], -2, 99, -2)))).
                                addContainerGap()).
                        addGroup(layout.createSequentialGroup().
                                addGap(107, 107, 107).
                                addComponent(this.startProcessButton, -2, 187, -2).
                                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
                                addComponent(this.viewResults, -2, 187, -2).
                                addContainerGap(-1, Short.MAX_VALUE))));
        layout.setVerticalGroup(layout.createSequentialGroup().
                addContainerGap(-1, Short.MAX_VALUE).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.inputLabel).
                        addComponent(this.inputFolderField, -2, -1, -2).
                        addComponent(this.inputFolderButton)).
                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.outputLabel).
                        addComponent(this.outputFileField, -2, -1, -2).
                        addComponent(this.outputFolderButton)).
                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.codeSmells[0]).
                        addComponent(this.codeSmells[1]).
                        addComponent(this.codeSmells[2]).
                        addComponent(this.codeSmells[3]).
                        addComponent(this.codeSmells[4])).
                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.codeSmells[5]).
                        addComponent(this.codeSmells[6]).
                        addComponent(this.codeSmells[7]).
                        addComponent(this.codeSmells[8]).
                        addComponent(this.codeSmells[9])).
                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.codeSmells[10]).
                        addComponent(this.codeSmells[11]).
                        addComponent(this.codeSmells[12]).
                        addComponent(this.codeSmells[13]).
                        addComponent(this.codeSmells[14])).
                addGap(18, 18, 18).
                addComponent(jScrollPane1, -2, 133, -2).
                addGap(18, 18, 18).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.startProcessButton, -2, 38, -2).
                        addComponent(this.viewResults, -2, 38, -2)).
                addContainerGap());
        this.pack();
    }

    private void startProcessButtonActionPerformed(ActionEvent evt) {
        this.statusLabel.setText(null);
        this.viewResults.setEnabled(false);
        String inputPath = this.inputFolderField.getText();
        String outputPath = this.outputFileField.getText();
        Integer[] smellTypesNeeded = {0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        int numOfSmells = 0;
        for (int i = 0; i < codeSmells.length; i++) {
            if (codeSmells[i].isSelected()) {
                smellTypesNeeded[i] = 1;
                numOfSmells++;
            }
        }

        String smellTypesString = StringUtils.join(smellTypesNeeded);
        boolean valid = true;
        if (inputPath.isEmpty()) {
            System.out.println("Input folder not selected.");
            valid = false;
        }

        if (outputPath.isEmpty()) {
            System.out.println("Output file not selected.");
            valid = false;
        }

        if (numOfSmells == 0) {
            System.out.println("None of the smells has been selected.");
            valid = false;
        }

        if (valid) {
            String[] args = new String[]{inputPath, outputPath, smellTypesString};
            try {
                RunAndroidSmellDetection.main(args);
                this.viewResults.setEnabled(true);
            } catch (CoreException | IOException e) {
                System.out.println("Error!");
            }
        }
    }

    private void folderButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        if (evt.getActionCommand().equals("input")) {
            chooser.setFileSelectionMode(1);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        int res = chooser.showOpenDialog(null);
        if (res == 0) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            if (evt.getActionCommand().equals("input")) {
                this.inputFolderField.setText(filename);
            } else {
                this.outputFileField.setText(filename);
            }
        }
    }

    private void viewResultsActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        (new ResultsUI(this.outputFileField.getText())).setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(lookAndFeels[0].getClassName());
            for (UIManager.LookAndFeelInfo info : lookAndFeels) {
                if (info.getName().equals("Windows")) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, e);
        }

        EventQueue.invokeLater(() -> (new MainUI()).setVisible(true));
    }
}