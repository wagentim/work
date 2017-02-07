package cn.wagentim.work.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Utility 
{
	private static final Properties properties = new Properties();

	private static File file = null;
		
	public static final int MODEL_NAME = 0;
	public static final int MODEL_TYPE = 1;
	public static final int MODEL_MODULE = 2;

	public static final String localSettingDir = System.getProperty("user.home")
			+ System.getProperty("file.separator")	+ "modelmaker";

	public static final String localFileName = "localSetting.properties";
	
//
//	public static synchronized boolean saveSettings(final Settings settings) throws IOException 
//	{
//		if (Controller.onlyGlobalSettings|| (settings == null)) 
//		{
//			return false;
//		}
//		
//		properties.setProperty(Constants.PROPERTY_DB_ADDRESS, settings.getDBAddress());
//		properties.setProperty(Constants.PROPERTY_DB_DRIVER, settings.getDBDriver());
//		properties.setProperty(Constants.PROPERTY_DB_PORT, "" + settings.getDBPort());
//		properties.setProperty(Constants.PROPERTY_LOGIN_NAME, settings.getLoginName());
//		properties.setProperty(Constants.PROPERTY_LOGIN_PASSWORD, settings.getLoginPassword());
//		properties.setProperty(Constants.PROPERTY_DB_NAME, settings.getDBName());
//		properties.setProperty(Constants.PROPERTY_DB_NAME_SDIS, settings.getDBNameSDIS());
//		properties.setProperty(Constants.PROPERTY_DB_PROTOCOL, settings.getDBProtocol());
//		properties.setProperty(Constants.PROPERTY_DB_PARAMETER, settings.getDBParameter());
//		properties.setProperty(Constants.PROPERTY_MODULES, arrayToString(settings.getModules()));
//		properties.setProperty(Constants.PROPERTY_MODULES_SDIS, arrayToString(settings.getModulesSDIS()));
//		properties.setProperty(Constants.PROPERTY_TARGET_MODULES_SDIS, arrayToString(settings.getTargetModulesSDIS()));
//		properties.setProperty(Constants.PROPERTY_MODEL_TYPE_SDIS, arrayToString(settings.getModelTypesSDIS()));
////		properties.setProperty(Constants.START_BLOCK_TIME, "" + settings.getStartBlockTime());
////		properties.setProperty(Constants.END_BLOCK_TIME, "" + settings.getEndBlockTime());
////		properties.setProperty(Constants.BLOCK_TIME_TYPE, "" + settings.getBlockTimeType());
////		properties.setProperty(Constants.BLOCK_TIME_ADDITIONAL, "" + settings.getBlockTimeAdditionInfo().toString());
//
//		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//
//		properties.store(new FileOutputStream(file), dateFormat.format(new Date()));
//
//		return true;
//	}

	public static ArrayList<String> stringToList(final String content,	final String seperator) 
	{
		ArrayList<String> result = null;

		final String[] values = content.split(seperator);

		if (values == null || values.length == 0) 
		{
			return null;
		}

		result = new ArrayList<String>();

		for (final String value : values) 
		{
			result.add(value.trim().replace(" ", ""));
		}

		return result;
	}

	public static String arrayToString(final String[] array) 
	{
		if (array == null || array.length == 0) 
		{
			return "";
		}

		final StringBuilder str = new StringBuilder();
		
		for (int i = 0; i < array.length; i++) 
		{
			if (array[i].trim().toLowerCase().equals("root")
					|| array[i].trim().toLowerCase().equals("all")) 
			{
				continue;
			}

			str.append(array[i]);
			if (i < array.length - 1) 
			{
				str.append(", ");
			}
		}

		return str.toString();
	}

//	public static synchronized Settings loadSettings() 
//	{
////		if (!Controller.onlyGlobalSettings && !loadSettingFromLocal()) 
////		{
////			createLocalSetting();
////		}
//
//		try {
//			final FileInputStream fin = /*Controller.onlyGlobalSettings ?*/ new FileInputStream(new File(Constants.SETTINGS)) /*: new FileInputStream(file)*/;
//			properties.load(fin);
//
//			fin.close();
//		} catch (final FileNotFoundException e) {
//			e.printStackTrace();
//
//			return null;
//		} catch (final IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		return assignValues(properties);
//
//	}

//	private static boolean loadSettingFromLocal() 
//	{
//		file = new File(localSettingDir, localFileName);
//
//		if (file == null || !file.exists()) 
//		{
//			return false;
//		}
//
//		return true;
//	}
//
//	private static boolean createLocalSetting() 
//	{
//		file = new File(localSettingDir);
//
//		if (file == null) 
//		{
//			return false;
//		}
//
//		if (!file.exists()) 
//		{
//			file.mkdir();
//		}
//
//		file = new File(localSettingDir, localFileName);
//
//		if (file == null) 
//		{
//			return false;
//		}
//
//		if (!file.exists()) 
//		{
//			try {
//				file.createNewFile();
//			} catch (final IOException e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
//
//		if (!copySettingFile()) 
//		{
//			return false;
//		}
//
//		return true;
//	}

//	private static boolean copySettingFile() 
//	{
//		final File defaultFile = new File(Constants.SETTINGS);
//
//		if (defaultFile == null || !defaultFile.exists()) 
//		{
//			return false;
//		}
//
//		BufferedWriter bw = null;
//		BufferedReader br = null;
//
//		try {
//			bw = new BufferedWriter(new FileWriter(file));
//			br = new BufferedReader(new FileReader(defaultFile));
//
//			String line;
//
//			while ((line = br.readLine()) != null) 
//			{
//				bw.write(line);
//				bw.write("\n");
//				bw.flush();
//			}
//
//			bw.close();
//			br.close();
//
//		} catch (final FileNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		} catch (final IOException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//
//			bw = null;
//			br = null;
//		}
//
//		return true;
//	}

//	private static Settings assignValues(final Properties properties) 
//	{
//		final Settings settings = new Settings();
//
//		settings.setDBAddress(properties.getProperty(Constants.PROPERTY_DB_ADDRESS).toString());
//		settings.setDBDriver(properties.getProperty(Constants.PROPERTY_DB_DRIVER).toString());
//		settings.setDBPort(Integer
//				.parseInt(properties.get(Constants.PROPERTY_DB_PORT).toString()));
//		settings.setLoginName(properties.get(Constants.PROPERTY_LOGIN_NAME).toString());
//		settings.setLoginPassword(properties.getProperty(Constants.PROPERTY_LOGIN_PASSWORD));
//		settings.setDBName(properties.getProperty(Constants.PROPERTY_DB_NAME));
//		settings.setDBNameSDIS(properties.getProperty(Constants.PROPERTY_DB_NAME_SDIS));
//		settings.setDBProtocol(properties.getProperty(Constants.PROPERTY_DB_PROTOCOL));
//		settings.setDBParameter(properties.getProperty(Constants.PROPERTY_DB_PARAMETER));
//		settings.setModules(properties.getProperty(Constants.PROPERTY_MODULES));
//		settings.setModulesSDIS(properties.getProperty(Constants.PROPERTY_MODULES_SDIS));
//		settings.setTargetModulesSDIS(properties.getProperty(Constants.PROPERTY_TARGET_MODULES_SDIS));
//		settings.setCanBufferedModels(properties.getProperty("BufferedModels"));
//		settings.setModelTypesSDIS(properties.getProperty(Constants.PROPERTY_MODEL_TYPE_SDIS));
////		settings.setStartBlockTime(Long.valueOf(properties.getProperty(Constants.START_BLOCK_TIME)));
////		settings.setEndBlockTime(Long.valueOf(properties.getProperty(Constants.END_BLOCK_TIME)));
////		settings.setBlockTimeType(Integer.valueOf(properties.getProperty(Constants.BLOCK_TIME_TYPE)));
////		settings.setBlockTimeAdditionInfo(new Object());
//
//		return settings;
//	}

	public static Image loadImage(final String file) 
	{
		Image image = null;

		image = new Image(Display.getCurrent(), Utility.class.getClassLoader()
				.getResourceAsStream(file));

		return image;
	}

//	public static List<ModelInstance> sortModel(final List<ModelInstance> models,
//			final int type, final boolean absend) 
//	{
//		final List<ModelInstance> tmp = models;
//		final ModelCompare compare = new ModelCompare(type, absend);
//		Collections.sort(tmp, compare);
//
//		return tmp;
//	}

//	static class ModelCompare implements Comparator<Object> 
//	{
//		private final int type;
//		private final boolean absend;
//
//		public ModelCompare(final int type, final boolean absend) 
//		{
//			this.type = type;
//			this.absend = absend;
//		}

//		@Override
//		public int compare(final Object model_one, final Object model_two) 
//		{
//			final ModelInstance m1 = (ModelInstance) model_one;
//			final ModelInstance m2 = (ModelInstance) model_two;
//
//			switch (type) 
//			{
//			case MODEL_NAME:
//				if (absend)
//					return m1.getModelName().compareTo(m2.getModelName());
//				else
//					return m2.getModelName().compareTo(m1.getModelName());
//			case MODEL_TYPE:
//				if (absend)
//					return m1.getType().compareTo(m2.getType());
//				else
//					return m2.getType().compareTo(m1.getType());
//			case MODEL_MODULE:
//				if (absend)
//					return m1.getApplication().compareTo(m2.getApplication());
//				else
//					return m2.getApplication().compareTo(m1.getApplication());
//			}
//
//			return -1;
//		}
//
//	}
	
//	public static void copytoClipboard(final String text) 
//	{
//        final Display display = Display.getCurrent();
//        final Clipboard clipboard = new Clipboard(display);
//        final TextTransfer textTransfer = TextTransfer.getInstance();
//        clipboard.setContents(new Object[] { text }, new Transfer[] { textTransfer });
//        clipboard.dispose();
//    }
//	
//	public static void checkNull(final Object o, final String text)
//	{
//		if(null == o)
//		{
//			throw new IllegalArgumentException(text);
//		}
//		
//	}
}
