package cn.wagentim.work.share;

public class ModelInstanceConstants {

	public static final int MODIFY_NONE = 0;

	public static final int MODIFY_NAME = 0x01;

	public static final int MODIFY_TYPE = MODIFY_NAME << 1;

	public static final int MODIFY_BUFFERED = MODIFY_NAME << 2;

	public static final int MODIFY_TERMINAL = MODIFY_NAME << 3;

	public static final int MODIFY_PROJECT = MODIFY_NAME << 4;

	public static final int MODIFY_MODULE = MODIFY_NAME << 5;

	public static final int MODIFY_DELETE = MODIFY_NAME << 6;

	public static final int MODIFY_CORE = MODIFY_NAME << 7;

	public static final int MODIFY_NEW = MODIFY_NAME << 8;

	public static final int MODIFY_COMMENT = MODIFY_NAME << 9;

	public static final int MODIFY_SYSTEM_CONSTANT = MODIFY_NAME << 10;

	public static final String NAME_MODIFY_DELETE = "Deleted";

	public static final String NAME_MODIFY_TYPE = "Type change";

	public static final String NAME_MODIFY_PROJECT = "Project ownership";

	public static final String NAME_MODIFY_CORE = "Core change";

	public static final String NAME_MODIFY_NONE = "";

	public static final String NAME_MODIFY_ANY = "Modified (all changes)";
}
