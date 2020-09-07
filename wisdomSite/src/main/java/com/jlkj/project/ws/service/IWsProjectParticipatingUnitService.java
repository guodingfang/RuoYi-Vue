package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO;
import com.jlkj.project.ws.dto.WsProjectParticipatingUnitDTO;

import java.util.List;

/**
 * 项目参建单位 Service接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface IWsProjectParticipatingUnitService
{
    /**
     * 查询项目参建单位
     *
     * @param id 项目参建单位 ID
     * @return 项目参建单位
     */
    public WsProjectParticipatingUnit selectWsProjectParticipatingUnitById(Integer id);

    /**
     * 查询项目参建单位 列表
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 项目参建单位 集合
     */
    public List<WsProjectParticipatingUnit> selectWsProjectParticipatingUnitList(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    /**
     * 新增项目参建单位
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 结果
     */
    public int insertWsProjectParticipatingUnit(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    /**
     * 修改项目参建单位
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 结果
     */
    public int updateWsProjectParticipatingUnit(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    /**
     * 批量删除项目参建单位
     *
     * @param ids 需要删除的项目参建单位 ID
     * @return 结果
     */
    public int deleteWsProjectParticipatingUnitByIds(Integer[] ids);

    /**
     * 删除项目参建单位 信息
     *
     * @param id 项目参建单位 ID
     * @return 结果
     */
    public int deleteWsProjectParticipatingUnitById(Integer id);

    /**
        * @Author liujie
        * @Description 判断项目内某个参建单位是否已存在
        * @Date 2020/6/13 11:48
        * @Param [wsProjectParticipatingUnit]
        * @Return boolean
       **/
    boolean existWsProjectParticipatingUnitInProject(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    /**
        * @Author liujie
        * @Description 批量新增工程参建单位
        * @Date 2020/6/16 6:02
        * @Param [wsParticipatingUnitDTO]
        * @Return int
       **/
    int patchAdd(WsProjectParticipatingUnitDTO wsParticipatingUnitDTO);



    /**
        * @Author liujie
        * @Description 创建树列表
        * @Date 2020/7/6 14:36
        * @Param [projectTeamList]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO>
       **/
    List<WsProjectTeamTreeVO> buildProjectTeamTreeSelect(List<WsProjectTeamTreeVO> projectTeamList);
    /**
        * @Author liujie
        * @Description 获取项目班组列表
     *
        * @Date 2020/6/18 11:25
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO>
       **/
    List<WsProjectTeamTreeVO> selectProjectTeamList();

    /**
        * @Author liujie
        * @Description 创建项目班组树
        * @Date 2020/6/18 15:28
        * @Param [projectTeamList]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO>
       **/
    List<WsProjectTeamTreeVO> buildProjectTeamTreeVO(List<WsProjectTeamTreeVO> projectTeamList);
}
