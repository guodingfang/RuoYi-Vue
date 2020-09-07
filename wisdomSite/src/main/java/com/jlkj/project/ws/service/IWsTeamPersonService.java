package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsTeamPersonVO;
import com.jlkj.project.ws.dto.WsTeamPersonDTO;

import java.util.List;

/**
 * 班组人员 Service接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface IWsTeamPersonService
{
    /**
     * 查询班组人员
     *
     * @param id 班组人员 ID
     * @return 班组人员
     */
    public WsTeamPerson selectWsTeamPersonById(Integer id);


    /**
     * @Author liujie
     * @Description 通过guid 获取人员信息
     * @Date 2020/8/13 13:16
     * @Param [guid]
     * @Return com.jlkj.project.ws.domain.WsTeamPerson
     **/
    WsTeamPerson selectWsTeamPersonByGuid(String guid);

    /**
     * 查询班组人员 列表
     *
     * @param wsTeamPerson 班组人员
     * @return 班组人员 集合
     */
    public List<WsTeamPerson> selectWsTeamPersonList(WsTeamPerson wsTeamPerson);

    /**
     * 新增班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    public int insertWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
     * 修改班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    public int updateWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
     * 批量删除班组人员
     *
     * @param ids 需要删除的班组人员 ID
     * @return 结果
     */
    public int deleteWsTeamPersonByIds(Integer[] ids);

    /**
     * 删除班组人员 信息
     *
     * @param id 班组人员 ID
     * @return 结果
     */
    public int deleteWsTeamPersonById(Integer id);

    /**
        * @Author liujie
        * @Description 批量修改人员信息
        * @Date 2020/6/15 18:37
        * @Param [WsTeamPersonDTO]
        *
       **/
    int batchUpdateWsTeamPerson(WsTeamPersonDTO wsTeamPersonDTO);

    /**
        * @Author liujie
        * @Description 批量插入人员信息
        * @Date 2020/6/16 16:46
        * @Param [wsTeamPerson】
        * @Return int
       **/
    int batchInsertWsTeamPerson(WsTeamPerson wsTeamPerson);
    /**
    * @Author liujie
    * @Description 导入人员信息
    * @Date 2020/6/17 10:50
    * @Param [personList, unitId]
    * @Return java.lang.String
   **/
    String importPerson(List<WsTeamPerson> personList,Integer unitId);

    /**
        * @Author liujie
        * @Description 获取考勤授权列表
        * @Date 2020/6/29 8:38
        * @Param [wsTeamPerson]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsTeamPersonVO>
       **/
    List<WsTeamPersonVO> selectWsTeamPersonAuthList(WsTeamPerson wsTeamPerson);

    /**
     * 查询花名册
     * @param wsTeamPerson
     * @return
     */
    public List<WsTeamPerson> selectWsTeamPersonListRoster(WsTeamPerson wsTeamPerson);
}
