package de.eso.modelmaker.datenbank;

public class SQLStates {

	public static final String SQL_ADD_MODEL = "INSERT INTO model (guideId, name, type, comment, application, buffered, terminalDependent, type_id, iscore, ismodified) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static final String SQL_ADD_SYS_CONST_VALUES = "INSERT INTO sys_const (guideId, `key`, value, comment) "
			+ "VALUES ( ?, ?, ?, ? );";

	public static final String SQL_UPDATE_WITH_NAME_MODEL = "UPDATE model "
			+ "SET name = ( ? ), type = ( ? ), comment = ( ? ), application = ( ? ) , buffered = ( ? ), terminalDependent = ( ? ), type_id = ( ? ), iscore = ( ? ) "
			+ "WHERE guideId = ( ? );";

	public static final String SQL_UPDATE_WITH_NAME_MODEL_SDIS = "UPDATE model "
			+ "SET name = ( ? ), type = ( ? ), comment = ( ? ), application = ( ? ) , targetApplication = ( ? ) , buffered = ( ? ), terminalDependent = ( ? ), type_id = ( ? ), iscore = ( ? ) "
			+ "WHERE guideId = ( ? );";

	public static final String SQL_Delete_ALL_SYS_CONST_VALUES = "DELETE FROM sys_const WHERE guideId = ( ? ); ";

	public static final String SQL_INSERT_MODEL_PROJECT_SELECTION_INTO_MODIFIED_PROJECT = "INSERT INTO modifiedproject (model_id, project_id) VALUES ( ?, ? );";
	public static final String SQL_INSERT_MODEL_IN_MODEL_HAS_PROJECT_TABLE = "INSERT INTO model_has_project (model_id, project_id) VALUES ( ?, ? );";

	public static final String SQL_DELETE_MODEL_PROJECT_SELECTION_FROM_MODIFIED_PROJECT = "DELETE FROM modifiedproject WHERE model_id = ( ? );";

	public static final String SQL_DELETE_MODEL_HAS_PROJECT_ALL = "DELETE FROM model_has_project WHERE model_id = ( ? );";
	public static final String SQL_DELETE_MODEL_FROM_MAINTABLE = "DELETE FROM model WHERE id = ( ? );";
	public static final String SQL_DELETE_MODEL_FROM_MODIFIEDMODEL = "DELETE FROM modelmodel WHERE guideId = ( ? );";
	public static final String SQL_DELETE_MODEL_FROM_VERIFIED_PROJECTS = "DELETE FROM verifiedproject WHERE model_id = ( ? );";


	public static final String SQL_SELECT_PROJECT_FROM_MODIFIED_PROJECT = "SELECT project_id FROM modifiedproject WHERE model_id = ( ? );";

	public static final String SQL_SELECT_MODELS_ALL = "SELECT * FROM model;";

	public static final String SQL_SELECT_HISTORY_ALL = "SELECT * FROM history WHERE guideId = ( ? );";

	public static final String SQL_SELECT_SYS_CONST_ALL = "SELECT * FROM sys_const WHERE guideId = ( ? );";

	public static final String SQL_SELECT_PROJECT_IDS_BY_MODEL_ID = "SELECT project_id FROM model_has_project WHERE model_id = ( ? );";

	public static final String SQL_SELECT_PROPERTY_BY_MODEL_ID = "SELECT property_name FROM property WHERE model_id = ( ? );";

	public static final String SQL_SELECT_GUIDEID = "SELECT guideId FROM model WHERE guideId = ( ? );";

	public static final String SQL_SELECT_MODEL_ID = "SELECT id FROM model WHERE guideId = (?);";

	public static final String SQL_SELECT_GUIDEID_BY_MODELNAME = "SELECT guideId FROM model WHERE name = ( ? );";

	public static final String SQL_SELECT_TYPE_ID = "SELECT id FROM type WHERE name = (?);";

	public static final String SQL_SELECT_TYPES_NAME = "SELECT name FROM type;";

	public static final String SQL_SELECT_MODEL_BY_NAME = "SELECT * FROM model WHERE name = ( ? );";

	public static final String SQL_SELECT_MODIFICATION_BY_GUIDEID = "SELECT ismodified FROM model WHERE guideId = ( ? );";

	public static final String SQL_SELECT_PROJECT = "SELECT project FROM project WHERE id = ( ? );";

	public static final String SQL_ADD_HISTORY = "INSERT INTO history (guideId, user, comment) "
			+ "VALUES ( ?, ?, ?);";

	public static final String SQL_ADD_MODIFIED_MODEL = "INSERT INTO modifiedmodel (guideId, name, type, application, buffered, terminalDependent, type_id, iscore, modifyinfo, comment) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static final String SQL_UPDATE_MODIFY = "UPDATE model "
			+ "SET ismodified = ( ? )"
			+ "WHERE guideId = ( ? );";

	public static final String SQL_SELECT_MODEL = "SELECT * FROM model WHERE guideId = ( ? );";

	public static final String SQL_SELECT_MODIFIED_MODEL = "SELECT * FROM modifiedmodel where guideId = ( ? );";

	public static final String SQL_SELECT_MODIFIED_INFO_FROM_TEMPLATE_TABLE = "SELECT modifyinfo FROM modifiedmodel WHERE guideId = (?);";

	public static final String SQL_UPDATE_MODIFIED_MODEL = "UPDATE modifiedmodel "
			+ "SET name = ( ? ), type = ( ? ), application = ( ? ) , buffered = ( ? ), terminalDependent = ( ? ), type_id = ( ? ), iscore = ( ? ), modifyinfo = ( ? ) "
			+ "WHERE guideId = ( ? );";

	public static final String SQL_UPDATE_MAIN_TABLE_MODEL = "UPDATE model "
			+ "SET name = ( ? ), application = ( ? ) , buffered = ( ? ), terminalDependent = ( ? ), iscore = ( ? ) "
			+ "WHERE guideId = ( ? );";

	public static final String SQL_UPDATE_MODIFIED_MODEL_SDIS = "UPDATE modifiedmodel "
			+ "SET name = ( ? ), type = ( ? ), application = ( ? ) , targetApplication = ( ? ) , buffered = ( ? ), terminalDependent = ( ? ), type_id = ( ? ), iscore = ( ? ), modifyinfo = ( ? ) "
			+ "WHERE guideId = ( ? );";

	public static final String SQL_COUNT_RECORDS = "select count(*) from model;";

	public static final String 	SQL_SELECT_ALL_PROJECT = "select * from project";

	public static final String SQL_INSERT_PROJECT_IN_VERIFIED_TABLE = "INSERT INTO verifiedproject (model_id, project_id) VALUES ( ?, ? );";

	public static final String SQL_SELECT_NOT_VERIFIED_PROJECTS = "SELECT * FROM verifiedproject WHERE model_id = ( ? );";

	public static final String SQL_UPDATE_COMMENT = "UPDATE model "
			+ "SET comment = ( ? ) WHERE guideId = ( ? );";

	public static final String SQL_SELECT_ADDITIONAL_INFO = "SELECT * FROM additional;";
}
