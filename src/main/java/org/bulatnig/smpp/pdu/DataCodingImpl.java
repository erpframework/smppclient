package org.bulatnig.smpp.pdu;

import java.util.HashMap;
import java.util.Map;

/**
 * Comment here.
 * <p/>
 * User: Bulat Nigmatullin
 * Date: 04.05.2009
 * Time: 10:40:51
 */
public class DataCodingImpl implements DataCodingStrategy {
    
    private static final String ASCII = "US-ASCII";
    private static final String LATIN1 = "ISO-8859-1";
    private static final String JIS = "JIS-C-6226";
    private static final String CYRYLIC = "ISO-8859-5";
    private static final String HEBREW = "ISO-8859-8";
    private static final String UCS2 = "UTF-16BE";
    private static final String ISO_2022_JP = "ISO-2022-JP";

    private final Map<Short, String> charsetMap = new HashMap<Short, String>();

    public DataCodingImpl() {
        charsetMap.put((short) 0, ASCII);
        charsetMap.put((short) 3, LATIN1);
        charsetMap.put((short) 5, JIS);
        charsetMap.put((short) 6, CYRYLIC);
        charsetMap.put((short) 7, HEBREW);
        charsetMap.put((short) 8, UCS2);
        charsetMap.put((short) 10, ISO_2022_JP);
    }

    @Override
    public String getCharsetName(short dataCoding) {
        String result = charsetMap.get(dataCoding);
        return result != null ? result : ASCII;
    }
}