package cn.appsys.service.appcategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService 
{
	/**
	 * 根据父级id获取分类
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId) throws Exception;
}
