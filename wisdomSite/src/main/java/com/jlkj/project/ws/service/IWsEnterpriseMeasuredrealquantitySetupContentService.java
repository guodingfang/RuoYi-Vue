package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetupContent;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantitySetupVO;

/**
 * 实测实量设置分项工程实测内容 Service接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface IWsEnterpriseMeasuredrealquantitySetupContentService 
{
    /**
     * 查询实测实量设置分项工程实测内容 
     * 
     * @param measuredrealquantitySetupId 分项工程id
     * @return 实测实量设置分项工程实测内容 
     */
    public WsEnterpriseMeasuredrealquantitySetupVO selectWsEnterpriseMeasuredrealquantitySetupContentById(Integer measuredrealquantitySetupId);


    /**
     * 新增实测实量设置分项工程实测内容 
     * 
     * @param wsEnterpriseMeasuredrealquantitySetupVO 实测实量设置分项工程实测DTO
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO);

    /**
     * 编辑保存实测实量设置分项工程实测内容
     * 
     * @param wsEnterpriseMeasuredrealquantitySetupVO 实测实量设置分项工程实测VO
     * @return 结果
     */
    public int setWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO);



    /**
     * 修改实测实量设置分项工程实测内容
     *
     * @param wsEnterpriseMeasuredrealquantitySetupContent 实测实量设置分项工程实测
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantitySetupContent(List<WsEnterpriseMeasuredrealquantitySetupContent> wsEnterpriseMeasuredrealquantitySetupContent);




    /**
     * 删除实测实量设置分项工程实测内容 信息
     * 
     * @param id 实测实量设置分项工程实测内容 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantitySetupContentById(Integer id);
}
