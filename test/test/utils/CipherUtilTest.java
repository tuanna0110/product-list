package test.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import utils.CipherUtil;

public class CipherUtilTest {
	@Test
	public void testFindByLoginInfo() {
		assertEquals(new String(
				"45347181E1662328984B8A80B470BD4EA9305F424719A5470717E822AC2E0E99DEA18D1BFF3C192B305794B44B6F2B6FB8D96BBC5590DB6F49F5CD7A5B8FB197")
						.toLowerCase(),
				CipherUtil.getSha512("test23"));
		assertEquals(new String(
				"7FCF4BA391C48784EDDE599889D6E3F1E47A27DB36ECC050CC92F259BFAC38AFAD2C68A1AE804D77075E8FB722503F3ECA2B2C1006EE6F6C7B7628CB45FFFD1D")
						.toLowerCase(),
				CipherUtil.getSha512("admin123"));
		assertEquals(new String(
				"78DC20C6E141A3688DD406C1E6F906A6B8256417DB4059B41D09E16776B7AAA6913432D68215BC0AE88302564F24CFA7D1C670B8968AF4FE4A033D5901C6D809")
						.toLowerCase(),
				CipherUtil.getSha512("hello123"));
		assertEquals(new String(
				"8160A9B4333F56F0EE76EC5F4D6E694DB2D8161AEEBC82C9547BB4119B50935164E6A34393037067285A922ED48E12B7C1730B7E6E0173F59DB7BDBE52F88674")
						.toLowerCase(),
				CipherUtil.getSha512("good123"));
		assertEquals(new String(
				"D9E6762DD1C8EAF6D61B3C6192FC408D4D6D5F1176D0C29169BC24E71C3F274AD27FCD5811B313D681F7E55EC02D73D499C95455B6B5BB503ACF574FBA8FFE85")
						.toLowerCase(),
				CipherUtil.getSha512("123456789"));

	}
}