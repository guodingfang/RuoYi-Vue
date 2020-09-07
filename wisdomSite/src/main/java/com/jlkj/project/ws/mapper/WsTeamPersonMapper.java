package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsTeamPersonVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班组人员 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsTeamPersonMapper
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
     * @param wsTeamPerson 班组人员,dataScope数据范围
     * @return 班组人员 集合
     */
    public List<WsTeamPerson> selectWsTeamPersonList(@Param("wsTeamPerson") WsTeamPerson wsTeamPerson,@Param("dataScope") List<WsProject> dataScope);

    /**
     * 新增班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    public int insertWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
     * 通过DTO新增班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    int insertWsTeamPersonByLibrary(WsTeamPerson wsTeamPerson);

    /**
     * 修改班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    public int updateWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
     * 删除班组人员
     *
     * @param id 班组人员 ID
     * @return 结果
     */
    public int deleteWsTeamPersonById(Integer id);



    /**
     * 批量删除班组人员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTeamPersonByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 过身份证号或者手机号统计某项目下的人员数
        * @Date 2020/6/15 13:58
        * @Param [wsTeamPerson]
        * @Return int
       **/
    int countWsTeamPersonInProject(WsTeamPerson wsTeamPerson);

    int countWsTeamPersonInProjectAndId(@Param("projectId") Integer projectId,@Param("idNumber")  String idNumber, @Param("mobilePhone") String mobilePhone);
    /**
        * @Author liujie
        * @Description 根据项目参加单位id获取项目人数
        * @Date 2020/7/16 9:24
        * @Param [unitId]
        * @Return int
       **/
    int countWsTeamPersonInWsProjectParticipatingUnit(@Param("unitId")Integer unitId);
    /**
        * @Author liujie
        * @Description 通过身份证号或者手机号查找某项目下的人员
        * @Date 2020/6/15 13:59
        * @Param [wsTeamPerson]
        * @Return com.jlkj.project.ws.domain.WsTeamPerson
       **/
    List<WsTeamPerson> findWsTeamPersonInProject(WsTeamPerson wsTeamPerson);

    /**
        * @Author liujie
        * @Description 批量设置班组人员信息
        * @Date 2020/6/15 18:48
        * @Param [WsTeamPerson]\
        * @Return int
       **/
    int batchUpateWsTeamPerson(WsTeamPerson wsTeamPerson);

    /**
        * @Author liujie
        * @Description 批量插入人员信息
        * @Date 2020/6/16 17:39
        * @Param [personList]
        * @Return int
       **/
    int batchInsertWsTeamPerson(List<WsTeamPerson> personList);

    /**
        * @Author liujie
        * @Description 获取人员考勤授权认证列表
        * @Date 2020/6/29 14:32
        * @Param [wsTeamPerson]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsTeamPersonVO>
       **/
    List<WsTeamPersonVO> selectWsTeamPersonListWithAuth (@Param("wsTeamPerson")WsTeamPerson wsTeamPerson,@Param("dataScope")  List<WsProject>dataScope);

    /**
     * 查询花名册
     * @param wsTeamPerson
     * @return
     */
    public List<WsTeamPerson> selectWsTeamPersonListRoster(@Param("wsTeamPerson") WsTeamPerson wsTeamPerson,@Param("projectIds") Integer[] projectIds);
    /**
        * @Author liujie
        * @Description 通过监控设备编号反向获取项目人员信息
        * @Date 2020/7/13 22:06
        * @Param [serialNumber]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsTeamPerson>
       **/
    List<WsTeamPerson>   selectWsTeamPersonByserialNumber(@Param("serialNumber")String serialNumber);
}
