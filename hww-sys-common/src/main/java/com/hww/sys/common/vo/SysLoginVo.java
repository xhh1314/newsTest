package com.hww.sys.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

public class SysLoginVo extends AbsBaseVo
{
    private String username;
    private String password;
    private String captcha;
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getCaptcha()
    {
        return captcha;
    }
    public void setCaptcha(String captcha)
    {
        this.captcha = captcha;
    }

}
