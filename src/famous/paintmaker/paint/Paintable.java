package famous.paintmaker.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Paintable {

    public Color color;
    public int h;
    public Paintable shadow;
    public BasicStroke stroke;
    public Color strokeColor;
    public int w;
    public int x;
    public int y;

    public abstract Paintable duplicate();

    public abstract String getDrawCode();

    public abstract String getFillCode();

    public abstract String getListText();

    public abstract void paintTo(Graphics2D paramGraphics2D);

    public abstract String toOutput();
}
