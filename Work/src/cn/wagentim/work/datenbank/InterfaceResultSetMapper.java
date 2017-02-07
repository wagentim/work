package cn.wagentim.work.datenbank;

import java.sql.ResultSet;

public interface InterfaceResultSetMapper<T>
{
    public T fromStore (ResultSet rs);
}
