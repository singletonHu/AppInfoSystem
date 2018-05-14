package cn.appsys.service.appversion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService 
{

	@Resource
	private AppVersionMapper mapper;
	
	@Override
	public List<AppVersion> getAppVersionListByAppId(Integer appId)  throws Exception
	{
		return mapper.getAppVersionListByAppId(appId);
	}

	@Override
	public int addAppVersion(AppVersion appVersion) throws Exception 
	{
		return mapper.addAppVersion(appVersion);
	}

	@Override
	public int updateAppVersionInfo(AppVersion appVersion) throws Exception 
	{
		return mapper.updateAppVersionInfo(appVersion);
	}

	@Override
	public AppVersion getAppVersionByid(Integer vid) throws Exception
	{
		return mapper.getAppVersionByid(vid);
	}

	@Override
	public int deleteApkById(Integer id) throws Exception
	{
		return mapper.deleteApkById(id);
	}

	@Override
	public int updateAppVersionById(AppVersion appVersion) 
	{
		return mapper.updateAppVersionById(appVersion);
	}

	@Override
	public int delAppVersionByAppId(Integer appId) 
	{
		return mapper.delAppVersionByAppId(appId);
	}

}
