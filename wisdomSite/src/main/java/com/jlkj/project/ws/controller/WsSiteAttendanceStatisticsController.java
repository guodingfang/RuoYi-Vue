package com.jlkj.project.ws.controller;

import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO;
import com.jlkj.project.ws.dto.WsSiteAttendanceStatisticsDTO;
import com.jlkj.project.ws.service.IWsAttendanceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工地考勤首页统计 Controller
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Api(value = "工地考勤首页统计接口",tags = "工地考勤首页统计页面")
@RestController
@RequestMapping("/ws/attendanceStatistics")
public class WsSiteAttendanceStatisticsController extends BaseController
{
    @Autowired
    private IWsAttendanceRecordService wsAttendanceRecordService;

    /**
     * 获取首页 人员在岗，在册，在场，出勤数据
     * 公司级与项目级账号
     */
    @ApiOperation("获取首页 人员在岗，在册，在场，出勤数据")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/personnelNumber")
    public AjaxResult getPersonnelNumber(WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO)
    {
        attendanceStatisticsDTO = wsAttendanceRecordService.selectPersonnelNumber();
        return AjaxResult.success(attendanceStatisticsDTO);
    }

    /**
     * 获取近7日 在场，出勤 数
     * 公司级账号
     */
    @ApiOperation("获取近7日 在场，出勤 数")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/bePresentORattendanceToday")
    public AjaxResult getBePresentORattendanceToday()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectBePresentORattendanceToday();
        return AjaxResult.success(list);
    }

    /**
     * 各项目在场人员占比前十
     */
    @ApiOperation("各项目在场人员占比前十")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/personnelProjectProportion")
    public AjaxResult getPersonnelProjectProportion()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectPersonnelProjectProportion();
        return AjaxResult.success(list);
    }

    /**
     * 各项目在场人员占比前十--查询全部
     */
    @ApiOperation("各项目在场人员占比前十--查询全部")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/personnelProjectProportionWhole")
    public AjaxResult getPersonnelProjectProportionWhole()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectPersonnelProjectProportionWhole();
        return AjaxResult.success(list);
    }

    /**
     * 下属项目出勤统计--集团级
     */
    @ApiOperation("下属项目出勤统计--集团级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项：日期条件：randomData",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/projectAttendance")
    public AjaxResult getProjectAttendance(@Param("randomData") String randomData)
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectProjectAttendance(randomData);
        return AjaxResult.success(list);
    }

    /**
     * 现场人员变化趋势图--项目级
     */
    @ApiOperation("现场人员变化趋势图--项目级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/scenePersonnelChangeTrend")
    public AjaxResult getScenePersonnelChangeTrend()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectPersonAttendanceTrend(new WsAttendanceRecordVO());
        return AjaxResult.success(list);
    }

    /**
     * 近7日人员出勤--项目级
     */
    @ApiOperation("近7日人员出勤--项目级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/sevenDaysPersonnelAttendance")
    public AjaxResult getSevenDaysPersonnelAttendance()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectSevenDaysPersonnelAttendance();
        return AjaxResult.success(list);
    }

    /**
     * 现场工种前十统计--项目级
     */
    @ApiOperation("现场工种前十统计--项目级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/fieldWork")
    public AjaxResult getFieldWork()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectFieldWork();
        return AjaxResult.success(list);
    }

    /**
     * 现场工种前十统计--项目级 --查询全部
     */
    @ApiOperation("现场工种前十统计--项目级 --查询全部")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/fieldWorkWhole")
    public AjaxResult getFieldWorkWhole()
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectFieldWorkWhole();
        return AjaxResult.success(list);
    }

    /**
     *建筑工人分析前十--项目级
     */
    @ApiOperation("建筑工人分析前十--项目级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项：条件：type（1:地区;2:年龄；3：性别；4：工种）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/constructionWorker")
    public AjaxResult getConstructionWorker(WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO)
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectConstructionWorker(attendanceStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     *建筑工人分析前十--项目级--查询全部
     */
    @ApiOperation("建筑工人分析前十--项目级--查询全部")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项：条件：type（1:地区;2:年龄；3：性别；4：工种）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/constructionWorkerWhole")
    public AjaxResult getConstructionWorkerWhole(WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO)
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectConstructionWorkerWhole(attendanceStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 下属参建单位统计--项目级
     */
    @ApiOperation("下属参建单位统计--项目级")
    @ApiImplicitParam(name = "attendanceStatisticsDTO", value = "工地考勤首页统计实体项：日期条件：randomData ",required = false)
    @PreAuthorize("@ss.hasPermi('ws:attendanceStatistics:query')")
    @GetMapping(value = "/participatingUnits")
    public AjaxResult getParticipatingUnits(@Param("randomData") String randomData)
    {
        List<WsSiteAttendanceStatisticsDTO> list = wsAttendanceRecordService.selectParticipatingUnits(randomData);
        return AjaxResult.success(list);
    }
}
