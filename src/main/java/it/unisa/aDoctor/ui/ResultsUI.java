package it.unisa.aDoctor.ui;

import it.unisa.aDoctor.process.RunAndroidSmellDetection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:07
 * Description:
 */
public class ResultsUI extends JFrame {
    private Class[] TABLE_TYPES = {String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class};
    private JTextField filterField;
    private JButton filterResultsButton;
    private JButton goBack;
    private JTable smellTable;
    private JScrollPane tab1;
    private String csvFile;

    public ResultsUI(String inputLocationPath) {
        this.initComponents();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - (double) this.getWidth()) / 2.0D);
        int y = (int) ((dimension.getHeight() - (double) this.getHeight()) / 2.0D);
        this.setLocation(x, y);
        try {
            this.csvFile = inputLocationPath;
            this.updateTable("");
        } catch (IOException var6) {
            Logger.getLogger(ResultsUI.class.getName()).log(Level.SEVERE, (String) null, var6);
        }
    }

    private java.util.List<SmellData> filterData(String nameFilter) throws IOException {
        Reader in = new FileReader(this.csvFile);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        java.util.List<SmellData> smellDataList = new ArrayList();
        Iterator var5 = records.iterator();

        while (true) {
            String className;
            ArrayList values;
            do {
                if (!var5.hasNext()) {
                    return smellDataList;
                }

                CSVRecord record = (CSVRecord) var5.next();
                className = record.get("Class");
                values = new ArrayList();

                for (int i = 1; i < record.size(); ++i) {
                    values.add(record.get(i));
                }
            } while (!className.contains(nameFilter) && !nameFilter.equals(""));

            SmellData smellData = new SmellData(className, values);
            smellDataList.add(smellData);
        }
    }

    private void updateTable(String nameFilter) throws IOException {
        List<SmellData> filteredDataList = this.filterData(nameFilter);
        DefaultTableModel model = (DefaultTableModel) this.smellTable.getModel();
        model.setRowCount(0);
        this.smellTable.setAutoCreateRowSorter(true);
        Iterator var4 = filteredDataList.iterator();

        while (var4.hasNext()) {
            SmellData filteredData = (SmellData) var4.next();
            Object[] rowData = new Object[filteredData.getValues().size() + 1];
            rowData[0] = filteredData.getClassName();

            for (int i = 0; i < filteredData.getValues().size(); ++i) {
                rowData[i + 1] = filteredData.getValues().get(i);
            }

            model.addRow(rowData);
        }

    }

    private void initComponents() {
        this.filterResultsButton = new JButton();
        this.filterField = new JTextField();
        this.goBack = new JButton();
        this.tab1 = new JScrollPane();
        this.smellTable = new JTable();
        this.setDefaultCloseOperation(3);
        this.setTitle("PETrA");
        this.setIconImage((new ImageIcon(this.getClass().getResource("/aDoctor.png"))).getImage());
        this.filterResultsButton.setIcon(new ImageIcon(this.getClass().getResource("/filter.png")));
        this.filterResultsButton.setText("Filter Results");
        this.filterResultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ResultsUI.this.filterResultsButtonActionPerformed(evt);
            }
        });
        this.filterField.setToolTipText("Filter results.");
        this.filterField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                ResultsUI.this.filterFieldKeyPressed(evt);
            }
        });
        this.goBack.setIcon(new ImageIcon(this.getClass().getResource("/back.png")));
        this.goBack.setText("Go back");
        this.goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ResultsUI.this.goBackActionPerformed(evt);
            }
        });
        this.smellTable.setModel(new DefaultTableModel(new Object[0][], RunAndroidSmellDetection.FILE_HEADER) {
            Class[] types;
            boolean[] canEdit;

            {
                this.types = ResultsUI.this.TABLE_TYPES;
                this.canEdit = new boolean[]{false, false, false};
            }

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.smellTable.setColumnSelectionAllowed(true);
        this.tab1.setViewportView(this.smellTable);
        this.smellTable.getColumnModel().getSelectionModel().setSelectionMode(0);
        if (this.smellTable.getColumnModel().getColumnCount() > 0) {
            this.smellTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup().addComponent(this.tab1, -2, 719, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, 32767).addComponent(this.goBack, -2, 100, -2)).addGroup(layout.createSequentialGroup().addComponent(this.filterField).addGap(18, 18, 18).addComponent(this.filterResultsButton, -2, 197, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.filterField, -2, -1, -2).addComponent(this.filterResultsButton, -2, 36, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.tab1, -2, -1, -2).addComponent(this.goBack)).addContainerGap(-1, 32767)));
        this.pack();
    }

    private void filterResultsButtonActionPerformed(ActionEvent evt) {
        try {
            this.updateTable(this.filterField.getText());
        } catch (IOException e) {
            Logger.getLogger(ResultsUI.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void goBackActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        (new MainUI()).setVisible(true);
    }

    private void filterFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            try {
                this.updateTable(this.filterField.getText());
            } catch (IOException var3) {
                Logger.getLogger(ResultsUI.class.getName()).log(Level.SEVERE, (String) null, var3);
            }
        }

    }
}