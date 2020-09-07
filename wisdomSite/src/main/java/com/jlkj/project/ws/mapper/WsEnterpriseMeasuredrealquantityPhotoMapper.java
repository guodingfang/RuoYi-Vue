package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityPhoto;

/**
 * 实测实量图片Mapper接口
 * 
 * @author jlkj
 * @date 2020-08-20
 */
public interface WsEnterpriseMeasuredrealquantityPhotoMapper 
{
    /**
     * 查询实测实量图片
     * 
     * @param id rasID
     * @return ras
     */
    public WsEnterpriseMeasuredrealquantityPhoto selectWsEnterpriseMeasuredrealquantityPhotoById(Integer id);

    /**
     * 查询实测实量图片列表
     * 
     * @param wsEnterpriseMeasuredrealquantityPhoto ras
     * @return ras集合
     */
    public List<WsEnterpriseMeasuredrealquantityPhoto> selectWsEnterpriseMeasuredrealquantityPhotoList(WsEnterpriseMeasuredrealquantityPhoto wsEnterpriseMeasuredrealquantityPhoto);

    /**
     * 新增实测实量图片
     * 
     * @param wsEnterpriseMeasuredrealquantityPhoto ras
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantityPhoto(WsEnterpriseMeasuredrealquantityPhoto wsEnterpriseMeasuredrealquantityPhoto);

    /**
     * 修改ras
     * 
     * @param wsEnterpriseMeasuredrealquantityPhoto ras
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityPhoto(WsEnterpriseMeasuredrealquantityPhoto wsEnterpriseMeasuredrealquantityPhoto);


}
