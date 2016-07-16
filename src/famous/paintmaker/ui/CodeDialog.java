package famous.paintmaker.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

public class CodeDialog extends JDialog {

    private JScrollPane codeBox;
    private JTextArea codeText;
    private JButton copyCodeButton;
    private JButton copyImportsButton;
    private JTextArea importBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;

    public CodeDialog(Frame parent, boolean modal, String code, String imports) {
        super(parent, modal);
        initComponents();
        codeText.setText(code);
        importBox.setText(imports);
    }

    private void copyCodeButtonActionPerformed(ActionEvent evt) {
        codeText.selectAll();
        codeText.copy();
    }

    private void copyImportsButtonActionPerformed(ActionEvent evt) {
        importBox.selectAll();
        importBox.copy();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        importBox = new JTextArea();
        codeBox = new JScrollPane();
        codeText = new JTextArea();
        copyImportsButton = new JButton();
        copyCodeButton = new JButton();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();

        setDefaultCloseOperation(2);

        importBox.setColumns(20);
        importBox.setRows(2);
        jScrollPane1.setViewportView(importBox);

        codeText.setColumns(20);
        codeText.setRows(5);
        codeBox.setViewportView(codeText);

        copyImportsButton.setText("Copy Imports to Clipboard");
        copyImportsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                copyImportsButtonActionPerformed(evt);
            }
        });

        copyCodeButton.setText("Copy Code to Clipboard");
        copyCodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                copyCodeButtonActionPerformed(evt);
            }
        });
        jLabel1.setText("Needed imports:");
        jLabel2.setText("repaint method (and variable declarations):");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel1).addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 380, 32767).addComponent(jLabel2).addComponent(codeBox, -1, 380, 32767).addComponent(copyImportsButton).addComponent(copyCodeButton)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(copyImportsButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(codeBox, -2, 173, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(copyCodeButton).addContainerGap()));
        pack();
    }
}
