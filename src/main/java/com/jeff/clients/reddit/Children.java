package com.jeff.clients.reddit;

public class Children
{
    private ChildData data;

    private String kind;

    public ChildData getData ()
    {
        return data;
    }

    public void setData (ChildData data)
    {
        this.data = data;
    }

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", kind = "+kind+"]";
    }
}
