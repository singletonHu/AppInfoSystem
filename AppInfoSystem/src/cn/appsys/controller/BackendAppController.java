package cn.appsys.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.service.datadictionary.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping(value = "/manager/backend/app")
public class BackendAppController 
{
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private AppVersionService appVersionService;
	
	private Logger logger = Logger.getLogger(BackendAppController.class);
	
	@RequestMapping(value = "/list")
	public String getAppInfoList(Model model, HttpSession session,
			@RequestParam(value = "querySoftwareName",required = false) String querySoftwareName,
			@RequestParam(value = "queryFlatformId",required = false) String _queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1",required = false) String _queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2",required = false) String _queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3",required = false) String _queryCategoryLevel3,
			@RequestParam(value = "pageIndex",required = false) String _pageIndex)
	{
		logger.info("getAppInfoList ===========>：" + querySoftwareName);
		logger.info("getAppInfoList ===========>：" + _queryFlatformId);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel1);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel2);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel3);
		logger.info("getAppInfoList ===========>：" + _pageIndex);
		
		List<AppInfo> appInfoList = null ;
		List<DataDictionary> flatformList = null;
		
		// 一级分类列表，二级、三级通过ajax获取
		List<AppCategory> categoryLevel1List = null;
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		
		// 页面容量
		int pageSize = Constants.pageSize;
		
		Integer currentPageNo = 1;
		if (_pageIndex != null)
		{
			currentPageNo = Integer.parseInt(_pageIndex);
		}
		Integer queryFlatformId = null;
		if (_queryFlatformId != null && !"".equals(_queryFlatformId))
		{
			queryFlatformId = Integer.parseInt(_queryFlatformId);
		}
		Integer queryCategoryLevel1 = null;
		if (_queryCategoryLevel1 != null && !"".equals(_queryCategoryLevel1))
		{
			queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
		}
		Integer queryCategoryLevel2 = null;
		if (_queryCategoryLevel2 != null && !"".equals(_queryCategoryLevel2))
		{
			queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
		}
		Integer queryCategoryLevel3 = null;
		if (_queryCategoryLevel3 != null && !"".equals(_queryCategoryLevel3))
		{
			queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
		}
		
		// 总数量
		Integer totalCount = null;
		try {
			totalCount = appInfoService.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 分页配置
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		Integer totalPageCount = pages.getTotalPageCount();
		
		// 控制尾页首页
		if (currentPageNo < 1)
		{
			currentPageNo = 1;
		}
		else if (currentPageNo >totalPageCount )
		{
			currentPageNo = totalPageCount;
		}
		// 获取信息
		try 
		{
			appInfoList = appInfoService.getAppInfoList(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, null, currentPageNo, pageSize);
			flatformList = this.getDataDictionaryList("APP_FLATFORM");
			categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
			if (queryCategoryLevel1 != null)
			categoryLevel2List = appCategoryService.getAppCategoryListByParentId(queryCategoryLevel1);
			if (queryCategoryLevel2 != null)
			categoryLevel3List = appCategoryService.getAppCategoryListByParentId(queryCategoryLevel2);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("flatFormList", flatformList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("pages", pages);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);
		return "backend/applist";
	}
	
	public List<DataDictionary> getDataDictionaryList(String status)
	{
		List<DataDictionary> dataDictionaries = null;
		try {
			dataDictionaries = dataDictionaryService.getDataDictionaryList(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataDictionaries;
	}
	
	@RequestMapping(value = "/categorylevellist.json")
	@ResponseBody
	public List<AppCategory> categoryLevelList(@RequestParam String pid)
	{
		if ("".equals(pid))
		{
			pid = null;
		}
		List<AppCategory> appCategoryList = null;
		try 
		{
			appCategoryList = appCategoryService.getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return appCategoryList;
	}
	
	@RequestMapping(value = "/check")
	public String checkApp(@RequestParam(value = "aid") Integer aid, @RequestParam(value = "vid")Integer vid, Model model)
	{
		AppVersion appVersion = null;
		AppInfo appInfo = null;
		try 
		{
			appVersion = appVersionService.getAppVersionByid(vid);
			appInfo = appInfoService.getAppInfoById(aid);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersion", appVersion);
		return "backend/appcheck";
	}
	
	@RequestMapping(value = "/checksave")
	public String checkSave(AppInfo appInfo, HttpSession session)
	{
		if (null != appInfo)
		{
			Integer userId = ((BackendUser)session.getAttribute(Constants.BACKEND_USER_SESSION)).getId();
			appInfo.setModifyBy(userId);
			appInfo.setModifyDate(new Date());
			try 
			{
				if (appInfoService.appInfoCheckStatus(appInfo) > 0)
				{
					return "redirect:/manager/backend/app/list";
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return "backend/appcheck";
	}
	
}
