package com.ndialog.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class FileUtil {
	public int j = 0;
	public static ArrayList<File> list = new ArrayList<File>();

	// ����·��path��ȡ��·���µ������ļ����ļ���
	public String[] getFileNames(String path) {
		File file = new File(path);
		return file.list();
	}

	public void renameFile(String oldFile, String newFile) {
		File file = new File(oldFile);
		file.renameTo(new File(newFile));
	}

	public void createFolder(String sfile) {
		File file = new File(sfile);
		file.mkdirs();
	}

	public void createFile(String sfile) throws IOException {
		File file = new File(sfile);
		file.createNewFile();
	}

	// �����ļ���׺���ж��ǲ���txt�ļ�
	public boolean isTxtFile(String fileName) {
		File file = new File(fileName);
		String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		if (fileEnd.equalsIgnoreCase("txt")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMp3File(String fileName) {
		String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		if (fileEnd.equalsIgnoreCase("mp3")) {
			return true;

		} else {
			return false;
		}
	}

	public boolean isImageFile(String fileName) {
		String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		if (fileEnd.equalsIgnoreCase("jpg")) {
			return true;

		} else if (fileEnd.equalsIgnoreCase("png")) {
			return true;

		} else if (fileEnd.equalsIgnoreCase("gif")) {
			return true;
		} else {
			return false;
		}
	}

	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs();
			// ����ļ��в����� �������ļ���
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory() && !newPath.contains(oldPath)) {// ��������ļ���
																		// ��Ҫ�ж��Ƿ��Ǹ����ڱ�Ŀ¼��������Ʊ�Ŀ¼��
																		// ��ֻ�ݹ�һ�η�����ѭ��
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("���������ļ������ݲ�������");
			e.printStackTrace();
		}
	}

	static public boolean copyFile(String frompath, String topath) {
		try {
			File fromFile = new File(frompath);
			File toFile = new File(topath);
			FileInputStream fosfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c); // ������д�����ļ�����
			}
			fosfrom.close();
			fosto.close();
		} catch (Exception ex) {
			Log.e("readfile", ex.getMessage());
		}
		return true;
	}

	public boolean delFile(String path) {
		File file = new File(path);
		return file.delete();
		// return true;
	}

	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���

		} catch (Exception e) {
			System.out.println("ɾ���ļ��в�������");
			e.printStackTrace();

		}
	}

	/**
	 * ɾ���ļ�������������ļ�
	 * 
	 * @param path
	 * 
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
			}
		}
	}

	public static ArrayList<File> getAllFiles(File root) {

		File files[] = root.listFiles();

		if (files != null)
			for (File f : files) {

				if (f.isDirectory()) {
					getAllFiles(f);
				} else {
					if (f.getName().indexOf(".lrc") > 0)
						list.add(f);
				}
			}
		return list;
	}
}
