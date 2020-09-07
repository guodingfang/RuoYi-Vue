package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsQualificationCertificate;
import com.jlkj.project.ws.domain.WsQualificationCertificateLibrary;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.WsTeamPersonLibrary;

import java.util.List;

/**
 * 班组人员库Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsTeamPersonLibraryMapper<insertWsTeamPersonLibraryByWsTeamPerson>
{
    /**
     * 查询班组人员库
     *
     * @param id 班组人员库ID
     * @return 班组人员库
     */
    public WsTeamPersonLibrary selectWsTeamPersonLibraryById(Integer id);

    /**
     * 查询班组人员库列表
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 班组人员库集合
     */
    public List<WsTeamPersonLibrary> selectWsTeamPersonLibraryList(WsTeamPersonLibrary wsTeamPersonLibrary);

    /**
     * 新增班组人员库
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 结果
     */
    public int insertWsTeamPersonLibrary(WsTeamPersonLibrary wsTeamPersonLibrary);

    /**
     * 修改班组人员库
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 结果
     */
    public int updateWsTeamPersonLibrary(WsTeamPersonLibrary wsTeamPersonLibrary);

    /**
        * @Author liujie
        * @Description 通过项目班组人员更新人员库
        * @Date 2020/6/15 12:04
        * @Param [wsTeamPerson]
        * @Return int
       **/
    int updateWsTeamPersonLibraryByWsTeamPerson(WsTeamPerson wsTeamPerson);


    /**
     * 删除班组人员库
     *
     * @param id 班组人员库ID
     * @return 结果
     */
    public int deleteWsTeamPersonLibraryById(Integer id);

    /**
     * 批量删除班组人员库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTeamPersonLibraryByIds(Integer[] ids);

/**
    * @Author liujie
    * @Description 通过班组人员插入建筑工人库
    * @Date 2020/6/14 23:04
    * @Param [wsTeamPerson]
    * @Return int
   **/
    int insertWsTeamPersonLibraryByWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
        * @Author liujie
        * @Description 通过身份证查找人员库数量
        * @Date 2020/6/15 11:56
        * @Param [idNumber]
        * @Return int
       **/
    int countWsTeamPersonByIdNumber(String idNumber);

    /**
        * @Author liujie
        * @Description 通过身份证号查询人员信息
        * @Date 2020/6/27 17:50
        * @Param [idNumber]
        * @Return com.jlkj.project.ws.domain.WsTeamPersonLibrary
       **/
    WsTeamPersonLibrary selectWsTeamPersonLibraryByIdNumber(String idNumber);

    /**
        * @Author liujie
        * @Description 通过身份账号查询人员资格证书
        * @Date 2020/7/14 19:03
        * @Param
        * @Return
       **/
    List<WsQualificationCertificate> selectCertificateListByIdNumber(String idNumber);

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
        * @Param [wsQualificationCertificateLibrary]
        * @Return int
       **/
    int  insertWsQualificationCertificate(WsQualificationCertificateLibrary wsQualificationCertificateLibrary);

    /**
        * @Author liujie
        * @Description 更新人员资格证书
        * @Date 2020/6/27 20:08
        * @Param [wsQualificationCertificateLibrary]
        * @Return int
       **/
    int updateWsQualificationCertificate(WsQualificationCertificateLibrary wsQualificationCertificateLibrary);
}
