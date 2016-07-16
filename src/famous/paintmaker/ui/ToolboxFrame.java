package famous.paintmaker.ui;

import famous.paintmaker.paint.JavaConverter;
import famous.paintmaker.paint.PCircle;
import famous.paintmaker.paint.PImage;
import famous.paintmaker.paint.PLine;
import famous.paintmaker.paint.PPolygon;
import famous.paintmaker.paint.PRectangle;
import famous.paintmaker.paint.PRoundRect;
import famous.paintmaker.paint.PString;
import famous.paintmaker.paint.PaintIO;
import famous.paintmaker.paint.Paintable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

public class ToolboxFrame extends JFrame {

    public JRadioButtonMenuItem aaAllOp;
    public JRadioButtonMenuItem aaTextOp;
    private JMenu aboutMenu;
    private JMenuItem aboutOption;
    private ButtonGroup antialiasingGroup;
    private BufferedImage background;
    private JMenuItem backgroundOp;
    private JToggleButton boldButton;
    public Canvas canvas;
    private CanvasFrame canvasFrame;
    private JToggleButton circleButton;
    private JMenuItem clearOption;
    private ColorChooserDialog colorChooser;
    private JButton deleteLayerButton;
    public JCheckBoxMenuItem drawInterfaceOp;
    private JPanel dummy;
    private JButton duplicateButton;
    private JToggleButton editButton;
    private boolean editing;
    private File file;
    private FileFilter fileFilter;
    private JMenu fileMenu;
    private JCheckBox fillBox;
    private JPanel fillColorPanel;
    private JComboBox fontDropDown;
    private JSpinner fontSize;
    private JMenuItem generateOption;
    private GridDialog gridOptions;
    private JMenuItem gridOptionsOption;
    private JToggleButton imageButton;
    private String imageURL;
    private JToggleButton italicsButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JMenuBar jMenuBar1;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JButton layerDownButton;
    private JList layerList;
    private JButton layerUpButton;
    public PaintableListModel layers;
    private JToggleButton lineButton;
    private Image loadedImage;
    private JToggleButton moveButton;
    private JRadioButtonMenuItem noneOp;
    private JFileChooser openDialog;
    private JMenuItem openOption;
    private JToggleButton polygonButton;
    private JToggleButton rectButton;
    private JToggleButton resizeButton;
    private JToggleButton roundRectButton;
    private RRDialog roundRectOptions;
    private JButton rroptionsButton;
    private JMenuItem saveAsOption;
    private JFileChooser saveDialog;
    private JMenuItem saveOption;
    private JButton shadowButton;
    private JPanel shadowColorPanel;
    private JSpinner shadowX;
    private JSpinner shadowY;
    public JCheckBoxMenuItem showGridBox;
    public JCheckBoxMenuItem snap2GridBox;
    private JToggleButton stringButton;
    private JCheckBox strokeBox;
    private JPanel strokeColorPanel;
    private JSpinner strokeSpinner;
    private JTextField textBox;
    private JToggleButton tool;
    private ButtonGroup toolsGroup;
    private JMenu viewMenu;

