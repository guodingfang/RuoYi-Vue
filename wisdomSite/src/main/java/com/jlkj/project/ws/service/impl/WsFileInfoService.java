package com.jlkj.project.ws.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.project.ws.domain.WsFileInfo;
import com.jlkj.project.ws.mapper.WsFileInfoMapper;
import com.jlkj.project.ws.service.IWsFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName WsFileInfoService
 * @Description 文件信息业务层
 * @Author liujie
 * @Date 2020/6/20 8:47
 **/

@Service
public class WsFileInfoService implements IWsFileInfoService {

    @Autowired
    private WsFileInfoMapper wsFileInfoMapper;
    @Override
    public WsFileInfo insertFileInfo(WsFileInfo fileInfo) {
        int row = wsFileInfoMapper.insertFileInfo(fileInfo);
        if(row==0) throw new CustomException("文件上传失败，请联系管理员");
        return fileInfo;
    }
}
