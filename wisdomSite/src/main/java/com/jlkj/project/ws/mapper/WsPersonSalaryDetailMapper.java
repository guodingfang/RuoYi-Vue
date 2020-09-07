package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsPersonSalaryDetail;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO;
import com.jlkj.project.ws.dto.WsPersonSalaryDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工资明细 Mapper接口
 *
 * @author liujie
 * @date 2020-07-31
 */
public interface WsPersonSalaryDetailMapper
{
    /**
     * 查询工资明细
     *
     * @param id 工资明细 ID
     * @return 工资明细
     */
    public WsPersonSalaryDetail selectWsPersonSalaryDetailById(Integer id);
    /**
        * @Author liujie
        * @Description 通过工资单号获取工资明细
        * @Date 2020/7/31 16:22
        * @Param [payrollNumber]
        * @Return com.jlkj.project.ws.domain.WsPersonSalaryDetail
       **/

    WsPersonSalaryDetail selectWsPersonSalaryDetailByPayrollNumber(String payrollNumber);

    /**
     * 查询工资明细 列表
     *
     * @param wsPersonSalaryDetailDTO 工资明细,dataScope 项目范围
     * @return 工资明细 集合
     */
    public List<WsPersonSalaryDetailVO> selectWsPersonSalaryDetailList(@Param("wsPersonSalaryDetailDTO") WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO,@Param("dataScope")  List<WsProject>dataScope);

/**
    * @Author liujie
    * @Description 统计人员工资明细
    * @Date 2020/7/31 18:42
    * @Param [wsPersonSalaryDetailDTO]
    * @Return com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO,dataScope
   **/
    WsPersonSalaryDetailVO statisticsWsPersonSalaryDetail(@Param("wsPersonSalaryDetailDTO") WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO,@Param("dataScope")  List<WsProject>dataScope);
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
     * 删除工资明细
     *
     * @param id 工资明细 ID
     * @return 结果
     */
    public int deleteWsPersonSalaryDetailById(Integer id);

    /**
     * 批量删除工资明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsPersonSalaryDetailByIds(Integer[] ids);


}
