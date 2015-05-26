package application.container.itext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ItextDemoServiceImpl implements ItextDemoService {

	/**
	 * 创建document,使用PdfWriter编辑器,并打开文档
	 * 通过构造函数初始化生成的pdf的轮廓
	 * Document(pageSize, marginLeft, marginRight, marginTop, marginBottom)
	 * Document(纸张大小 ,  左边距      ,   右边距       ,   上边距   ,   下边距        )
	 * @param  file
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public Document createDocument(File file) {
		try {
			Document document = new Document(PageSize.A4, 90, 90, 70, 50);
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
	@Override
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
	@Override
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
	@Override
	public Section addSection(Section section, String title, Font font) {
		Paragraph paragraph = new Paragraph(title, font);
		return section.addSection(paragraph);
	}

	/**
	 * 创建段落,默认对其方式
	 * @param leading 字间距
	 * @param Indent 缩进
	 * @param content 段落内容
	 * @param font 字体设置
	 * @return
	 */
	@Override
	public Paragraph createParagraph(float leading, float indent, String content, Font font) {

		Paragraph paragraph = new Paragraph(leading, content, font);
		paragraph.setFirstLineIndent(indent);//设置首行缩进
		paragraph.setAlignment(Element.ALIGN_LEFT);//对齐方式，这里默认设置了左对齐
		return paragraph;
	}

	/**
	 * 创建段落，可设置对其方式
	 * @param leading 字间距
	 * @param indent 缩进
	 * @param content 段落内容
	 * @param font 字体设置
	 * @param align 对齐方式
	 * @return
	 */
	@Override
	public Paragraph createParagraph(float leading, float indent, String content, Font font, int align) {

		Paragraph paragraph = new Paragraph(leading, content, font);
		paragraph.setFirstLineIndent(indent);//设置首行缩进
		paragraph.setAlignment(align);//对齐方式，这里默认设置了左对齐
		return paragraph;
	}

	/**
	 * 创建表格
	 * @param section
	 * @param header
	 * @param body
	 */
	@Override
	public void createTable(Section section, List<String> header, List<Object[]> body, Paragraph title, Paragraph remark) {
		//新建表格
		Font font = new Font(ItextDemo.baseFont, 12, Font.NORMAL, new BaseColor(0, 0, 0));//自定义颜色，颜色也可以提出去，作为参数传进来 
		int column = header.size();
		PdfPTable table = new PdfPTable(column);//表头列任务是header List的size
		table.setSpacingBefore(10f);//表格上面的间距
		table.setSpacingAfter(10f);//表格下面的间距
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		//添加表格标题
		if (title != null) {
			section.add(title);
		}

		//创建表头
		for (String content : header) {
			PdfPCell cell = new PdfPCell(new Phrase(content, font));
			table.addCell(cell);
		}
		//创建表格体
		for (Object[] obj : body) {
			for (int i = 0; i < column; i++) {
				PdfPCell cell = new PdfPCell(new Phrase(obj[i].toString(), font));
				table.addCell(cell);
			}
		}
		section.add(table);

		//添加表格标题,这里调用了可以设置对其方式的createParagraph 方法
		if (remark != null) {
			section.add(remark);
		}
	}

	/**
	 * 关闭文档
	 * @param document
	 * @throws DocumentException
	 */
	@Override
	public void closeDocument(Document document) {
		document.close();
	}

	/**
	 * 内部类
	 * 设置页眉和页脚
	 * @author dingshijie
	 *
	 */
	private static class Header extends PdfPageEventHelper {

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			//设置页眉
			setHeader(writer, document);
			//页脚设置
			setFooter(writer, document);
		}

		/**
		 * 设置页眉
		 * @param writer
		 * @param document
		 */
		private void setHeader(PdfWriter writer, Document document) {
			//这里我推算的结果为：
			//left ,leftMargin ,right ,rightMargin是以"起笔点"算起，到对应左右"页面边缘"的位置
			//top  ,topMargin  ,buttom,buttomMargin是以"起笔点"算起,到对应"页面边缘"的位置 ,其中top为页面高度，应理解为到底侧边缘的距离
			//理解了上面，下面的引用就可以理解了
			Font font = new Font(ItextDemo.baseFont, 10f, Font.NORMAL, new BaseColor(0, 0, 0));

			//设置页眉方法一(有人说该方式只适合A4纸，具体可以根据情况选择)：
			//			float x_top = (document.right() + document.leftMargin()) / 2.0f;
			//			float y_top = (document.top() + 20f);
			//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
			//					new Phrase(String.format("%s", "hibernate中文文档"), font), x_top, y_top, 0);

			//设置页眉方法二：
			PdfPTable table = new PdfPTable(3);//这里设置了三列，对于没有用到的列我设置的内容为空，在下面可以见到
			//如果页眉显示多个信息可用 table.setWidths 进行拆分设置列宽
			table.setTotalWidth(document.right() - document.rightMargin());//设置总宽度
			table.setLockedWidth(true);//锁定列宽

			table.getDefaultCell().setFixedHeight(20f);//固定高度
			table.getDefaultCell().setBorder(Rectangle.BOTTOM);//设置只显示底部边框（这样边框就变成了页眉的分割线）
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);//设置单元格对其方式，右对齐
			table.getDefaultCell().setBorderColor(new BaseColor(0, 0, 0));//设置边框的颜色
			table.getDefaultCell().setBorderWidth(0.5f);//设置边框的宽度
			//添加表头
			table.addCell("");
			table.addCell(new Phrase(String.format("%s", "hibernate中文文档"), font));
			table.addCell("");
			//设置边框所在的位置
			table.writeSelectedRows(0, -1, document.leftMargin(), document.top() + 40f, writer.getDirectContent());
		}

		/**
		 * 设置页脚
		 * 这里之所以和header分开，因为一般设置的页眉和页脚都是不同的样式
		 * @param writer
		 * @param document
		 */
		private void setFooter(PdfWriter writer, Document document) {

			Font font = new Font(ItextDemo.baseFont, 10f, Font.NORMAL, new BaseColor(0, 0, 0));

			//设置页眉方法二：
			PdfPTable table = new PdfPTable(1);//这里设置了三列，对于没有用到的列我设置的内容为空，在下面可以见到
			//如果页眉显示多个信息可用 table.setWidths 进行拆分设置列宽
			table.setTotalWidth(document.right() - document.rightMargin());//设置总宽度
			table.setLockedWidth(true);//锁定列宽

			table.getDefaultCell().setFixedHeight(20f);//固定高度
			table.getDefaultCell().setBorder(Rectangle.TOP);//设置只显示底部边框（这样边框就变成了页眉的分割线）
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);//设置单元格对其方式，右对齐
			table.getDefaultCell().setBorderColor(new BaseColor(0, 0, 0));//设置边框的颜色
			table.getDefaultCell().setBorderWidth(0.5f);//设置边框的宽度
			//添加表头
			table.addCell(new Phrase(String.format("%d", writer.getPageNumber()), font));
			//设置边框所在的位置
			table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() + 20f,
					writer.getDirectContent());
		}
	}

}
