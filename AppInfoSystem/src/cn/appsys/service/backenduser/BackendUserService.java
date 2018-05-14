package cn.appsys.service.backenduser;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService 
{
	/**
	 * 后台用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public BackendUser backendUserLogin(String userCode, String userPassword);
}
