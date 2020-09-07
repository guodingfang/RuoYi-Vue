package com.jlkj.project.ws.service.impl;

import java.util.List;

import com.jlkj.common.exception.CustomException;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityContentMapper;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMeasuredLocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMapper;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实测实量 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantityServiceImpl implements IWsEnterpriseMeasuredrealquantityService {
    @Autowired
    private WsEnterpriseMeasuredrealquantityMapper wsEnterpriseMeasuredrealquantityMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityContentMapper wsEnterpriseMeasuredrealquantityContentMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityMeasuredLocationMapper wsEnterpriseMeasuredrealquantityMeasuredLocationMapper;

    /**
     * 查询实测实量 列表
     *
     * @param wsEnterpriseMeasuredrealquantity 实测实量
     * @return 实测实量
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantity> selectWsEnterpriseMeasuredrealquantityList(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity) {
        return wsEnterpriseMeasuredrealquantityMapper.selectWsEnterpriseMeasuredrealquantityList(wsEnterpriseMeasuredrealquantity);
    }

    /**
     * 新增实测实量
     *
     * @param wsEnterpriseMeasuredrealquantity 实测实量
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWsEnterpriseMeasuredrealquantity(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity) {

        return wsEnterpriseMeasuredrealquantityMapper.insertWsEnterpriseMeasuredrealquantity(wsEnterpriseMeasuredrealquantity);

    }

    /**
     * 修改实测实量
     *
     * @param wsEnterpriseMeasuredrealquantity 实测实量
     * @return 结果
     */
    @Override
    public int updateWsEnterpriseMeasuredrealquantity(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity) {

        return wsEnterpriseMeasuredrealquantityMapper.updateWsEnterpriseMeasuredrealquantity(wsEnterpriseMeasuredrealquantity);
    }

    /**
     * 根据id删除实测实量
     *
     * @param id 实测实量 ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityById(Integer id) {
            int i = wsEnterpriseMeasuredrealquantityContentMapper.deleteWsEnterpriseMeasuredrealquantityContentById(id);
           int i2 = wsEnterpriseMeasuredrealquantityMapper.deleteWsEnterpriseMeasuredrealquantityById(id);
            if (i > 0 && i2 > 0){
                return 1;
            }
            throw new CustomException("删除失败");
    }


    /**
     * 删除实测实量 信息
     *
     * @param id 实测实量 ID
     * @return 结果
     */
    @Override
    public int updateWsEnterpriseMeasuredrealquantityById(Integer id) {
        return wsEnterpriseMeasuredrealquantityMapper.updateWsEnterpriseMeasuredrealquantityById(id);
    }
}
