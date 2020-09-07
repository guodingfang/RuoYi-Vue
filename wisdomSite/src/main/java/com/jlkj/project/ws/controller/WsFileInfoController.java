package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.file.FileUploadUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsFileInfo;
import com.jlkj.project.ws.service.IWsFileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName FileInfoController
 * @Description 文件信息管理
 * @Author liujie
 * @Date 2020/6/19 22:03
 **/
@Api("文件信息管理")
@RestController
@RequestMapping("/ws/fileInfo")
public class WsFileInfoController extends BaseController {

    @Autowired
    IWsFileInfoService wsFileInfoService;

    //@PreAuthorize("@ss.hasPermi('ws:fileInfo:upload')")
    @ApiOperation("上传文件")
    //@ApiImplicitParam(name="file", value="文件",dataType  ="MultipartFile")
    @PostMapping("/upload")
    public AjaxResult uploadSave(@RequestParam("file") MultipartFile file, WsFileInfo fileInfo) throws IOException
    {
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件路径
        filePath = FileUploadUtils.upload(filePath, file);
        fileInfo.setUrl(filePath);
        if(StringUtils.isEmpty(fileInfo.getName())){
            fileInfo.setName(file.getOriginalFilename());
        }
        WsFileInfo saveFile =wsFileInfoService.insertFileInfo(fileInfo);
        return AjaxResult.success(saveFile);

    }

}
