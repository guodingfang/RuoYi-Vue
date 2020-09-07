package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyPhoto;

/**
 * 安全图片管理Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface WsSafetyPhotoMapper 
{
    /**
     * 查询安全图片管理
     * 
     * @param id 安全图片管理ID
     * @return 安全图片管理
     */
    public WsSafetyPhoto selectWsSafetyPhotoById(Integer id);

    /**
     * 查询安全图片管理列表
     * 
     * @param wsSafetyPhoto 安全图片管理
     * @return 安全图片管理集合
     */
    public List<WsSafetyPhoto> selectWsSafetyPhotoList(WsSafetyPhoto wsSafetyPhoto);

    /**
     * 新增安全图片管理
     * 
     * @param wsSafetyPhoto 安全图片管理
     * @return 结果
     */
    public int insertWsSafetyPhoto(WsSafetyPhoto wsSafetyPhoto);

    /**
     * 修改安全图片管理
     * 
     * @param wsSafetyPhoto 安全图片管理
     * @return 结果
     */
    public int updateWsSafetyPhoto(WsSafetyPhoto wsSafetyPhoto);

    /**
     * 删除安全图片管理
     * 
     * @param id 安全图片管理ID
     * @return 结果
     */
    public int deleteWsSafetyPhotoById(Integer id);

    /**
     * 批量删除安全图片管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsSafetyPhotoByIds(Integer[] ids);
}
