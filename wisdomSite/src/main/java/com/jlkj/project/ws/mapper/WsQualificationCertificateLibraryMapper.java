package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsQualificationCertificateLibrary;

/**
 * 班组人员资格证书接口 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsQualificationCertificateLibraryMapper
{
    /**
     * @Author liujie
     * @Description 通过身份账号查询人员资格证书
     * @Date 2020/6/27 19:23
     * @Param [idNumber]
     * @Return com.jlkj.project.ws.domain.WsQualificationCertificateLibrary
     **/
    WsQualificationCertificateLibrary selectCertificateListByIdNumber(String idNumber);

    /**
     * @Author liujie
     * @Description 通过证书编号统计人员资格证书数量
     * @Date 2020/6/27 19:43
     * @Param [certificateNumber]
     * @Return int
     **/
    int countWsQualificationCertificateLibraryByCertificateNumber(String certificateNumber);

    /**
     * @Author liujie
     * @Description 插入人员资格证书
     * @Date 2020/6/27 20:07
     * @Param [wsQualificationCertificateLibrary]
     * @Return int
     **/
    int  insertWsQualificationCertificateLibrary(WsQualificationCertificateLibrary wsQualificationCertificateLibrary);

    /**
     * @Author liujie
     * @Description 更新人员资格证书
     * @Date 2020/6/27 20:08
     * @Param [wsQualificationCertificateLibrary]
     * @Return int
     **/
    int updateWsQualificationCertificateLibrary(WsQualificationCertificateLibrary wsQualificationCertificateLibrary);


    /**
        * @Author liujie
        * @Description 通过班组人员身份证号删除证书
        * @Date 2020/6/28 10:15
        * @Param [idNumber]
        * @Return int
       **/
    int deleteWsQualificationCertificateLibraryByIdNumber(String idNumber);
}
