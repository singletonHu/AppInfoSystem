package cn.appsys.service.backenduser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService 
{

	@Resource
	private BackendUserMapper mapper;
	@Override
	public BackendUser backendUserLogin(String userCode, String userPassword) 
	{
		return mapper.backendUserLogin(userCode, userPassword);
	}

}
