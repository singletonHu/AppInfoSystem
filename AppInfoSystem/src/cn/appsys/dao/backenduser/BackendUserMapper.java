package cn.appsys.dao.backenduser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface BackendUserMapper 
{

	/**
	 * 后台用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public BackendUser backendUserLogin(@Param("userCode")String userCode, @Param("userPassword")String userPassword);
}
