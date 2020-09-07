package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsArea;

import java.util.List;

/**
 * 区域 Service接口
 *
 * @author jlkj
 * @date 2020-07-02
 */
public interface IWsAreaService
{
    /**
     * 查询区域
     *
     * @param id 区域 ID
     * @return 区域
     */
    public WsArea selectWsAreaById(Integer id);

    /**
     * 查询区域 列表
     *
     * @param wsArea 区域
     * @return 区域 集合
     */
    public List<WsArea> selectWsAreaList(WsArea wsArea);

    /**
     * 新增区域
     *
     * @param wsArea 区域
     * @return 结果
     */
    public int insertWsArea(WsArea wsArea);

    /**
     * 修改区域
     *
     * @param wsArea 区域
     * @return 结果
     */
    public int updateWsArea(WsArea wsArea);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的区域 ID
     * @return 结果
     */
    public int deleteWsAreaByIds(Integer[] ids);

    /**
     * 删除区域 信息
     *
     * @param id 区域 ID
     * @return 结果
     */
    public int deleteWsAreaById(Integer id);


    /**
     * @Author liujie
     * @Description 根据县区code获取三级省市区
     * @Date 2020/7/21 9:50
     * @Param
     * @Return
     **/

    WsArea  selectThreeLevelAreaByCode(String code);
    /**
        * @Author liujie
        * @Description 获取缩放区域code
        * @Date 2020/7/22 15:56
        * @Param [zoom, longitude, latitude]
        * @Return java.lang.String
       **/
    String getZoomCode(String zoom, Double longitude, Double latitude);
}
