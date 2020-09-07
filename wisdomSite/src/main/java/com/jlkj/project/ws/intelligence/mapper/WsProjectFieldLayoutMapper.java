package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工程场布图管理 Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsProjectFieldLayoutMapper
{
    /**
     * 查询工程场布图管理
     *
     * @param id 工程场布图管理 ID
     * @return 工程场布图管理
     */
    public WsProjectFieldLayout selectWsProjectFieldLayoutById(Integer id);

    /**
     * 查询工程场布图管理 列表
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 工程场布图管理 集合
     */
    public List<WsProjectFieldLayout> selectWsProjectFieldLayoutList(WsProjectFieldLayout wsProjectFieldLayout);

    /**
     * 新增工程场布图管理
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 结果
     */
    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout);

    /**
     * 修改工程场布图管理
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 结果
     */
    public int updateWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout);

    /**
     * 删除工程场布图管理
     *
     * @param id 工程场布图管理 ID
     * @return 结果
     */
    public int deleteWsProjectFieldLayoutById(Integer id);

    /**
     * 批量删除工程场布图管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsProjectFieldLayoutByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 查询项目下设备的场布图
        * @Date 2020/7/4 15:46
        * @Param [projectId, equipmentType]
        * @Return com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout
       **/
    WsProjectFieldLayout selectWsProjectFieldLayoutByProjecAndType(@Param("projectId")Integer projectId, @Param("equipmentType")Integer equipmentType);

    /**
        * @Author liujie
        * @Description 删除项目下指定设备场布图
        * @Date 2020/7/6 11:27
        * @Param [projectId, equipmentType]
        * @Return int
       **/
    int removeWsProjectFieldLayoutByProjectAndType(@Param("projectId")Integer projectId, @Param("equipmentType")Integer equipmentType);
    /**
        * @Author liujie
        * @Description 获取工程下指定设备类型场布图
        * @Date 2020/7/6 13:36
        * @Param [projectId, equipmentType]
        * @Return com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout
       **/
     WsProjectFieldLayout selectWsProjectFieldLayoutByProjectAndType(@Param("projectId")Integer projectId, @Param("equipmentType")Integer equipmentType);

}
