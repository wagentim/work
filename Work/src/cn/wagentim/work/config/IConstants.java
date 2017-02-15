package cn.wagentim.work.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IConstants
{
	public static final String SETTINGS = "settings.properties";

	public static final String ICON_TITLE = "res/eso.png";
	public static final String ICON_MODULE = "res/module.png";
	public static final String ICON_CONNECT = "res/connect.png";
	public static final String ICON_CONNECT_SDIS = "res/connectSDIS.png";
	public static final String ICON_SAVE = "res/save.png";
	public static final String ICON_EXIT = "res/exit.png";
	public static final String ICON_SETTING = "res/setting.png";
	public static final String ICON_CREATE = "res/create.png";
	public static final String ICON_EDIT = "res/edit.png";
	public static final String ICON_DELETE = "res/delete.png";
	public static final String ICON_QUESTION = "res/question.png";
	public static final String ICON_ERROR = "res/error.png";
	public static final String ICON_WARN = "res/warn.png";
	public static final String ICON_MODEL = "res/model.png";
	public static final String ICON_MODELMAKER = "res/modelmaker.png";
	public static final String ICON_ABOUT = "res/about.png";
	public static final String ICON_HISTORY = "res/history.png";
	public static final String ICON_COPY_TO_BRANCH = "res/copy.png";
	public static final String ICON_MOVE_TO_MODULE = "res/moveToModule.png";
	public static final String ICON_BRANCH = "res/branch.png";
	public static final String ICON_VALUE = "res/value.png";

	public static final int DIALOG_INFO = 3;
	public static final int DIALOG_CHOICE = 4;

	public static final int DB_DEFAULT = 0;
	public static final int DB_SDIS = 1;

	public static final String STRING_KPM_ID = "KPM ID";
	public static final String STRING_SHORT_TEXT = "Short Text";
	public static final String STRING_SUPPLIER = "Supplier: ";
	public static final String STRING_PROBLEM_STATUS = "P Status: ";
	public static final String STRING_ENGINEER_STATUS = "E Status: ";
	
	
	public static final String STRING_TICKET_ID = "Ticket ID";


	public static final String DB_MUAS_FIX = "mustfix.odb";
	public static final String DB_TICKET = "tickets.odb";
	public static final String DB_TICKET_COMMENT = "ticketcomments.odb";
	public static final String DB_SVEN = "sven.odb";
	public static final String DB_SHEET = "sheet.odb";
	public static final String DB_SURFIX = ".odb";

	private static final Map<Integer,String> projectIndexToName;
	private static final Map<String,Integer> projectNameToIndex;

	public static int sdisProjectIndex = -1;

	public final static int MAX_NUMBER_OF_PROJECTS;

	private final static boolean verificationProcessRequired;

	public static final String String_SHEET_INDEX = "Sheet Index";

	public static final String String_IGNORE_LINES = "Ignore Lines";

	public static final String String_KPM_ID_INDEX = "KPM ID Index";

	public static final String String_COMMENT_INDEX = "Comment Index";

	public static final String String_STATUS_INDEX = "Status Index";

	static {
		projectIndexToName = null; //DBManager.sqlGetAllProjectIDNameMappings();

		projectNameToIndex = new HashMap<String,Integer>(projectIndexToName.size());

		MAX_NUMBER_OF_PROJECTS = projectIndexToName.size();

		for(final Integer projIndex : projectIndexToName.keySet())
		{
			final String projectName = projectIndexToName.get(projIndex).toLowerCase();

			if(projectName.contains("android") || projectName.contains("sdis") )
			{
				sdisProjectIndex = projIndex;
			}

			projectNameToIndex.put(projectIndexToName.get(projIndex), projIndex);
		}

		verificationProcessRequired = false; //(Controller.getInstance().getSettings().getDBActive() != DB_SDIS) && ( MAX_NUMBER_OF_PROJECTS - ((sdisProjectIndex == -1) ? 0 : 1)) > 1;
	}

	public static Integer getProjectIndex(final String projectName)
	{
		if(projectName == null || projectName.equalsIgnoreCase("Core"))
		{
			return null;
		}

		return projectNameToIndex.get(projectName.toLowerCase().trim());
	}

	public static String getProjectName(final int projectIndex)
	{
		return projectIndexToName.get(projectIndex);
	}

	public static List<Integer> getNonAndroidProjectIndexes()
	{
		final List<Integer> indexes = new ArrayList<Integer>(MAX_NUMBER_OF_PROJECTS);

		for(final int index : projectIndexToName.keySet())
		{
			if(index != sdisProjectIndex)
			{
				indexes.add(index);
			}
		}

		return indexes;
	}

	public static boolean hasValidProjectRelations( final List<Integer> modelProjects )
	{
		for( Integer projectIndex : modelProjects )
		{
			if( projectIndexToName.containsKey( projectIndex ) )
			{
				return true;
			}
		}

		return false;
	}

	public static boolean modelChangesMustBeVerified()
	{
		return verificationProcessRequired;
	}
}
