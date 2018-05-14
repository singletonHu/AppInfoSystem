package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper 
{
	
	/**
	 * 获取APP信息列表
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @param devId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<AppInfo> getAppInfoList(@Param("softwareName")String softwareName, @Param("status") Integer status
													, @Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2
													, @Param("categoryLevel3") Integer categoryLevel3, @Param("flatformId") Integer flatformId
													, @Param("devId") Integer devId, @Param("pageNo") Integer pageNo
													, @Param("pageSize") Integer pageSize);
	
	/**
	 * 获取APP信息记录数
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @param devId
	 * @param pageNo
	 * @return
	 */
	public int getAppInfoCount(@Param("softwareName")String softwareName, @Param("status") Integer status
												, @Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2
												, @Param("categoryLevel3") Integer categoryLevel3, @Param("flatformId") Integer flatformId
												, @Param("devId") Integer devId);
	
	/**
	 * 验证APKName是否唯一
	 * @param APKName
	 * @return
	 */
	public int checkAPKName(@Param("APKName")String APKName);
	
	/**
	 * 添加app基本信息
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo);
	
	/**
	 * 根据id获取app信息
	 * @param id
	 * @return
	 */
	public AppInfo getAppInfoById(@Param("id")Integer id);
	
/*	*//**
	 * 修改app信息
	 * @param appInfo
	 * @return
	 */
	public int updateAppInfo(AppInfo appInfo);
	
	/**
	 * 根据id删除Logo文件
	 * @param id
	 * @return
	 */
	public int deleteLogoById(@Param("id")Integer id);
	
	/**
	 * 根据id获取APKName
	 * @param id
	 * @return
	 */
	public String getAPKNameById(@Param("id")Integer id);
	
	/**
	 * 根据id获取记录数
	 * @param id
	 * @return
	 */
	public int getAppInfoCountById(@Param("id") Integer id);
	
	/**
	 * 根据id删除app信息
	 * @param id
	 * @return
	 */
	public int delAppInfoById(@Param("id")Integer id);
	
	/**
	 * app上架
	 * @param appInfo
	 * @return
	 */
	public int appInfoSaleById(AppInfo appInfo);
	
	/**
	 * app审核
	 * @param appInfo
	 * @return
	 */
	public int appInfoCheckStatus(AppInfo appInfo);
}