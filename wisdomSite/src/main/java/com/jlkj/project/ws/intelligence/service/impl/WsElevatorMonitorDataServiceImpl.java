package com.jlkj.project.ws.intelligence.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMonitorData;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorMonitorDataVO;
import com.jlkj.project.ws.intelligence.mapper.WsElevatorMonitorDataMapper;
import com.jlkj.project.ws.intelligence.service.IWsElevatorMonitorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 施工电梯实时数据管理Service业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsElevatorMonitorDataServiceImpl implements IWsElevatorMonitorDataService
{
    @Autowired
    private WsElevatorMonitorDataMapper wsElevatorMonitorDataMapper;


    /**
     * 查询施工电梯实时数据管理列表
     *
     * @param wsElevatorMonitorData 施工电梯实时数据管理
     * @return 施工电梯实时数据管理
     */
    @DataSource( DataSourceType.SLAVE)
    @Override
    public List<WsElevatorMonitorData> selectWsElevatorMonitorDataList(WsElevatorMonitorData wsElevatorMonitorData)
    {
        return wsElevatorMonitorDataMapper.selectWsElevatorMonitorDataList(wsElevatorMonitorData);
    }

    @DataSource( DataSourceType.SLAVE)
    @Override
    public WsElevatorMonitorDataVO findWsElevatorMonitorDataBySerialNumber(WsElevatorMonitorData wsElevatorMonitorData) {

        if (StringUtils.isNull(wsElevatorMonitorData)||StringUtils.isEmpty(wsElevatorMonitorData.getSerialNumber())) {
            throw  new CustomException("请选择监控设备");
        }
        return wsElevatorMonitorDataMapper.selectWsElevatorMonitorDataBySerialNumber(wsElevatorMonitorData);
    }
}
