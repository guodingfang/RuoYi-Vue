package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsQualificationCertificate;
import com.jlkj.project.ws.domain.WsTeamPersonLibrary;

import java.util.List;

/**
 * 班组人员库Service接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface IWsTeamPersonLibraryService
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
     * 批量删除班组人员库
     *
     * @param ids 需要删除的班组人员库ID
     * @return 结果
     */
    public int deleteWsTeamPersonLibraryByIds(Integer[] ids);

    /**
     * 删除班组人员库信息
     *
     * @param id 班组人员库ID
     * @return 结果
     */
    public int deleteWsTeamPersonLibraryById(Integer id);

    /**
        * @Author liujie
        * @Description根据身份证号查询证书列表
        * @Date 2020/7/14 19:04
        * @Param [idNumber]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsQualificationCertificate>
       **/
    List<WsQualificationCertificate> selectCeitificateListByIdNumber(String idNumber);
}
