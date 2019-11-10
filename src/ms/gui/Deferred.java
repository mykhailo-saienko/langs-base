package ms.gui;

import java.util.List;

import ms.ipp.base.KeyValue;

public interface Deferred<T> {
	List<KeyValue<T, Object>> getContent();
}
