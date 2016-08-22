package test.utils;

import java.io.IOException;
import java.util.Arrays;

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
	
	/**
	 * Evolutionと同じフォマットがあるファイルからテストデータを作成
	 * 
	 * @param fileName
	 *            Evolutionと同じフォマットがあるファイル
	 * @param index
	 *            CREATE_DB_INDEX(0): UP分部にあるSQLを実行
	 *            DROP_DB_INDEX(1): DOWN分部にあるSQLを実行            
	 */
	private static void prepareTestDatabaseFromFile(String fileName, int index) {
		try {

			//偽のApplicationを開始する
			Application app = Helpers.fakeApplication();
			Helpers.start(app);
			
			//index変数によって、ファイルにあるUPかDOWNかのSQL分を習得する
		    String evolutionContent = FileUtils.readFileToString(
		            app.getWrappedApplication().getFile(TESTDATA_PATH + fileName));
		    String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
		    String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
		    String sqls = upsDowns[index];
		    
		    //複数のSQL分を一つ一つ実行します。必要ではないですが、Java8のラムダ式を使ってみたい。
		    Arrays.stream(sqls.split(System.getProperty("line.separator")))
		        .map((sql) -> {
		        	return sql.trim();
		        })
		    	.filter((sql) -> {
		    		return sql!= null && !sql.isEmpty();
		    	})
		    	.forEach((sql) -> {
		    		Ebean.execute(Ebean.createCallableSql(sql));
		    	});
		    
		    //偽のApplicationを中止する
		    Helpers.stop(app);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
