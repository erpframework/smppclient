package org.bulatnig.smpp.pdu;

import org.bulatnig.smpp.pdu.tlv.ParameterTag;
import org.bulatnig.smpp.pdu.tlv.ScInterfaceVersion;
import org.bulatnig.smpp.pdu.tlv.TLV;
import org.bulatnig.smpp.pdu.tlv.TLVException;
import org.bulatnig.smpp.util.SMPPByteBuffer;
import org.bulatnig.smpp.util.WrongLengthException;

import java.util.List;

/**
 * BindTransceiver Response PDU.
 *
 * @author Bulat Nigmatullin
 * @see BindTransceiver
 */
public class BindTransceiverResp extends PDU {

    /**
     * Максимальная длина systemId поля.
     */
    private static final int MAX_SYSTEMID_LENGTH = 16;

    /**
     * SMSC identifier. Identifies the SMSC to the ESME.
     */
    private String systemId;
    /**
     * SMPP version supported by SMSC.
     */
    private ScInterfaceVersion scInterfaceVersion;

    /**
     * Constructor.
     */
    public BindTransceiverResp() {
        super(CommandId.BIND_TRANSCEIVER_RESP);
    }

    /**
     * Constructor.
     *
     * @param bytes байткод PDU
     * @throws PDUException ошибка обработки PDU
     */
    public BindTransceiverResp(final byte[] bytes) throws PDUException {
        super(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final byte[] getBodyBytes() throws PDUException {
        if (getCommandStatus() == CommandStatus.ESME_ROK) {
            SMPPByteBuffer bb = new SMPPByteBuffer();
            if (systemId != null && systemId.length() > MAX_SYSTEMID_LENGTH) {
                throw new PDUException("systemId field is too long");
            }
            bb.appendCString(systemId);
            if (scInterfaceVersion != null) {
                try {
                    bb.appendBytes(scInterfaceVersion.getBytes(), scInterfaceVersion
                            .getBytes().length);
                } catch (TLVException e) {
                    throw new PDUException("TLVs parsing failed", e);
                }
            }
            return bb.getBuffer();
        } else {
            return new byte[0];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void parseBody(final byte[] bytes)
            throws PDUException {
        if (getCommandId() != CommandId.BIND_TRANSCEIVER_RESP) {
            throw new ClassCastException();
        }
        if (getCommandStatus() == CommandStatus.ESME_ROK) {
            SMPPByteBuffer bb = new SMPPByteBuffer(bytes);
            try {
                systemId = bb.removeCString();
            } catch (WrongLengthException e) {
                throw new PDUException("PDU parsing error", e);
            }
            if (systemId.length() > MAX_SYSTEMID_LENGTH) {
                throw new PDUException("systemId field is too long");
            }
            if (bb.length() > 0) {
                    List<TLV> list = getOptionalParams(bb.getBuffer());
                    for (TLV tlv : list) {
                        if (tlv.getTag() == ParameterTag.SC_INTERFACE_VERSION) {
                            scInterfaceVersion = (ScInterfaceVersion) tlv;
                        }
                    }
            }
        }
    }

    /**
     * @return SMSC identifier
     */
    public final String getSystemId() {
        return systemId;
    }

    /**
     * @param systemId SMSC identifier
     */
    public final void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return SMPP version supported by SMSC
     */
    public final ScInterfaceVersion getScInterfaceVersion() {
        return scInterfaceVersion;
    }

    /**
     * @param scInterfaceVersionVal SMPP version supported by SMSC
     */
    public final void setScInterfaceVersion(
            final ScInterfaceVersion scInterfaceVersionVal) {
        scInterfaceVersion = scInterfaceVersionVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return getClass().getName() + " Object {" + "\nsystemId : " + systemId
                + "\nscInterfaceVersion : " + scInterfaceVersion + "\n}";
    }

}