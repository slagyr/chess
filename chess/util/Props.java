package chess.util;

import java.util.*;
import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

public class Props
{
    private static String[] filenames = {"chess.properties", "values.properties"};
    private static HashMap propertiesMap = new HashMap();

    static
    {
        for(int i = 0; i < filenames.length; i++)
        {
            try
            {
                Properties tempProps = new Properties();
                File file = findFileInClasspath(filenames[i]);
                tempProps.load(new FileInputStream(file));
                propertiesMap.put(filenames[i], tempProps);
            }
            catch(Exception e)
            {
                System.err.println("Error loading properties: " + filenames[i] + "\n" + e);
            }
        }
    }

	public static String get(String name)
	{
		for(int i = 0; i < filenames.length; i++)
        {
            Properties p = (Properties)propertiesMap.get(filenames[i]);
            if(p.containsKey(name))
                return p.getProperty(name);
        }
        return null;
	}

    public static String get(String name, String filename)
	{
		Properties p = (Properties)propertiesMap.get(filenames);
        if(p != null && p.containsKey(filename))
            return (String)p.get(name);
        else
            return null;
	}

	public static void set(String name, String value, String filename)
	{
		Properties p = (Properties)propertiesMap.get(filename);
        if(p != null)
            p.setProperty(name, value);
        else
            System.err.println("non-existant properties file: " + filename);
	}

    public static boolean hasProperties(String filename)
    {
        return propertiesMap.containsKey(filename);
    }

    public static Properties getProperties(String filename)
    {
        return (Properties)propertiesMap.get(filename);
    }

    public static File findFileInClasspath(String filename)
	{
		String classpath = System.getProperty("java.class.path");
		StringTokenizer tokens = new StringTokenizer(classpath, System.getProperty("path.separator"));
		File file = null;
		while (tokens.hasMoreTokens())
		{
			String path = tokens.nextToken();
			file = new File(path + "/" + filename);
			if(file.canRead())
				break;
		}
		return file;
	}
}
