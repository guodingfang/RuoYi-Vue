package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.dto.WsSiteAttendanceStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 班组人员 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsSiteAttendanceStatisticsMapper
{

    /**
     * 查询人员在场数
     * @return
     */
    WsSiteAttendanceStatisticsDTO selectPersonnelNumberOnDutyJc(Integer[] projectIds);
    WsSiteAttendanceStatisticsDTO selectPersonnelNumberOnDutyCc(Integer[] projectIds);

    WsSiteAttendanceStatisticsDTO selectPersonnelNumberAttendanceToday(@Param("projectIds") Integer[] projectIds);

    WsSiteAttendanceStatisticsDTO selectPersonnelNumberBePresent(Integer[] projectIds);

    WsSiteAttendanceStatisticsDTO selectPersonnelNumberRegistered(Integer[] projectIds);

    List<WsSiteAttendanceStatisticsDTO> selectParticipatingUnitNumber(Integer[] projectIds);

    List<WsSiteAttendanceStatisticsDTO> selectBePresentORattendanceToday(Integer[] projectIds);

    List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportion(Integer[] projectIds);

    WsSiteAttendanceStatisticsDTO selectProjectAttendance(Integer projectId,String randomData);

    List<WsSiteAttendanceStatisticsDTO> selectScenePersonnelChangeTrend(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectSevenDaysPersonnelAttendance(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectFieldWork(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerRegion(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerAge(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerSex(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerProfession(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectParticipatingUnits(Integer projectId, String randomData);

    List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportionWhole(Integer[] projectIds);

    List<WsSiteAttendanceStatisticsDTO> selectFieldWorkWhole(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerRegionWhole(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerAgeWhole(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerSexWhole(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerProfessionWhole(Integer projectId);

    List<WsSiteAttendanceStatisticsDTO> selectProjectAttendanceProject(Integer[] projectIds);

    WsSiteAttendanceStatisticsDTO selectBePresentORattendanceTodayCq(@Param("projectIds") Integer[] projectIds,@Param("bePresentORattendanceTodayTime") Date bePresentORattendanceTodayTime);
}
