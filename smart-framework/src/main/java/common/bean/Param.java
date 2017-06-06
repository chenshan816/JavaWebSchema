package common.bean;

import java.util.Map;

import common.utils.CastUtil;

/**
 * 从HttpServletRequest对象中获取所有请求参数
 * @author cs
 * @since 1.0.0
 */
public class Param {
	private Map<String,Object> paramMap;
	

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 根据参数名获取long型参数值
	 */
	public long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}
	
	/**
	 * 获取所有字段信息
	 */
	public Map<String,Object> getMap(){
		return paramMap;
	}
}
