package com.jlkj.project.ws.excel;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.web.domain.AjaxResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


public class CommonExportExcel {

    /**  数据行数*/
    private Integer dataRows;
    /**  数据列数*/
    private Integer columns;
    /**  表名*/
    private String sheetName;
    /**  数据行*/
    private List<List<String>> body=new ArrayList<>();
    /**  标题行*/
    private List<String> header=new ArrayList<>();
    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;
    /**  工作簿*/
    private Workbook wb;


    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int SHEET_SIZE = 65536;

    private final Logger logger = LoggerFactory.getLogger(CommonExportExcel.class);

    public Integer getDataRows() {
        return dataRows;
    }

    public void setDataRows(Integer dataRows) {
        this.dataRows = dataRows;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<List<String>> getBody() {
        return body;
    }

    public void setBody(List<List<String>> body) {
        this.body = body;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public Map<String, CellStyle> getStyles() {
        return styles;
    }

    public void setStyles(Map<String, CellStyle> styles) {
        this.styles = styles;
    }

    public Workbook getWb() {
        return wb;
    }

    public void setWb(Workbook wb) {
        this.wb = wb;
    }


    public CommonExportExcel(String sheetName, @NotNull List<List<String>> body,@NotNull List<String> header) {
        this.body =body;
        this.header = header;
        this.sheetName = sheetName;
    }

    private  void init(){
        this.dataRows = body.size();
        this.columns  = header.size();
        createWorkBook();
        this.styles = createStyles();

    }

    public  AjaxResult exportExcel()
    {
        init();
        Workbook wb = excel();
        return export(wb,sheetName);
    }
    private Workbook excel()  {

        int sheets =(dataRows/(SHEET_SIZE-1));
        for (int i = 0; i <=sheets; i++) {
            //为sheet 添加名称
            String ext = i==0?"":String.valueOf(i);
            Sheet sh = wb.createSheet(sheetName+ext);
            //创建表头
            Row headerRow= sh.createRow(0);
            headerRow.setHeight((short) 600);// 设定行的高度
            createAndFillRowCells(styles.get("header"),headerRow,header.size(),header);
            //填充数据体
            int cols =0 ;
            if(body.size()>0) cols = header.size()>body.size()?body.size():header.size();
            for (int j = 0; j <dataRows ; j++) {
                Row bodyRow= sh.createRow(j+1);
                createAndFillRowCells(styles.get("body"),bodyRow,cols,body.get(j));
            }
        }
       return wb;
    }

    private void createWorkBook() {
        this.wb = new SXSSFWorkbook(1000);
    }

    /**
     * 创建表格样式
     *
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles()
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        styles.put("data", createBodyStyle());
        styles.put("header", createHeaderStyle());

        return styles;
    }
    private CellStyle createCellStyle(){
        return wb.createCellStyle();

    }
    private CellStyle createBodyStyle(){
        CellStyle style = createCellStyle();
        setCellStyle(style);
        return style;
    }

    private void setCellStyle(CellStyle style) {
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
    }

    private CellStyle createHeaderStyle(){
        CellStyle style = wb.createCellStyle();
        style = wb.createCellStyle();
        style.cloneStyleFrom(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        return style;
    }




    /**
        * @Author liujie
        * @Description //创建并行单元格
        * @Date 2020/7/1 8:50
        * @Param [style, row, cols, list]
        * @Return void
       **/
    private void createAndFillRowCells(CellStyle style, Row row,int cols,List<String> list) {
        for (int i = 0; i <  cols; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(StringUtils.isNull(list.get(i))?"":list.get(i));
            cell.setCellStyle(style);
        }
    }


    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }


    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    private AjaxResult export( Workbook wb ,String sheetName)
    {
        OutputStream out = null;
        try
        {
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            logger.error("导出Excel异常{}", e.getMessage());
            throw new CustomException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

}
