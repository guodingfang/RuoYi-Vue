package com.jlkj.project.ws.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsAttendanceRegisterMapper;
import com.jlkj.project.ws.domain.WsAttendanceRegister;
import com.jlkj.project.ws.service.IWsAttendanceRegisterService;

/**
 * 人员考勤平台注册Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Service
public class WsAttendanceRegisterServiceImpl implements IWsAttendanceRegisterService
{
    @Autowired
    private WsAttendanceRegisterMapper wsAttendanceRegisterMapper;

    /**
     * 查询人员考勤平台注册
     *
     * @param id 人员考勤平台注册ID
     * @return 人员考勤平台注册
     */
    @Override
    public WsAttendanceRegister selectWsAttendanceRegisterById(Integer id)
    {
        return wsAttendanceRegisterMapper.selectWsAttendanceRegisterById(id);
    }

    /**
     * 查询人员考勤平台注册列表
     *
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 人员考勤平台注册
     */
    @Override
    public List<WsAttendanceRegister> selectWsAttendanceRegisterList(WsAttendanceRegister wsAttendanceRegister)
    {
        return wsAttendanceRegisterMapper.selectWsAttendanceRegisterList(wsAttendanceRegister);
    }

    /**
     * 新增人员考勤平台注册
     *
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 结果
     */
    @Override
    public int insertWsAttendanceRegister(WsAttendanceRegister wsAttendanceRegister)
    {
        return wsAttendanceRegisterMapper.insertWsAttendanceRegister(wsAttendanceRegister);
    }

    /**
     * 修改人员考勤平台注册
     *
     * @param wsAttendanceRegister 人员考勤平台注册
     * @return 结果
     */
    @Override
    public int updateWsAttendanceRegister(WsAttendanceRegister wsAttendanceRegister)
    {
        return wsAttendanceRegisterMapper.updateWsAttendanceRegister(wsAttendanceRegister);
    }

    /**
     * 批量删除人员考勤平台注册
     *
     * @param ids 需要删除的人员考勤平台注册ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceRegisterByIds(Integer[] ids)
    {
        return wsAttendanceRegisterMapper.deleteWsAttendanceRegisterByIds(ids);
    }

    /**
     * 删除人员考勤平台注册信息
     *
     * @param id 人员考勤平台注册ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceRegisterById(Integer id)
    {
        return wsAttendanceRegisterMapper.deleteWsAttendanceRegisterById(id);
    }
}
