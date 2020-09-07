package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsParticipatingUnitType;

import java.util.List;

/**
 * 参建单位类型 Service接口
 *
 * @author jlkj
 * @date 2020-07-02
 */
public interface IWsParticipatingUnitTypeService
{
    /**
     * 查询参建单位类型
     *
     * @param id 参建单位类型 ID
     * @return 参建单位类型
     */
    public WsParticipatingUnitType selectWsParticipatingUnitTypeById(Integer id);

    /**
     * 查询参建单位类型 列表
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 参建单位类型 集合
     */
    public List<WsParticipatingUnitType> selectWsParticipatingUnitTypeList(WsParticipatingUnitType wsParticipatingUnitType);

    /**
     * 新增参建单位类型
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 结果
     */
    public int insertWsParticipatingUnitType(WsParticipatingUnitType wsParticipatingUnitType);

    /**
     * 修改参建单位类型
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 结果
     */
    public int updateWsParticipatingUnitType(WsParticipatingUnitType wsParticipatingUnitType);

    /**
     * 批量删除参建单位类型
     *
     * @param ids 需要删除的参建单位类型 ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitTypeByIds(Integer[] ids);

    /**
     * 删除参建单位类型 信息
     *
     * @param id 参建单位类型 ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitTypeById(Integer id);
}
