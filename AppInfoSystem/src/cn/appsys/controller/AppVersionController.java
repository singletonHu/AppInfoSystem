package cn.appsys.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value = "/dev/flatform/app")
public class AppVersionController 
{
	@Resource
	private AppVersionService appVersionService;
	
	@Resource
	private AppInfoService appInfoService;
	
	private Logger logger = Logger.getLogger(AppVersionController.class);
	
	@RequestMapping(value = "appversionadd")
	public String appVersionAdd(Model model ,@RequestParam(value = "id")Integer id)
	{
		List<AppVersion> appVersionList = null;
		AppVersion appVersion = new AppVersion();
		try 
		{
			appVersionList = appVersionService.getAppVersionListByAppId(id);
			appVersion.setAppId(id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionadd";
	}
	
	@RequestMapping(value = "/addversionsave", method = RequestMethod.POST)
	public String addVersionSave(HttpSession session, HttpServletRequest request, @RequestParam(value = "a_downloadLink")MultipartFile attach,
			AppVersion appVersion)
	{
		String downloadLink = null;
		String apkLocPath = null;
		
		boolean flag = true;
		if (!attach.isEmpty())
		{
			String dllPath = request.getContextPath() + "/" + "statics" + "/" + "uploadfiles";
			String dpklPath = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("dllPath ==========> : " + dllPath);
			logger.info("dpklPath ==========> : " + dpklPath);
			
			String oldFileName = attach.getOriginalFilename();	// 原文件名
			logger.info("oldFileName ==========> : " + oldFileName);
			
			String prefix = FilenameUtils.getExtension(oldFileName);	// 原文件后缀
			logger.info("prefix ==========> : " + prefix);
			
			int filesize = 50000000;
			logger.debug("size ======== > : " + attach.getSize());
			if (attach.getSize() > filesize )
			{
				request.setAttribute("fileUploadError", "* 上传文件不得超过500MB");
				flag = false;
			}
			else if ("apk".equalsIgnoreCase(prefix))
			{
				String APKName = null;
				try {
					APKName = appInfoService.getAPKNameById(appVersion.getAppId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				String fileName = APKName + "-" + appVersion.getVersionNo() + ".apk";
				
				logger.debug("new fileName ======== > : " + attach.getName());
				File targetFile = new File(dpklPath, fileName);
				if (!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				
				try 
				{
					attach.transferTo(targetFile);
					
					downloadLink = dllPath + "/" + fileName;
					apkLocPath = dpklPath + File.separator + fileName;
					appVersion.setApkFileName(fileName);
					appVersion.setApkLocPath(apkLocPath);
					appVersion.setDownloadLink(downloadLink);
				} catch (Exception e) 
				{
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败");
					flag = false;
				}
			}
			else
			{
				System.out.println(appVersion.getAppId() + "======" + appVersion.getApkFileName());
				request.setAttribute("fileUploadError", "* 上传文件格式必须是：apk");
				flag = false;
			}
		}
		
			if (flag)
			{
				appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
				appVersion.setCreationDate(new Date());
				try {
					if (appVersionService.addAppVersion(appVersion) > 0)
					{
						List<AppVersion> appVersionList = appVersionService.getAppVersionListByAppId(appVersion.getAppId());
						if (appVersionService.updateAppVersionInfo(appVersionList.get(appVersionList.size() - 1)) > 0)
						{
							return "redirect:/dev/flatform/app/list";
						}
					}
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			List<AppVersion> appVersionList = null;
			try {
				appVersionList = appVersionService.getAppVersionListByAppId(appVersion.getAppId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("appVersionList", appVersionList);
			return "developer/appversionadd";
	}
	
	
	@RequestMapping(value = "/appversionmodify")
	public String appVersionModify(@RequestParam(value = "vid")Integer vid, @RequestParam(value = "aid") Integer aid, Model model)
	{
		List<AppVersion> appVersionList = null;
		AppVersion appVersion = null;
		try 
		{
			appVersionList = appVersionService.getAppVersionListByAppId(aid);
			for (int i = 0; i < appVersionList.size(); i++) 
			{
				if (appVersionList.get(i).getId() == vid)
				{
					appVersion = appVersionList.get(i);
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	@RequestMapping(value = "/appversionmodifysave")
	public String modifyAppVersionSava(HttpSession session, HttpServletRequest request, @RequestParam(value = "attach")MultipartFile attach,
			AppVersion appVersion)
	{
		boolean flag = true;
		if (!attach.isEmpty())
		{
			String downloadLink = null;
			String apkLocPath = null;
			String dllPath = request.getContextPath() + "/" + "statics" + "/" + "uploadfiles";
			String dpklPath = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("dllPath ==========> : " + dllPath);
			logger.info("dpklPath ==========> : " + dpklPath);
			
			String oldFileName = attach.getOriginalFilename();	// 原文件名
			logger.info("oldFileName ==========> : " + oldFileName);
			
			String prefix = FilenameUtils.getExtension(oldFileName);	// 原文件后缀
			logger.info("prefix ==========> : " + prefix);
			
			int filesize = 50000000;
			logger.debug("size ======== > : " + attach.getSize());
			if (attach.getSize() > filesize )
			{
				request.setAttribute("fileUploadError", "* 上传文件不得超过500MB");
				flag = false;
			}
			else if ("apk".equalsIgnoreCase(prefix))
			{
				String APKName = null;
				try {
					APKName = appInfoService.getAPKNameById(appVersion.getAppId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				String fileName = APKName + "-" + appVersion.getVersionNo() + ".apk";
				
				logger.debug("new fileName ======== > : " + attach.getName());
				File targetFile = new File(dpklPath, fileName);
				if (!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				
				try 
				{
					attach.transferTo(targetFile);
					
					downloadLink = dllPath + "/" + fileName;
					apkLocPath = dpklPath + File.separator + fileName;
					appVersion.setApkFileName(fileName);
					appVersion.setApkLocPath(apkLocPath);
					appVersion.setDownloadLink(downloadLink);
				} catch (Exception e) 
				{
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败");
					flag = false;
				}
			}
			else
			{
				System.out.println(appVersion.getAppId() + "======" + appVersion.getApkFileName());
				request.setAttribute("fileUploadError", "* 上传文件格式必须是：apk");
				flag = false;
			}
		}
		
			if (flag)
			{
				if (appVersion.getDownloadLink() == null || "".equals(appVersion.getDownloadLink()))
				{
					appVersion.setDownloadLink(null);
					appVersion.setApkFileName(null);
					appVersion.setApkLocPath(null);
				}
				appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
				appVersion.setModifyDate(new Date());
				try 
				{
					if (appVersionService.updateAppVersionById(appVersion) > 0)
					{
						return "redirect:/dev/flatform/app/list";
					}
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			List<AppVersion> appVersionList = null;
			try {
				appVersionList = appVersionService.getAppVersionListByAppId(appVersion.getAppId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("appVersionList", appVersionList);
			return "developer/appversionmodify";
	}
}
