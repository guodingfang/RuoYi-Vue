package com.jlkj.project.ws.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecord;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecordDTO;
import com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO;
import com.jlkj.project.ws.mapper.WsExchangeItemManagementMapper;
import com.jlkj.project.ws.mapper.WsTeamPersonExchangeRecordMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import com.jlkj.project.ws.service.IWsTeamPersonExchangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 班组人员兑换记录Service业务层处理
 *
 * @author liujie
 * @date 2020-07-23
 */
@Service
public class WsTeamPersonExchangeRecordServiceImpl implements IWsTeamPersonExchangeRecordService
{
    @Autowired
    private WsTeamPersonExchangeRecordMapper wsTeamPersonExchangeRecordMapper;

    @Autowired
    private WsExchangeItemManagementMapper wsExchangeItemManagementMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询班组人员兑换记录
     *
     * @param id 班组人员兑换记录ID
     * @return 班组人员兑换记录
     */
    @Override
    public WsTeamPersonExchangeRecord selectWsTeamPersonExchangeRecordById(Integer id)
    {
        return wsTeamPersonExchangeRecordMapper.selectWsTeamPersonExchangeRecordById(id);
    }

    /**
     * 查询班组人员兑换记录列表
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 班组人员兑换记录
     */
    @Override
    public List<WsTeamPersonExchangeRecordVO> selectWsTeamPersonExchangeRecordList(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsTeamPersonExchangeRecord)||StringUtils.isNull(wsTeamPersonExchangeRecord.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        return wsTeamPersonExchangeRecordMapper.selectWsTeamPersonExchangeRecordList(wsTeamPersonExchangeRecord,projectScope);
    }

    /**
     * 新增班组人员兑换记录
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 结果
     */
    @Override
    public int insertWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        if(StringUtils.isNull(wsTeamPersonExchangeRecord)) throw new CustomException("兑换信息不能为空");

        return wsTeamPersonExchangeRecordMapper.insertWsTeamPersonExchangeRecord(wsTeamPersonExchangeRecord);
    }

    /**
     * 修改班组人员兑换记录
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 结果
     */
    @Override
    public int updateWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        return wsTeamPersonExchangeRecordMapper.updateWsTeamPersonExchangeRecord(wsTeamPersonExchangeRecord);
    }

    /**
     * 批量删除班组人员兑换记录
     *
     * @param ids 需要删除的班组人员兑换记录ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonExchangeRecordByIds(Integer[] ids)
    {
        return wsTeamPersonExchangeRecordMapper.deleteWsTeamPersonExchangeRecordByIds(ids);
    }

    /**
     * 删除班组人员兑换记录信息
     *
     * @param id 班组人员兑换记录ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonExchangeRecordById(Integer id)
    {
        return wsTeamPersonExchangeRecordMapper.deleteWsTeamPersonExchangeRecordById(id);
    }

    @Transactional
    @Override
    public int bacthWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecordDTO wsTeamPersonExchangeRecordDTO) {
        int rows =0;
        int compare = 0 ;
        if(StringUtils.isNull(wsTeamPersonExchangeRecordDTO)) throw new CustomException("请选择兑换物品");
        if(StringUtils.isEmpty(wsTeamPersonExchangeRecordDTO.getExchanges())) throw new CustomException("请选择兑换物品");
        List<WsTeamPersonExchangeRecord> exchanges = wsTeamPersonExchangeRecordDTO.getExchanges();
        exchanges.forEach(x->{
            x.setProjectId(wsTeamPersonExchangeRecordDTO.getProjectId());
            x.setPersonId(wsTeamPersonExchangeRecordDTO.getPersonId());
        });
        for (WsTeamPersonExchangeRecord exchange : exchanges) {
            if(exchange.getExchangeMount()==0) continue;
            int count = wsExchangeItemManagementMapper.getItemStockByName(wsTeamPersonExchangeRecordDTO.getProjectId(), exchange.getItemName());
            if(count<exchange.getExchangeMount())  throw new CustomException("物品名称："+exchange.getItemName()+"，库存不足！");
            rows += wsExchangeItemManagementMapper.updateItemStockByName(exchange);
            compare++;
            rows += wsTeamPersonExchangeRecordMapper.insertWsTeamPersonExchangeRecord(exchange);
            compare++;
        }
        if(rows!=compare){
            throw new CustomException("物品兑换失败，请联系管理员");
        }
        return rows>0?1:0;
    }

    @Override
    public WsTeamPersonExchangeRecordVO statisticstPersonExchangedPoints(Integer personId) {
        return wsTeamPersonExchangeRecordMapper.statisticstPersonExchangedPoints(personId);
    }
}
