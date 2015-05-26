package application.container.itext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;

public interface ItextDemoService {

	/**
	 * 创建document,使用PdfWriter编辑器,并打开文档
	 * 通过构造函数初始化生成的pdf的轮廓
	 * Document(pageSize, marginLeft, marginRight, marginTop, marginBottom)
	 * Document(纸张大小 ,  左边距      ,   右边距       ,   上边距   ,   下边距        )
	 * @param  file
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public Document createDocument(File file);

	/**
	 * 新建章
	 * @param document
	 * @param title 章的标题
	 * @param font 章的显示风格
	 * @param number 章号
	 * @param depth 如果将编号深度设置为 0，就不会在页面上显示章编号
	 * @return
	 */
	public Chapter createChapter(Document document, String title, Font font, int number, int depth);

	/**
	 * 创建节
	 * @param chapter 章
	 * @param title 显示名称
	 * @param font 样式
	 * @return
	 */
	public Section createSection(Chapter chapter, String title, Font font);

	/**
	 * 创建子章节
	 * @param section 节
	 * @param title 显示名称
	 * @param font 样式
	 * @return
	 */
	public Section addSection(Section section, String title, Font font);

	/**
	 * 创建段落,默认对其方式
	 * @param leading 字间距
	 * @param Indent 缩进
	 * @param content 段落内容
	 * @param font 字体设置
	 * @return
	 */
	public Paragraph createParagraph(float leading, float indent, String content, Font font);

	/**
	 * 创建段落，可设置对其方式
	 * @param leading 字间距
	 * @param indent 缩进
	 * @param content 段落内容
	 * @param font 字体设置
	 * @param align 对齐方式
	 * @return
	 */
	public Paragraph createParagraph(float leading, float indent, String content, Font font, int align);

	/**
	 * 创建表格
	 * @param section
	 * @param header
	 * @param body
	 */
	public void createTable(Section section, List<String> header, List<Object[]> body, Paragraph title, Paragraph remark);

	/**
	 * 关闭文档
	 * @param document
	 * @throws DocumentException
	 */
	public void closeDocument(Document document);

}
