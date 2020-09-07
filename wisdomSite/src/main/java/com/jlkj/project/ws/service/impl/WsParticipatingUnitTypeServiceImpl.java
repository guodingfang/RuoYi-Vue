package com.jlkj.project.ws.service.impl;

import com.jlkj.project.ws.domain.WsParticipatingUnitType;
import com.jlkj.project.ws.mapper.WsParticipatingUnitTypeMapper;
import com.jlkj.project.ws.service.IWsParticipatingUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参建单位类型 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-02
 */
@Service
public class WsParticipatingUnitTypeServiceImpl implements IWsParticipatingUnitTypeService
{
    @Autowired
    private WsParticipatingUnitTypeMapper wsParticipatingUnitTypeMapper;

    /**
     * 查询参建单位类型
     *
     * @param id 参建单位类型 ID
     * @return 参建单位类型
     */
    @Override
    public WsParticipatingUnitType selectWsParticipatingUnitTypeById(Integer id)
    {
        return wsParticipatingUnitTypeMapper.selectWsParticipatingUnitTypeById(id);
    }

    /**
     * 查询参建单位类型 列表
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 参建单位类型
     */
    @Override
    public List<WsParticipatingUnitType> selectWsParticipatingUnitTypeList(WsParticipatingUnitType wsParticipatingUnitType)
    {
        return wsParticipatingUnitTypeMapper.selectWsParticipatingUnitTypeList(wsParticipatingUnitType);
    }

    /**
     * 新增参建单位类型
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 结果
     */
    @Override
    public int insertWsParticipatingUnitType(WsParticipatingUnitType wsParticipatingUnitType)
    {
        return wsParticipatingUnitTypeMapper.insertWsParticipatingUnitType(wsParticipatingUnitType);
    }

    /**
     * 修改参建单位类型
     *
     * @param wsParticipatingUnitType 参建单位类型
     * @return 结果
     */
    @Override
    public int updateWsParticipatingUnitType(WsParticipatingUnitType wsParticipatingUnitType)
    {
        return wsParticipatingUnitTypeMapper.updateWsParticipatingUnitType(wsParticipatingUnitType);
    }

    /**
     * 批量删除参建单位类型
     *
     * @param ids 需要删除的参建单位类型 ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingUnitTypeByIds(Integer[] ids)
    {
        return wsParticipatingUnitTypeMapper.deleteWsParticipatingUnitTypeByIds(ids);
    }

    /**
     * 删除参建单位类型 信息
     *
     * @param id 参建单位类型 ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingUnitTypeById(Integer id)
    {
        return wsParticipatingUnitTypeMapper.deleteWsParticipatingUnitTypeById(id);
    }
}
