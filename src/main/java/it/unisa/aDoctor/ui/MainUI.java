package it.unisa.aDoctor.ui;

import it.unisa.aDoctor.process.RunAndroidSmellDetection;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JCheckBox DRCheck, DTWCCheck, DWCheck, IDFPCheck, IDSCheck,
            IGSCheck, ISQLQCheck, LICCheck, LTCheck, MIMCheck,
            NLMRCheck, PDCheck, RAMCheck, SLCheck, UCCheck;
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
        final ImageIcon titleIcon = new ImageIcon(this.getClass().getResource("/aDoctor.png")),
                inputIcon = new ImageIcon(this.getClass().getResource("/folder.png")),
                processIcon = new ImageIcon(this.getClass().getResource("/play-button.png")),
                resultIcon = new ImageIcon(this.getClass().getResource("/results.png"));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("aDoctor");
        this.setIconImage(titleIcon.getImage());
        this.setResizable(false);

        this.inputLabel = new JLabel("Input Folder");
        this.inputFolderField = new JTextField();
        this.inputFolderField.setEditable(false);
        this.inputFolderField.setToolTipText("Path of the Android SDK Platform Tools folder.");
        this.inputFolderButton = new JButton("Open", inputIcon);
        this.inputFolderButton.addActionListener(MainUI.this::inputFolderButtonActionPerformed);

        this.outputLabel = new JLabel("Output File");
        this.outputFileField = new JTextField();
        this.outputFileField.setEditable(false);
        this.outputFileField.setToolTipText("Device power profile (see https://source.android.com/devices/tech/power/).");
        this.outputFolderButton = new JButton("Open", inputIcon);
        this.outputFolderButton.addActionListener(MainUI.this::outputFolderButtonActionPerformed);

        this.codeSmells = new JCheckBox[15];
        this.codeSmells[0] = new JCheckBox("DTWC", true);
        this.codeSmells[1] = new JCheckBox("DR", true);
        this.codeSmells[2] = new JCheckBox("DW", true);
        this.codeSmells[3] = new JCheckBox("IDFP", true);
        this.codeSmells[4] = new JCheckBox("IDS", true);
        this.codeSmells[5] = new JCheckBox("ISQLQ", true);
        this.codeSmells[6] = new JCheckBox("IGS", true);
        this.codeSmells[7] = new JCheckBox("LIC", true);
        this.codeSmells[8] = new JCheckBox("LT", true);
        this.codeSmells[9] = new JCheckBox("MIM", true);
        this.codeSmells[10] = new JCheckBox("NLMR", true);
        this.codeSmells[11] = new JCheckBox("PD", true);
        this.codeSmells[12] = new JCheckBox("RAM", true);
        this.codeSmells[13] = new JCheckBox("SL", true);
        this.codeSmells[14] = new JCheckBox("UC", true);

        this.codeSmells[0].setToolTipText("Data Transmission Without Compression");
        this.codeSmells[1].setToolTipText("Debuggable Release");
        this.codeSmells[2].setToolTipText("Durable Wakelock");
        this.codeSmells[3].setToolTipText("Inefficient Data Format and Parser");
        this.codeSmells[4].setToolTipText("Inefficient Data Structure");
        this.codeSmells[5].setToolTipText("Inefficient SQL Query");
        this.codeSmells[6].setToolTipText("Internal Getter and Setter");
        this.codeSmells[7].setToolTipText("Leaking Inner Class");
        this.codeSmells[8].setToolTipText("Leaking Thread");
        this.codeSmells[9].setToolTipText("Member Ignoring Method");
        this.codeSmells[10].setToolTipText("No Low Memory Resolver");
        this.codeSmells[11].setToolTipText("Public Data");
        this.codeSmells[12].setToolTipText("Rigid Alarm Manager");
        this.codeSmells[13].setToolTipText("Slow Loop");
        this.codeSmells[14].setToolTipText("Unclosed Closable");

        this.DTWCCheck = new JCheckBox("DTWC", true);
        this.DRCheck = new JCheckBox("DR", true);
        this.DWCheck = new JCheckBox("DW", true);
        this.IDFPCheck = new JCheckBox("IDFP", true);
        this.IDSCheck = new JCheckBox("IDS", true);
        this.ISQLQCheck = new JCheckBox("ISQLQ", true);
        this.IGSCheck = new JCheckBox("IGS", true);
        this.LICCheck = new JCheckBox("LIC", true);
        this.LTCheck = new JCheckBox("LT", true);
        this.MIMCheck = new JCheckBox("MIM", true);
        this.NLMRCheck = new JCheckBox("NLMR", true);
        this.PDCheck = new JCheckBox("PD", true);
        this.RAMCheck = new JCheckBox("RAM", true);
        this.SLCheck = new JCheckBox("SL", true);
        this.UCCheck = new JCheckBox("UC", true);

        this.DTWCCheck.setToolTipText("Data Transmission Without Compression");
        this.DRCheck.setToolTipText("Debuggable Release");
        this.DWCheck.setToolTipText("Durable Wakelock");
        this.IDFPCheck.setToolTipText("Inefficient Data Format and Parser");
        this.IDSCheck.setToolTipText("Inefficient Data Structure");
        this.ISQLQCheck.setToolTipText("Inefficient SQL Query");
        this.IGSCheck.setToolTipText("Internal Getter and Setter");
        this.LICCheck.setToolTipText("Leaking Inner Class");
        this.LTCheck.setToolTipText("Leaking Thread");
        this.MIMCheck.setToolTipText("Member Ignoring Method");
        this.NLMRCheck.setToolTipText("No Low Memory Resolver");
        this.PDCheck.setToolTipText("Public Data");
        this.RAMCheck.setToolTipText("Rigid Alarm Manager");
        this.SLCheck.setToolTipText("Slow Loop");
        this.UCCheck.setToolTipText("Unclosed Closable");

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

