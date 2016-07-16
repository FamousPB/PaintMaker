package famous.paintmaker.ui;

import famous.paintmaker.paint.PCircle;
import famous.paintmaker.paint.PImage;
import famous.paintmaker.paint.PLine;
import famous.paintmaker.paint.PPolygon;
import famous.paintmaker.paint.PRectangle;
import famous.paintmaker.paint.PRoundRect;
import famous.paintmaker.paint.PString;
import famous.paintmaker.paint.Paintable;
import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Canvas extends Applet implements Runnable, MouseMotionListener, MouseListener, KeyListener {

    private final Color BACKGROUND = new Color(236, 233, 216);
    private final Color SELECTED1 = new Color(255, 255, 255, 150);
    private final Color SELECTED2 = new Color(0, 0, 0, 150);
    private ArrayList<Integer> Xs;
    private ArrayList<Integer> Ys;
    private final RenderingHints all = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private Image background;
    private Image dbImage;
    private Graphics dbg;
    private Font defaultFont = null;
    private final BasicStroke defaultStroke = new BasicStroke(1.0F);
    public Dimension dimension;
    private int dragX;
    private int dragY;
    private boolean drawing;
    private Font f;
    private PaintableListModel layers;
    private String mouseInfo = "X: 0, Y: 0";
    private final RenderingHints off = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    private ToolboxFrame parent;
    private GeneralPath polyToDraw;
    private int pressX;
    private int pressY;
    private int shadowHeight;
    private int shadowWidth;
    boolean shiftDown;
    private int startHeight;
    private int startWidth;
    private final RenderingHints textOnly = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    private Toolkit tk;
    private TexturePaint tp;

    public Canvas(ToolboxFrame parent) {
        tk = Toolkit.getDefaultToolkit();
        this.parent = parent;
        layers = parent.layers;
        Xs = new ArrayList();
        Ys = new ArrayList();
        polyToDraw = new GeneralPath();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        getRuneScapeVersion(tk);
        new Thread(this).start();
    }

    private void getRuneScapeVersion(Toolkit tk) {
        String[] buttons = {"RS3", "Old School"};
        int rc = JOptionPane.showOptionDialog(null, "Please select which version of RuneScape you are developing for:", "RuneScape Version",
                JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
        if (rc == 0) {
            background = tk.getImage(getClass().getResource("/famous/paintmaker/images/rs3preview.png"));
            try {
                BufferedImage bimage = ImageIO.read((getClass().getResource("/famous/paintmaker/images/rs3preview.png")));
                dimension = (new Dimension(bimage.getWidth(), bimage.getHeight() + 25));
            } catch (IOException e) {
                System.out.println("Error reading rs3preview file");
            }

        } else {
            background = tk.getImage(getClass().getResource("/famous/paintmaker/images/ospreview.png"));
            try {
                BufferedImage bimage = ImageIO.read((getClass().getResource("/famous/paintmaker/images/ospreview.png")));
                dimension = (new Dimension(bimage.getWidth(), bimage.getHeight() + 25));
            } catch (IOException e) {
                System.out.println("Error reading ospreview file");
            }
        }
    }

    @Override
    public void init() {
        PImage.IMG_ERROR_IMG = tk.getImage(getClass().getResource("/famous/paintmaker/images/imageerror.png"));
        try {
            BufferedImage bi = ImageIO.read(getClass().getResourceAsStream("/famous/paintmaker/images/pattern2.png"));
            tp = new TexturePaint(bi, new Rectangle(0, 0, 16, 16));
        } catch (IOException e) {
            tp = null;
        }
        PImage.CANVAS = this;
    }

    public int[] integerALToIntArray(ArrayList<Integer> integers) {
        int[] asInts = new int[integers.size()];
        for (int i = 0; i < asInts.length; i++) {
            asInts[i] = (integers.get(i));
        }
        return asInts;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 16:
                shiftDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 16:
                shiftDown = false;
                break;
            case 8:
                if ((drawing) && (parent.getSelectedTool() == 5)) {
                    Xs.remove(Xs.size() - 1);
                    Ys.remove(Ys.size() - 1);
                    polyToDraw.reset();
                    if (Xs.isEmpty()) {
                        drawing = false;
                    } else {
                        polyToDraw.moveTo((Xs.get(0)), (Ys.get(0)));
                        for (int i = 1; i < Xs.size(); i++) {
                            polyToDraw.lineTo((Xs.get(i)), (Ys.get(i)));
                        }
                    }
                }
                break;
            case 27:
                if ((drawing) && (parent.getSelectedTool() == 5)) {
                    Xs.clear();
                    Ys.clear();
                    polyToDraw.reset();
                    drawing = false;
                }
                break;
            case 127:
                if (!drawing) {
                    layers.removeSelected();
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseInfo = ("X: " + e.getX() + ", Y: " + e.getY());
        Paintable p = layers.getSelected();
        dragX = e.getX();
        dragY = e.getY();
        if (parent.snap2GridBox.isSelected()) {
            dragX += (int) Math.round(parent.getGridWidth() / 2.0D);
            dragY += (int) Math.round(parent.getGridHeight() / 2.0D);
            dragX = (dragX / parent.getGridWidth() * parent.getGridWidth());
            dragY = (dragY / parent.getGridHeight() * parent.getGridHeight());
        }
        Paintable shadow;
        switch (parent.getSelectedTool()) {
            case 0:
                int oldX = p.x;
                int oldY = p.y;
                p.x = (dragX - pressX);
                p.y = (dragY - pressY);
                oldX = p.x - oldX;
                oldY = p.y - oldY;
                if ((p instanceof PLine)) {
                    ((PLine) p).move(oldX, oldY);
                } else if ((p instanceof PPolygon)) {
                    ((PPolygon) p).move(oldX, oldY);
                }
                shadow = p.shadow;
                if (shadow != null) {
                    shadow.x += oldX;
                    shadow.y += oldY;
                    if ((shadow instanceof PLine)) {
                        ((PLine) shadow).move(oldX, oldY);
                    } else if ((shadow instanceof PPolygon)) {
                        ((PPolygon) shadow).move(oldX, oldY);
                    }
                }
                break;
            case 7:
                int xdist = dragX - pressX;
                int ydist = dragY - pressY;
                p.w = (startWidth + xdist);
                p.h = (startHeight + ydist);
                if (shiftDown) {
                    p.w = Math.min(p.w, p.h);
                    p.h = p.w;
                }
                shadow = p.shadow;
                if (shadow != null) {
                    shadow.w = (shadowWidth + xdist);
                    shadow.h = (shadowHeight + ydist);
                }
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseInfo = ("X: " + e.getX() + ", Y: " + e.getY());
        if (parent.getSelectedTool() == 5) {
            dragX = e.getX();
            dragY = e.getY();
            if (parent.snap2GridBox.isSelected()) {
                dragX += (int) Math.round(parent.getGridWidth() / 2.0D);
                dragY += (int) Math.round(parent.getGridHeight() / 2.0D);
                dragX = (dragX / parent.getGridWidth() * parent.getGridWidth());
                dragY = (dragY / parent.getGridHeight() * parent.getGridHeight());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = (dragX = e.getX());
        pressY = (dragY = e.getY());
        if (parent.snap2GridBox.isSelected()) {
            pressX += (int) Math.round(parent.getGridWidth() / 2.0D);
            pressY += (int) Math.round(parent.getGridHeight() / 2.0D);
            pressX = (pressX / parent.getGridWidth() * parent.getGridWidth());
            pressY = (pressY / parent.getGridHeight() * parent.getGridHeight());
        }
        switch (parent.getSelectedTool()) {
            case 0:
                pressX -= layers.getSelected().x;
                pressY -= layers.getSelected().y;
                break;
            case 7:
                startWidth = layers.getSelected().w;
                startHeight = layers.getSelected().h;
                Paintable shadow = layers.getSelected().shadow;
                if (shadow != null) {
                    shadowWidth = shadow.w;
                    shadowHeight = shadow.h;
                }
                break;
            case 1:
                f = parent.getSelectedFont();
                break;
            case 5:
                if (polyToDraw.getCurrentPoint() == null) {
                    Xs.clear();
                    Ys.clear();
                }
                break;
            case 8:
                dragX = pressX;
                dragY = pressY;
        }
        if (parent.getSelectedTool() > 0) {
            drawing = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int releaseX = e.getX();
        int releaseY = e.getY();
        if (parent.snap2GridBox.isSelected()) {
            releaseX += (int) Math.round(parent.getGridWidth() / 2.0D);
            releaseY += (int) Math.round(parent.getGridHeight() / 2.0D);
            releaseX = releaseX / parent.getGridWidth() * parent.getGridWidth();
            releaseY = releaseY / parent.getGridHeight() * parent.getGridHeight();
        }
        drawing = (parent.getSelectedTool() == 5);
        int x = Math.min(pressX, releaseX);
        int y = Math.min(pressY, releaseY);
        int w = Math.abs(pressX - releaseX);
        int h = Math.abs(pressY - releaseY);
        if (shiftDown) {
            w = Math.min(w, h);
            h = w;
        }
        switch (parent.getSelectedTool()) {
            case 4:
                layers.insert(new PLine(pressX, pressY, releaseX, releaseY, parent.getStrokeColor(), parent.getStroke()));
                break;
            case 2:
                layers.insert(new PRectangle(x, y, w, h, parent.getColor(), parent.getStrokeColor(), parent.getStroke()));
                break;
            case 6:
                layers.insert(new PRoundRect(x, y, w, h, parent.getArcWidth(), parent.getArcHeight(), parent.getColor(), parent.getStrokeColor(), parent.getStroke()));
                break;
            case 3:
                layers.insert(new PCircle(x, y, w, h, parent.getColor(), parent.getStrokeColor(), parent.getStroke()));
                break;
            case 5:
                if (Xs.isEmpty()) {
                    polyToDraw.moveTo(releaseX, releaseY);
                    Xs.add(releaseX);
                    Ys.add(releaseY);
                } else {
                    int xdist = (Xs.get(0)) - releaseX;
                    int ydist = (Ys.get(0)) - releaseY;
                    double dist = Math.sqrt(xdist * xdist + ydist * ydist);
                    if ((dist < 3.0D) && (Xs.size() > 1)) {
                        drawing = false;
                        polyToDraw.reset();
                        layers.insert(new PPolygon(integerALToIntArray(Xs), integerALToIntArray(Ys), parent.getColor(), parent.getStrokeColor(), parent.getStroke()));
                    } else {
                        polyToDraw.lineTo(releaseX, releaseY);
                        Xs.add(releaseX);
                        Ys.add(releaseY);
                    }
                }
                break;
            case 1:
                layers.add(new PString(parent.getString(), releaseX, releaseY, parent.getColor(), f));
                break;
            case 8:
                Image img = parent.getImage();
                if (img != null) {
                    layers.add(new PImage(img, parent.getImageURL(), releaseX, releaseY));
                }
                break;
        }
    }

    @Override
    public void paint(Graphics g1) {
        if (defaultFont == null) {
            defaultFont = g1.getFont();
        }
        Graphics2D g = (Graphics2D) g1;
        if (parent.aaAllOp.isSelected()) {
            g.setRenderingHints(all);
        } else if (parent.aaTextOp.isSelected()) {
            g.setRenderingHints(textOnly);
        } else {
            g.setRenderingHints(off);
        }
        g.setColor(parent.getCanvasBackground());
        g.fillRect(0, 0, 765, 503);
        if (parent.drawInterfaceOp.isSelected()) {
            g.drawImage(background, null, this);
        }
        for (Paintable p : layers.getArrayList()) {
            p.paintTo(g);
        }
        Paintable selection = layers.getSelected();
        g.setStroke(defaultStroke);
        if (selection != null) {
            int y;
            int x;
            int w;
            int h;
            if ((selection instanceof PString)) {
                Rectangle2D r = g.getFontMetrics(((PString) selection).font).getStringBounds(((PString) selection).text, g);
                w = (int) r.getWidth();
                h = (int) r.getHeight();
                x = selection.x;
                y = selection.y - h;
            } else {
                x = selection.x;
                y = selection.y;
                w = selection.w;
                h = selection.h;
            }
            g.setColor(SELECTED1);
            g.drawRect(x - 1, y - 1, w + 2, h + 2);
            g.setColor(SELECTED2);
            g.drawRect(x - 2, y - 2, w + 4, h + 4);
        }
        int x = -1;
        int y = -1;
        int w = -1;
        int h = -1;
        if (drawing) {
            x = Math.min(dragX, pressX);
            y = Math.min(dragY, pressY);
            w = Math.abs(dragX - pressX);
            h = Math.abs(dragY - pressY);
            if (shiftDown) {
                w = Math.min(w, h);
                h = w;
            }
            Color fill = parent.getColor();
            Color stroke = parent.getStrokeColor();
            switch (parent.getSelectedTool()) {
                case 4:
                    g.setColor(parent.getStrokeColor());
                    g.setStroke(parent.getStroke());
                    g.drawLine(pressX, pressY, dragX, dragY);
                    break;
                case 3:
                    fill = parent.getColor();
                    stroke = parent.getStrokeColor();
                    if (fill != null) {
                        g.setColor(fill);
                        g.fillOval(x, y, w, h);
                    }
                    if (stroke != null) {
                        g.setColor(stroke);
                        g.setStroke(parent.getStroke());
                        g.drawOval(x, y, w, h);
                    }
                    break;
                case 2:
                    fill = parent.getColor();
                    stroke = parent.getStrokeColor();
                    if (fill != null) {
                        g.setColor(fill);
                        g.fillRect(x, y, w, h);
                    }
                    if (stroke != null) {
                        g.setColor(stroke);
                        g.setStroke(parent.getStroke());
                        g.drawRect(x, y, w, h);
                    }
                    break;
                case 6:
                    fill = parent.getColor();
                    stroke = parent.getStrokeColor();
                    if (fill != null) {
                        g.setColor(fill);
                        g.fillRoundRect(x, y, w, h, parent.getArcWidth(), parent.getArcHeight());
                    }
                    if (stroke != null) {
                        g.setColor(stroke);
                        g.setStroke(parent.getStroke());
                        g.drawRoundRect(x, y, w, h, parent.getArcWidth(), parent.getArcHeight());
                    }
                    break;
                case 5:
                    fill = parent.getColor();
                    stroke = parent.getStrokeColor();
                    if ((fill != null) && (Xs.size() > 2)) {
                        g.setColor(fill);
                        g.fill(polyToDraw);
                    }
                    if ((stroke != null)
                            && (Xs.size() > 1)) {
                        g.setColor(stroke);
                        g.setStroke(parent.getStroke());
                        g.draw(polyToDraw);
                    }
                    if (Xs.size() > 0) {
                        if (parent.getStroke() != null) {
                            g.setStroke(parent.getStroke());
                        } else {
                            g.setStroke(defaultStroke);
                        }
                        g.drawLine(dragX, dragY, (Xs.get(Xs.size() - 1)), (Ys.get(Ys.size() - 1)));
                        g.setStroke(defaultStroke);
                        g.setColor(SELECTED1);
                        x = (Xs.get(0));
                        y = (Ys.get(0));
                        g.drawRect(x - 3, y - 3, 6, 6);
                        g.setColor(SELECTED2);
                        g.drawRect(x - 4, y - 4, 8, 8);
                    }
                    break;
                case 1:
                    g.setColor(parent.getColor());
                    g.setFont(f);
                    g.drawString(parent.getString(), dragX, dragY);
                    break;
                case 8:
                    Image img = parent.getImage();
                    if (img != null) {
                        g.drawImage(img, dragX, dragY, this);
                    }
                    break;
            }
        }
        if (parent.showGridBox.isSelected()) {
            g.setColor(parent.getGridColor());
            for (int i = parent.getGridWidth(); i < getWidth(); i += parent.getGridWidth()) {
                g.drawLine(i, 0, i, 508);
            }
            for (int i = parent.getGridHeight(); i < getWidth(); i += parent.getGridHeight()) {
                g.drawLine(0, i, 765, i);
            }
        }
        g.setColor(BACKGROUND);
        g.fillRect(0, dimension.height, dimension.width, 25);
        g.setColor(Color.BLACK);
        g.setFont(defaultFont);
        if ((h >= 0) && (w >= 0)) {
            g.drawString(mouseInfo + " - W: " + w + ", H: " + h, 5, dimension.height - 8);
        } else {
            g.drawString(mouseInfo, 5, dimension.height - 8);
        }
        g.setFont(parent.getPreviewFont());
        g.drawString("Preview Text", 400, dimension.height - 8);
    }

    public void reset() {
        polyToDraw.reset();
        Xs.clear();
        Ys.clear();
        shiftDown = false;
        drawing = false;
    }

    @Override
    public void run() {
        for (;;) {
            repaint();
            try {
                Thread.sleep(25L);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void update(Graphics g) {
        if (dbImage == null) {
            dbImage = createImage(getSize().width, getSize().height);
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, getSize().width, getSize().height);
        dbg.setColor(getForeground());
        paint(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
}
