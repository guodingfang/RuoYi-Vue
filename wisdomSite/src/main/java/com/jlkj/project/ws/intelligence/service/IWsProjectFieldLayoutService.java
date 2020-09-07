package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;

import java.util.List;

/**
 * 工程场布图管理 Service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsProjectFieldLayoutService
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
     * 批量删除工程场布图管理
     *
     * @param ids 需要删除的工程场布图管理 ID
     * @return 结果
     */
    public int deleteWsProjectFieldLayoutByIds(Integer[] ids);

    /**
     * 删除工程场布图管理 信息
     *
     * @param id 工程场布图管理 ID
     * @return 结果
     */
    public int deleteWsProjectFieldLayoutById(Integer id);


    /**
        * @Author liujie
        * @Description 获取工程下指定设备类型场布图
        * @Date 2020/7/6 13:34
        * @Param [projectId, equipmentType]
        * @Return com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout
       **/
    WsProjectFieldLayout selectWsProjectFieldLayoutByProjectAndType(Integer projectId, Integer equipmentType);
}
