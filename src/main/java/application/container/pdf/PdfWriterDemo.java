package application.container.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfWriterDemo {

	private static Logger logger = LoggerFactory.getLogger(PdfWriterDemo.class);

	public static void main(String[] args) throws IOException {

		String path = "d://pdfOutput";

		File file = new File(path + "/pdf2.pdf");

		file.createNewFile();
		PdfWriterDemo demo = new PdfWriterDemo();
		Document document = null;
		BaseFont baseFont = null;
		try {
			//这里由于无法显示中文文字到pdf,设置编码的iTextAsian有在maven仓库中不存在，这里就暂时使用这个方式，回头打包成资源包进来也可以
			baseFont = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document = demo.createDocument(file);
		Font chapter_font = new Font(baseFont, 16f, Font.BOLD, new BaseColor(0, 0, 0));

		Font section_font = new Font(baseFont, 14f, Font.BOLD, new BaseColor(0, 0, 0));

		Font paragraph_font = new Font(baseFont, 12f, Font.NORMAL, new BaseColor(0, 0, 0));

		//chapter 1
		Chapter chapter_1 = demo.createChapter(document, "教程", chapter_font, 1, 5);

		StringBuffer content0_1 = new StringBuffer("面向新用户，从一个简单的使用内存数据库的例子开始，本章提供对 Hibernate 的逐步介绍。本");
		content0_1.append("教程基于 Michael Gloegl 早期编写的手册。所有代码都包含在 tutorials/web 目录下。");

		Paragraph paragraph_0_1 = demo.createParagraph(20f, 24f, content0_1.toString(), paragraph_font);

		chapter_1.add(paragraph_0_1);

		//section 1
		Section section_1 = demo.createSection(chapter_1, "第1部分 hibernate示例", section_font);

		String content1_1 = "在这个例子里，我们将设立一个小应用程序可以保存我们希望参加的活动（events）和这些活动主办方的相关信息。（译者注：在本教程的后面部分，我们将直接使用 event 而不是它的中文翻译“活动”，以免混淆。）";

		Paragraph paragraph1_1 = demo.createParagraph(20f, 24f, content1_1, paragraph_font);

		section_1.add(paragraph1_1);

		//section 1.1
		Section section_1_1 = demo.addSection(section_1, "设置", section_font);

		StringBuffer content1_1_1 = new StringBuffer("我们需要做的第一件事情是设置开发环境。我们将使用许多构建工具如");
		content1_1_1.append(" Maven [http://maven.org]  所鼓吹的“标准格式”。特别是 Maven，它的资源对这个格式（layout）  [http://");
		content1_1_1
				.append("maven.apache.org/guides/introduction/introduction-to-the-standard-directorylayout.html] 有着很好的描述。");
		content1_1_1.append("因为本教程使用的是 web 应用程序，我么将创建和使用 src/main/java、 src/main/resources 和 src/main/webapp 目录。");

		Paragraph paragraph1_1_1 = demo.createParagraph(20f, 24f, content1_1_1.toString(), paragraph_font);

		section_1_1.add(paragraph1_1_1);

		//section 2
		Section section_2 = demo.createSection(chapter_1, "第2部分 关联映射", section_font);

		String content2_1 = "我们已经映射了一个持久化实体类到表上。让我们在这个基础上增加一些类之间的关联。首先我们往应用程序里增加人（people）的概念，并存储他们所参与的一个 Event 列表。（译者注：与Event 一样，我们在后面将直接使用 person 来表示“人”而不是它的中文翻译）";

		Paragraph paragraph2_1 = demo.createParagraph(20f, 24f, content2_1, paragraph_font);

		section_2.add(paragraph2_1);

		try {
			document.add(chapter_1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		demo.creatChapter2(document, baseFont);

		demo.closeDocument(document);

		/**
		 * 通过构造函数初始化生成的pdf的轮廓
		 * Document(pageSize, marginLeft, marginRight, marginTop, marginBottom)
		 * 			纸张大小    左边距              右边距               上边距          下边距
		 */
		/*
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		String path = "d://pdfOutput";
		File file = new File(path + "/pdf.pdf");
		file.createNewFile();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file, false));
			document.open();
		} catch (FileNotFoundException e) {
			logger.warn("FileNotFoundException: " + file.getName());
			e.printStackTrace();
		} catch (DocumentException e) {
			logger.warn("DocumentException.");
			e.printStackTrace();
		}

		//
		Anchor anchor = new Anchor("First Page");
		anchor.setName("back to first page.");

		Paragraph paragraph1 = new Paragraph();
		paragraph1.setSpacingBefore(0);
		paragraph1.add(anchor);

		try {
			document.add(paragraph1);
			document.add(new Paragraph("first paragraph", FontFactory.getFont(FontFactory.COURIER, 14, Font.NORMAL,
					new BaseColor(0, 0, 0))));
		} catch (DocumentException e) {
			logger.warn("DocumentException");
			e.printStackTrace();
		}

		//新建章
		Paragraph title1 = new Paragraph("this is chapter 1", FontFactory.getFont(FontFactory.HELVETICA, 14,
				Font.NORMAL, new BaseColor(0, 0, 255)));

		Chapter chapter1 = new Chapter(title1, 1);
		chapter1.setNumberDepth(5);//将编号深度设置为 0，这样就不会在页面上显示章编号。

		//新建节
		Paragraph title1_1 = new Paragraph("this is section 1 in chapter 1", FontFactory.getFont(FontFactory.HELVETICA,
				14, Font.BOLD, new BaseColor(0, 0, 0)));

		Section section1 = chapter1.addSection(title1_1);
		Paragraph paragraph1_1 = new Paragraph("this is some paragraph of chapter 1 section 1.");

		section1.add(paragraph1_1);
		paragraph1_1 = new Paragraph("this is some paragraph of chapter 1 section 1 too.\r\nfollowing is a 2 × 3 table");

		section1.add(paragraph1_1);

		//新建表格
		PdfPTable table1 = new PdfPTable(3);
		table1.setSpacingBefore(25);//表格上面的间距
		table1.setSpacingAfter(25);//表格下面的间距

		PdfPCell c1 = new PdfPCell(new Phrase("columns 1"));
		table1.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("columns 2"));
		table1.addCell(c2);
		PdfPCell c3 = new PdfPCell(new Phrase("columns 3"));
		table1.addCell(c3);

		table1.addCell("1");
		table1.addCell("2");
		table1.addCell("3");

		section1.add(table1);

		List list1 = new List(true, false, 10);//生成带编号的列表，这里控制的是列表的样式
		list1.add(new ListItem("list of first line"));
		list1.add(new ListItem("list of second line"));

		section1.add(list1);

		try {
			Image image1 = Image.getInstance(path + "/win.png");
			image1.scaleAbsolute(120f, 120f);

			section1.add(image1);
		} catch (BadElementException e) {
			logger.warn("BadElementException");
			e.printStackTrace();
		}

		Paragraph title2 = new Paragraph("Use Anchor ", FontFactory.getFont(FontFactory.COURIER, 14, Font.NORMAL,
				new BaseColor(0, 0, 255)));

		section1.add(title2);

		title2.setSpacingBefore(5000);

		Anchor anchor2 = new Anchor("back to top");
		anchor2.setReference("#First Page");

		section1.add(anchor2);

		try {
			document.add(chapter1);
		} catch (DocumentException e) {
			logger.warn("DocumentException");
			e.printStackTrace();
		}

		document.close();
		 */
	}

	/**
	 * 测试
	 * @param document
	 * @param baseFont
	 */
	public void creatChapter2(Document document, BaseFont baseFont) {
		PdfWriterDemo demo = new PdfWriterDemo();

		Font chapter_font = new Font(baseFont, 16f, Font.BOLD, new BaseColor(0, 0, 0));

		Font section_font = new Font(baseFont, 14f, Font.BOLD, new BaseColor(0, 0, 0));

		Font paragraph_font = new Font(baseFont, 12f, Font.NORMAL, new BaseColor(0, 0, 0));
		//chapter 1
		Chapter chapter_1 = demo.createChapter(document, "体系结构", chapter_font, 2, 5);

		//section 1
		Section section_1 = demo.createSection(chapter_1, "第1部分 hibernate示例", section_font);

		StringBuffer content1_1 = new StringBuffer("由于 Hibernate 非常灵活，且支持多种应用方案， 所以我们这只描述一下两种极端的情况：“最小”和“全面解决”的体系结构方案。");
		content1_1.append("“最小”的体系结构方案，要求应用程序提供自己的 JDBC 连接并管理自己的事务。这种方案使用了Hibernate API 的最小子集。");
		content1_1.append("“全面解决”的体系结构方案，将应用层从底层的 JDBC/JTA API 中抽象出来，而让 Hibernate来处理这些细节。");

		Paragraph paragraph1_1 = demo.createParagraph(20f, 24f, content1_1.toString(), paragraph_font);

		section_1.add(paragraph1_1);

		//section 2
		Section section_2 = demo.addSection(chapter_1, "实例状态", section_font);

		StringBuffer content2_1 = new StringBuffer(
				"一个持久化类的实例可能处于三种不同状态中的某一种。这三种状态的定义则与所谓的持久化上下文（persistence context） 有关。");
		content2_1.append(" Hibernate 的 Session 对象就是这个所谓的持久化上下文。这三种不同的状态如下：");
		content2_1.append("瞬态（transient）该实例从未与任何持久化上下文关联过。它没有持久化标识（相当于主键值）。");
		content2_1.append("持久化（persistent）实例目前与某个持久化上下文有关联。它拥有持久化标识（相当于主键值），并且可能在数据库中有一个对应的行。");
		content2_1.append("脱管（detached）实例曾经与某个持久化上下文发生过关联，不过那个上下文被关闭了，或者这个实例是被序列化（serialize）到另外的进程。");

		Paragraph paragraph2_1 = demo.createParagraph(20f, 24f, content2_1.toString(), paragraph_font);

		section_2.add(paragraph2_1);

		try {
			document.add(chapter_1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建document,使用PdfWriter编辑器,并打开文档
	 * 通过构造函数初始化生成的pdf的轮廓
	 * Document(pageSize, marginLeft, marginRight, marginTop, marginBottom)
	 * Document(纸张大小 ,  左边距      ,   右边距       ,   上边距   ,   下边距        )
	 * @param  file
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public Document createDocument(File file) {
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file, false));

			Header header = new Header();
			writer.setPageEvent(header);//进行监听，设置页脚

			document.open();
			return document;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 新建章
	 * @param document
	 * @param title 章的标题
	 * @param font 章的显示风格
	 * @param number 章号
	 * @param depth 如果将编号深度设置为 0，就不会在页面上显示章编号
	 * @return
	 */
	public Chapter createChapter(Document document, String title, Font font, int number, int depth) {

		Paragraph paragraph = new Paragraph(title, font);

		Chapter chapter = new Chapter(paragraph, number);
		chapter.setNumberDepth(depth);

		return chapter;
	}

	/**
	 * 创建节
	 * @param chapter 章
	 * @param title 显示名称
	 * @param font 样式
	 * @return
	 */
	public Section createSection(Chapter chapter, String title, Font font) {
		Paragraph paragraph = new Paragraph(title, font);
		return chapter.addSection(paragraph);
	}

	/**
	 * 创建子章节
	 * @param section 节
	 * @param title 显示名称
	 * @param font 样式
	 * @return
	 */
	public Section addSection(Section section, String title, Font font) {
		Paragraph paragraph = new Paragraph(title, font);
		return section.addSection(paragraph);
	}

	/**
	 * 创建段落
	 * @param leading 字间距
	 * @param Indent 缩进
	 * @param content 段落内容
	 * @param font 字体设置
	 * @return
	 */
	public Paragraph createParagraph(float leading, float indent, String content, Font font) {

		Paragraph paragraph = new Paragraph(leading, content, font);
		paragraph.setFirstLineIndent(indent);//设置首行缩进
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);//对齐方式
		return paragraph;
	}

	/**
	 * 关闭文档
	 * @param document
	 * @throws DocumentException
	 */
	public void closeDocument(Document document) {
		document.close();
	}

	/**
	 * 内部类
	 * 设置页脚
	 * @author dingshijie
	 *
	 */
	private static class Header extends PdfPageEventHelper {

		@Override
		public void onEndPage(PdfWriter writer, Document document) {

			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase(String.format("%d", writer.getPageNumber())), 300, 50, 0);
		}

	}
}
