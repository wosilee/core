package com.dotcms.contenttype.test;
import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dotmarketing.business.web.LanguageWebAPIImpl;
import com.dotmarketing.common.db.DotConnect;
import com.dotmarketing.db.DbConnectionFactory;
import com.dotmarketing.db.test.DataSourcesForTesting;
import com.dotmarketing.db.test.TestingJndiDatasource;
import com.dotmarketing.util.Config;
import com.google.common.io.Files;



	@RunWith(Suite.class)
	@SuiteClasses({ 
		ContentTypeBuilderTest.class, 
		ContentTypeFactoryImplTest.class, 
		FieldFactoryImplTest.class, 
		FieldBuilderTest.class 
	})


	public class SuperContentTypeTest   {
		public interface Application {
			  public String myFunction(String abc);
			  
		}
	    final static String dbFile = "/Users/will/git/META-INF/context.xml";
		static boolean inited=false;
		@BeforeClass
		public static void SetUpTests() throws FileNotFoundException, Exception {
			if(inited){
				return;
			}
			inited=true;


		    new TestingJndiDatasource().init();
		    Config._setupFakeTestingContext();
		    

			DotConnect dc = new DotConnect();
			String structsToDelete = "(select inode from structure where structure.velocity_var_name like 'velocityVarNameTesting%' )";

			dc.setSQL("delete from field where structure_inode in " + structsToDelete);
			dc.loadResult();

			dc.setSQL("delete from inode where type='field' and inode not in  (select inode from field)");
			dc.loadResult();

			dc.setSQL("delete from contentlet_version_info where identifier in (select identifier from contentlet where structure_inode in "
					+ structsToDelete + ")");
			dc.loadResult();

			dc.setSQL("delete from contentlet where structure_inode in " + structsToDelete);
			dc.loadResult();

			dc.setSQL("delete from inode where type='contentlet' and inode not in  (select inode from contentlet)");
			dc.loadResult();

			dc.setSQL("delete from structure where  structure.velocity_var_name like 'velocityVarNameTesting%' ");
			dc.loadResult();

			dc.loadResult();
			dc.setSQL("delete from inode where type='structure' and inode not in  (select inode from structure)");
			dc.loadResult();

			dc.setSQL("delete from field where structure_inode not in (select inode from structure)");
			dc.loadResult();

			dc.setSQL("delete from inode where type='field' and inode not in  (select inode from field)");
			dc.loadResult();

			dc.setSQL("update structure set structure.url_map_pattern =null, structure.page_detail=null where structuretype =3");
			dc.loadResult();


		}
	}
