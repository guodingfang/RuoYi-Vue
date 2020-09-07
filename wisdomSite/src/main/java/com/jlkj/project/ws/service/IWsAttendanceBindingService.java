package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsAttendanceBinding;
import com.jlkj.project.ws.dto.WsAttendanceBindingDTO;

import java.util.List;

/**
 * 考勤绑定 Service接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface IWsAttendanceBindingService
{
    /**
     * 查询考勤绑定
     *
     * @param id 考勤绑定 ID
     * @return 考勤绑定
     */
    public WsAttendanceBinding selectWsAttendanceBindingById(Integer id);

    /**
     * 查询考勤绑定 列表
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 考勤绑定 集合
     */
    public List<WsAttendanceBinding> selectWsAttendanceBindingList(WsAttendanceBinding wsAttendanceBinding);

    /**
     * 新增考勤绑定
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 结果
     */
    public int insertWsAttendanceBinding(WsAttendanceBinding wsAttendanceBinding);

    /**
     * 修改考勤绑定
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 结果
     */
    public int updateWsAttendanceBinding(WsAttendanceBinding wsAttendanceBinding);

    /**
     * 批量删除考勤绑定
     *
     * @param guids 需要删除的考勤绑定 ID
     * @return 结果
     */
    public int deleteWsAttendanceBindingByGuids(String[] guids);

    /**
     * 删除考勤绑定 信息
     *
     * @param id 考勤绑定 ID
     * @return 结果
     */
    public int deleteWsAttendanceBindingById(Integer id);

    /**
     * 批量插入打卡机绑定
     * @param wsAttendanceBindingDTO
     * @return
     */
    int batchAddWsAttendanceBinding(WsAttendanceBindingDTO wsAttendanceBindingDTO);
}
