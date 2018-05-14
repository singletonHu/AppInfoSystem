package cn.appsys.service.appcategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService 
{

	@Resource
	private AppCategoryMapper appCategoryMapper;
	@Override
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId)
			throws Exception 
	{
		return appCategoryMapper.getAppCategoryListByParentId(parentId);
	}

}
