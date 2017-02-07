package de.eso.modelmaker.datenbank;

import java.sql.ResultSet;

public interface InterfaceResultSetMapper<T>
{
    public T fromStore (ResultSet rs);
}
