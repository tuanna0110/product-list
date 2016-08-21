package test.utils;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.avaje.ebean.Ebean;

import play.Application;
import play.test.*;

public class TestUtil {
	public final static String TESTDATA_PATH = "test/evolutions/";
	public final static int CREATE_DB_INDEX = 0;
	public final static int DROP_DB_INDEX = 1;
	
	public static void createTestDatabase(String fileName) {
		prepareTestDatabaseFromFile(fileName, CREATE_DB_INDEX);
	}
	
	public static void dropTestDatabase(String fileName) {
		prepareTestDatabaseFromFile(fileName, DROP_DB_INDEX);
	}
	
	private static void prepareTestDatabaseFromFile(String fileName, int index) {
		try {
			Application app = Helpers.fakeApplication();
			Helpers.start(app);
		    String evolutionContent = FileUtils.readFileToString(
		            app.getWrappedApplication().getFile(TESTDATA_PATH + fileName));
		    String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
		    String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
		    String sqls = upsDowns[index];
		    Ebean.execute(Ebean.createCallableSql(sqls));
		    Helpers.stop(app);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
