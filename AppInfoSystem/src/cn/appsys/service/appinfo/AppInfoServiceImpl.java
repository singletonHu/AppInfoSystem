package cn.appsys.service.appinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService 
{

	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Override
	public List<AppInfo> getAppInfoList(String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer devId,
			Integer pageNo, Integer pageSize)throws Exception
	{
		return appInfoMapper.getAppInfoList(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId, (pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public int getAppInfoCount(String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer devId) throws Exception
	{
		return appInfoMapper.getAppInfoCount(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId);
	}

	@Override
	public int checkAPKName(String APKName) throws Exception 
	{
		return appInfoMapper.checkAPKName(APKName);
	}

	@Override
	public int addAppInfo(AppInfo appInfo) throws Exception 
	{
		return appInfoMapper.addAppInfo(appInfo);
	}

	@Override
	public AppInfo getAppInfoById(Integer id) 
	{
		return appInfoMapper.getAppInfoById(id);
	}

	@Override
	public int updateAppInfo(AppInfo appInfo) 
	{
		return appInfoMapper.updateAppInfo(appInfo);
	}

	@Override
	public int deleteLogoById(Integer id) 
	{
		return appInfoMapper.deleteLogoById(id);
	}

	@Override
	public String getAPKNameById(Integer id) throws Exception 
	{
		return appInfoMapper.getAPKNameById(id);
	}

	@Override
	public Integer getAppInfoCountById(Integer id) throws Exception 
	{
		return appInfoMapper.getAppInfoCountById(id);
	}

	@Override
	public int delAppInfoById(Integer id) throws Exception 
	{
		return appInfoMapper.delAppInfoById(id);
	}

	@Override
	public int appInfoSaleById(AppInfo appInfo) throws Exception 
	{
		return appInfoMapper.appInfoSaleById(appInfo);
	}

	@Override
	public int appInfoCheckStatus(AppInfo appInfo) throws Exception 
	{
		return appInfoMapper.appInfoCheckStatus(appInfo);
	}

}
