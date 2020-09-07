package com.jlkj.project.ws.service.impl;

import java.util.List;

import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsSafetyOperationTypeMapper;
import com.jlkj.project.ws.domain.WsSafetyOperationType;
import com.jlkj.project.ws.service.IWsSafetyOperationTypeService;

/**
 * 作业类型 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Service
public class WsSafetyOperationTypeServiceImpl implements IWsSafetyOperationTypeService {
    @Autowired
    private WsSafetyOperationTypeMapper wsSafetyOperationTypeMapper;

    /**
     * 查询作业类型
     *
     * @param id 作业类型 ID
     * @return 作业类型
     */
    @Override
    public WsSafetyOperationType selectWsSafetyOperationTypeById(Integer id) {
        return wsSafetyOperationTypeMapper.selectWsSafetyOperationTypeById(id);
    }

    /**
     * 查询作业类型 列表
     *
     * @param wsSafetyOperationType 作业类型
     * @return 作业类型
     */
    @Override
    public List<WsSafetyOperationType> selectWsSafetyOperationTypeList(WsSafetyOperationType wsSafetyOperationType) {

        SysUser userInfo = SecurityUtils.getUserInfo();
        wsSafetyOperationType.setEnterpriseId(userInfo.getEnterpriseId().intValue());
        return wsSafetyOperationTypeMapper.selectWsSafetyOperationTypeList(wsSafetyOperationType);
    }

    /**
     * 新增作业类型
     *
     * @param wsSafetyOperationType 作业类型
     * @return 结果
     */
    @Override
    public int insertWsSafetyOperationType(WsSafetyOperationType wsSafetyOperationType) {
        List<WsSafetyOperationType> wsSafetyOperationTypes = wsSafetyOperationTypeMapper.selectWsSafetyOperationTypeList(wsSafetyOperationType);
        if (StringUtils.isNotEmpty(wsSafetyOperationTypes)) {
            return 0;
        }
        SysUser userInfo = SecurityUtils.getUserInfo();
        wsSafetyOperationType.setEnterpriseId(userInfo.getEnterpriseId().intValue());
        return wsSafetyOperationTypeMapper.insertWsSafetyOperationType(wsSafetyOperationType);
    }

    /**
     * 修改作业类型
     *
     * @param wsSafetyOperationType 作业类型
     * @return 结果
     */
    @Override
    public int updateWsSafetyOperationType(WsSafetyOperationType wsSafetyOperationType) {

        WsSafetyOperationType safeOperationType = wsSafetyOperationTypeMapper.selectWsSafetyOperationTypeByName(wsSafetyOperationType);
        if (safeOperationType != null) {
            return 0;
        }
        return wsSafetyOperationTypeMapper.updateWsSafetyOperationType(wsSafetyOperationType);
    }


    /**
     * 删除作业类型 信息
     *
     * @param id 作业类型 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyOperationTypeById(Integer id) {
        WsSafetyOperationType wsSafetyOperationType = wsSafetyOperationTypeMapper.selectWsSafetyOperationTypeById(id);
        if (wsSafetyOperationType.getBindingStatus() == 1){
            return 0;
        }
        return wsSafetyOperationTypeMapper.deleteWsSafetyOperationTypeById(id);
    }
}
