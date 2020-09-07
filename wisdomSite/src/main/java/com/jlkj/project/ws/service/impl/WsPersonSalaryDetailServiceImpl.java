package com.jlkj.project.ws.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsPersonSalaryDetail;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO;
import com.jlkj.project.ws.dto.WsPersonSalaryDetailDTO;
import com.jlkj.project.ws.mapper.WsPersonSalaryDetailMapper;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import com.jlkj.project.ws.mapper.WsTeamPersonMapper;
import com.jlkj.project.ws.service.IWsPersonSalaryDetailService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 工资明细 Service业务层处理
 *
 * @author liujie
 * @date 2020-07-31
 */
@Service
public class WsPersonSalaryDetailServiceImpl implements IWsPersonSalaryDetailService
{
    @Autowired
    private WsPersonSalaryDetailMapper wsPersonSalaryDetailMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询工资明细
     *
     * @param id 工资明细 ID
     * @return 工资明细
     */
    @Override
    public WsPersonSalaryDetail selectWsPersonSalaryDetailById(Integer id)
    {
        return wsPersonSalaryDetailMapper.selectWsPersonSalaryDetailById(id);
    }

    /**
     * 查询工资明细 列表
     *
     * @param wsPersonSalaryDetailDTO 工资明细
     * @return 工资明细
     */
    @Override
    public List<WsPersonSalaryDetailVO> selectWsPersonSalaryDetailList(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO)
    {
        if(StringUtils.isNull(wsPersonSalaryDetailDTO)) throw new CustomException("请选择月份");
        if(StringUtils.isNull(wsPersonSalaryDetailDTO.getMonth())) throw new CustomException("请选择月份");
        parseFilterDate(wsPersonSalaryDetailDTO);
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsPersonSalaryDetailDTO)||StringUtils.isNull(wsPersonSalaryDetailDTO.getProjectId())){
            projectScope = wsProjectService.selectWsProjectList(null);
        }
        return wsPersonSalaryDetailMapper.selectWsPersonSalaryDetailList(wsPersonSalaryDetailDTO,projectScope);
    }

    private void parseFilterDate(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO) {
        String year = String.valueOf(DateUtil.year(wsPersonSalaryDetailDTO.getMonth()));
        String month = String.valueOf(DateUtil.month(wsPersonSalaryDetailDTO.getMonth())+1);
        Date beginTime = DateUtil.beginOfMonth(wsPersonSalaryDetailDTO.getMonth());
        Date endTime = DateUtil.endOfMonth(wsPersonSalaryDetailDTO.getMonth());
        wsPersonSalaryDetailDTO.setBeginTime(beginTime);
        wsPersonSalaryDetailDTO.setEndTime(endTime);
        wsPersonSalaryDetailDTO.setSalaryMonth(month.length()==1?"0"+month:month);
        wsPersonSalaryDetailDTO.setSalaryYear(year);
    }

    /**
     * 新增工资明细
     *
     * @param wsPersonSalaryDetail 工资明细
     * @return 结果
     */
    @Override
    public int insertWsPersonSalaryDetail(WsPersonSalaryDetail wsPersonSalaryDetail)
    {
        return wsPersonSalaryDetailMapper.insertWsPersonSalaryDetail(wsPersonSalaryDetail);
    }

    /**
     * 修改工资明细
     *
     * @param wsPersonSalaryDetail 工资明细
     * @return 结果
     */
    @Override
    public int updateWsPersonSalaryDetail(WsPersonSalaryDetail wsPersonSalaryDetail)
    {
        return wsPersonSalaryDetailMapper.updateWsPersonSalaryDetail(wsPersonSalaryDetail);
    }

    /**
     * 批量删除工资明细
     *
     * @param ids 需要删除的工资明细 ID
     * @return 结果
     */
    @Override
    public int deleteWsPersonSalaryDetailByIds(Integer[] ids)
    {
        return wsPersonSalaryDetailMapper.deleteWsPersonSalaryDetailByIds(ids);
    }

    /**
     * 删除工资明细 信息
     *
     * @param id 工资明细 ID
     * @return 结果
     */
    @Override
    public int deleteWsPersonSalaryDetailById(Integer id)
    {
        return wsPersonSalaryDetailMapper.deleteWsPersonSalaryDetailById(id);
    }

    @Override
    public String importPersonSalaryDetail(List<WsPersonSalaryDetail> salaryDetails,Integer projectId) {
        if (StringUtils.isEmpty(salaryDetails)) throw new CustomException("导入工资明细数据不能为空！");

        WsProject wsProject = wsProjectMapper.selectWsProjectById(projectId);
        if (StringUtils.isNull(wsProject)) throw new CustomException("人员所属项目不存在");

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (WsPersonSalaryDetail salary : salaryDetails)
        {
            try
            {
                //验证数据格式
                if (!isCorrect(salary)) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、人员 ").append(salary.getPersonName()).append(" :人员身份证、年份、月份、工资单号格式错误");
                    continue;
                }
                salary.setProjectId(wsProject.getId());
                //验证班组人员是否存在
                if(!isExisted(salary)){
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、人员 ").append(salary.getPersonName()).append(" :项目人员不存在");
                    continue;
                }

                //执行插入或更新
                WsPersonSalaryDetail wsPersonSalaryDetail = wsPersonSalaryDetailMapper.selectWsPersonSalaryDetailByPayrollNumber(salary.getPayrollNumber());
                salary.formatCorrect();
                if(StringUtils.isNotNull(wsPersonSalaryDetail)){
                    salary.setId(wsPersonSalaryDetail.getId());
                    wsPersonSalaryDetailMapper.updateWsPersonSalaryDetail(salary);
                    successNum++;
                }else{
                    wsPersonSalaryDetailMapper.insertWsPersonSalaryDetail(salary);
                    successNum++;
                }

            }
            catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、人员 " + salary.getPersonName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }

    private boolean isExisted(WsPersonSalaryDetail salary) {
        WsTeamPerson wsTeamPerson = new WsTeamPerson();
        wsTeamPerson.setProjectId(salary.getProjectId());
        wsTeamPerson.setIdNumber(salary.getIdNumber());
        List<WsTeamPerson> list = wsTeamPersonMapper.findWsTeamPersonInProject(wsTeamPerson);
        return StringUtils.isNotEmpty(list);
    }

    private boolean isCorrect(WsPersonSalaryDetail salary) {
        if(StringUtils.isEmpty(salary.getIdNumber())) return false;
        if(!NumberUtil.isNumber(salary.getSalaryYear()))  return false;
        if(Integer.valueOf(salary.getSalaryYear())<2000||Integer.valueOf(salary.getSalaryYear())>2999) return false;
        if(!NumberUtil.isNumber(salary.getSalaryMonth())) return  false;
        if(Integer.valueOf(salary.getSalaryMonth())>12||Integer.valueOf(salary.getSalaryMonth())<1) return false;
        return !StringUtils.isEmpty(salary.getPayrollNumber());
    }

    @Override
    public WsPersonSalaryDetailVO statisticsWsPersonSalaryDetail(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO) {
        if(StringUtils.isNull(wsPersonSalaryDetailDTO)) throw new CustomException("请选择月份");
        if(StringUtils.isNull(wsPersonSalaryDetailDTO.getMonth())) throw new CustomException("请选择月份");
        parseFilterDate(wsPersonSalaryDetailDTO);
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsPersonSalaryDetailDTO)||StringUtils.isNull(wsPersonSalaryDetailDTO.getProjectId())){
            projectScope = wsProjectService.selectWsProjectList(null);
        }

        return wsPersonSalaryDetailMapper.statisticsWsPersonSalaryDetail(wsPersonSalaryDetailDTO,projectScope);
    }
}
