package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsPersonSalaryDetail;
import com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO;
import com.jlkj.project.ws.dto.WsPersonSalaryDetailDTO;

import java.util.List;

/**
 * 工资明细 Service接口
 *
 * @author liujie
 * @date 2020-07-31
 */
public interface IWsPersonSalaryDetailService
{
    /**
     * 查询工资明细
     *
     * @param id 工资明细 ID
     * @return 工资明细
     */
    public WsPersonSalaryDetail selectWsPersonSalaryDetailById(Integer id);

    /**
     * 查询工资明细 列表
     *
     * @param wsPersonSalaryDetailDTO 工资明细
     * @return 工资明细 集合
     */
    public List<WsPersonSalaryDetailVO> selectWsPersonSalaryDetailList(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO);

    /**
     * 新增工资明细
     *
     * @param wsPersonSalaryDetail 工资明细
     * @return 结果
     */
    public int insertWsPersonSalaryDetail(WsPersonSalaryDetail wsPersonSalaryDetail);

    /**
     * 修改工资明细
     *
     * @param wsPersonSalaryDetail 工资明细
     * @return 结果
     */
    public int updateWsPersonSalaryDetail(WsPersonSalaryDetail wsPersonSalaryDetail);

    /**
     * 批量删除工资明细
     *
     * @param ids 需要删除的工资明细 ID
     * @return 结果
     */
    public int deleteWsPersonSalaryDetailByIds(Integer[] ids);

    /**
     * 删除工资明细 信息
     *
     * @param id 工资明细 ID
     * @return 结果
     */
    public int deleteWsPersonSalaryDetailById(Integer id);

    /**
        * @Author liujie
        * @Description 导入人员薪资明细
        * @Date 2020/7/31 15:07
        * @Param [salaryDetails,projectId]
        * @Return int
       **/
    String importPersonSalaryDetail(List<WsPersonSalaryDetail> salaryDetails,Integer projectId);

    /**
     * @Author liujie
     * @Description 统计人员工资明细
     * @Date 2020/7/31 18:42
     * @Param [wsPersonSalaryDetailDTO]
     * @Return com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO
     **/
    WsPersonSalaryDetailVO statisticsWsPersonSalaryDetail(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO);
}
