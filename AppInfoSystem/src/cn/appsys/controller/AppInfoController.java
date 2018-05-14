package cn.appsys.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.service.datadictionary.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController 
{
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;

	Logger logger = Logger.getLogger(AppInfoController.class);
	@RequestMapping(value = "/list")
	public String getAppInfoList(Model model, HttpSession session,
			@RequestParam(value = "querySoftwareName",required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus",required = false) String _queryStatus,
			@RequestParam(value = "queryFlatformId",required = false) String _queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1",required = false) String _queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2",required = false) String _queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3",required = false) String _queryCategoryLevel3,
			@RequestParam(value = "pageIndex",required = false) String _pageIndex)
	{
		logger.info("getAppInfoList ===========>：" + querySoftwareName);
		logger.info("getAppInfoList ===========>：" + _queryStatus);
		logger.info("getAppInfoList ===========>：" + _queryFlatformId);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel1);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel2);
		logger.info("getAppInfoList ===========>：" + _queryCategoryLevel3);
		logger.info("getAppInfoList ===========>：" + _pageIndex);
		
		Integer devId = ((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
		
		List<AppInfo> appInfoList = null ;
		List<DataDictionary> statusList = null;
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
		Integer queryStatus = null;
		if (_queryStatus != null && !"".equals(_queryStatus))
		{
			queryStatus = Integer.parseInt(_queryStatus);
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
			totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
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
			appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, pageSize);
			statusList = this.getDataDictionaryList("APP_STATUS");
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
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatformList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("pages", pages);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);
		return "developer/appinfolist";
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
	
	/**
	 * 动态获取三级分类信息
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/categorylevellist.json")
	@ResponseBody
	public List<AppCategory> getCategoryList(@RequestParam String pid)
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
	
	@RequestMapping(value = "/appinfoadd")
	public String addAppInfo()
	{
		return "developer/appinfoadd";
	}
	
	/**
	 * 动态获取平台信息
	 * @param tcode
	 * @return
	 */
	@RequestMapping(value = "/datadictionarylist.json")
	@ResponseBody
	public List<DataDictionary> getfaltform(@RequestParam(value = "tcode")String tcode)
	{
		return this.getDataDictionaryList(tcode);
	}
	
	@RequestMapping(value = "/appinfoaddsave", method = RequestMethod.POST)
	public String addAppInfoSave(AppInfo appInfo, @RequestParam(value = "a_logoPicPath") MultipartFile attach,
			HttpSession session, HttpServletRequest request)
	{
		String logoPicPath = null;
		String logoLocPath = null;
		if(!attach.isEmpty())
		{
			String picPath = request.getContextPath() + "/" + "statics" + "/" + "uploadfiles";
			String locPath = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("picPath ==========> : " + picPath);
			logger.info("locPath ==========> : " + locPath);
			
			String oldFileName = attach.getOriginalFilename();	// 原文件名
			logger.info("oldFileName ==========> : " + oldFileName);
			
			String prefix = FilenameUtils.getExtension(oldFileName);	// 原文件后缀
			logger.info("prefix ==========> : " + prefix);
			
			int filesize = 500000;
			logger.debug("size ======== > : " + attach.getSize());
			if (attach.getSize() > filesize)
			{
				request.setAttribute("fileUploadError", "* 上传文件不得超过500KB");
				return "developer/appinfoadd";
			}
			else if ("jpg".equalsIgnoreCase(prefix)  || "jpeg".equalsIgnoreCase(prefix) ||  "png".equalsIgnoreCase(prefix))
			{
				String fileName = appInfo.getAPKName() + ".jpg";
				logger.debug("new fileName ======== > : " + attach.getName());
				File targetFile = new File(locPath, fileName);
				if (!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				
				try 
				{
					attach.transferTo(targetFile);
				} catch (Exception e) 
				{
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败");
					return "developer/appinfoadd";
				}
				logoPicPath = picPath + "/" + fileName;
				logoLocPath = locPath + File.separator + fileName;
			}
			else
			{
				request.setAttribute("fileUploadError", "* 上传文件格式必须是：jpg、jpeg、png");
				return "developer/appinfoadd";
			}
			
			appInfo.setLogoLocPath(logoLocPath);
			appInfo.setLogoPicPath(logoPicPath);
			appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appInfo.setCreationDate(new Date());
			appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appInfo.setStatus(appInfo.getStatus() != 1 ? 1 : appInfo.getStatus());
			try 
			{
				if (appInfoService.addAppInfo(appInfo) == 1)
				{
					return "redirect:/dev/flatform/app/list";
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return "developer/appinfoadd";
	}
	
	@RequestMapping(value = "/apkexist.json")
	@ResponseBody
	public Object checkAPKName(@RequestParam("APKName") String APKName)
	{
		HashMap<String, Object> result = new HashMap<String, Object>();
		try 
		{
			if (APKName == null || "".equals(APKName))
			{
				result.put("APKName", "empty");
			}else if (appInfoService.checkAPKName(APKName) > 0)
			{
				result.put("APKName", "exist");
			}
			else
			{
				result.put("APKName", "noexist");
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/appinfomodify")
	public String appInfoModify(Model model, @RequestParam(value = "id")Integer id)
	{
		AppInfo appInfo = null;
		try 
		{
			appInfo = appInfoService.getAppInfoById(id);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	
	@RequestMapping(value = "/delfile.json")
	@ResponseBody
	public Object delFile(@RequestParam Integer id, @RequestParam String flag, HttpServletRequest request)
	{
		HashMap<String, Object> result = new HashMap<String, Object>();
		if ("logo".equals(flag))
		{
			AppInfo appInfo = appInfoService.getAppInfoById(id);
			File file = new File(appInfo.getLogoLocPath());
			if (file.exists())
			{
				file.delete();
			}
			
			if (appInfoService.deleteLogoById(id) > 0)
			{
				result.put("result", "success");
			}
			else
			{
				result.put("result", "failed");
			}
		}
		else if ("apk".equals(flag))
		{
			try 
			{
				AppVersion appVersion = appVersionService.getAppVersionByid(id);
				File file = new File(appVersion.getApkLocPath());
				if (file.exists())
				{
					file.delete();
				}
				
				if (appVersionService.deleteApkById(id) > 0)
				{
					result.put("result", "success");
				}
				else
				{
					result.put("result", "failed");
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/appinfomodifysave")
	public String updateAppInfoSave(HttpSession session, HttpServletRequest request, AppInfo appInfo, @RequestParam(value = "attach") MultipartFile attach)
	{
		if (!attach.isEmpty())
		{
			String picPath = request.getContextPath() + "/" + "statics" + "/" + "uploadfiles";
			String locPath = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("picPath ==========> : " + picPath);
			logger.info("locPath ==========> : " + locPath);
			
			String oldFileName = attach.getOriginalFilename();	// 原文件名
			logger.info("oldFileName ==========> : " + oldFileName);
			
			String prefix = FilenameUtils.getExtension(oldFileName);	// 原文件后缀
			logger.info("prefix ==========> : " + prefix);
			
			int filesize = 500000;
			logger.debug("size ======== > : " + attach.getSize());
			if (attach.getSize() > filesize)
			{
				request.setAttribute("fileUploadError", "* 上传文件不得超过500KB");
				return "developer/appinfomodify";
			}
			else if ("jpg".equalsIgnoreCase(prefix)  || "jpeg".equalsIgnoreCase(prefix) ||  "png".equalsIgnoreCase(prefix))
			{
				String fileName = appInfo.getAPKName() + ".jpg";
				logger.debug("new fileName ======== > : " + attach.getName());
				File targetFile = new File(locPath, fileName);
				if (!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				
				try 
				{
					attach.transferTo(targetFile);
				} catch (Exception e) 
				{
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败");
					return "developer/appinfomodify";
				}
				appInfo.setLogoPicPath(picPath + "/" + fileName);
				appInfo.setLogoLocPath(locPath + File.separator + fileName);
			}
			else
			{
				request.setAttribute("fileUploadError", "* 上传文件格式必须是：jpg、jpeg、png");
				return "developer/appinfomodify";
			}
		}
		
		if (appInfo.getLogoLocPath().isEmpty() || appInfo.getLogoPicPath().isEmpty())
		{
			appInfo.setLogoLocPath(null);
			appInfo.setLogoPicPath(null);
		}
		appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		if (appInfoService.updateAppInfo(appInfo) > 0)
		{
			return "redirect:/dev/flatform/app/list";
		}
		return "developer/appinfomodify";
	}
	
	@RequestMapping(value = "/appview/{id}")
	public String appView(@PathVariable Integer id, Model model)
	{
		List<AppVersion> appVersionList = null;
		AppInfo appInfo = null;
		try 
		{
			 appVersionList = appVersionService.getAppVersionListByAppId(id);
			 appInfo = appInfoService.getAppInfoById(id);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfoview";
	}
	
	@RequestMapping(value = "/delapp.json")
	@ResponseBody
	public Object delAppInfo(@RequestParam("id") Integer id)
	{
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			if (appInfoService.getAppInfoCountById(id) > 0)
			{
				try 
				{
					List<AppVersion> appVersionList = appVersionService.getAppVersionListByAppId(id);
					AppInfo appInfo = appInfoService.getAppInfoById(id);
					File file = new File(appInfo.getLogoLocPath());
					if (file.exists())
					{
						file.delete();
					}
					
					if (appVersionList != null)
					{
						for (AppVersion appVersion : appVersionList) 
						{
							file = new File(appVersion.getApkLocPath());
							if (file.exists())
							{
								file.delete();
							}
						}
						appVersionService.delAppVersionByAppId(id);
					}
					
					if (appInfoService.delAppInfoById(id) > 0)
					{
						result.put("delResult", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					result.put("delResult", "false");
				}
			}
			else
			{
				result.put("delResult", "notexist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("delResult", "false");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/{id}/sale.json")
	@ResponseBody
	public Object appInfoSale(@PathVariable("id")Integer appId, HttpSession session)
	{
		HashMap<String, String> result = new HashMap<String,String>();
		if (appId != null && appId != 0)
		{
			AppInfo appInfo = appInfoService.getAppInfoById(appId);
			if (appInfo.getStatus() == 4)
			{
				appInfo.setStatus(5);
			}
			else
			{
				appInfo.setStatus(4);
			}
			
			appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appInfo.setModifyDate(new Date());
			
			try 
			{
				if (appInfoService.appInfoSaleById(appInfo) > 0)
				{
					result.put("resultMsg", "success");
				}
				else
				{
					result.put("resultMsg", "failed");
				}
				result.put("errorCode", "0");
			} catch (Exception e) 
			{
				e.printStackTrace();
				result.put("errorCode", "exception000001");
			}
		}
		else
		{
			result.put("errorCode", "param000001");
		}
		return result;
	}
}
