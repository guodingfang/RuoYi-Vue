package com.jlkj.project.ws.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsTeamPerson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


public class AttendanceRecordExcel {
    private final Logger logger = LoggerFactory.getLogger(AttendanceRecordExcel.class);

    public  AjaxResult exportExcel(Map<String, Object> excelMap, String sheetName)
    {
        Workbook wb = excel(excelMap);
        return export(wb,sheetName);
    }
    public Workbook excel( Map<String, Object> excelMap)  {

        List<WsTeamPerson> data = (List<WsTeamPerson>) excelMap.get("data");
        int monthCol = DateUtil.endOfMonth((Date) excelMap.get("yearAndMonth")).dayOfMonth();
        int totalCol = monthCol + 10;
        int flag =1;//默认按照考勤统计
        Workbook wb = new SXSSFWorkbook(1000);
        if (null != data && data.size() > 0) {
            //设置sheet名称及列数
            Sheet sh = wb.createSheet("考勤报表");
            setSheetColumn(monthCol, totalCol, sh);
            //第一行标题
            CellStyle style = getCellStyle(wb);
            Row row00 = sh.createRow(0);
            row00.setHeight((short) 600);// 设定行的高度
            List content = Arrays.asList(new String[totalCol]);
            String projectName= StringUtils.isNotNull(excelMap.get("projectName"))?excelMap.get("projectName").toString():"";
            content.set(0, projectName+ "人员考勤报表");
           createAndFillRowCells(style,row00,content);

            //第二行统计
            CellStyle style1 = getCellStyle(wb);
            Row row01 = sh.createRow(1);
            row01.setHeight((short) 400);
            content = getCountTitleContent(excelMap, data, totalCol);
            createAndFillRowCells(style1,row01,content);

            //第三\四行小标题行
            CellStyle style2 = getCellStyle(wb);
            Row row02 = sh.createRow(2);
            row02.setHeight((short) 400);
            content = getSecondTitle(excelMap, totalCol);
            createAndFillRowCells(style2,row02,content);

            Row row03 = sh.createRow(3);
            row03.setHeight((short) 400);
            CellStyle style3= getCellStyle(wb);
            content = getThirdTitle(totalCol,8,monthCol);
            createAndFillRowCells(style3,row03,content);

            //统一设置合并单元格
            mergeCells(totalCol, sh);

            //开始填充数据列表
            Integer index = 1;
            if(StringUtils.isNotNull(excelMap.get("flag"))){
                flag=Integer.valueOf(excelMap.get("flag").toString());
            }
            //设置数据区域样式
            int startRows = 4;
            CellStyle style4= getCellStyle(wb);
            for (WsTeamPerson record : data) {
                Row row = sh.createRow(startRows);
                row.setHeight((short) 400);
                content = Arrays.asList(new String[totalCol]);
                content.set(0,index.toString());
                content.set(1,record.getName());
                content.set(2,"1".equals(record.getSex())?"男":"女");
                content.set(3,record.getIdNumber());
                content.set(4,record.getMobilePhone());
                content.set(5,record.getUnitName());
                content.set(6,record.getTeamName());
                content.set(7,record.getWorkTypeName());
                int begin = 8;
                for (Float manHour : record.getManHours()) {

                   content.set(begin,StringUtils.isNotNull(manHour)?manHour.toString():"");

                    begin++;
                }
                content.set(totalCol-2,record.getTotalAttendanceDays().toString());
                content.set(totalCol-1,record.getTotalAttendanceHours().toString());
                createAndFillStyle(style4,row,content.size());
                fillValue2Cell(row,content,flag,8,monthCol);
                startRows++;
                index++;
            }
        }

       return wb;
    }

    private List getThirdTitle(int totalCol,int startCol,int days) {
        List content;
        content = Arrays.asList(new String[totalCol]);
        for(int i=0;i<totalCol;i++){
            if(startCol<= i && i<days+startCol){
                String day = Integer.toString(i-startCol+1);
                content.set(i,day);
            }else{
                content.set(i,"");
            }
        }
        return content;
    }

    private List getSecondTitle(Map<String, Object> excelMap, int totalCol) {
        List content;
        content = Arrays.asList(new String[totalCol]);
        content.set(0,"序号");
        content.set(1,"姓名");
        content.set(2,"性别");
        content.set(3,"身份证号");
        content.set(4,"手机号");
        content.set(5,"参建单位");
        content.set(6,"班组");
        content.set(7,"工种");
        content.set(8,"时间："+ DateUtil.format((Date) excelMap.get("yearAndMonth"),"yyyy-MM"));
        content.set(38,"合计（天)");
        content.set(39,"合计（在场时长)");
        return content;
    }

