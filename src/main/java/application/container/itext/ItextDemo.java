package application.container.itext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;

@Controller
@RequestMapping(value = "/itext")
public class ItextDemo {

	private static Logger logger = LoggerFactory.getLogger(ItextDemo.class);

	public static BaseFont baseFont = null;//设置基本的文字模式

	@Autowired
	private ItextDemoService itextDemoService;

	@RequestMapping(value = "itext.html", method = RequestMethod.GET)
	public String iTextHtml(Model model) {
		return "itext/itext";
	}

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	@ResponseBody
	public boolean CreatePdf() throws IOException {

		String path = "d://pdfOutput";

		File file = new File(path + "/pdf2.pdf");

		file.createNewFile();
		Document document = null;
		try {
			//这里由于无法显示中文文字到pdf,设置编码的iTextAsian有在maven仓库中不存在，
			//这里就暂时使用这个方式，这个字体是楷体
			baseFont = BaseFont.createFont("/SIMKAI_0.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document = itextDemoService.createDocument(file);
		Font chapter_font = new Font(baseFont, 16f, Font.BOLD, new BaseColor(0, 0, 0));

		Font section_font = new Font(baseFont, 14f, Font.BOLD, new BaseColor(0, 0, 0));

		Font paragraph_font = new Font(baseFont, 12f, Font.NORMAL, new BaseColor(0, 0, 0));

		//chapter 1
		Chapter chapter_1 = itextDemoService.createChapter(document, "教程", chapter_font, 1, 5);

		StringBuffer content0_1 = new StringBuffer("面向新用户，从一个简单的使用内存数据库的例子开始，本章提供对 Hibernate 的逐步介绍。本");
		content0_1.append("教程基于 Michael Gloegl 早期编写的手册。所有代码都包含在 tutorials/web 目录下。");

		Paragraph paragraph_0_1 = itextDemoService.createParagraph(20f, 24f, content0_1.toString(), paragraph_font);

		chapter_1.add(paragraph_0_1);

		//section 1
		Section section_1 = itextDemoService.createSection(chapter_1, "第1部分 hibernate示例", section_font);

		String content1_1 = "在这个例子里，我们将设立一个小应用程序可以保存我们希望参加的活动（events）和这些活动主办方的相关信息。（译者注：在本教程的后面部分，我们将直接使用 event 而不是它的中文翻译“活动”，以免混淆。）";

		Paragraph paragraph1_1 = itextDemoService.createParagraph(20f, 24f, content1_1, paragraph_font);

		section_1.add(paragraph1_1);

		//section 1.1
		Section section_1_1 = itextDemoService.addSection(section_1, "设置", section_font);

		StringBuffer content1_1_1 = new StringBuffer("我们需要做的第一件事情是设置开发环境。我们将使用许多构建工具如");
		content1_1_1.append(" Maven [http://maven.org]  所鼓吹的“标准格式”。特别是 Maven，它的资源对这个格式（layout）  [http://");
		content1_1_1
				.append("maven.apache.org/guides/introduction/introduction-to-the-standard-directorylayout.html] 有着很好的描述。");
		content1_1_1.append("因为本教程使用的是 web 应用程序，我么将创建和使用 src/main/java、 src/main/resources 和 src/main/webapp 目录。");

		Paragraph paragraph1_1_1 = itextDemoService.createParagraph(20f, 24f, content1_1_1.toString(), paragraph_font);

		section_1_1.add(paragraph1_1_1);

		//section 2
		Section section_2 = itextDemoService.createSection(chapter_1, "第2部分 关联映射", section_font);

		String content2_1 = "我们已经映射了一个持久化实体类到表上。让我们在这个基础上增加一些类之间的关联。首先我们往应用程序里增加人（people）的概念，并存储他们所参与的一个 Event 列表。（译者注：与Event 一样，我们在后面将直接使用 person 来表示“人”而不是它的中文翻译）";

		Paragraph paragraph2_1 = itextDemoService.createParagraph(20f, 24f, content2_1, paragraph_font);

		section_2.add(paragraph2_1);

		try {
			document.add(chapter_1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		creatChapter2(document);

		itextDemoService.closeDocument(document);

		return true;
	}

	/**
	 * 测试
	 * @param document
	 * @param baseFont
	 */
	public void creatChapter2(Document document) {

		Font chapter_font = new Font(baseFont, 16f, Font.BOLD, new BaseColor(0, 0, 0));

		Font section_font = new Font(baseFont, 14f, Font.BOLD, new BaseColor(0, 0, 0));

		Font paragraph_font = new Font(baseFont, 12f, Font.NORMAL, new BaseColor(0, 0, 0));
		//chapter 1
		Chapter chapter_1 = itextDemoService.createChapter(document, "体系结构", chapter_font, 2, 5);

		//section 1
		Section section_1 = itextDemoService.createSection(chapter_1, "概况（Overview）", section_font);

		StringBuffer content1_1 = new StringBuffer("由于 Hibernate 非常灵活，且支持多种应用方案， 所以我们这只描述一下两种极端的情况：“最小”和“全面解决”的体系结构方案。");
		content1_1.append("“最小”的体系结构方案，要求应用程序提供自己的 JDBC 连接并管理自己的事务。这种方案使用了Hibernate API 的最小子集。");
		content1_1.append("“全面解决”的体系结构方案，将应用层从底层的 JDBC/JTA API 中抽象出来，而让 Hibernate来处理这些细节。");

		Paragraph paragraph1_1 = itextDemoService.createParagraph(20f, 24f, content1_1.toString(), paragraph_font);

		section_1.add(paragraph1_1);

		//section 2
		Section section_2 = itextDemoService.addSection(chapter_1, "实例状态", section_font);

		StringBuffer content2_1 = new StringBuffer(
				"一个持久化类的实例可能处于三种不同状态中的某一种。这三种状态的定义则与所谓的持久化上下文（persistence context） 有关。");
		content2_1.append(" Hibernate 的 Session 对象就是这个所谓的持久化上下文。这三种不同的状态如下：");
		StringBuffer content2_2 = new StringBuffer("瞬态（transient）");
		StringBuffer content2_3 = new StringBuffer("该实例从未与任何持久化上下文关联过。它没有持久化标识（相当于主键值）。");
		StringBuffer content2_4 = new StringBuffer("持久化（persistent）");
		StringBuffer content2_5 = new StringBuffer("实例目前与某个持久化上下文有关联。它拥有持久化标识（相当于主键值），并且可能在数据库中有一个对应的行。");
		StringBuffer content2_6 = new StringBuffer("脱管（detached）");
		StringBuffer content2_7 = new StringBuffer("实例曾经与某个持久化上下文发生过关联，不过那个上下文被关闭了，或者这个实例是被序列化（serialize）到另外的进程。");

		Paragraph paragraph2_1 = itextDemoService.createParagraph(20f, 24f, content2_1.toString(), paragraph_font);

		Paragraph paragraph2_2 = itextDemoService.createParagraph(20f, 0f, content2_2.toString(), paragraph_font);
		Paragraph paragraph2_3 = itextDemoService.createParagraph(20f, 24f, content2_3.toString(), paragraph_font);
		Paragraph paragraph2_4 = itextDemoService.createParagraph(20f, 0f, content2_4.toString(), paragraph_font);
		Paragraph paragraph2_5 = itextDemoService.createParagraph(20f, 24f, content2_5.toString(), paragraph_font);
		Paragraph paragraph2_6 = itextDemoService.createParagraph(20f, 0f, content2_6.toString(), paragraph_font);
		Paragraph paragraph2_7 = itextDemoService.createParagraph(20f, 24f, content2_7.toString(), paragraph_font);

		section_2.add(paragraph2_1);

		section_2.add(new Paragraph("\n"));//添加一个空行

		section_2.add(paragraph2_2);
		section_2.add(paragraph2_3);
		section_2.add(paragraph2_4);
		section_2.add(paragraph2_5);
		section_2.add(paragraph2_6);
		section_2.add(paragraph2_7);

		List<String> header = new ArrayList<String>();
		header.add("姓名");
		header.add("性别");
		header.add("年龄");
		List<Object[]> body = new ArrayList<Object[]>();
		body.add(new Object[] { "zhangsan", "男", 18 });
		body.add(new Object[] { "hanmeimei", "女", 18 });
		body.add(new Object[] { "liming", "男", 20 });

		//这里调用了可以设置对其方式的createParagraph 方法
		Paragraph table_title = itextDemoService.createParagraph(20f, 0f, "表格title", paragraph_font,
				Element.ALIGN_CENTER);

		itextDemoService.createTable(section_2, header, body, table_title, null);

		try {
			document.add(chapter_1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
