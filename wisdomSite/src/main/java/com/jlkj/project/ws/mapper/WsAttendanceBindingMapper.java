package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsAttendanceBinding;
import com.jlkj.project.ws.domain.WsProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考勤绑定 Mapper接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface WsAttendanceBindingMapper
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
     * @param wsAttendanceBinding 考勤绑定,dataScope 项目范围
     * @return 考勤绑定 集合
     */
    public List<WsAttendanceBinding> selectWsAttendanceBindingList(@Param("wsAttendanceBinding") WsAttendanceBinding wsAttendanceBinding,@Param("dataScope")  List<WsProject>dataScope);

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
     * 删除考勤绑定
     *
     * @param id 考勤绑定 ID
     * @return 结果
     */
    public int deleteWsAttendanceBindingById(Integer id);

    /**
     * 批量删除考勤绑定
     *
     * @param guids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsAttendanceBindingByGuids(String[] guids);

/**
    * @Author liujie
    * @Description 通过人员唯一标识和设备序列查询考勤绑定记录数
    * @Date 2020/6/29 10:19
    * @Param [guid, serial_number]
    * @Return int
   **/
    int countWsAttdanceBindingByGuidAndkey(@Param("guid") String guid, @Param("serialNumber") String serialNumber);


    /**
     * 批量插入人员授权绑定
     * @param insertList
     */
    int batchAdd( List<WsAttendanceBinding> insertList);

    /**
        * @Author liujie
        * @Description 批量更新人员授权绑定
        * @Date 2020/6/29 10:33
        * @Param [updateList]
        * @Return void
       **/
    int batchUpdate(List<WsAttendanceBinding> updateList);
    /**
        * @Author liujie
        * @Description 根据guid集合获取授权Map
        * @Date 2020/6/29 16:40
        * @Param [guids]
        * @Return Map<String,Object>
       **/
    Map<String,Object> selectAuthDeviceByGuids(String[] guids);
}
