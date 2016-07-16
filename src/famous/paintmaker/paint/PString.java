package famous.paintmaker.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class PString extends Paintable {

    public Font font;
    public String text;

    public PString(String text, int x, int y, Color color, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
    }

    @Override
    public Paintable duplicate() {
        return new PString(this.text, this.x, this.y, this.color, this.font);
    }

    @Override
    public String getDrawCode() {
        return "";
    }

    @Override
    public String getFillCode() {
        return "g.drawString(\"" + this.text.replaceAll("\"", "\\\"") + "\", " + this.x + ", " + this.y + ");";
    }

    @Override
    public String getListText() {
        return "String (\"" + this.text + "\", " + this.x + ", " + this.y + ")";
    }

    @Override
    public void paintTo(Graphics2D g) {
        g.setColor(this.color);
        g.setFont(this.font);
        g.drawString(this.text, this.x, this.y);
    }

    @Override
    public String toOutput() {
        return "layer STRING" + PaintIO.NEWLINE + this.x + PaintIO.NEWLINE + this.y + PaintIO.NEWLINE + this.text + PaintIO.NEWLINE + PaintIO.colorToString(this.color) + PaintIO.NEWLINE + PaintIO.fontToString(this.font);
    }
}
