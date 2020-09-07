package com.jlkj.project.common;

import cn.hutool.core.date.DateUtil;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.file.FileUploadUtils;
import com.jlkj.common.utils.file.FileUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.config.ServerConfig;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsAttendanceMachine;
import com.jlkj.project.ws.domain.WsAttendanceRecord;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.service.IWsAttendanceMachineService;
import com.jlkj.project.ws.service.IWsAttendanceRecordService;
import com.jlkj.project.ws.service.IWsTeamPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用请求处理
 *
 * @author jlkj
 */
@RestController
public class CommonController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IWsAttendanceMachineService wsAttendanceMachineService;

    @Autowired
    private IWsAttendanceRecordService wsAttendanceRecordService;

    @Autowired
    private IWsTeamPersonService wsTeamPersonService;


    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // 本地资源路径
        String localPath = RuoYiConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(name, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }


    @PostMapping(value = "/callBack/attendanceRecord", produces = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public void callbackAttendRecord(String personGuid, String deviceKey,String showTime,String photoUrl) {

        WsAttendanceMachine wsAttendanceMachine = wsAttendanceMachineService.findWsAttendanceMachineBySerialNumber(deviceKey);
        if(StringUtils.isNull(wsAttendanceMachine)) {
            logger.error("考勤机不存在");
            return;
        }
        WsTeamPerson wsTeamPerson = wsTeamPersonService.selectWsTeamPersonByGuid(personGuid);
        if(StringUtils.isNull(wsTeamPerson)) {
            logger.error("考勤人员不存在");
            return;
        }
        WsAttendanceRecord wsAttendanceRecord = new WsAttendanceRecord();
        wsAttendanceRecord.setAttendanceTime(DateUtil.parse(showTime,"yyyy-MM-dd HH:mm:ss"));
        wsAttendanceRecord.setTeamPersonId(wsTeamPerson.getId());
        wsAttendanceRecord.setMachineId(wsAttendanceMachine.getId());
        wsAttendanceRecordService.insertWsAttendanceRecord(wsAttendanceRecord);
    }


}
