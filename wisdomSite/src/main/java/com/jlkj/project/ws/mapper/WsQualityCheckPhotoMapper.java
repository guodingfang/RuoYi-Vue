package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityCheckPhoto;

/**
 * 质量巡检照片 Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-06
 */
public interface WsQualityCheckPhotoMapper 
{
    /**
     * 查询质量巡检照片 
     * 
     * @param id 质量巡检照片 ID
     * @return 质量巡检照片 
     */
    public WsQualityCheckPhoto selectWsQualityCheckPhotoById(Integer id);

    /**
     * 查询质量巡检照片 列表
     * 
     * @param wsQualityCheckPhoto 质量巡检照片 
     * @return 质量巡检照片 集合
     */
    public List<WsQualityCheckPhoto> selectWsQualityCheckPhotoList(WsQualityCheckPhoto wsQualityCheckPhoto);

    /**
     * 新增质量巡检照片 
     * 
     * @param wsQualityCheckPhoto 质量巡检照片 
     * @return 结果
     */
    public int insertWsQualityCheckPhoto(WsQualityCheckPhoto wsQualityCheckPhoto);

    /**
     * 修改质量巡检照片 
     * 
     * @param wsQualityCheckPhoto 质量巡检照片 
     * @return 结果
     */
    public int updateWsQualityCheckPhoto(WsQualityCheckPhoto wsQualityCheckPhoto);

    /**
     * 删除质量巡检照片 
     * 
     * @param id 质量巡检照片 ID
     * @return 结果
     */
    public int deleteWsQualityCheckPhotoById(Integer id);

    /**
     * 批量删除质量巡检照片 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityCheckPhotoByIds(Integer[] ids);

    public List<WsQualityCheckPhoto> selectWsQualityCheckPhotoByCheckManagerId(WsQualityCheckPhoto qualityCheckPhoto);
}
