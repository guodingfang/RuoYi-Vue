package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目参建单位 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsProjectParticipatingUnitMapper
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
    public List<WsProjectParticipatingUnit> selectWsProjectParticipatingUnitList(@Param("wsProjectParticipatingUnit") WsProjectParticipatingUnit wsProjectParticipatingUnit,@Param("dataScope")  List<WsProject>dataScope);


    /**
     * 查询项目参建单位项目经历列表
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 项目参建单位 集合
     */
    List<WsProjectParticipatingUnit>  selectWsProjectParticipatingUnitProjectExperience(WsProjectParticipatingUnit wsProjectParticipatingUnit);
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
     * 删除项目参建单位
     *
     * @param id 项目参建单位 ID
     * @return 结果
     */
    public int deleteWsProjectParticipatingUnitById(Integer id);

    /**
     * 批量删除项目参建单位
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsProjectParticipatingUnitByIds(Integer[] ids);

    /**
    * @Author liujiecountWsProjectParticipatingUnitInProject
    * @Description 统计项目内某个参建单位的数量
    * @Date 2020/6/13 11:46
    * @Param [wsProjectParticipatingUnit]
    * @Return int
   **/
    int countWsProjectParticipatingUnitInProject(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    /**
     * @Author liujie
     * @Description 通过单位库库插入工程单位信息
     * @Date 2020/6/16 11:19
     * @Param [list]
     * @Return int
     **/
    int batchAddByWsParticipatingUnitLibrary(List<WsParticipatingUnitLibrary> list);


    /**
        * @Author liujie
        * @Description 获取项目班组树列表
        * @Date 2020/7/16 23:52
        * @Param [dataScope]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO>
       **/
    List<WsProjectTeamTreeVO> selectProjectTeamTree(@Param("dataScope") List<WsProject>dataScope);

}
