package famous.paintmaker.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;

public class RRDialog extends JDialog {

    public JSpinner arcHeight;
    public JSpinner arcWidth;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;

    public RRDialog(ToolboxFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void formWindowClosing(WindowEvent evt) {
        ((ToolboxFrame) getParent()).roundRectDialogClosed();
    }

    private void initComponents() {
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        arcWidth = new JSpinner();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        arcHeight = new JSpinner();

        setTitle("Rounded Rectangle");
        addWindowListener(new WindowListener() {
            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                formWindowClosing(e);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }
        });

        jLabel1.setText("Rounded Rectangle options:");
        jLabel2.setText("Arc Width:");
        arcWidth.setModel(new SpinnerNumberModel(16, 0, null, 1));
        jLabel3.setText("Arc Height:");
        jLabel4.setText("<html>These values change how large the rounded<br>\nrectangle's edges will be, in pixels.");
        arcHeight.setModel(new SpinnerNumberModel(16, 0, null, 1));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel1).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel3).addComponent(jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 73, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(arcHeight).addComponent(arcWidth, -1, 76, 32767))).addComponent(jLabel4, GroupLayout.Alignment.TRAILING)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(arcWidth, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(arcHeight, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel4).addContainerGap(-1, 32767)));
        pack();
    }
}
