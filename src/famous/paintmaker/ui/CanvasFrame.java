package famous.paintmaker.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CanvasFrame extends JFrame {

    public CanvasFrame(ToolboxFrame toolbox) {
        setTitle("Preview");
        setDefaultCloseOperation(0);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(toolbox, "Do you really want to exit?", "Confirm", 0) == 0) {
                    System.exit(0);
                }
            }
        });

        toolbox.canvas = new Canvas(toolbox);
        toolbox.canvas.setPreferredSize(toolbox.canvas.dimension);
        toolbox.canvas.init();
        setResizable(false);
        add(toolbox.canvas);
        pack();
    }
}
