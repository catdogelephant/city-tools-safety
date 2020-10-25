package com.city.modules.tools.safety.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PDF生成辅助类
 * @author Goofy http://www.xdemo.org
 *
 */
@SuppressWarnings("all")
public class PdfHelper {
	
	public static ITextRenderer getRender() throws DocumentException, IOException {  
        
		ITextRenderer render = new ITextRenderer();  
        String path = getPath();  
        //添加字体，以支持中文
        render.getFontResolver().addFont(path + "pdf/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
        render.getFontResolver().addFont(path + "pdf/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
        return render;  
    } 
	
	//获取要写入PDF的内容
	public static String getPdfContent(String ftlPath, String ftlName, Object o) 
			throws TemplateNotFoundException, MalformedTemplateNameException, 
			ParseException, IOException, TemplateException {
		return useTemplate(ftlPath, ftlName, o);  
		
    }  
	
	//使用freemarker得到html内容
	public static String useTemplate(String ftlPath, String ftlName, Object o) 
			throws TemplateNotFoundException, MalformedTemplateNameException, 
			ParseException, IOException, TemplateException {  
        
		String html = null;  
        Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);  
        tpl.setEncoding("UTF-8");  
        StringWriter writer = new StringWriter();  
        tpl.process(o, writer);  
        writer.flush();  
        html = writer.toString();  
        return html;  
    } 
	
	/**
     * 获取Freemarker配置
     * @param templatePath
     * @return
     * @throws IOException
     */
	private static Configuration getFreemarkerConfig(String templatePath) throws IOException {  
        
		freemarker.template.Version version = new freemarker.template.Version("2.3.22");  
        Configuration config = new Configuration(version);  
        config.setDirectoryForTemplateLoading(new File(templatePath));  
        config.setEncoding(Locale.CHINA, "utf-8");  
        return config;  
    }
	
	/**
     * 获取类路径
     * @return
     */
	public static String getPath(){
		return PdfHelper.class.getResource("/").getPath().substring(1);
    }

    public static void main(String[] args) {
        String str1 = "<p style=\"border: 0px; margin-top: 0.63em; margin-bottom: 1.8em; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: PingFang SC, Arial, 微软雅黑, 宋体, simsun, sans-serif; white-space: normal;\">";
        String str2 = "和覅是阿;斯哦就font-family: PingFang SC, Arial, 微软雅黑, 宋体, simsun, sans-serif;说的话;";
        int fontIndex = str2.indexOf("font-family");
        int fenHaoIndex = str2.indexOf(";", fontIndex);
        String str = str1.substring(0, fontIndex) + str1.substring(fenHaoIndex);
        System.out.println(str);
    }
}
