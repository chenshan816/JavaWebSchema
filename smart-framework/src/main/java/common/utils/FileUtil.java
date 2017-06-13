package common.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件助手类
 * @author cs
 * @since 1.0.0
 */
public final class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 获得文件真实文件名
	 */
	public static String getRealFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}
	
	/**
	 * 创建文件
	 */
	public static File creatFile(String filePath) {
		File file;
		file = new File(filePath);
		File parentDir = file.getParentFile();
		//循环创建所有的文件夹
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
