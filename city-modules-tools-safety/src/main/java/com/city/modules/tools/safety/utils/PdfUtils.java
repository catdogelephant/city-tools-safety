package com.city.modules.tools.safety.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * PDF生成工具类
 * @author Goofy http://www.xdemo.org
 *
 */
@SuppressWarnings("all")
public class PdfUtils {

	/**
	  * 生成PDF到文件
	  * @param ftlPath 模板文件路径（不含文件名）
	  * @param ftlName 模板文件吗（不含路径）
	  * @param imageDiskPath 图片的磁盘路径
	  * @param data 数据
	  * @param outputFile 目标文件（全路径名称）
	  * @throws Exception
	  */
	public static void generateToFile(String ftlPath, String ftlName, String imageDiskPath, Object data, String outputFile) throws Exception {
		String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
		OutputStream out = null;
		ITextRenderer render = null;
		out = new FileOutputStream(outputFile);
		render = PdfHelper.getRender();
		render.setDocumentFromString(html);
		
		if (imageDiskPath != null && !imageDiskPath.equals("")) {
			// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
			render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
		}
		render.layout();
		render.createPDF(out);
		render.finishPDF();
		render = null;
		out.close();
	}
 
	/**
		* 生成PDF到输出流中（ServletOutputStream用于下载PDF）
		* @param ftlPath ftl模板文件的路径（不含文件名）
		* @param ftlName ftl模板文件的名称（不含路径）
		* @param imageDiskPath 如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径
		* @param data 输入到FTL中的数据
		* @param response HttpServletResponse
		* @return
		* @throws TemplateNotFoundException
		* @throws MalformedTemplateNameException
		* @throws ParseException
		* @throws IOException
		* @throws TemplateException
		* @throws DocumentException
		*/
	public static OutputStream generateToServletOutputStream(String ftlPath, String ftlName, String imageDiskPath,
			Object data, HttpServletResponse response) throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException, DocumentException {
		
		String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
		OutputStream out = null;
		ITextRenderer render = null;
		out = response.getOutputStream();
		render = PdfHelper.getRender();
		render.setDocumentFromString(html);
		if (imageDiskPath != null && !imageDiskPath.equals("")) {
			// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
			render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
		}
		render.layout();
		render.createPDF(out);
		render.finishPDF();
		render = null;
		return out;
	}

	/**
	 * 将html文件转化为PDF文件
	 * @param filePath 存储PDF文件位置
	 * @param htmlString html字符串
	 * @param path 图片存储的相对路径
	 */
	public static boolean htmlToPdf(String filePath, String htmlString, String path) {
		try {
			OutputStream fileOutputStream = new FileOutputStream(filePath);
			ITextRenderer iTextRenderer = new ITextRenderer();
			ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
			//指定字体，为了支持中文
			fontResolver.addFont("G:/Download/arial unicode ms.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			iTextRenderer.setDocumentFromString(htmlString);
			//解决图片的相对路径问题
			iTextRenderer.getSharedContext().setBaseURL("file:" + path);
			iTextRenderer.layout();
			iTextRenderer.createPDF(fileOutputStream);
			iTextRenderer.finishPDF();
			iTextRenderer = null;
			fileOutputStream.close();
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String pString = "<p style=\"text-align: center;\"><strong><span style=\"font-size:18px\">&nbsp; 知情同意书</span></strong></p > <p style=\"border: 0px; margin-top: 0.63em; margin-bottom: 1.8em; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: PingFang SC, Arial, 微软雅黑, 宋体, simsun, sans-serif; white-space: normal;\">&nbsp; &nbsp; &nbsp; &nbsp; 王沪宁在会上指出，习近平总书记的重要讲话深刻阐述国庆80。爱仕达哦啊是滴哦爱说大话ioh都i啊偶滴啊是都i啊是偶滴阿松ii哦啥的哦i阿斯顿按实际的吧啊是否iduhihdfiu合适的Ah fihf iskajd aih i赛后的iuah </p ><p style=\"border: 0px; margin-top: 0.63em; margin-bottom: 1.8em; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: PingFang SC, Arial, 微软雅黑, 宋体, simsun, sans-serif; white-space: normal;\">&nbsp; &nbsp; &nbsp;庆祝活动领导小组副组长丁薛祥、许其亮、黄坤明、蔡奇、肖捷、赵克志、何立峰、李作成参加会见并出席会议。庆祝活动领导小组成员，各工作机构和有关方面负责同志及工作人员、受阅部队官兵、游行联欢群众、服务保障人员、志愿者、演职人员、安保一线执勤人员代表等参加活动。</p >";
		boolean flag = true;
		while (flag) {
			int fontIndex = pString.indexOf("font-family");if (fontIndex == -1){
				flag = false;
				break;
			}
			int fenHaoIndex = pString.indexOf(";", fontIndex);
			pString = pString.substring(0, fontIndex) + pString.substring(fenHaoIndex);
		}
		String htmlString = "<!DOCTYPE html [<!ENTITY nbsp \"&#160;\"> ]>"
+ "<html lang=\"en\">"
+ "	<head>"
+ "   	<meta charset=\"UTF-8\"></meta>"
+ "    	<title>Issue Payment Receipt</title>"
+ "    	<style type=\"text/css\">"
+ "    			body {font-family: Arial Unicode MS;}"
+ "    		</style>"
+ "	</head>"
+ "	<body>"
+ 	pString
+ "<p><img src=\"" + "https://i.goldnurse.com/upload/JP20190215122156-35711.png" + "\"/></p>"
+ "	</body>"
+ "</html>";
		boolean b = PdfUtils.htmlToPdf("G:/Download/apment.pdf", htmlString, "G:/Download");
		System.out.println(b);
	}
}
