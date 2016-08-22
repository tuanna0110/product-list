package test.models;

import org.junit.*;

import models.User;
import play.test.WithApplication;
import test.utils.TestUtil;

import static org.junit.Assert.*;

import java.io.IOException;

public class UserTest extends WithApplication {

	private static String testdataFile = "usertest.sql";
	
	@BeforeClass
	public static void start() throws IOException {
		TestUtil.createTestDatabase(testdataFile);
	}

	@AfterClass
	public static void end() {
		TestUtil.dropTestDatabase(testdataFile);
	}
	
	@Test
	public void testFindByLoginInfo() {
		assertNull(User.findByLoginInfo("test", "test"));
		assertNotNull(User.findByLoginInfo("admin", "admin123"));

	}

	@Test
	public void testFindByAuthToken() {
		assertNull(User.findByAuthToken(null));
		assertNull(User.findByAuthToken("test"));
		User user = User.findByLoginInfo("admin", "admin123");
		String token = user.createToken();
		assertNotNull(User.findByAuthToken(token));
	}
}
