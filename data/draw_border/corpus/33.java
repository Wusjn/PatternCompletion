package org.my431.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author wangzhen
 *
 */
public class ExcelStyle {
	/**
	 * 标题样式
	 * @param workbook
	 * @param style
	 * @return
	 */
	public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook ,HSSFCellStyle style)
	{
		
		style.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样样式
		style.setFont(font);
		return style;
		
	}
	
	/**
	 * 内容样式
	 * @param workbook
	 * @param style2
	 * @return
	 */
	public static HSSFCellStyle setbodyStyle(HSSFWorkbook workbook ,HSSFCellStyle style2)
	{
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样样式
		style2.setFont(font2);
		return style2;
	}
}