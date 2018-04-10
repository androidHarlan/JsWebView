package com.tentcoo.pineapple.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtil {

	private static boolean flag;
	private static File file;

	/**
	 * 获取文件的输入流
	 * 
	 * @param path
	 * @return
	 */
	public static FileInputStream getFileInputStream(String path) {
		FileInputStream fis = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				fis = new FileInputStream(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	/**
	 * 获取文件的输出流
	 * 
	 * @param path
	 * @return
	 */
	public static OutputStream getFileOutputStream(String path) {
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				fos = new FileOutputStream(file);
			}
		} catch (Exception e) {
			return null;
		}
		return fos;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		flag = false;
		file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		flag = false;
		file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 从assets目录中复制整个文件夹内容
	 * 
	 * @param context
	 *            Context 使用CopyFiles类的Activity
	 * @param oldPath
	 *            String 原文件路径 如：/aa
	 * @param newPath
	 *            String 复制后路径 如：xx:/bb/cc
	 */
	public static boolean copyFilesFassets(Context context, String oldPath,
			String newPath) {
		try {
			String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
			if (fileNames.length > 0) {// 如果是目录
				File file = new File(newPath);
				file.mkdirs();// 如果文件夹不存在，则递归
				for (String fileName : fileNames) {
					copyFilesFassets(context, oldPath + "/" + fileName, newPath
							+ "/" + fileName);
				}
			} else {// 如果是文件
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
				int byteCount = 0;
				while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
																// buffer字节
					fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
				}
				fos.flush();// 刷新缓冲区
				is.close();
				fos.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String genrateVideoTempFilePath(Context context) {
		String dirPath = Environment.getExternalStorageDirectory()
				+ "/Android/data/" + context.getPackageName() + "/xml";
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory())
			file.mkdirs();

		return file.getAbsolutePath();
	}

	// 判断文件是否存在
	public static boolean isExists(String path) {
		File f = new File(path);
		if (f.exists()) {
			return true;
		}
		return false;
	}

	public static String readFile(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public static String GetReadFile(Context context,String path) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			InputStream fileInputStream = context.getAssets().open(path);
			//FileInputStream fileInputStream = getFileInputStream(path);
			br = new BufferedReader(new InputStreamReader(fileInputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