//        layout.setHorizontalGroup(layout.createSequentialGroup().
//                addGroup(true, layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                        addGroup(layout.createSequentialGroup().
//                                addContainerGap().
//                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                        addComponent(jScrollPane1, GroupLayout.Alignment.LEADING).
//                                        addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addComponent(this.inputLabel).
//                                                        addComponent(this.outputLabel)).
//                                                addGap(18).
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addComponent(this.inputFolderField).
//                                                        addComponent(this.outputFileField)).
//                                                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addComponent(this.inputFolderButton, -2, 90, -2).
//                                                        addComponent(this.outputFolderButton, -2, 90, -2))).
//                                        addGroup(layout.createSequentialGroup().
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addGroup(layout.createSequentialGroup().
//                                                                addComponent(this.DTWCCheck, -2, 99, -2).
//                                                                addGap(18).
//                                                                addComponent(this.DRCheck, -2, 99, -2).
//                                                                addGap(18).
//                                                                addComponent(this.DWCheck, -2, 99, -2)).
//                                                        addGroup(layout.createSequentialGroup().
//                                                                addComponent(this.ISQLQCheck, -2, 99, -2).
//                                                                addGap(18).
//                                                                addComponent(this.IGSCheck, -2, 99, -2).
//                                                                addGap(18).
//                                                                addComponent(this.LICCheck, -2, 99, -2))).
//                                                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE).
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addComponent(this.IDFPCheck, GroupLayout.Alignment.LEADING, -2, 99, -2).
//                                                        addComponent(this.LTCheck, GroupLayout.Alignment.LEADING, -2, 99, -2)).
//                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
//                                                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
//                                                        addComponent(this.IDSCheck, GroupLayout.Alignment.LEADING, -2, 99, -2).
//                                                        addComponent(this.MIMCheck, GroupLayout.Alignment.LEADING, -2, 99, -2))).
//                                        addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().
//                                                addComponent(this.NLMRCheck, -2, 99, -2).
//                                                addGap(18).
//                                                addComponent(this.PDCheck, -2, 99, -2).
//                                                addGap(18).
//                                                addComponent(this.RAMCheck, -2, 99, -2).
//                                                addGap(27).
//                                                addComponent(this.SLCheck, -2, 99, -2).
//                                                addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
//                                                addComponent(this.UCCheck, -1, -1, Short.MAX_VALUE))).
//                                addContainerGap()).
//                        addGroup(layout.createSequentialGroup().
//                                addGap(107, 107, 107).
//                                addComponent(this.startProcessButton, -2, 187, -2).
//                                addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
//                                addComponent(this.viewResults, -2, 187, -2).
//                                addContainerGap(-1, Short.MAX_VALUE))));
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
        if (this.DTWCCheck.isSelected()) {
            smellTypesNeeded[0] = 1;
            ++numOfSmells;
        }
        if (this.DRCheck.isSelected()) {
            smellTypesNeeded[1] = 1;
            ++numOfSmells;
        }
        if (this.DWCheck.isSelected()) {
            smellTypesNeeded[2] = 1;
            ++numOfSmells;
        }
        if (this.IDFPCheck.isSelected()) {
            smellTypesNeeded[3] = 1;
            ++numOfSmells;
        }
        if (this.IDSCheck.isSelected()) {
            smellTypesNeeded[4] = 1;
            ++numOfSmells;
        }
        if (this.ISQLQCheck.isSelected()) {
            smellTypesNeeded[5] = 1;
            ++numOfSmells;
        }
        if (this.IGSCheck.isSelected()) {
            smellTypesNeeded[6] = 1;
            ++numOfSmells;
        }

        if (this.LICCheck.isSelected()) {
            smellTypesNeeded[7] = 1;
            ++numOfSmells;
        }

        if (this.LTCheck.isSelected()) {
            smellTypesNeeded[8] = 1;
            ++numOfSmells;
        }

        if (this.MIMCheck.isSelected()) {
            smellTypesNeeded[9] = 1;
            ++numOfSmells;
        }

        if (this.NLMRCheck.isSelected()) {
            smellTypesNeeded[10] = 1;
            ++numOfSmells;
        }

        if (this.PDCheck.isSelected()) {
            smellTypesNeeded[11] = 1;
            ++numOfSmells;
        }

        if (this.RAMCheck.isSelected()) {
            smellTypesNeeded[12] = 1;
            ++numOfSmells;
        }

        if (this.SLCheck.isSelected()) {
            smellTypesNeeded[13] = 1;
            ++numOfSmells;
        }

        if (this.UCCheck.isSelected()) {
            smellTypesNeeded[14] = 1;
            ++numOfSmells;
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
            } catch (CoreException | IOException var10) {
                System.out.println("Error!");
            }

        }
    }

    private void inputFolderButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        chooser.setAcceptAllFileFilterUsed(false);
        int res = chooser.showOpenDialog(null);
        if (res == 0) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            this.inputFolderField.setText(filename);
        }

    }

    private void outputFolderButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(null);
        if (res == 0) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            this.outputFileField.setText(filename);
        }

    }

    private void viewResultsActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        (new ResultsUI(this.outputFileField.getText())).setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.LookAndFeelInfo[] var1 = UIManager.getInstalledLookAndFeels();
            int var2 = var1.length;

            label39:
            for (int var3 = 0; var3 < var2; ++var3) {
                UIManager.LookAndFeelInfo info = var1[var3];
                if (info.getName() != null) {
                    String var5 = info.getName();
                    byte var6 = -1;
                    switch (var5.hashCode()) {
                        case -1961578674:
                            if (var5.equals("Nimbus")) {
                                var6 = 2;
                            }
                            break;
                        case -1280820637:
                            if (var5.equals("Windows")) {
                                var6 = 1;
                            }
                            break;
                        case 2198253:
                            if (var5.equals("GTK+")) {
                                var6 = 0;
                            }
                    }

                    switch (var6) {
                        case 0:
                            UIManager.setLookAndFeel(info.getClassName());
                            break label39;
                        case 1:
                            UIManager.setLookAndFeel(info.getClassName());
                            break label39;
                        case 2:
                            UIManager.setLookAndFeel(info.getClassName());
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var7) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, var7);
        }

        EventQueue.invokeLater(() -> {
            (new MainUI()).setVisible(true);
        });
    }
}