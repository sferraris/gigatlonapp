package com.example.gigatlon.ui.extended_routine;

import java.util.List;

public class Cycle {

    private String ParentItemTitle;
    private List<String> ChildItemList;

    public Cycle(
            String ParentItemTitle,
            List<String> ChildItemList)
    {

        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }

    // Getter and Setter methods
    // for each parameter
    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(
            String parentItemTitle)
    {
        ParentItemTitle = parentItemTitle;
    }

    public List<String> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(
            List<String> childItemList)
    {
        ChildItemList = childItemList;
    }
}
