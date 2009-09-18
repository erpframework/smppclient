package junit4.smpp.domain.tlv;

import junit.framework.JUnit4TestAdapter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.bulatnig.smpp.pdu.tlv.*;
import org.bulatnig.smpp.util.SMPPByteBuffer;
import org.bulatnig.smpp.util.WrongParameterException;

public class SourceBearerTypeTest {
	
	// Used for backward compatibility (IDEs, Ant and JUnit 3 text runner)
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(SourceBearerTypeTest.class);
	}
	
	@Test
	public void testSBTConstructor1() throws TLVException, WrongParameterException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x000F);
		bb.appendShort(0x0001);
		bb.appendByte((byte)0x05);
		SourceBearerType dbt = new SourceBearerType(bb.getBuffer());
		assertEquals(ParameterTag.SOURCE_BEARER_TYPE, dbt.getTag());
		assertEquals(5, dbt.getBytes().length);
		assertEquals(BearerType.CDPD, dbt.getValue());
		assertEquals("000f000105", new SMPPByteBuffer(dbt.getBytes()).getHexDump());
	}

    @Test(expected = TLVNotFoundException.class)
	public void testSBTConstructor2() throws TLVException, WrongParameterException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x0000);
		bb.appendShort(0x0002);
		bb.appendByte((byte)0x05);
		new SourceBearerType(bb.getBuffer());
	}
	
	@Test(expected= TLVException.class)
	public void testSBTConstructor3() throws TLVException, WrongParameterException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x000F);
		bb.appendShort(0x0001);
		bb.appendShort(0x0003);
		new SourceBearerType(bb.getBuffer());
	}
	
	@Test
	public void testSBTConstructor4() throws TLVException, WrongParameterException {
		SourceBearerType sbt = new SourceBearerType(BearerType.CDPD);
		assertEquals(ParameterTag.SOURCE_BEARER_TYPE, sbt.getTag());
		assertEquals(5, sbt.getBytes().length);
		assertEquals(BearerType.CDPD, sbt.getValue());
		assertEquals("000f000105", new SMPPByteBuffer(sbt.getBytes()).getHexDump());
	}
	
	@Test(expected= TLVException.class)
	public void testSBTConstructor5() throws TLVException, WrongParameterException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x000F);
		bb.appendShort(0x0001);
		bb.appendShort(0x0014);
		new SourceBearerType(bb.getBuffer());
	}
	
	@Test(expected=ClassCastException.class)
	public void testSBTConstructor6() throws TLVException, WrongParameterException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x0005);
		bb.appendShort(0x0001);
		bb.appendByte((byte)0x00);
		new SourceBearerType(bb.getBuffer());
	}
	
	@Test
	public void testSBTConstructor7() throws WrongParameterException, TLVException {
		SMPPByteBuffer bb = new SMPPByteBuffer();
		bb.appendShort(0x000F);
		bb.appendShort(0x0001);
		bb.appendByte((byte)0x14);
		SourceBearerType dbt = new SourceBearerType(bb.getBuffer());
		assertEquals(ParameterTag.SOURCE_BEARER_TYPE, dbt.getTag());
		assertEquals(5, dbt.getBytes().length);
		assertEquals(BearerType.RESERVED, dbt.getValue());
		assertEquals((short) 20, dbt.getIntValue());
		assertEquals("000f000114", new SMPPByteBuffer(dbt.getBytes()).getHexDump());
	}

}