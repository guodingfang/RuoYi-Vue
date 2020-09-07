package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsAttendanceRegister;

/**
 * 人员考勤平台注册Service接口
 * 
 * @author gaowei
 * @date 2020-06-23
 */
public interface IWsAttendanceRegisterService 
{
    /**
     * 查询人员考勤平台注册
     * 
     * @param id 人员考勤平台注册ID
     * @return 人员考勤平台注册
     */
    public WsAttendanceRegister selectWsAttendanceRegisterById(Integer id);

    /**
     * 查询人员考勤平台注册列表
     * 
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 人员考勤平台注册集合
     */
    public List<WsAttendanceRegister> selectWsAttendanceRegisterList(WsAttendanceRegister wsAttendanceRegister);

    /**
     * 新增人员考勤平台注册
     * 
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 结果
     */
    public int insertWsAttendanceRegister(WsAttendanceRegister wsAttendanceRegister);

    /**
     * 修改人员考勤平台注册
     * 
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 结果
     */
    public int updateWsAttendanceRegister(WsAttendanceRegister wsAttendanceRegister);

    /**
     * 批量删除人员考勤平台注册
     * 
     * @param ids 需要删除的人员考勤平台注册ID
     * @return 结果
     */
    public int deleteWsAttendanceRegisterByIds(Integer[] ids);

    /**
     * 删除人员考勤平台注册信息
     * 
     * @param id 人员考勤平台注册ID
     * @return 结果
     */
    public int deleteWsAttendanceRegisterById(Integer id);
}
