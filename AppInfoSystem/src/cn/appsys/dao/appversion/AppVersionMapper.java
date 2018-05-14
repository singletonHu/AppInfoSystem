package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper 
{
	/**
	 * 根据appId获取版本列表
	 * @param appId
	 * @return
	 */
	public List<AppVersion> getAppVersionListByAppId(@Param("appId") Integer appId);
	
	/**
	 * 添加版本信息
	 * @param appVersion
	 * @return
	 */
	public int addAppVersion(AppVersion appVersion);
	
	/**
	 * 更新app版本
	 * @param appVersion
	 * @return
	 */
	public int updateAppVersionInfo(AppVersion appVersion);
	
	/**
	 * 根据id获取版本信息
	 * @param vid
	 * @return
	 */
	public AppVersion getAppVersionByid(@Param("id")Integer vid);
	
	/**
	 * 根据id删除apk信息
	 * @param id
	 * @return
	 */
	public int deleteApkById(@Param("id")Integer id);
	
	/**
	 * 修改版本信息
	 * @param appVersion
	 * @return
	 */
	public int updateAppVersionById(AppVersion appVersion);
	
	/**
	 * 根据appId删除版本信息
	 * @param appId
	 * @return
	 */
	public int delAppVersionByAppId(@Param("appId")Integer appId);
}
