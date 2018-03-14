package com.hww.base.common.page;

public class PageNo
{
    private String pageNoName;
    private Integer pageNo;
    private String url;

    public PageNo(String pageNoName, Integer pageNo, String url)
    {
        this.pageNoName = pageNoName;
        this.pageNo = pageNo;
        this.url = url;
    }
    public String getPageNoName()
    {
        return pageNoName;
    }
    public Integer getPageNo()
    {
        return pageNo;
    }
    public String getUrl()
    {
        return url;
    }
    public void setPageNoName(String pageNoName)
    {
        this.pageNoName = pageNoName;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

}