    public ToolboxFrame() {
        background = new BufferedImage(16, 16, 1);
        Graphics g = background.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 16, 16);
        dummy = new JPanel();
        dummy.setBackground(Color.LIGHT_GRAY);
        initComponents();
        fontDropDown.setSelectedItem("Arial");
        updateFont();
        canvasFrame = new CanvasFrame(this);
        canvasFrame.setVisible(true);
        canvasFrame.setLocation(10, 10);
        Point loc = canvasFrame.getLocation();
        setLocation(loc.x + canvasFrame.getWidth(), loc.y);
        colorChooser = new ColorChooserDialog(this, true);
        gridOptions = new GridDialog(this, true, colorChooser);
        saveDialog = new JFileChooser();
        saveDialog.setApproveButtonText("Save");
        saveDialog.setFileFilter(fileFilter);
        openDialog = new JFileChooser();
        openDialog.setApproveButtonText("Open");
        openDialog.setMultiSelectionEnabled(false);
        openDialog.setFileFilter(fileFilter);
        roundRectOptions = new RRDialog(this, true);
    }
    public static final int CIRCLE = 3;
    public static final int IMAGE = 8;
    public static final int LINE = 4;
    public static final int MOVE = 0;
    public static final int POLY = 5;
    public static final int RECT = 2;
    public static final int RESIZE = 7;
    public static final int ROUND = 6;
    public static final int TEXT = 1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToolboxFrame().setVisible(true);
            }
        });
    }

    private void aboutOptionActionPerformed(ActionEvent evt) {
        JOptionPane
                .showMessageDialog(
                        null,
                        "Updated by Famous for Powerbot, originally created by Enfilade.\r\nThis tool enables developers to paint aesthetic overlays for their scripts.\r\n");

    }

    private void backgroundOpActionPerformed(ActionEvent evt) {
        colorChooser.setTarget(dummy);
        colorChooser.setVisible(true);
    }

    private void boldButtonActionPerformed(ActionEvent evt) {
        updateFont();
    }

    public void checkLayerTools() {
        Paintable selection = layers.getSelected();
        boolean enabled = selection != null;
        boolean resizable = ((selection instanceof PRectangle)) || ((selection instanceof PRoundRect)) || ((selection instanceof PCircle));
        moveButton.setEnabled(enabled);
        layerUpButton.setEnabled(enabled);
        layerDownButton.setEnabled(enabled);
        deleteLayerButton.setEnabled(enabled);
        editButton.setEnabled(enabled);
        duplicateButton.setEnabled(enabled);
        shadowButton.setEnabled(enabled);
        resizeButton.setEnabled((enabled) && (resizable));
        if (!fillBox.isEnabled()) {
            fillBox.setSelected(false);
        }
        editing = ((enabled) && (editing));
    }

    private void circleButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    private void clearOptionActionPerformed(ActionEvent evt) {
        if ((layers.getSize() > 0) && (JOptionPane.showConfirmDialog(this, "Do you really want to clear the screen?", "Confirm", 0) == 0)) {
            layers.clear();
        }
    }

    public void colorChosen(JPanel p) {
        if (p == dummy) {
            Graphics g = background.getGraphics();
            g.setColor(dummy.getBackground());
            g.fillRect(0, 0, 16, 16);
            return;
        }
        if (!editing) {
            return;
        }
        if ((p == fillColorPanel) && (fillBox.isSelected())) {
            layers.getSelected().color = fillColorPanel.getBackground();
        } else if ((p == strokeColorPanel) && (strokeBox.isSelected())) {
            layers.getSelected().strokeColor = strokeColorPanel.getBackground();
        } else if ((p == shadowColorPanel) && (layers.selectedIndexIsValid())) {
            Paintable shadow = layers.getSelected().shadow;
            if (shadow.strokeColor != null) {
                shadow.strokeColor = shadowColorPanel.getBackground();
            }
            if (shadow.color != null) {
                shadow.color = shadowColorPanel.getBackground();
            }
        }
    }

    private void deleteLayerButtonActionPerformed(ActionEvent evt) {
        layers.removeSelected();
        checkLayerTools();
    }

    private void duplicateButtonActionPerformed(ActionEvent evt) {
        Paintable p = layers.getSelected();
        if (p != null) {
            layers.insert(p.duplicate());
        }
    }

    private void editButtonActionPerformed(ActionEvent evt) {
        editing = editButton.isSelected();
        if (editing) {
            setOptions(layers.getSelected());
        }
    }

    private void fillBoxActionPerformed(ActionEvent evt) {
        if ((!strokeBox.isSelected()) || (stringButton.isSelected())) {
            fillBox.setSelected(true);
        }
        if ((editing) && (!(layers.getSelected() instanceof PLine)) && (!(layers.getSelected() instanceof PImage))) {
            layers.getSelected().color = (fillBox.isSelected() ? fillColorPanel.getBackground() : null);
        }
    }

    private void fillColorPanelMouseReleased(MouseEvent evt) {
        colorChooser.setTarget(fillColorPanel);
        colorChooser.setVisible(true);
    }

    private void fontDropDownActionPerformed(ActionEvent evt) {
        updateFont();
    }

    private void fontSizeStateChanged(ChangeEvent evt) {
        if ((editing) && ((layers.getSelected() instanceof PString))) {
            updateFont();
        }
    }

    private void formWindowClosing(WindowEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Confirm", 0) == 0) {
            System.exit(0);
        }
    }

    private void generateOptionActionPerformed(ActionEvent evt) {
        int aa = 0;
        if (aaAllOp.isSelected()) {
            aa = 1;
        } else if (aaTextOp.isSelected()) {
            aa = 2;
        }
        String[] gen = JavaConverter.convertLayersToJava(layers.getArrayList(), aa);
        new CodeDialog(this, true, gen[0], gen[1]).setVisible(true);
    }

    public int getArcHeight() {
        return ((Integer) roundRectOptions.arcHeight.getValue());
    }

    public int getArcWidth() {
        return ((Integer) roundRectOptions.arcWidth.getValue());
    }

    public Color getCanvasBackground() {
        return dummy.getBackground();
    }

    public Color getColor() {
        if (fillBox.isSelected()) {
            return fillColorPanel.getBackground();
        }
        return null;
    }

    public Color getGridColor() {
        return gridOptions.gridColorPanel.getBackground();
    }

    public int getGridHeight() {
        return ((Integer) gridOptions.cellHeight.getValue());
    }

    public int getGridWidth() {
        return ((Integer) gridOptions.cellWidth.getValue());
    }

    public Image getImage() {
        return loadedImage;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Font getPreviewFont() {
        int flags = 0;
        if (boldButton.isSelected()) {
            flags |= 0x1;
        }
        if (italicsButton.isSelected()) {
            flags |= 0x2;
        }
        return new Font((String) fontDropDown.getSelectedItem(), flags, 11);
    }

    public Font getSelectedFont() {
        int flags = 0;
        if (boldButton.isSelected()) {
            flags |= 0x1;
        }
        if (italicsButton.isSelected()) {
            flags |= 0x2;
        }
        return new Font((String) fontDropDown.getSelectedItem(), flags, ((Integer) fontSize.getValue()));
    }

    public int getSelectedTool() {
        if (moveButton.isSelected()) {
            return 0;
        }
        if (stringButton.isSelected()) {
            return 1;
        }
        if (rectButton.isSelected()) {
            return 2;
        }
        if (circleButton.isSelected()) {
            return 3;
        }
        if (lineButton.isSelected()) {
            return 4;
        }
        if (polygonButton.isSelected()) {
            return 5;
        }
        if (roundRectButton.isSelected()) {
            return 6;
        }
        if (resizeButton.isSelected()) {
            return 7;
        }
        if (imageButton.isSelected()) {
            return 8;
        }
        return -1;
    }

    public String getString() {
        return textBox.getText();
    }

    public BasicStroke getStroke() {
        if (strokeBox.isSelected()) {
            return new BasicStroke(((Integer) strokeSpinner.getValue()));
        }
        return null;
    }

    public Color getStrokeColor() {
        if (strokeBox.isSelected()) {
            return strokeColorPanel.getBackground();
        }
        return null;
    }

    private void gridOptionsOptionActionPerformed(ActionEvent evt) {
        gridOptions.setLocationRelativeTo(this);
        gridOptions.setVisible(true);
    }

    private void imageButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        String enteredURL;
        Image loaded = PImage.getImageFrom(enteredURL = JOptionPane.showInputDialog(this, "Enter the image's URL."));
        if (loaded == null) {
            JOptionPane.showMessageDialog(this, "Invalid URL!");
            return;
        }
        if (loaded == PImage.IMG_ERROR_IMG) {
            JOptionPane.showMessageDialog(this, "Could not load the image, but you can still add it to your paint.");
        }
        loadedImage = loaded;
        imageURL = enteredURL;
    }

    private void initComponents() {
        toolsGroup = new ButtonGroup();
        antialiasingGroup = new ButtonGroup();
        jScrollPane1 = new JScrollPane();
        layerList = new JList();
        layerUpButton = new JButton();
        layerDownButton = new JButton();
        lineButton = new JToggleButton();
        deleteLayerButton = new JButton();
        polygonButton = new JToggleButton();
        stringButton = new JToggleButton();
        rectButton = new JToggleButton();
        circleButton = new JToggleButton();
        moveButton = new JToggleButton();
        fillBox = new JCheckBox();
        fillColorPanel = new JPanel();
        strokeColorPanel = new JPanel();
        strokeBox = new JCheckBox();
        strokeSpinner = new JSpinner();
        jLabel1 = new JLabel();
        fontDropDown = new JComboBox();
        boldButton = new JToggleButton();
        italicsButton = new JToggleButton();
        fontSize = new JSpinner();
        editButton = new JToggleButton();
        textBox = new JTextField();
        jLabel2 = new JLabel();
        duplicateButton = new JButton();
        jLabel3 = new JLabel();
        shadowButton = new JButton();
        shadowColorPanel = new JPanel();
        jLabel4 = new JLabel();
        shadowX = new JSpinner();
        shadowY = new JSpinner();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        roundRectButton = new JToggleButton();
        rroptionsButton = new JButton();
        resizeButton = new JToggleButton();
        imageButton = new JToggleButton();
        jMenuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        clearOption = new JMenuItem();
        jSeparator3 = new JSeparator();
        openOption = new JMenuItem();
        saveOption = new JMenuItem();
        saveAsOption = new JMenuItem();
        jSeparator2 = new JSeparator();
        generateOption = new JMenuItem();
        viewMenu = new JMenu();
        aboutMenu = new JMenu();
        aboutOption = new JMenuItem();
        noneOp = new JRadioButtonMenuItem();
        aaAllOp = new JRadioButtonMenuItem();
        aaTextOp = new JRadioButtonMenuItem();
        jSeparator1 = new JSeparator();
        showGridBox = new JCheckBoxMenuItem();
        snap2GridBox = new JCheckBoxMenuItem();
        gridOptionsOption = new JMenuItem();
        jSeparator4 = new JSeparator();
        drawInterfaceOp = new JCheckBoxMenuItem();
        backgroundOp = new JMenuItem();

        setDefaultCloseOperation(0);
        setTitle("Toolbox");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formWindowClosing(e);
            }
        });

        layers = new PaintableListModel(layerList, this);
        layerList.setModel(layers);
        layerList.setSelectionMode(0);
        layerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                layerListValueChanged(e);
            }
        });

        layerList.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                layerListKeyReleased(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        jScrollPane1.setViewportView(layerList);
        layerUpButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/layerup.png")));
        layerUpButton.setToolTipText("Move selected layer up.");
        layerUpButton.setEnabled(false);
        layerUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layerUpButtonActionPerformed(evt);
            }
        });

        layerDownButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/layerdown.png")));
        layerDownButton.setToolTipText("Move selected layer down.");
        layerDownButton.setEnabled(false);
        layerDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layerDownButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(lineButton);
        lineButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/line.png")));
        lineButton.setToolTipText("Draw a line.");
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineButtonActionPerformed(evt);
            }
        });

        deleteLayerButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/trashcan.png")));
        deleteLayerButton.setToolTipText("Delete the selected layer.");
        deleteLayerButton.setEnabled(false);
        deleteLayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLayerButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(polygonButton);
        polygonButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/polygon.png")));
        polygonButton.setToolTipText("Draw a polygon.");
        polygonButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polygonButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(stringButton);
        stringButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/string.png")));
        stringButton.setToolTipText("Draw a string.");
        stringButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(rectButton);
        rectButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/rect.png")));
        rectButton.setToolTipText("Draw a rectangle.");
        rectButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(circleButton);
        circleButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/circle.png")));
        circleButton.setToolTipText("Draw a circle or oval.");
        circleButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circleButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(moveButton);
        moveButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/move.png")));
        moveButton.setToolTipText("Move selected layer.");
        moveButton.setEnabled(false);
        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveButtonActionPerformed(evt);
            }
        });

        fillBox.setSelected(true);
        fillBox.setText("Fill");
        fillBox.setToolTipText("Toggles whether or not this layer has a fill.");
        fillBox.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillBoxActionPerformed(evt);
            }
        });

        fillColorPanel.setBackground(new Color(255, 255, 255));
        fillColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fillColorPanel.setToolTipText("Choose a fill color. This is also used when drawing text.");
        fillColorPanel.setPreferredSize(new Dimension(26, 25));
        fillColorPanel.addMouseListener(new MouseListener() {
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
                fillColorPanelMouseReleased(e);
            }
        });

        GroupLayout fillColorPanelLayout = new GroupLayout(fillColorPanel);
        fillColorPanel.setLayout(fillColorPanelLayout);
        fillColorPanelLayout.setHorizontalGroup(fillColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));
        fillColorPanelLayout.setVerticalGroup(fillColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));
        strokeColorPanel.setBackground(new Color(0, 0, 0));
        strokeColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        strokeColorPanel.setToolTipText("Choose a stroke color. This is also used when drawing lines.");
        strokeColorPanel.addMouseListener(new MouseListener() {
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
                strokeColorPanelMouseReleased(e);
            }
        });

        GroupLayout strokeColorPanelLayout = new GroupLayout(strokeColorPanel);
        strokeColorPanel.setLayout(strokeColorPanelLayout);
        strokeColorPanelLayout.setHorizontalGroup(strokeColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

        strokeColorPanelLayout.setVerticalGroup(strokeColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

        strokeBox.setSelected(true);
        strokeBox.setText("Stroke");
        strokeBox.setToolTipText("Toggles whether or not this layer has a stroke.");
        strokeBox.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strokeBoxActionPerformed(evt);
            }
        });

        strokeSpinner.setModel(new SpinnerNumberModel(1, 1, 25, 1));
        strokeSpinner.setToolTipText("Changes how thick the stroke around this layer is.");
        strokeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                strokeSpinnerStateChanged(e);
            }
        });

        jLabel1.setText("Font:");
        fontDropDown.setModel(new DefaultComboBoxModel(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        fontDropDown.setToolTipText("Choose a font.");
        fontDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontDropDownActionPerformed(evt);
            }
        });

        boldButton.setFont(new Font("Tahoma", 1, 11));
        boldButton.setText("B");
        boldButton.setToolTipText("Toggles bold text.");
        boldButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boldButtonActionPerformed(evt);
            }
        });

        italicsButton.setFont(new Font("Tahoma", 2, 11));
        italicsButton.setText("I");
        italicsButton.setToolTipText("Toggles italicized text.");
        italicsButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italicsButtonActionPerformed(evt);
            }
        });

        fontSize.setModel(new SpinnerNumberModel(9, 7, 32, 1));
        fontSize.setToolTipText("Change the size of your font. (Will not be shown in the preview)");
        fontSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fontSizeStateChanged(e);
            }
        });

        toolsGroup.add(editButton);
        editButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/edit.png")));
        editButton.setToolTipText("Edit selected layer's colors, stroke, font, and text.");
        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        textBox.setToolTipText("Text here will be drawn with the text tool.");
        textBox.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                textBoxKeyReleased(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        jLabel2.setText("Text:");
        duplicateButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/duplicate.png")));
        duplicateButton.setToolTipText("Duplicate selected layer.");
        duplicateButton.setEnabled(false);
        duplicateButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicateButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Layers:");

        shadowButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/shadow.png")));
        shadowButton.setToolTipText("<html>Creates a shadow for the selected layer.<br>\nThe new shadow layer will be moved as you move the selected layer.");
        shadowButton.setEnabled(false);
        shadowButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowButtonActionPerformed(evt);
            }
        });

        shadowColorPanel.setBackground(new Color(0, 0, 0));
        shadowColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        shadowColorPanel.setToolTipText("Choose a stroke color. This is also used when drawing lines.");
        shadowColorPanel.addMouseListener(new MouseListener() {
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
                shadowColorPanelMouseReleased(e);
            }
        });

        GroupLayout shadowColorPanelLayout = new GroupLayout(shadowColorPanel);
        shadowColorPanel.setLayout(shadowColorPanelLayout);
        shadowColorPanelLayout.setHorizontalGroup(shadowColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));
        shadowColorPanelLayout.setVerticalGroup(shadowColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));
        jLabel4.setText("Shadow:");
        shadowX.setModel(new SpinnerNumberModel(3, null, null, 1));
        shadowY.setModel(new SpinnerNumberModel(3, null, null, 1));
        jLabel5.setText("X:");
        jLabel6.setText("Y:");
        toolsGroup.add(roundRectButton);
        roundRectButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/roundrect.png")));
        roundRectButton.setToolTipText("Draw a rounded rectangle.");
        roundRectButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundRectButtonActionPerformed(evt);
            }
        });

        rroptionsButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/roundrectops.png")));
        rroptionsButton.setToolTipText("Change the arc width and height of rounded rectangles.");
        rroptionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rroptionsButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(resizeButton);
        resizeButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/resize.png")));
        resizeButton.setToolTipText("<html>Resize selected layer.<br>\n(Only usable on rectangles, rounded rectangles, and ovals)");
        resizeButton.setEnabled(false);
        resizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resizeButtonActionPerformed(evt);
            }
        });

        toolsGroup.add(imageButton);
        imageButton.setIcon(new ImageIcon(getClass().getResource("/famous/paintmaker/images/image.png")));
        imageButton.setToolTipText("Load and draw an image from a URL.");
        imageButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        clearOption.setText("Clear");
        clearOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOptionActionPerformed(evt);
            }
        });

        fileMenu.add(clearOption);
        fileMenu.add(jSeparator3);
        openOption.setText("Open...");
        openOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOptionActionPerformed(evt);
            }
        });

        fileMenu.add(openOption);

        saveOption.setText("Save");
        saveOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveOptionActionPerformed(evt);
            }
        });

        fileMenu.add(saveOption);

        saveAsOption.setText("Save As...");
        saveAsOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsOptionActionPerformed(evt);
            }
        });

        fileMenu.add(saveAsOption);
        fileMenu.add(jSeparator2);

        generateOption.setText("Generate Code");
        generateOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateOptionActionPerformed(evt);
            }
        });

        fileMenu.add(generateOption);
        jMenuBar1.add(fileMenu);
        viewMenu.setText("View");
        antialiasingGroup.add(noneOp);
        noneOp.setSelected(true);
        noneOp.setText("No anti-aliasing");
        viewMenu.add(noneOp);
        antialiasingGroup.add(aaAllOp);
        aaAllOp.setText("Anti-alias Shapes and Text");
        viewMenu.add(aaAllOp);
        antialiasingGroup.add(aaTextOp);
        aaTextOp.setText("Anti-alias Text");
        viewMenu.add(aaTextOp);
        viewMenu.add(jSeparator1);
        showGridBox.setText("Show Grid");
        viewMenu.add(showGridBox);
        snap2GridBox.setText("Snap-to Grid");
        viewMenu.add(snap2GridBox);
        gridOptionsOption.setText("Grid Options...");
        gridOptionsOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridOptionsOptionActionPerformed(evt);
            }
        });

        viewMenu.add(gridOptionsOption);
        viewMenu.add(jSeparator4);
        drawInterfaceOp.setSelected(true);
        drawInterfaceOp.setText("Draw RS Interface");
        viewMenu.add(drawInterfaceOp);
        backgroundOp.setIcon(new ImageIcon(background));
        backgroundOp.setText("Change Background Color");
        backgroundOp.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundOpActionPerformed(evt);
            }
        });

        viewMenu.add(backgroundOp);
        jMenuBar1.add(viewMenu);
        aboutMenu.setText("Help");
        aboutOption.setText("About");
        aboutOption.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutOptionActionPerformed(evt);
            }
        });
        aboutMenu.add(aboutOption);
        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(stringButton, -2, 27, -2).addComponent(rectButton, -2, 27, -2)).addComponent(roundRectButton, -2, 27, -2)).addComponent(circleButton, -2, 27, -2).addComponent(lineButton, -2, 27, -2).addComponent(polygonButton, -2, 27, -2).addComponent(moveButton, -2, 27, -2).addComponent(resizeButton, -2, 27, -2).addComponent(imageButton, -2, 27, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(shadowColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(shadowX, -2, 40, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(shadowY, -2, 40, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(strokeColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(strokeBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(strokeSpinner, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(rroptionsButton, -2, 28, -2)).addGroup(layout.createSequentialGroup().addComponent(fillColorPanel, -2, 25, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(fillBox)).addComponent(jLabel1).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(textBox, -1, 170, 32767)).addComponent(fontDropDown, 0, -1, 32767)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(boldButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(italicsButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(fontSize, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jScrollPane1, 0, 0, 32767).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(layerUpButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(layerDownButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(editButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(duplicateButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(shadowButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(deleteLayerButton, -2, 28, -2)).addComponent(jLabel3))).addContainerGap(20, 32767)));
        layout.linkSize(0, new Component[]{circleButton, lineButton, moveButton, polygonButton, rectButton, stringButton});
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(fillColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(strokeBox).addComponent(strokeSpinner, -2, -1, -2).addComponent(rroptionsButton)).addComponent(strokeColorPanel, -2, -1, -2))).addComponent(fillBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(textBox, -2, -1, -2).addComponent(jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(fontDropDown, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(boldButton).addComponent(italicsButton).addComponent(fontSize, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(editButton, GroupLayout.Alignment.LEADING).addComponent(shadowButton).addComponent(duplicateButton).addComponent(deleteLayerButton)).addComponent(layerUpButton).addComponent(layerDownButton))).addGroup(layout.createSequentialGroup().addComponent(stringButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(rectButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(roundRectButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(circleButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(lineButton).addGap(5, 5, 5).addComponent(polygonButton).addGap(5, 5, 5).addComponent(imageButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(moveButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(resizeButton))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(shadowX, -2, -1, -2).addComponent(jLabel5).addComponent(jLabel6).addComponent(shadowY, -2, -1, -2)).addComponent(shadowColorPanel, -2, -1, -2).addComponent(jLabel4)).addContainerGap(22, 32767)));
        layout.linkSize(1, new Component[]{circleButton, lineButton, moveButton, polygonButton, rectButton, stringButton});
        pack();
    }

    private void italicsButtonActionPerformed(ActionEvent evt) {
        updateFont();
    }

    private void layerDownButtonActionPerformed(ActionEvent evt) {
        layers.moveSelectedDown();
    }

    private void layerListKeyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case 127:
                layers.removeSelected();
                break;
            case 85:
                layers.moveSelectedUp();
                break;
            case 68:
                layers.moveSelectedDown();
        }
    }

    private void layerListValueChanged(ListSelectionEvent evt) {
        boolean enabled = layers.selectedIndexIsValid();
        checkLayerTools();
        if (editing) {
            setOptions(layers.getSelected());
        }
    }

    private void layerUpButtonActionPerformed(ActionEvent evt) {
        layers.moveSelectedUp();
    }

    private void lineButtonActionPerformed(ActionEvent evt) {
        editing = false;
        strokeBox.setSelected(true);
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    private void moveButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(13));
        canvas.reset();
    }

    public void open() {
        if (openDialog.showOpenDialog(this) == 0) {
            File f = openDialog.getSelectedFile();
            if (!f.exists()) {
                JOptionPane.showMessageDialog(this, "File does not exist!");
                return;
            }
            ArrayList<Paintable> newLayers = PaintIO.open(f, this);
            if (newLayers == null) {
                JOptionPane.showMessageDialog(this, "Error reading file!");
                return;
            }
            file = f;
            canvasFrame.setTitle("Preview of " + f.getName());
            layers.clear();
            layers.getArrayList().addAll(newLayers);
            layerList.updateUI();
            checkLayerTools();
        }
    }

    private void openOptionActionPerformed(ActionEvent evt) {
        if ((layers.getSize() > 0) && (JOptionPane.showConfirmDialog(this, "Opening a project will clear the screen. Are you sure?", "Confirm", 0) != 0)) {
            return;
        }
        open();
    }

    private void polygonButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    private void rectButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    private void resizeButtonActionPerformed(ActionEvent evt) {
        canvas.setCursor(Cursor.getPredefinedCursor(6));
        canvas.reset();
    }

    private void roundRectButtonActionPerformed(ActionEvent evt) {
        editing = false;
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    public void roundRectDialogClosed() {
        if ((editing) && ((layers.getSelected() instanceof PRoundRect))) {
            PRoundRect p = (PRoundRect) layers.getSelected();
            p.arcWidth = getArcWidth();
            p.arcHeight = getArcHeight();
        }
    }

    private void rroptionsButtonActionPerformed(ActionEvent evt) {
        roundRectOptions.setLocationRelativeTo(this);
        roundRectOptions.setVisible(true);
    }

    public void save() {
        if (file == null) {
            saveAs();
        } else {
            PaintIO.saveLayersAs(layers.getArrayList(), file, this);
        }
    }

    public void saveAs() {
        if (saveDialog.showSaveDialog(this) == 0) {
            File f = saveDialog.getSelectedFile();
            if (!f.getName().endsWith(".eep")) {
                f = new File(f.getPath() + ".eep");
            }
            if ((f.exists()) && (JOptionPane.showConfirmDialog(this, "Do you really want to overwrite " + f.getName() + "?", "Confirm", 0) != 0)) {
                return;
            }
            if (PaintIO.saveLayersAs(layers.getArrayList(), f, this)) {
                file = f;
                canvasFrame.setTitle("Preview of " + f.getName());
            }
        }
    }

    private void saveAsOptionActionPerformed(ActionEvent evt) {
        saveAs();
    }

    private void saveOptionActionPerformed(ActionEvent evt) {
        save();
    }

    private void setOptions(Paintable p) {
        if ((p.stroke != null) && (p.strokeColor != null)) {
            strokeColorPanel.setBackground(p.strokeColor);
            strokeSpinner.setValue((int) p.stroke.getLineWidth());
            strokeBox.setSelected(true);
        } else {
            strokeBox.setSelected(false);
        }
        if (p.color != null) {
            fillColorPanel.setBackground(p.color);
            fillBox.setSelected(true);
        } else {
            fillBox.setSelected(false);
        }
        if ((p instanceof PString)) {
            textBox.setText(((PString) p).text);
            Font f = ((PString) p).font;
            fontDropDown.setSelectedItem(f.getName());
            int flags = f.getStyle();
            boldButton.setSelected((flags & 0x1) > 0);
            italicsButton.setSelected((flags & 0x2) > 0);
            fontSize.setValue(f.getSize());
        } else if ((p instanceof PRoundRect)) {
            PRoundRect prr = (PRoundRect) p;
            roundRectOptions.arcWidth.setValue(prr.arcWidth);
            roundRectOptions.arcHeight.setValue(prr.arcHeight);
        }
    }

    private void shadowButtonActionPerformed(ActionEvent evt) {
        Paintable selection = layers.getSelected();
        if (selection != null) {
            Paintable p;
            if ((selection instanceof PImage)) {
                p = new PRectangle(selection.x, selection.y, selection.w, selection.h, Color.BLACK, null, null);
            } else {
                p = selection.duplicate();
            }
            int moveX = ((Integer) shadowX.getValue());
            int moveY = ((Integer) shadowY.getValue());
            p.x += moveX;
            p.y += moveY;
            if ((p instanceof PPolygon)) {
                ((PPolygon) p).move(moveX, moveY);
            } else if ((p instanceof PLine)) {
                ((PLine) p).move(moveX, moveY);
            }
            if (p.color != null) {
                p.color = shadowColorPanel.getBackground();
            }
            if (p.strokeColor != null) {
                p.strokeColor = shadowColorPanel.getBackground();
            }
            selection.shadow = p;
            layers.add(layers.getSelectedIndex(), p);
        }
    }

    private void shadowColorPanelMouseReleased(MouseEvent evt) {
        colorChooser.setTarget(shadowColorPanel);
        colorChooser.setVisible(true);
    }

    private void stringButtonActionPerformed(ActionEvent evt) {
        editing = false;
        fillBox.setSelected(true);
        canvas.setCursor(Cursor.getPredefinedCursor(1));
        canvas.reset();
    }

    private void strokeBoxActionPerformed(ActionEvent evt) {
        if ((!fillBox.isSelected()) || (lineButton.isSelected())) {
            strokeBox.setSelected(true);
        }
        if ((editing) && (!(layers.getSelected() instanceof PString)) && (!(layers.getSelected() instanceof PImage))) {
            layers.getSelected().strokeColor = (strokeBox.isSelected() ? strokeColorPanel.getBackground() : null);
            layers.getSelected().stroke = getStroke();
        }
    }

    private void strokeColorPanelMouseReleased(MouseEvent evt) {
        colorChooser.setTarget(strokeColorPanel);
        colorChooser.setVisible(true);
    }

    private void strokeSpinnerStateChanged(ChangeEvent evt) {
        if ((editing) && (!(layers.getSelected() instanceof PImage)) && (!(layers.getSelected() instanceof PString))) {
            layers.getSelected().stroke = new BasicStroke(((Integer) strokeSpinner.getValue()));
        }
    }

    private void textBoxKeyReleased(KeyEvent evt) {
        if ((editing) && ((layers.getSelected() instanceof PString))) {
            ((PString) layers.getSelected()).text = textBox.getText();
        }
    }

    private void updateFont() {
        if ((editing) && ((layers.getSelected() instanceof PString))) {
            ((PString) layers.getSelected()).font = getSelectedFont();
        }
    }

}
