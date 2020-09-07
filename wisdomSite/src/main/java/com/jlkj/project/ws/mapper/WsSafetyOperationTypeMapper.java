package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyOperationType;

/**
 * 作业类型 Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface WsSafetyOperationTypeMapper 
{
    /**
     * 查询作业类型 
     * 
     * @param id 作业类型 ID
     * @return 作业类型 
     */
    public WsSafetyOperationType selectWsSafetyOperationTypeById(Integer id);



    /**
     * 查询作业类型
     *
     * @param wsSafetyOperationType 作业类型
     * @return 作业类型
     */
    public WsSafetyOperationType selectWsSafetyOperationTypeByName(WsSafetyOperationType wsSafetyOperationType);


    /**
     * 查询作业类型 列表
     * 
     * @param wsSafetyOperationType 作业类型 
     * @return 作业类型 集合
     */
    public List<WsSafetyOperationType> selectWsSafetyOperationTypeList(WsSafetyOperationType wsSafetyOperationType);

    /**
     * 新增作业类型 
     * 
     * @param wsSafetyOperationType 作业类型 
     * @return 结果
     */
    public int insertWsSafetyOperationType(WsSafetyOperationType wsSafetyOperationType);

    /**
     * 修改作业类型 
     * 
     * @param wsSafetyOperationType 作业类型 
     * @return 结果
     */
    public int updateWsSafetyOperationType(WsSafetyOperationType wsSafetyOperationType);

    /**
     * 删除作业类型 
     * 
     * @param id 作业类型 ID
     * @return 结果
     */
    public int deleteWsSafetyOperationTypeById(Integer id);

}
