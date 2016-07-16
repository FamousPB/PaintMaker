package famous.paintmaker.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;

public class GridDialog extends JDialog {

    private ColorChooserDialog colorChooser;
    public JSpinner cellHeight;
    public JSpinner cellWidth;
    public JPanel gridColorPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;

    public GridDialog(Frame parent, boolean modal, ColorChooserDialog colorChooser) {
        super(parent, modal);
        this.colorChooser = colorChooser;
        initComponents();
    }

    private void initComponents() {
        cellWidth = new JSpinner();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        cellHeight = new JSpinner();
        jLabel4 = new JLabel();
        gridColorPanel = new JPanel();
        setTitle("Grid Options");
        cellWidth.setModel(new SpinnerNumberModel(25, 1, null, 1));
        jLabel1.setText("Grid Options:");
        jLabel2.setText("Cell Width:");
        jLabel3.setText("Cell Height:");
        cellHeight.setModel(new SpinnerNumberModel(25, 1, null, 1));
        jLabel4.setText("Color:");
        gridColorPanel.setBackground(new Color(255, 0, 0, 150));
        gridColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        gridColorPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gridColorPanelMouseReleased(e);
            }
        });

        GroupLayout gridColorPanelLayout = new GroupLayout(gridColorPanel);
        gridColorPanel.setLayout(gridColorPanelLayout);
        gridColorPanelLayout.setHorizontalGroup(gridColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 25, 32767));
        gridColorPanelLayout.setVerticalGroup(gridColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 25, 32767));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel1).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 91, 32767).addComponent(cellWidth, -2, 80, -2)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(gridColorPanel, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 88, 32767).addComponent(cellHeight, -2, 80, -2))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(cellWidth, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(cellHeight, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel4).addComponent(gridColorPanel, -2, -1, -2)).addContainerGap(-1, 32767)));
        pack();
    }

    private void gridColorPanelMouseReleased(MouseEvent evt) {
        colorChooser.setTarget(gridColorPanel);
        colorChooser.setVisible(true);
    }
}