    private List getCountTitleContent(Map<String, Object> excelMap, List<WsTeamPerson> data, int totalCol) {
        List content;
        content = Arrays.asList(new String[totalCol]);
        String unitName ="全部";
        if (StringUtils.isNotNull(excelMap.get("unitName"))&&StringUtils.isNotEmpty(excelMap.get("unitName").toString())) {
            unitName=excelMap.get("unitName").toString();
        }
        String teamName="全部";
        if (StringUtils.isNotNull(excelMap.get("teamName"))&& StringUtils.isNotEmpty(excelMap.get("teamName").toString())) {
            teamName=excelMap.get("teamName").toString();
        }
        String projectName =StringUtils.isNotNull(excelMap.get("projectName"))?excelMap.get("projectName").toString():"";
        content.set(0,"项目名称："+projectName );
        content.set(8,"劳务公司名称："+ unitName);
        content.set(22,"班组名称"+teamName);
        content.set(36,"总人数："+data.size());

        return content;
    }

    private void mergeCells(int totalCol, Sheet sh) {
        sh.addMergedRegion(new CellRangeAddress(0, 0, 0, totalCol-1));
        sh.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
        sh.addMergedRegion(new CellRangeAddress(1, 1, 8, 21));
        sh.addMergedRegion(new CellRangeAddress(1, 1, 22, 35));
        sh.addMergedRegion(new CellRangeAddress(1, 1, 36, 39));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 4, 4));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
        sh.addMergedRegion(new CellRangeAddress(2, 3, 7, 7));
        sh.addMergedRegion(new CellRangeAddress(2, 2, 8, totalCol-3));
        sh.addMergedRegion(new CellRangeAddress(2, 3, totalCol-2, totalCol-2));
        sh.addMergedRegion(new CellRangeAddress(2, 3, totalCol-1, totalCol-1));
    }

    private void setSheetColumn(int monthCol, int totalCol, Sheet sh) {
        sh.setColumnWidth(0, 1000);
        sh.setColumnWidth(1, 3000);
        sh.setColumnWidth(2, 1000);
        sh.setColumnWidth(3, 3000);
        sh.setColumnWidth(4, 3000);
        sh.setColumnWidth(5, 5000);
        sh.setColumnWidth(6, 3000);
        sh.setColumnWidth(7, 3000);
        int beginCol = 7;
        for (int i = 1; i <= monthCol; i++) {
            sh.setColumnWidth(beginCol + i, 1000);
        }
        sh.setColumnWidth(totalCol - 2, 3000);
        sh.setColumnWidth(totalCol - 1, 3000);
    }

    private CellStyle getCellStyle(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);

        CellStyle style = wb.createCellStyle();
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
        // 设置边框
        style.setFont(font);// 设置字体
        return style;
    }


    /**
        * @Author liujie
        * @Description //创建并行单元格
        * @Date 2020/7/1 8:50
        * @Param [style, row01, contents, startcol]
        * @Return void
       **/
    private void createAndFillRowCells(CellStyle style, Row row01, List contents) {
        int len = contents.size();
        for (int i = 0; i <  contents.size(); i++) {
            Cell cell = row01.createCell(i);

             cell.setCellValue(StringUtils.isNull(contents.get(i))?"":contents.get(i).toString());
            cell.setCellStyle(style);
        }
    }
    private void createAndFillStyle(CellStyle style, Row row01, int rows) {

        for (int i = 0; i <  rows; i++) {
            Cell cell = row01.createCell(i);
            cell.setCellStyle(style);
        }
    }

    private void fillValue2Cell( Row row,List contents,int flag,int tickBeginCol,int len ){
        if(row.getLastCellNum()<contents.size()){
            return;
        }
        for (int i = 0; i < contents.size(); i++) {
            boolean b=StringUtils.isNotNull(contents.get(i))&&StringUtils.isNotEmpty(contents.get(i).toString());
            if(flag==2){
                row.getCell(i).setCellValue(b?contents.get(i).toString():"");
                continue;
            }
            if(i>=tickBeginCol && i<tickBeginCol+len){
                Assert.notNull(contents.get(i));
                row.getCell(i) .setCellValue (b?unicode2String("\\\\u2713"):"");
            }else {

                row.getCell(i).setCellValue(b?contents.get(i).toString():"");
            }

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
