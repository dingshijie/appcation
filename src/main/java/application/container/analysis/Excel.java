package application.container.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.util.StringUtils;

public class Excel {
	//	public static void main(String[] args) {
	//		String path = "d:/2015本科新旧专业对照.xls";
	//		File file = new File(path);
	//		try {
	//			InputStream in = new FileInputStream(file);
	//			Workbook wb = Workbook.getWorkbook(in);
	//			int wbSheet = wb.getNumberOfSheets();
	//			for (int i = 0; i < wbSheet; i++) {
	//				Sheet sheet = wb.getSheet(i);
	//				if (sheet != null) {
	//					//获取总行数
	//					int row = sheet.getRows();
	//					String zydlCode = null;
	//					String zydlName = null;
	//					String zyzlCode = null;
	//					String zyzlName = null;
	//					//获取总列数
	//					int emptynum = 0;
	//					//int col = sheet.getColumns();
	//					for (int j = 0; j < row; j++) {
	//						Cell[] cell = sheet.getRow(j);
	//						String code = StringUtils.trimAllWhitespace(cell[0].getContents());
	//						String name = StringUtils.trimAllWhitespace(cell[1].getContents());
	//						if (!StringUtils.hasText(code)) {
	//							emptynum++;
	//							continue;
	//						}
	//						if (code.length() == 1) {
	//							zydlCode = "0" + code;
	//							zydlName = name;
	//						} else if (code.length() == 2) {
	//							zydlCode = code;
	//							zydlName = name;
	//						} else if (code.length() == 3) {
	//							zyzlCode = "0" + code;
	//							zyzlName = name;
	//							System.out.printf("专业大类：%5s %5s\t专业中类：%5s %15s\t专业小类：%5s %5s\n", zydlCode, zydlName,
	//									zyzlCode, zyzlName, zyzlCode + "00", name);
	//						} else if (code.length() == 4) {
	//							zyzlCode = code;
	//							zyzlName = name;
	//							System.out.printf("专业大类：%5s %5s\t专业中类：%5s %15s\t专业小类：%5s %5s\n", zydlCode, zydlName,
	//									zyzlCode, zyzlName, zyzlCode + "00", name);
	//						} else {
	//							if (isNumeric(code)) {
	//								if (code.length() != 6) {
	//									code = "0" + code;
	//								}
	//							} else {
	//								code = subStringCode(code);
	//								if (code.length() != 6) {
	//									code = "0" + code;
	//								}
	//							}
	//							System.out.printf("专业大类：%5s %5s\t专业中类：%5s %15s\t专业小类：%5s %5s\n", zydlCode, zydlName,
	//									zyzlCode, zyzlName, code, name);
	//							//							System.out.println();
	//							//							System.out.println("专业大类：" + zydlCode + zydlName + "\t专业中类：" + zyzlCode + zyzlName
	//							//									+ "\t专业小类：" + code + name);
	//						}
	//					}
	//					System.out.println("row:" + (row - emptynum));
	//				}
	//			}
	//
	//		} catch (FileNotFoundException e) {
	//			e.printStackTrace();
	//		} catch (BiffException e) {
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//	}

	/**
	 * 检测字符传是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String subStringCode(String code) {
		if (!isNumeric(code)) {
			code = code.substring(0, code.length() - 1);
			return subStringCode(code);
		}
		return code;
	}

	public static void main(String args[]) {
		String path = "d:/2015本科新旧专业对照.xls";
		File file = new File(path);
		try {
			InputStream in = new FileInputStream(file);
			Workbook wb = Workbook.getWorkbook(in);
			int wbSheet = wb.getNumberOfSheets();
			Map<String, Set<String>> resultMap = new LinkedHashMap<String, Set<String>>();
			for (int i = 0; i < 1; i++) {
				Sheet sheet = wb.getSheet(i);
				if (sheet != null) {
					//获取总行数
					int row = sheet.getRows();
					String zydlCode = null;
					String zydlName = null;
					String zyzlCode = null;
					String zyzlName = null;
					//获取总列数
					int emptynum = 0;
					//int col = sheet.getColumns();
					for (int j = 0; j < row; j++) {
						Cell[] cell = sheet.getRow(j);
						String newCode = StringUtils.trimAllWhitespace(cell[0].getContents());
						String newName = StringUtils.trimAllWhitespace(cell[1].getContents());
						String oldCode = StringUtils.trimAllWhitespace(cell[2].getContents());
						String oldName = StringUtils.trimAllWhitespace(cell[3].getContents());
						if (!StringUtils.hasText(newCode)) {
							emptynum++;
							continue;
						}
						if (oldCode.length() == 3) {
							oldCode = "0" + oldCode;
							newCode = "0" + newCode;
							if (resultMap.containsKey(oldCode + oldName)) {
								Set<String> list = resultMap.get(oldCode + oldName);
								list.add(newCode + newName);
							} else {
								Set<String> list = new HashSet<String>();
								list.add(newCode + newName);
								resultMap.put(oldCode + oldName, list);
							}
						} else if (oldCode.length() == 4) {
							if (resultMap.containsKey(oldCode + oldName)) {
								Set<String> list = resultMap.get(oldCode + oldName);
								list.add(newCode + newName);
							} else {
								Set<String> list = new HashSet<String>();
								list.add(newCode + newName);
								resultMap.put(oldCode + oldName, list);
							}
						}
					}
				}
			}
			//			Set set = resultMap.keySet();
			Iterator iterator = resultMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Set<String> list = resultMap.get(key);
				System.out.println(key + ":");
				System.out.print("\t");
				for (String s : list) {
					System.out.print(s + ",");
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//	public static void main(String[] args) {
	//		System.out.println(subStringCode("130311TK"));
	//	}
}
