/*
 * Copyright (C) Audi Electronics Venture GmbH
 * All rights reserved
 */

package cn.wagentim.work.core;

public class SysConstBean implements Cloneable
{
    private String key;
    private String value;
    private String comment;
    private Long model;
    
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    public String getComment()
    {
        return comment;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    public Object clone()
    {
        SysConstBean result = new SysConstBean();
        
        result.setKey(new String(key));
        result.setValue(new String(value));
        result.setComment(new String(comment));
        
        return result;
    }
    public void setModel(Long model)
    {
        this.model = model;
    }
    public Long getModel()
    {
        return model;
    }
}
