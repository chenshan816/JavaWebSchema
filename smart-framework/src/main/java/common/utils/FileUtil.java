package common.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �ļ�����������
 * @author cs
 * @since 1.0.0
 */
public final class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * ��ȡ��ʵ�ļ������Զ�ȥ���ļ�·����
	 */
	public static String getRealFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}
	
	/**
	 * �����ļ�
	 */
	public static File creatFile(String filePath) {
		File file;
		file = new File(filePath);
		File parentDir = file.getParentFile();
		//�ݹ鴴��Ŀ¼
		try {
			if(!parentDir.exists()){
			FileUtils.forceMkdir(parentDir);
		}
		} catch (IOException e) {
			LOGGER.error("create file failure",e);
			throw new RuntimeException(e);
		}
		return file;
	}
}
