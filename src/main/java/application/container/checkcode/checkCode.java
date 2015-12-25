package application.container.checkcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工商行政注册码或组织机构代码校验
 * @author dingshijie
 *
 */
public class checkCode {

	public static void main(String[] args) {
		boolean result = false;
		//result = new checkCode().checkOrgCode("00990986X");//校验组织机构代码入口
		//result = new checkCode().checkOrgCode("L5701181X");//校验组织机构代码入口
		//new checkCode().checkRegisterNumber("");//校验工商注册号入口
		//如下示例
		//result = new checkCode().checkRegisterNumber("110108000000016");
		//result = new checkCode().checkRegisterNumber("123456789012345");
		result = new checkCode().CheckGB_32100_2015Code("311501053414322665");
		System.out.println(result);
	}

	/**
	* 根据GB 11714-1997标准对组织机构代码中的校验位进行校验
	* @param orgCode 组织机构代码
	* @return 是否正确的组织机构代码
	*/

	private boolean checkOrgCode(String orgCode) {
		if (orgCode.length() != 9 || orgCode.equals("000000000") || orgCode.equals("123456788"))
			return false;
		int chkNum = 0; // 校验数
		int[] factors = { 3, 7, 9, 10, 5, 8, 4, 2 }; // 加权因子
		// 取组织机构代码的八位本体代码为基数，与相应的加权因子相乘后求和，进行校验数的计算
		for (int i = 0; i < 8; i++) {
			char ch = orgCode.charAt(i);
			int num = 0;
			// 获取八位本体代码并转换为相应的数字
			if (ch >= 'A' && ch <= 'Z') {
				num = ch - 'A' + 10;
			} else if (ch >= '0' && ch <= '9') {
				num = ch - '0';
			} else {
				return false; // 含有数字字母以外的字符，直接返回错误
			}
			num = num * factors[i]; // 取Wi本体代码与加权银子对应各位相乘
			chkNum += num; // 乘积相加求和数
			System.out.printf("\n%2d + %2d = %2d\t", num, num, chkNum);
		}
		chkNum = chkNum % 11; // 取模数11除和数，求余数
		chkNum = 11 - chkNum; // 以模数11减余数，求校验码数值
		if (chkNum == 11) {
			chkNum = 0; // 校验数为11时直接转换为0
		}
		// 获取校验码，并跟校验数进行比较。校验数为10时候校验码应为“X”
		String chkCode = String.valueOf(orgCode.charAt(8));
		if (chkCode.endsWith(String.valueOf(chkNum)) || (chkCode.equals("X") && chkNum == 10)) {
			System.out.printf("获取的校验码为：%d, 校验位是%s, 校验成功", chkNum, chkCode);
			return true;
		} else {
			System.out.printf("获取的校验码为：%d, 由于校验位是%s, 校验失败", chkNum, chkCode);
			return false;
		}
	}

	/**
		* 校验工商行政注册码
		* 按照GB/T 17710，MOD 11,10校验
		* @param number
		* @return
		*/

	public boolean checkRegisterNumber(String number) {
		if (number.length() != 15) {
			System.err.println("工商注册码不为15位");
			return false;
		} else if (!isNumeric(number)) {//判断是否都是数字
			if (number.endsWith("X") && ("NA".equals(number.substring(6, 8)) || "NB".equals(number.substring(6, 8)))) {
				return true;
			}
			System.err.println("工商注册码不是全数字，且不符合非数字的校验规则");
			return false;
		}
		//对于此注册码的前六位的校验即：首次登记机关代码从工商行政注册号的编制规则 4.2来看，机关代码就全是地区代码，
		//     所以前六位目前暂无理由校验不合法
		//最后一位(第十五位)是校验位
		int checkBit = Integer.parseInt(number.substring(14));
		int num = checkNum(10, number.substring(0, 14));

		if ((checkBit + num) % 10 == 1) {
			System.out.printf("\n%2d + %2d = %2d\t 由于 %2d mod 10 == 1，校验正确", num, checkBit, (checkBit + num),
					(checkBit + num));
			return true;
		} else {
			System.out.printf("\n%2d + %2d = %2d\t 由于 %2d mod 10 != 1，校验失败", num, checkBit, (checkBit + num),
					(checkBit + num));
			return false;
		}
	}

	/**
		* 按照GB/T 17710，MOD 11,10校验公式
		* 最终递归循环得到 P_15 % 11,用于调用处来验证是否正确
		* @param P_num
		* @param subStr用来递归的子串
		* @return
		*/

	public int checkNum(int P_num, String subStr) {
		if ("".equals(subStr)) {//直到递归无剩余子串，可返回结果
			return P_num % 11;
		}
		System.out.println();
		int sub_Num = Integer.parseInt(subStr.substring(0, 1));//每次截取第一位用来处理
		System.out.printf("%2d + %2d = %2d\t", (P_num % 11), sub_Num, (P_num % 11 + sub_Num));
		sub_Num = P_num % 11 + sub_Num;
		P_num = (sub_Num % 10 != 0 ? sub_Num % 10 : 10) * 2;
		System.out.printf("%2d * %2d = %2d\t", (sub_Num % 10 != 0 ? sub_Num % 10 : 10), 2, P_num);
		System.out.printf("%2d ÷ %2d = %2d 余 %2d\t", P_num, 11, (P_num / 11), (P_num % 11));
		return checkNum(P_num, subStr.substring(1));//下一次进入递归的内容则是出去第一位的其余子串
	}

	/**
		* 检测字符传是否全为数字
		* @param str
		* @return
		*/

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	//	String[] changecode = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
	//			"F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "T", "U", "W", "X", "Y" };
	char[] changecode = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'U', 'W', 'X', 'Y' };

	int[] modNum = new int[] { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };

	//按照GB_32100_2015校验
	public boolean CheckGB_32100_2015Code(String code) {
		int result = 0;
		for (int i = 0; i < 17; i++) {
			char ch = code.charAt(i);
			int num = 0;
			if (ch >= 'A' && ch <= 'H') {
				num = ch - 'A' + 10;
			} else if (ch >= 'J' && ch <= 'N') {
				num = ch - 'A' + 10 - 1;
			} else if (ch >= 'P' && ch <= 'R') {
				num = ch - 'A' + 10 - 2;
			} else if (ch >= 'T' && ch <= 'U') {
				num = ch - 'A' + 10 - 3;
			} else if (ch >= 'W' && ch <= 'Y') {
				num = ch - 'A' + 10 - 4;
			} else if (ch >= '0' && ch <= '9') {
				num = ch - '0';
			} else {
				return false; // 含有数字字母以外的字符，直接返回错误
			}
			result += num * modNum[i];
			System.out.printf("%2d + %2d = %2d\n", num, modNum[i], result);
		}
		System.out.printf("计算出的校验位为:%2d, 该代码的校验位为:%2s\n", (31 - result % 31), code.charAt(17));
		result = 31 - result % 31;
		if (code.charAt(17) == changecode[result]) {
			return true;
		}
		return false;
	}

}
