package application.container.dom4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * dom4j 解析xml
 * @author dingshijie
 *
 */
public class Dom4j {

	public int status = 0;
	public String message = "";
	public String userId = "";
	public String username = "";

	public static void main(String[] args) throws Exception {
		//读xml格式的字符串
		String text = "<?xml version='1.0' encoding='UTF-8'?><createuser requestId='xxxxxxxx'><status>0</status><message>创建成功</message><user><userId>cn.ncss.jym</userId><username>ncss-jym</username></user></createuser>";
		//String text = "<?xml version='1.0' encoding='UTF-8'?><createuser requestId='xxxxxxxx'><status>1</status><message>参数错误</message></createuser>";
		new Dom4j().parseXml(text);

		//读文件
		//		String path = "d://新建文本文档.xml";
		//		try {
		//			File file = new File(path);
		//			new Dom4j().parseXml(file);
		//		} catch (Exception e) {
		//			throw new FileNotFoundException(path + "(系统找不到指定的文件。)");
		//		}
	}

	/**
	 * 解析xml文件
	 * @param file
	 * @throws Exception
	 */
	public void parseXml(File file) throws Exception {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			Element root = document.getRootElement();
			listNodes(root);

			System.out.println(this.status + "=" + this.message + "=" + this.userId + "=" + this.username);
		} catch (DocumentException e) {
			throw new DocumentException("Can not parse text to Document.");
		}
	}

	/**
	 * 解析xml格式的String串
	 * @param text
	 * @throws Exception
	 */
	public void parseXml(String text) throws Exception {
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			listNodes(root);

			System.out.println(this.status + "=" + this.message + "=" + this.userId + "=" + this.username);
		} catch (DocumentException e) {
			throw new DocumentException("Can not parse text to Document.");
		}
	}

	@SuppressWarnings("unchecked")
	public void listNodes(Element node) throws Exception {
		// 根据根节点获取当前节点下的所有节点
		System.out.println("当前节点的名称：：" + node.getName());
		if (!(node.getTextTrim().equals(""))) {
			System.out.println("文本内容：：：：" + node.getTextTrim());
		}
		//获取对应的值（附加）
		if (node.getName().equals("status")) {
			try {
				this.status = Integer.parseInt(node.getTextTrim());
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Can not parser fro input string: " + node.getTextTrim()
						+ " to Integer.");
			}
		}
		if (node.getName().equals("message")) {
			this.message = node.getTextTrim();
		}
		if (node.getName().equals("userId")) {
			this.userId = node.getTextTrim();
		}
		if (node.getName().equals("username")) {
			this.username = node.getTextTrim();
		}
		// 获取当前节点的所有属性节点  
		List<Attribute> list = node.attributes();
		// 遍历属性节点  
		for (Attribute attr : list) {
			System.out.println(attr.getName() + "==" + attr.getValue());
		}

		// 当前节点下面子节点迭代器  
		Iterator<Element> it = node.elementIterator();
		// 遍历  
		while (it.hasNext()) {
			// 获取某个子节点对象  
			Element e = it.next();
			// 对子节点进行遍历  
			listNodes(e);
		}
	}
}
