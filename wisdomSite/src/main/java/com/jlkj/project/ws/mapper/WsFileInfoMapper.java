package com.jlkj.project.ws.mapper;


import com.jlkj.project.ws.domain.WsFileInfo;

/**
    * @Author liujie
    * @Description 区域mapper接口
    *  @Date 2020/6/20 9:08
   **/
public interface WsFileInfoMapper {

    /**
        * @Author liujie
        * @Description 插入文件
        * @Date 2020/6/20 9:10
        * @Param [file]
        * @Return int
       **/
    int insertFileInfo(WsFileInfo file);

    /**
        * @Author liujie
        * @Description 通过ID查找文件
        * @Date 2020/6/20 9:11
        * @Param [id]
        * @Return com.jlkj.project.ws.domain.FileInfo
       **/
    WsFileInfo selectFileInfoById(Integer id);

}
