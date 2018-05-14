package cn.appsys.service.datadictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl  implements DataDictionaryService
{
	@Resource
	private DataDictionaryMapper mapper;
	@Override
	public List<DataDictionary> getDataDictionaryList(String typeCode)  throws Exception
	{
		return mapper.getDataDictionaryList(typeCode);
	}

}
