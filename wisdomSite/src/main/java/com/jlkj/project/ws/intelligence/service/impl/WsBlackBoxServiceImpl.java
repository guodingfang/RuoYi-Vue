package com.jlkj.project.ws.intelligence.service.impl;

import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import com.jlkj.project.ws.intelligence.mapper.WsBlackBoxMapper;
import com.jlkj.project.ws.intelligence.service.IWsBlackBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 黑匣子信息ervice业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsBlackBoxServiceImpl implements IWsBlackBoxService
{
    @Autowired
    private WsBlackBoxMapper wsBlackBoxMapper;

    @DataSource(value = DataSourceType.SLAVE)
    @Override
    public WsElevatorManagerStatisticsVO statisticsOnline(String[] serialNumbers) {
        if (StringUtils.isNull(serialNumbers)||serialNumbers.length==0) {
            return  new WsElevatorManagerStatisticsVO();
        }
        return wsBlackBoxMapper.statisticsOnline(serialNumbers);
    }

    @DataSource(value = DataSourceType.SLAVE)
    @Override
    public WsBlackBox selectWsBlackBoxBySeriNumberAndType(String serialNumber, String equipmentType) {
        if (StringUtils.isEmpty(equipmentType)) {
            throw  new CustomException("设备类型不能为空");

        }
        if (StringUtils.isEmpty(serialNumber)) {
            throw  new CustomException("设备设备编号不能为空");

        }
        if (IntelligentMonitoringConstants.ELEVATOR_FACE.equals(equipmentType)) {
            equipmentType=IntelligentMonitoringConstants.ELEVATOR;
        }


        return wsBlackBoxMapper.selectWsBlackBoxBySeriNumberAndType(serialNumber,equipmentType);
    }

    @DataSource(value = DataSourceType.SLAVE)
    @Override
    public List<WsBlackBox> selectWsBlackBoxList(WsBlackBox wsBlackBox) {
        return wsBlackBoxMapper.selectWsBlackBoxList(wsBlackBox);
    }
}
