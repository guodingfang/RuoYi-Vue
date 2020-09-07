package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsFileInfo;

public interface IWsFileInfoService {

    /**
        * @Author liujie
        * @Description 插入文件
        * @Date 2020/6/20 8:41
        * @Param [fileInfo]
        * @Return int
       **/
    WsFileInfo insertFileInfo(WsFileInfo fileInfo);
}
