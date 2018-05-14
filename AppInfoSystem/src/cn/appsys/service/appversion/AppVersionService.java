package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService 
{
	/**
	 * 根据appId获取版本列表
	 * @param appId
	 * @return
	 */
	public List<AppVersion> getAppVersionListByAppId(Integer appId) throws Exception;
	
	/**
	 * 添加版本信息
	 * @param appVersion
	 * @return
	 */
	public int addAppVersion(AppVersion appVersion)throws Exception;
	
	/**
	 * 更新app版本
	 * @param appVersion
	 * @return
	 */
	public int updateAppVersionInfo(AppVersion appVersion)throws Exception;
	
	/**
	 * 根据appid和vid获取版本信息
	 * @param vid
	 * @return
	 */
	public AppVersion getAppVersionByid(Integer vid) throws Exception;
	
	/**
	 * 根据id删除apk信息
	 * @param id
	 * @return
	 */
	public int deleteApkById(Integer id) throws Exception;
	
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
	public int delAppVersionByAppId(Integer appId);
}
