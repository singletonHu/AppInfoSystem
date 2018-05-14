package cn.appsys.service.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService 
{
	/**
	 * 根据用户编码获取用户信息
	 * @param userCode
	 * @return
	 */
	public DevUser getLoginUserByDevCode(String 	devCode, String password);
}
