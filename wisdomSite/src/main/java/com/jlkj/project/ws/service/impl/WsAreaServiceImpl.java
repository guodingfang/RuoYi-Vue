package com.jlkj.project.ws.service.impl;

import com.jlkj.framework.redis.BaiDuApiUtil;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.mapper.WsAreaMapper;
import com.jlkj.project.ws.service.IWsAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-02
 */
@Service
public class WsAreaServiceImpl implements IWsAreaService
{
    @Autowired
    private WsAreaMapper wsAreaMapper;

    /**
     * 查询区域
     *
     * @param id 区域 ID
     * @return 区域
     */
    @Override
    public WsArea selectWsAreaById(Integer id)
    {
        return wsAreaMapper.selectWsAreaById(id);
    }

    /**
     * 查询区域 列表
     *
     * @param wsArea 区域
     * @return 区域
     */
    @Override
    public List<WsArea> selectWsAreaList(WsArea wsArea)
    {
        return wsAreaMapper.selectWsAreaList(wsArea);
    }

    /**
     * 新增区域
     *
     * @param wsArea 区域
     * @return 结果
     */
    @Override
    public int insertWsArea(WsArea wsArea)
    {
        return wsAreaMapper.insertWsArea(wsArea);
    }

    /**
     * 修改区域
     *
     * @param wsArea 区域
     * @return 结果
     */
    @Override
    public int updateWsArea(WsArea wsArea)
    {
        return wsAreaMapper.updateWsArea(wsArea);
    }

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的区域 ID
     * @return 结果
     */
    @Override
    public int deleteWsAreaByIds(Integer[] ids)
    {
        return wsAreaMapper.deleteWsAreaByIds(ids);
    }

    /**
     * 删除区域 信息
     *
     * @param id 区域 ID
     * @return 结果
     */
    @Override
    public int deleteWsAreaById(Integer id)
    {
        return wsAreaMapper.deleteWsAreaById(id);
    }

    @Override
    public WsArea selectThreeLevelAreaByCode(String code) {
        return wsAreaMapper.selectThreeLevelAreaByCode(code);
    }

    @Override
    public String getZoomCode(String zoom, Double longitude, Double latitude) {
        String areaCode = BaiDuApiUtil.parseAreaCode(longitude,latitude);

        String targetCode = null;
        WsArea area= wsAreaMapper.selectThreeLevelAreaByCode(areaCode);
        switch(zoom){
            case "1"://全国
                break;
            case "2":
                targetCode=area.getProvinceCode();//全省
                break;
            case "3":
                targetCode=area.getCityCode();//全市
                break;
            case "4":
                targetCode=area.getCountyCode();//县区
                break;
        }
        return targetCode;
    }
}
