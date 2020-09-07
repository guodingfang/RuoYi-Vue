package com.jlkj.project.ws.intelligence.service.impl;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.mapper.WsProjectFieldLayoutMapper;
import com.jlkj.project.ws.intelligence.service.IWsProjectFieldLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工程场布图管理 Service业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsProjectFieldLayoutServiceImpl implements IWsProjectFieldLayoutService
{
    @Autowired
    private WsProjectFieldLayoutMapper wsProjectFieldLayoutMapper;

    /**
     * 查询工程场布图管理
     *
     * @param id 工程场布图管理 ID
     * @return 工程场布图管理
     */
    @Override
    public WsProjectFieldLayout selectWsProjectFieldLayoutById(Integer id)
    {
        return wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutById(id);
    }

    /**
     * 查询工程场布图管理 列表
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 工程场布图管理
     */
    @Override
    public List<WsProjectFieldLayout> selectWsProjectFieldLayoutList(WsProjectFieldLayout wsProjectFieldLayout)
    {
        return wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutList(wsProjectFieldLayout);
    }

    /**
     * 新增工程场布图管理
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout)
    {
        WsProjectFieldLayout layout = wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutByProjecAndType(wsProjectFieldLayout.getProjectId(), wsProjectFieldLayout.getEquipmentType());
        if(StringUtils.isNotNull(layout)){
            wsProjectFieldLayoutMapper.deleteWsProjectFieldLayoutById(layout.getId());
        }
        wsProjectFieldLayout.setIsDelete("0");
        return wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(wsProjectFieldLayout);
    }

    /**
     * 修改工程场布图管理
     *
     * @param wsProjectFieldLayout 工程场布图管理
     * @return 结果
     */
    @Override
    public int updateWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout)
    {
        WsProjectFieldLayout layout = wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutByProjecAndType(wsProjectFieldLayout.getProjectId(), wsProjectFieldLayout.getEquipmentType());
        if(StringUtils.isNotNull(layout)){
            wsProjectFieldLayoutMapper.deleteWsProjectFieldLayoutById(layout.getId());
        }
        wsProjectFieldLayout.setIsDelete("0");
        return wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(wsProjectFieldLayout);
    }

    /**
     * 批量删除工程场布图管理
     *
     * @param ids 需要删除的工程场布图管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectFieldLayoutByIds(Integer[] ids)
    {
        return wsProjectFieldLayoutMapper.deleteWsProjectFieldLayoutByIds(ids);
    }

    /**
     * 删除工程场布图管理 信息
     *
     * @param id 工程场布图管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectFieldLayoutById(Integer id)
    {
        return wsProjectFieldLayoutMapper.deleteWsProjectFieldLayoutById(id);
    }

    @Override
    public WsProjectFieldLayout selectWsProjectFieldLayoutByProjectAndType(Integer projectId, Integer equipmentType) {
        WsProjectFieldLayout wsProjectFieldLayout = new WsProjectFieldLayout();
        WsProjectFieldLayout fieldLayout = wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutByProjectAndType(projectId,equipmentType);
        if(fieldLayout != null){
            wsProjectFieldLayout.setId(fieldLayout.getId());
            wsProjectFieldLayout.setHeight(fieldLayout.getHeight());
            wsProjectFieldLayout.setWidth(fieldLayout.getWidth());
            wsProjectFieldLayout.setFieldlayoutAddress(fieldLayout.getFieldlayoutAddress());
        }
        return wsProjectFieldLayout;
    }
}
