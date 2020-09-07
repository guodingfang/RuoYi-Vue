package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsQualificationCertificate;

import java.util.List;

/**
 * 班组人员资格证书接口 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsQualificationCertificateMapper
{
    /**
     * @Author liujie
     * @Description 通过身份账号查询人员资格证书
     * @Date 2020/6/27 19:23
     * @Param [idNumber]
     * @Return com.jlkj.project.ws.domain.WsQualificationCertificate
     **/
    WsQualificationCertificate selectCertificateListByIdNumber(String idNumber);

    /**
     * @Author liujie
     * @Description 通过证书编号统计人员资格证书数量
     * @Date 2020/6/27 19:43
     * @Param [idNumber]
     * @Return int
     **/
    int countWsQualificationCertificateByCertificateNumber(String certificateNumber);

    /**
     * @Author liujie
     * @Description 插入人员资格证书
     * @Date 2020/6/27 20:07
     * @Param [wsQualificationCertificate]
     * @Return int
     **/
    int  insertWsQualificationCertificate(WsQualificationCertificate wsQualificationCertificate);

    /**
     * @Author liujie
     * @Description 更新人员资格证书
     * @Date 2020/6/27 20:08
     * @Param [wsQualificationCertificate]
     * @Return int
     **/
    int updateWsQualificationCertificate(WsQualificationCertificate wsQualificationCertificate);

    /**
        * @Author liujie
        * @Description 通过ID获取人员资格证书列表
        * @Date 2020/6/28 9:57
        * @Param [id]
        * @Return com.jlkj.project.ws.domain.WsQualificationCertificate
       **/
    List<WsQualificationCertificate> selectCertificateListByPersonId(Integer personId);


    /**
        * @Author liujie
        * @Description 删除人员证书
        * @Date 2020/8/21 15:53
        * @Param [id]
        * @Return int
       **/
    int   deleteWsQualificationCertificateById (Integer id);
}
