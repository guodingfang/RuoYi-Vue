package com.jlkj.project.ws.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsExchangeItemManagement;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.mapper.WsExchangeItemManagementMapper;
import com.jlkj.project.ws.service.IWsExchangeItemManagementService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物品兑换管理
Service业务层处理
 *
 * @author liujie
 * @date 2020-07-23
 */
@Service
public class WsExchangeItemManagementServiceImpl implements IWsExchangeItemManagementService
{
    @Autowired
    private WsExchangeItemManagementMapper wsExchangeItemManagementMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询物品兑换管理

     *
     * @param id 物品兑换管理
ID
     * @return 物品兑换管理

     */
    @Override
    public WsExchangeItemManagement selectWsExchangeItemManagementById(Integer id)
    {
        return wsExchangeItemManagementMapper.selectWsExchangeItemManagementById(id);
    }

    /**
     * 查询物品兑换管理
列表
     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 物品兑换管理

     */
    @Override
    public List<WsExchangeItemManagement> selectWsExchangeItemManagementList(WsExchangeItemManagement wsExchangeItemManagement)
    {

        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsExchangeItemManagement)||StringUtils.isNull(wsExchangeItemManagement.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        return wsExchangeItemManagementMapper.selectWsExchangeItemManagementList(wsExchangeItemManagement,projectScope);
    }

    /**
     * 新增物品兑换管理

     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 结果
     */
    @Override
    public int insertWsExchangeItemManagement(WsExchangeItemManagement wsExchangeItemManagement)
    {
        if(StringUtils.isNull(wsExchangeItemManagement)) throw new CustomException("物品管理不能为空");
        int count = wsExchangeItemManagementMapper.countItemOfProjectByName(wsExchangeItemManagement);
        if (count>0) throw new CustomException("物品名称重复");
        if(StringUtils.isEmpty(wsExchangeItemManagement.getEnabled())) wsExchangeItemManagement.setEnabled("0");
        return wsExchangeItemManagementMapper.insertWsExchangeItemManagement(wsExchangeItemManagement);
    }

    /**
     * 修改物品兑换管理

     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 结果
     */
    @Override
    public int updateWsExchangeItemManagement(WsExchangeItemManagement wsExchangeItemManagement)
    {
        if(StringUtils.isNull(wsExchangeItemManagement)) throw new CustomException("物品不不能为空");
        WsExchangeItemManagement item = wsExchangeItemManagementMapper.selectWsExchangeItemManagementById(wsExchangeItemManagement.getId());
        if(StringUtils.isNull(item)) throw new CustomException("物品不存在");
        int count = wsExchangeItemManagementMapper.countItemOfProjectByName(wsExchangeItemManagement);
        if (count>0) throw new CustomException("物品名称重复");
        return wsExchangeItemManagementMapper.updateWsExchangeItemManagement(wsExchangeItemManagement);
    }

    /**
     * 批量删除物品兑换管理

     *
     * @param ids 需要删除的物品兑换管理
ID
     * @return 结果
     */
    @Override
    public int deleteWsExchangeItemManagementByIds(Integer[] ids)
    {

        return wsExchangeItemManagementMapper.deleteWsExchangeItemManagementByIds(ids);
    }

    /**
     * 删除物品兑换管理
信息
     *
     * @param id 物品兑换管理
ID
     * @return 结果
     */
    @Override
    public int deleteWsExchangeItemManagementById(Integer id)
    {
        WsExchangeItemManagement wsExchangeItemManagement = wsExchangeItemManagementMapper.selectWsExchangeItemManagementById(id);
        if(StringUtils.isNull(wsExchangeItemManagement)) throw new CustomException("物品不存在");
        return wsExchangeItemManagementMapper.deleteWsExchangeItemManagementById(id);
    }
}
