package it.codeSmell.aDoctor.ui;

import it.codeSmell.aDoctor.process.RunAndroidSmellDetection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private JTable smellTable;
    private JButton goBack;
    private String csvFile;

    public ResultsUI(String inputLocationPath) {
        this.initComponents();
        this.initLayout();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - (double) this.getWidth()) / 2.0D);
        int y = (int) ((dimension.getHeight() - (double) this.getHeight()) / 2.0D);
        this.setLocation(x, y);
        try {
            this.csvFile = inputLocationPath;
            this.updateTable("");
        } catch (IOException e) {
            Logger.getLogger(ResultsUI.this.getName()).log(Level.SEVERE, null, e);
        }
    }

    private List<SmellData> filterData(String nameFilter) throws IOException {
        Reader in = new FileReader(this.csvFile);
        CSVParser records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        List<SmellData> smellDataList = new ArrayList();
        for (CSVRecord record : records) {
            String className = record.get("Class");
            if (className.contains(nameFilter)) {
                List<String> values = new ArrayList();
                for (int i = 1; i < record.size(); i++) {
                    values.add(record.get(i));
                }
                smellDataList.add(new SmellData(className, values));
            }
        }
        return smellDataList;
    }

    private void updateTable(String nameFilter) throws IOException {
        List<SmellData> filteredDataList = this.filterData(nameFilter);
        DefaultTableModel model = (DefaultTableModel) this.smellTable.getModel();
        model.setRowCount(0);
        this.smellTable.setAutoCreateRowSorter(true);

        for (SmellData filteredData : filteredDataList) {
            Object[] rowData = new Object[filteredData.getValues().size() + 1];
            rowData[0] = filteredData.getClassName();
            for (int i = 0; i < filteredData.getValues().size(); i++) {
                rowData[i + 1] = filteredData.getValues().get(i);
            }
            model.addRow(rowData);
        }
    }

    private void initComponents() {
        ImageIcon titleImage = new ImageIcon(this.getClass().getResource("/aDoctor.png")),
                filterImage = new ImageIcon(this.getClass().getResource("/filter.png")),
                backImage = new ImageIcon(this.getClass().getResource("/back.png"));

        this.setTitle("PETrA");
        this.setIconImage(titleImage.getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.filterField = new JTextField();
        this.filterField.setToolTipText("Filter results.");
        this.filterField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                ResultsUI.this.filterFieldKeyPressed(evt);
            }
        });
        this.filterResultsButton = new JButton("Filter Results", filterImage);
        this.filterResultsButton.addActionListener(ResultsUI.this::filterResultsButtonActionPerformed);

        this.smellTable = new JTable();
        this.smellTable.setModel(new DefaultTableModel(new Object[0][], RunAndroidSmellDetection.FILE_HEADER) {
            Class[] types = ResultsUI.this.TABLE_TYPES;
            boolean[] canEdit = {false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return this.canEdit[columnIndex];
                return false;
            }
        });
        this.smellTable.setColumnSelectionAllowed(true);
        this.smellTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (this.smellTable.getColumnModel().getColumnCount() > 0) {
            this.smellTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }

        this.goBack = new JButton("Go back", backImage);
        this.goBack.addActionListener(ResultsUI.this::goBackActionPerformed);
    }

    private void initLayout() {
        JScrollPane contentPane = new JScrollPane();
        contentPane.setViewportView(this.smellTable);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().
                addGap(18).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                        addGroup(layout.createSequentialGroup().
                                addComponent(this.filterField, -2, 500, -2).
                                addGap(20).
                                addComponent(this.filterResultsButton, -2, 200, -2)).
                        addGroup(layout.createSequentialGroup().
                                addComponent(contentPane, -2, 580, -2).
                                addGap(20).
                                addComponent(this.goBack, -2, 120, -2))).
                addGap(18));
        layout.setVerticalGroup(layout.createSequentialGroup().
                addContainerGap().
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(this.filterField, -2, -1, -2).
                        addComponent(this.filterResultsButton, -2, 36, -2)).
                addGap(18).
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).
                        addComponent(contentPane, -2, -1, -2).
                        addComponent(this.goBack, -2, 36, -2)).
                addContainerGap());
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
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.filterResultsButtonActionPerformed(null);
        }
    }
}