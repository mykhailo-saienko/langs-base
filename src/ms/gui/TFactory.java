package ms.gui;

import ms.utils.KWs;

public interface TFactory {

	void terminate();

	void init();

	void parseTag(String tag, KWs<String> values);

	void endParseTag();
}
