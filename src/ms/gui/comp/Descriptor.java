package ms.gui.comp;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ms.trading.OrderSide;

public class Descriptor {

    private final String caption;
    private final String id;
    private final Map<String, Object> params;

    public static final String BACKGROUND_ATTR = "background";
    public static final String CONST_ATTR = "const";
    public static final String SIDE_ATTR = "side";

    public Descriptor(String caption, String id) {
        this.caption = caption;
        this.id = id;
        params = new HashMap<>();
    }

    public String getCaption() {
        return caption;
    }

    public String getId() {
        return id;
    }

    public Set<String> getParamNames() {
        return getParams().keySet();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public boolean paramExists(String attr) {
        return params.containsKey(attr);
    }

    public Descriptor addParam(String name, Object value) {
        if (value != null) {
            params.put(name, value);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(String name) {
        return (T) params.get(name);
    }

    public Boolean isConst() {
        if (!paramExists(CONST_ATTR)) {
            return false;
        }
        return getParam(CONST_ATTR);
    }

    // convenience methods for different attributes/parameters
    public Descriptor addConst(boolean constant) {
        return addParam(CONST_ATTR, constant);
    }

    public Descriptor addBackground(Color background) {
        return addParam(BACKGROUND_ATTR, background);
    }

    public Descriptor addBackground(Map<?, Color> backgrounds) {
        return addParam(BACKGROUND_ATTR, backgrounds);
    }

    public Descriptor addSide(OrderSide side) {
        return addParam(SIDE_ATTR, side);
    }

    @Override
    public String toString() {
        return "[id=" + id + ", caption='" + caption + "', params=" + params + "]";
    }
}