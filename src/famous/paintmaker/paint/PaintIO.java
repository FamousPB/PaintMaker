package famous.paintmaker.paint;

import famous.paintmaker.ui.ToolboxFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaintIO {

    private static final int CIRCLE = 3;
    private static final int IMAGE = 6;
    private static final Pattern LAYERTYPE = Pattern.compile("([A-Z]+)");
    private static final int LINE = 4;
    public static String NEWLINE = "\r\n";

    private static final Pattern NUMBER = Pattern.compile("(-?[0-9]+)");
    private static final int POLY = 5;
    private static final int RECT = 1;
    private static final int RRECT = 2;
    private static final String[] TAGS = {"STRING", "RECTANGLE", "ROUNDRECT", "CIRCLE", "LINE", "POLYGON", "IMAGE"};
    private static final int TEXT = 0;

    public static String colorToString(Color c) {
        if (c == null) {
            return "NO COLOR";
        }
        return c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + c.getAlpha();
    }

    public static String fontToString(Font f) {
        return f.getName() + NEWLINE + f.getSize() + NEWLINE + f.getStyle();
    }

    public static String intArrayToString(int[] ints) {
        String text = "";
        for (int i = 0; i < ints.length; i++) {
            text = text + ints[i];
            if (i < ints.length - 1) {
                text = text + ",";
            }
        }
        return text;
    }

    public static ArrayList<Paintable> open(File f, ToolboxFrame parent) {
        ArrayList<Paintable> layers = new ArrayList();
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));

            ArrayList<String> lines = new ArrayList();
            ArrayList<Integer> shadowIndexes = new ArrayList();
            ArrayList<Integer> indexesWithShadow = new ArrayList();
            int type = -1;
            int count = 0;
            boolean firstLine = true;
            String line;
            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    if (line.equals("AA ALL")) {
                        parent.aaAllOp.setSelected(true);
                    } else if (line.equals("AA TEXT")) {
                        parent.aaTextOp.setSelected(true);
                    }
                }
                if (line.startsWith("layer ")) {
                    count++;
                    if (lines.size() > 0) {
                        layers.add(parse(lines, type));
                    }
                    lines.clear();
                    String tag = line.substring(6);
                    for (type = 0; type < TAGS.length; type++) {
                        if (TAGS[type].equals(tag)) {
                            break;
                        }
                    }
                } else if (line.startsWith("SHADOW ")) {
                    indexesWithShadow.add(count - 1);
                    shadowIndexes.add(new Integer(line.substring(7)));
                } else if (!firstLine) {
                    lines.add(line);
                }
                firstLine = false;
            }
            if (lines.size() > 0) {
                layers.add(parse(lines, type));
            }
            in.close();
            for (int i = 0; i < shadowIndexes.size(); i++) {
                ((Paintable) layers.get((indexesWithShadow.get(i)))).shadow = ((Paintable) layers.get((shadowIndexes.get(i))));
            }
        } catch (IOException e) {
            return null;
        }
        return layers;
    }

    public static Paintable parse(ArrayList<String> lines, int type) {
        switch (type) {
            case 0:
                return new PString((String) lines.get(2), toInt((String) lines.get(0)), toInt((String) lines.get(1)), stringToColor((String) lines.get(3)), new Font((String) lines.get(4), toInt((String) lines.get(6)), toInt((String) lines.get(5))));
            case 1:
                return new PRectangle(toInt((String) lines.get(0)), toInt((String) lines.get(1)), toInt((String) lines.get(2)), toInt((String) lines.get(3)), stringToColor((String) lines.get(4)), stringToColor((String) lines.get(5)), strokeFrom((String) lines.get(6)));
            case 2:
                return new PRoundRect(toInt((String) lines.get(0)), toInt((String) lines.get(1)), toInt((String) lines.get(2)), toInt((String) lines.get(3)), toInt((String) lines.get(4)), toInt((String) lines.get(5)), stringToColor((String) lines.get(6)), stringToColor((String) lines.get(7)), strokeFrom((String) lines.get(8)));
            case 3:
                return new PCircle(toInt((String) lines.get(0)), toInt((String) lines.get(1)), toInt((String) lines.get(2)), toInt((String) lines.get(3)), stringToColor((String) lines.get(4)), stringToColor((String) lines.get(5)), strokeFrom((String) lines.get(6)));
            case 4:
                return new PLine(toInt((String) lines.get(0)), toInt((String) lines.get(1)), toInt((String) lines.get(2)), toInt((String) lines.get(3)), stringToColor((String) lines.get(4)), strokeFrom((String) lines.get(5)));
            case 5:
                return new PPolygon(stringToIntArray((String) lines.get(0)), stringToIntArray((String) lines.get(1)), stringToColor((String) lines.get(2)), stringToColor((String) lines.get(3)), strokeFrom((String) lines.get(4)));
            case 6:
                return new PImage((String) lines.get(0), toInt((String) lines.get(1)), toInt((String) lines.get(2)));
        }
        return null;
    }

    public static boolean saveLayersAs(ArrayList<Paintable> layers, File f, ToolboxFrame parent) {
        String output = "";
        if (parent.aaAllOp.isSelected()) {
            output = output + "AA ALL" + NEWLINE;
        } else if (parent.aaTextOp.isSelected()) {
            output = output + "AA TEXT" + NEWLINE;
        }
        for (int i = 0; i < layers.size(); i++) {
            output = output + ((Paintable) layers.get(i)).toOutput();
            if (((Paintable) layers.get(i)).shadow != null) {
                int index = layers.indexOf(((Paintable) layers.get(i)).shadow);
                if (index >= 0) {
                    output = output + NEWLINE + "SHADOW " + index;
                }
            }
            if (i < layers.size() - 1) {
                output = output + NEWLINE;
            }
        }
        try {
            FileWriter out = new FileWriter(f);
            out.write(output);
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static Color stringToColor(String text) {
        if (text.equals("NO COLOR")) {
            return null;
        }
        int[] values = stringToIntArray(text);
        if (values.length < 4) {
            return null;
        }
        return new Color(values[0], values[1], values[2], values[3]);
    }

    public static int[] stringToIntArray(String text) {
        ArrayList<String> intsAsStrings = new ArrayList();
        Matcher m = NUMBER.matcher(text);
        while (m.find()) {
            intsAsStrings.add(m.group(1));
        }
        int[] ints = new int[intsAsStrings.size()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = toInt((String) intsAsStrings.get(i));
        }
        return ints;
    }

    public static BasicStroke strokeFrom(String text) {
        if (text.equals("NO STROKE")) {
            return null;
        }
        return new BasicStroke(toInt(text));
    }

    public static String strokeToString(BasicStroke bs) {
        if (bs == null) {
            return "NO STROKE";
        }
        return (int) bs.getLineWidth() + "";
    }

    public static int toInt(String text) {
        return Integer.parseInt(text);
    }
}
