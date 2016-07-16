package famous.paintmaker.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooserDialog extends JDialog implements ChangeListener {

    private JLabel alphaLabel;
    private JSlider alphaSlider;
    private JButton cancelButton;
    private JColorChooser colorChooser;
    private JLabel jLabel1;
    private ToolboxFrame parent;
    private JButton setButton;
    private JPanel target;

    public ColorChooserDialog(ToolboxFrame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
    }

    private void alphaSliderStateChanged(ChangeEvent evt) {
        Color c = colorChooser.getColor();
        colorChooser.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), alphaSlider.getValue()));
        alphaLabel.setText("" + alphaSlider.getValue());
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        setVisible(false);
    }

    private void initComponents() {
        colorChooser = new JColorChooser();
        jLabel1 = new JLabel();
        alphaSlider = new JSlider();
        alphaLabel = new JLabel();
        setButton = new JButton();
        cancelButton = new JButton();
        jLabel1.setText("Alpha:");
        alphaSlider.setMaximum(255);
        alphaSlider.setOrientation(1);
        alphaSlider.setValue(255);
        alphaSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                alphaSliderStateChanged(e);
            }
        });

        alphaLabel.setText("255");
        setButton.setText("Ok");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonActionPerformed(e);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonActionPerformed(e);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 486, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(colorChooser, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel1).addComponent(alphaSlider, -2, -1, -2).addComponent(alphaLabel))).addGroup(layout.createSequentialGroup().addComponent(setButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton))).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 391, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(alphaSlider, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(alphaLabel)).addComponent(colorChooser, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(setButton).addComponent(cancelButton)).addContainerGap(-1, 32767)));
        pack();
    }

    private void setButtonActionPerformed(ActionEvent evt) {
        target.setBackground(colorChooser.getColor());
        parent.colorChosen(target);
        setVisible(false);
    }

    public void setTarget(JPanel target) {
        this.target = target;
        setLocationRelativeTo(parent);
        colorChooser.setColor(target.getBackground());
        colorChooser.getSelectionModel().addChangeListener(this);
        alphaSlider.setValue(target.getBackground().getAlpha());
        alphaLabel.setText("" + alphaSlider.getValue());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        alphaSlider.setValue(colorChooser.getColor().getAlpha());
    }
}
