package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域 Mapper接口
 *
 * @author jlkj
 * @date 2020-07-02
 */
public interface WsAreaMapper
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
     * 删除区域
     *
     * @param id 区域 ID
     * @return 结果
     */
    public int deleteWsAreaById(Integer id);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsAreaByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 根据code获取自己区域列表
        * @Date 2020/7/14 13:50
        * @Param [wsArea]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsArea>
       **/
    List<WsArea> selectChidrenAreaList(@Param("code") String code);

    /**
        * @Author liujie
        * @Description 根据县区code获取三级省市区
        * @Date 2020/7/21 9:50
        * @Param
        * @Return
       **/

    WsArea  selectThreeLevelAreaByCode(String code);
}
