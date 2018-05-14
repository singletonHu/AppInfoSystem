package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper 
{
	/**
	 * 根据用户编码获取用户信息
	 * @param userCode
	 * @return
	 */
	public DevUser getLoginUserByDevCode(@Param("devCode")String 	devCode);
}
