package famous.paintmaker.ui;

import famous.paintmaker.paint.Paintable;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JList;

public class PaintableListModel extends AbstractListModel {

    private ToolboxFrame frame;
    private ArrayList<Paintable> list;
    private JList parent;

    public PaintableListModel(JList parent, ToolboxFrame frame) {
        list = new ArrayList();
        this.parent = parent;
        this.frame = frame;
    }

    public void add(Paintable p) {
        list.add(p);
        parent.updateUI();
        frame.checkLayerTools();
    }

    public void add(int index, Paintable p) {
        list.add(index, p);
        parent.updateUI();
        frame.checkLayerTools();
    }

    public void clear() {
        list.clear();
        parent.updateUI();
        frame.checkLayerTools();
    }

    public int convert(int index) {
        return list.size() - 1 - index;
    }

    public Paintable get(int index) {
        return (Paintable) list.get(index);
    }

    public ArrayList<Paintable> getArrayList() {
        return list;
    }

    @Override
    public Object getElementAt(int index) {
        return ((Paintable) list.get(convert(index))).getListText();
    }

    public Paintable getSelected() {
        int index = getSelectedIndex();
        if (!validIndex(index)) {
            return null;
        }
        return (Paintable) list.get(index);
    }

    public int getSelectedIndex() {
        return convert(parent.getSelectedIndex());
    }

    @Override
    public int getSize() {
        return list.size();
    }

    public void insert(Paintable p) {
        int index = getSelectedIndex() + 1;
        if (validIndex(index)) {
            add(index, p);
        } else {
            add(p);
        }
    }

    public void moveSelectedDown() {
        int index = getSelectedIndex() - 1;
        if (index < 0) {
            return;
        }
        Paintable selected = removeSelected();
        if (selected != null) {
            add(index, selected);
            parent.setSelectedIndex(convert(index));
        }
    }

    public void moveSelectedUp() {
        int index = getSelectedIndex() + 1;
        if (index >= list.size()) {
            return;
        }
        Paintable selected = removeSelected();
        if (selected != null) {
            add(index, selected);
            parent.setSelectedIndex(convert(index));
        }
    }

    public void paintAllTo(Graphics2D g) {
        for (Paintable p : list) {
            p.paintTo(g);
        }
    }

    public Paintable remove(int index) {
        if (!validIndex(index)) {
            return null;
        }
        Paintable removed = (Paintable) list.remove(index);
        parent.updateUI();
        frame.checkLayerTools();
        return removed;
    }

    public Paintable removeSelected() {
        return remove(getSelectedIndex());
    }

    public boolean selectedIndexIsValid() {
        return validIndex(getSelectedIndex());
    }

    public boolean validIndex(int index) {
        return (index >= 0) && (index < list.size());
    }
}
