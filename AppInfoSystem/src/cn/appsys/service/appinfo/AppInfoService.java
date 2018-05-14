package cn.appsys.service.appinfo;

import java.util.List;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService 
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
	public List<AppInfo> getAppInfoList(String softwareName, Integer status
													, Integer categoryLevel1,Integer categoryLevel2
													,  Integer categoryLevel3, Integer flatformId
													,  Integer devId, Integer pageNo
													, Integer pageSize) throws Exception;
	
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
	public int getAppInfoCount(String softwareName, Integer status
												, Integer categoryLevel1,  Integer categoryLevel2
												,  Integer categoryLevel3,  Integer flatformId
												,  Integer devId) throws Exception;
	
	/**
	 * 验证APKName是否唯一
	 * @param APKName
	 * @return
	 */
	public int checkAPKName(String APKName)throws Exception;
	
	/**
	 * 添加app基本信息
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo)throws Exception;
	
	/**
	 * 根据id获取app信息
	 * @param id
	 * @return
	 */
	public AppInfo getAppInfoById(Integer id);
	
	/**
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
	public int deleteLogoById(Integer id);
	
	/**
	 * 根据id获取APKName
	 * @param id
	 * @return
	 */
	public String getAPKNameById(Integer id) throws Exception;
	
	/**
	 * 根据id获取记录数
	 * @param id
	 * @return
	 */
	public Integer getAppInfoCountById(Integer id) throws Exception;
	
	/**
	 * 根据id删除app信息
	 * @param id
	 * @return
	 */
	public int delAppInfoById(Integer id) throws Exception;
	
	/**
	 * app上架
	 * @param appInfo
	 * @return
	 */
	public int appInfoSaleById(AppInfo appInfo) throws Exception;
	
	/**
	 * app审核
	 * @param appInfo
	 * @return
	 */
	public int appInfoCheckStatus(AppInfo appInfo)throws Exception;
}
