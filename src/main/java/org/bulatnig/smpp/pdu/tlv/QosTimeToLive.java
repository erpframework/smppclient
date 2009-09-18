package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.util.SMPPByteBuffer;
import org.bulatnig.smpp.util.WrongLengthException;
import org.bulatnig.smpp.util.WrongParameterException;
import org.bulatnig.smpp.pdu.EsmClass;

/**
 * This parameter defines the number of seconds which the sender requests the
 * SMSC to keep the message if undelivered before it is deemed expired and not
 * worth delivering. If the parameter is not present, the SMSC may apply a
 * default value.
 * 
 * @author Bulat Nigmatullin
 * 
 */
public class QosTimeToLive extends TLV {
	/**
	 * Длина значения параметра.
	 */
	private static final int LENGTH = 4;
	/**
	 * Значение параметра.
	 */
	private long value;

	/**
	 * Constructor.
	 * 
	 * @param qttl	значение параметра
	 */
	public QosTimeToLive(final long qttl) {
		super(ParameterTag.QOS_TIME_TO_LIVE);
		value = qttl;
	}

	/**
	 * Constructor.
	 * 
	 * @param bytes							bytecode of TLV
     * @throws TLVException ошибка разбора TLV
	 */
	public QosTimeToLive(final byte[] bytes) throws TLVException {
		super(bytes);
	}

    @Override
    protected void parseValue(byte[] bytes, final EsmClass esmClass, final short dataCoding) throws TLVException {
		if (getTag() != ParameterTag.QOS_TIME_TO_LIVE) {
			throw new ClassCastException();
		}
		if (bytes.length == LENGTH) {
            try {
                value = new SMPPByteBuffer(bytes).removeInt();
            } catch (WrongLengthException e) {
                throw new TLVException("Buffer error during parsing value", e);
            }
		} else {
            throw new TLVException("Value has wrong length: " + bytes.length + " but expected " + LENGTH);
        }
    }

    @Override
    protected byte[] getValueBytes(final EsmClass esmClass, final short dataCoding) throws TLVException {
        SMPPByteBuffer sbb = new SMPPByteBuffer();
        try {
            sbb.appendInt(value);
        } catch (WrongParameterException e) {
            throw new TLVException("Buffer error during parsing value", e);
        }
        return sbb.getBuffer();
    }

    /**
	 * @return	значение параметра
	 */
	public final long getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return getClass().getName() + " Object {" + "\nvalue : " + value
				+ "\n}";
	}

}