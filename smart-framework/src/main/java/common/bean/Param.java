package common.bean;

import java.util.Map;

import common.utils.CastUtil;

/**
 * ��HttpServletRequest�����л�ȡ�����������
 * @author cs
 * @since 1.0.0
 */
public class Param {
	private Map<String,Object> paramMap;
	

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * ���ݲ�������ȡlong�Ͳ���ֵ
	 */
	public long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}
	
	/**
	 * ��ȡ�����ֶ���Ϣ
	 */
	public Map<String,Object> getMap(){
		return paramMap;
	}
}
