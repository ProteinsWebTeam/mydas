package uk.ac.ebi.simpledas.controller;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import uk.ac.ebi.simpledas.exceptions.ConfigurationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

/**
 * Created Using IntelliJ IDEA.
 * Date: 08-May-2007
 * Time: 14:03:06
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 */
public class ConfigXmlUnmarshallerTest extends TestCase {

    /**
	 * Define a static logger variable so that it references the
	 * Logger instance named "XMLUnmarshaller".
	 */
	private static Logger logger = Logger.getLogger(ConfigXmlUnmarshallerTest.class);

    public ConfigXmlUnmarshallerTest() {
        super();
    }

    public ConfigXmlUnmarshallerTest(String string) {
        super(string);
    }

    /**
     * This test case performs a complete test of the XML Unmarshaller, and the constructors
     * and getter methods of the
     * DataSourceConfiguration, DataSourceManager and GlobalConfiguration classes.
     *
     * After loading the test XML file, performs a comprehensive check of every object and
     * every getter for every object in the resulting configuration.
     * @throws ConfigurationException in the event of a failure in reading the XML file.
     */
    public void testUnMarshaller() throws ConfigurationException{
        ConfigXmlUnmarshaller unmarshaller = new ConfigXmlUnmarshaller();
        BufferedReader reader = null;
        try{
            ClassLoader thisClassLoader = this.getClass().getClassLoader();
            reader = new BufferedReader(
                    new InputStreamReader (thisClassLoader.getResourceAsStream("SimpleDasServerConfig.xml"))
            );
            Assert.assertNotNull("The BufferedReader for the test configuration file is null.",
                    reader);
            ServerConfiguration serverConfig = unmarshaller.unMarshall(reader);
            Assert.assertNotNull("The unmarshaller has returned a null ServerConfiguration.",
                    serverConfig);
            Assert.assertNotNull("The GlobalConfiguration is null",
                    serverConfig.getGlobalConfiguration());
            Assert.assertEquals("The baseurl is not as expected",
                    "http://www.ebi.ac.uk/das-srv/uniprot/das/",
                    serverConfig.getGlobalConfiguration().getBaseURL());
            Assert.assertTrue("The gzipped value should be true", serverConfig.getGlobalConfiguration().isGzipped());
            Assert.assertEquals("The default stylesheet is not set as expected",
                    "simpleDasStyle.style",
                    serverConfig.getGlobalConfiguration().getDefaultStyleSheet());
            Assert.assertEquals("The number of global properties is not as expected.",
                    2,
                    serverConfig.getGlobalConfiguration().getGlobalParameters().size());
            Map<String, String> globalProps = serverConfig.getGlobalConfiguration().getGlobalParameters();
            Assert.assertEquals("Missing global property",
                    globalProps.get("TestKeyGlobal1"),
                    "TestValueGlobal1");
            Assert.assertEquals("Missing global property",
                    globalProps.get("TestKeyGlobal2"),
                    "TestValueGlobal2");
            Assert.assertEquals("The number of dsns is not as expected",
                    2,
                    serverConfig.getDataSourceConfigMap().size());
            Collection<DataSourceConfiguration> dsnCollection = serverConfig.getDataSourceConfigMap().values();
            boolean found1 = false;
            boolean found2 = false;
            for (DataSourceConfiguration dsnConfig : dsnCollection){
                Assert.assertTrue("An unexpected dsn ID has been found",
                        ("dsnId1".equals (dsnConfig.getId()) || "dsnId2".equals (dsnConfig.getId())));
                if ("dsnId1".equals (dsnConfig.getId())){
                    found1 = true;
                    Assert.assertEquals("Unexpected dsn name",
                            dsnConfig.getName(),
                            "dsnName1");
                    Assert.assertEquals("Unexpected dsn version",
                            dsnConfig.getVersion(),
                            "0.1");
                    Assert.assertEquals("Unexpected mapmaster",
                            dsnConfig.getMapmaster(),
                            "mapmaster1");
                    Assert.assertEquals("Unexpected description",
                            dsnConfig.getDescription(),
                            "dsnDescription1");
                    Assert.assertEquals("Unexpected description-href",
                            dsnConfig.getDescriptionHref().toString(),
                            "http://dsnDescriptionHref1");
                    Assert.assertEquals("Unexpected stylesheet",
                            dsnConfig.getStyleSheet(),
                            "override_style");
                    Assert.assertEquals("Unexpected number of properties",
                            2,
                            dsnConfig.getDataSourceProperties().size());
                    Map<String, String> dsnProps = dsnConfig.getDataSourceProperties();
                    Assert.assertEquals("Missing dsn property",
                            dsnProps.get("dsn1key1"),
                            "dsn1value1");
                    Assert.assertEquals("Missing dsn property",
                            dsnProps.get("dsn1key2"),
                            "dsn1value2");
                }
                else if ("dsnId2".equals (dsnConfig.getId())){
                    found2 = true;
                    Assert.assertEquals("Unexpected dsn name",
                            dsnConfig.getName(),
                            "dsnName2");
                    Assert.assertEquals("Unexpected dsn version",
                            dsnConfig.getVersion(),
                            "0.2");
                    Assert.assertEquals("Unexpected mapmaster",
                            dsnConfig.getMapmaster(),
                            "mapmaster2");
                    Assert.assertEquals("Unexpected description",
                            dsnConfig.getDescription(),
                            "dsnDescription2");
                    Assert.assertEquals("Unexpected description-href",
                            dsnConfig.getDescriptionHref().toString(),
                            "http://dsnDescriptionHref2");
                    Assert.assertNull("Unexpected stylesheet",
                            dsnConfig.getStyleSheet());
                    Assert.assertEquals("Unexpected number of properties",
                            0,
                            dsnConfig.getDataSourceProperties().size());
                }
            }
            Assert.assertTrue("Did not find dsn1 definition.", found1);
            Assert.assertTrue("Did not find dsn2 definition.", found2);
        }
        finally{
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}