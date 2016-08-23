package test.models;

import org.junit.*;

import models.Product;
import play.test.WithApplication;
import test.utils.TestUtil;

import static org.junit.Assert.*;

import java.io.IOException;

public class ProductTest extends WithApplication {

	private static String testdataFile = "producttest.sql";
	
	@BeforeClass
	public static void start() throws IOException {
		TestUtil.createTestDatabase(testdataFile);
	}

	@AfterClass
	public static void end() {
		TestUtil.dropTestDatabase(testdataFile);
	}
	
	@Test
	public void testSearchWithText() {
		assertEquals(7, Product.search("テスト", 100, 700, 1, 10, 0).size());		
		assertEquals(6, Product.search("テスト　テスト", 100, 700, 1, 10, 0).size());
		assertEquals(5, Product.search("テスト　テスト　テスト", 100, 700, 1, 10, 0).size());
		assertEquals(4, Product.search("テスト　テスト　テスト　テスト", 100, 700, 1, 10, 0).size());
		assertEquals(3, Product.search("テスト　テスト　テスト　テスト　テスト", 100, 700, 1, 10, 0).size());
		assertEquals(2, Product.search("テスト　テスト　テスト　テスト　テスト　テスト", 100, 700, 1, 10, 0).size());
		assertEquals(1, Product.search("テスト　テスト　テスト　テスト　テスト　テスト　テスト", 100, 700, 1, 10, 0).size());
		assertTrue(Product.search("テスト　テスト　テスト　テスト　テスト　テスト　テスト　テスト", 0, 700, 1, 10, 0).isEmpty());
	}
	
	@Test
	public void testSearchWithPrice() {
		assertEquals(7, Product.search("テスト", 100, 700, 1, 10, 0).size());
		assertEquals(6, Product.search("テスト", 200, 700, 1, 10, 0).size());
		assertEquals(5, Product.search("テスト", 300, 700, 1, 10, 0).size());
		assertEquals(4, Product.search("テスト", 400, 700, 1, 10, 0).size());
		assertEquals(3, Product.search("テスト", 500, 700, 1, 10, 0).size());
		assertEquals(2, Product.search("テスト", 600, 700, 1, 10, 0).size());
		assertEquals(1, Product.search("テスト", 700, 700, 1, 10, 0).size());
		assertEquals(0, Product.search("テスト", 800, 700, 1, 10, 0).size());
	}
	
	@Test
	public void testSearchWithOffsetLimit() {
		assertEquals(2, Product.search("テスト", 100, 700, 1, 2, 1).size());
		assertEquals("テスト　テスト", Product.search("テスト", 100, 700, 1, 2, 1).get(0).getTitle());
		
		assertEquals(3, Product.search("テスト", 100, 700, 1, 3, 3).size());
		assertEquals("テスト　テスト　テスト　テスト", Product.search("テスト", 100, 700, 1, 3, 3).get(0).getTitle());
	}
	
	@Test
	public void testSearchWithOrder() {
		assertEquals(7, Product.search("テスト", 100, 700, 1, 10, 0).size());
		assertEquals("テスト", Product.search("テスト", 100, 700, 1, 10, 0).get(0).getTitle());
		
		assertEquals(7, Product.search("テスト", 100, 700, -1, 10, 0).size());
		assertEquals("テスト　テスト　テスト　テスト　テスト　テスト　テスト", Product.search("テスト", 100, 700, -1, 10, 0).get(0).getTitle());
		
		assertEquals(7, Product.search("テスト", 100, 700, 2, 10, 0).size());
		assertEquals(100, Product.search("テスト", 100, 700, 2, 10, 0).get(0).getPrice().intValue());
		
		assertEquals(7, Product.search("テスト", 100, 700, -2, 10, 0).size());
		assertEquals(700, Product.search("テスト", 100, 700, -2, 10, 0).get(0).getPrice().intValue());
	}
	
	@Test
	public void testCount() {
		assertEquals(7, Product.count("テスト", 100, 700));
		assertEquals(2, Product.search("テスト", 600, 700, 2, 10, 0).size());
		assertEquals(0, Product.search("テスト", 800, 700, 2, 10, 0).size());
	}
}