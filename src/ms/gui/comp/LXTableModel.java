package ms.gui.comp;

import static ms.ipp.Iterables.isEqualOrNull;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class LXTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -2398574547673571671L;

    private Descriptor[] columnIds;

    public LXTableModel(Descriptor[] columnIds) {
        setColumns(columnIds);
    }

    public boolean attributeExists(int colIndex, String attr) {
        return columnIds[colIndex].paramExists(attr);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(int colIndex, String attr) {
        return (T) columnIds[colIndex].getParam(attr);
    }

    public boolean isAttributeEqualTo(int colIndex, String attr, Object value) {
        if (attributeExists(colIndex, attr)) {
            return isEqualOrNull(getAttribute(colIndex, attr), value);
        } else {
            return false;
        }
    }

    @Override
    public int getColumnCount() {
        return columnIds.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Descriptor desc = columnIds[columnIndex];
        String id = desc.getId();
        if (isConstant(columnIndex)) {
            return Object.class;
        } else {
            return getColumnClass(id);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= getRowCount() || columnIndex >= getColumnCount()) {
            return null;
        }
        Descriptor desc = columnIds[columnIndex];
        String id = desc.getId();
        if (isConstant(columnIndex)) {
            return id;
        } else {
            return doGetValueAt(rowIndex, columnIndex, id);
        }
    }

    @Override
    public String getColumnName(int index) {
        return columnIds[index].getCaption();
    }

    public void setColumns(List<Descriptor> columns) {
        setColumns(columns.toArray(new Descriptor[] {}));
    }

    public void setColumns(Descriptor[] columns) {
        if (columns == null) {
            throw new IllegalArgumentException("Column descriptions are not specified");
        }

        enrich(columns);
        this.columnIds = columns;
    }

    public boolean isConstant(int index) {
        return columnIds[index].isConst();
    }

    protected int getColumnIndex(String colId) {
        for (int i = 0; i < getColumnCount(); ++i) {
            if (getColumnId(i).equals(colId)) {
                return i;
            }
        }
        return -1;
    }

    protected String getColumnId(int index) {
        return columnIds[index].getId();
    }

    protected Class<?> getColumnClass(String colId) {
        return Object.class;
    }

    /**
     * Adds those attributes from the default model which are missing in the given model
     * 
     * @param columns
     */
    private void enrich(Descriptor[] columns) {
        Descriptor[] defaultModel = getDefaultModel();
        for (Descriptor c : columns) {
            enrich(c, defaultModel);
        }
    }

    private void enrich(Descriptor c, Descriptor[] defaultModel) {
        for (Descriptor dc : defaultModel) {
            // it is the right column
            if (dc.getId().equals(c.getId()) && !dc.isConst() && !c.isConst()) {
                for (String paramName : dc.getParamNames()) {
                    if (!c.paramExists(paramName)) {
                        c.addParam(paramName, dc.getParam(paramName));
                    }
                }
                break;
            }
        }
    }

    protected abstract Object doGetValueAt(int rowIndex, int colIndex, String colId);

    public abstract Descriptor[] getDefaultModel();
}
