package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsSitePartitionItem;
import org.apache.ibatis.annotations.Param;

public interface WsSitePartitionItemMapper {
    /**
     * 判断是否存在工地分区id
     * @param sitePartitionId
     * @return
     */
    public String ExistsSitePartitionItem (Integer sitePartitionId);
    /**
        * @Author liujie
        * @Description 插入分区名目绑定
        * @Date 2020/8/20 17:58
        * @Param [wsSitePartitionItem]
        * @Return int
       **/
    int insertWsSitePartitionItem(WsSitePartitionItem wsSitePartitionItem);

    /**
        * @Author liujie
        * @Description 删除分区名目绑定
        * @Date 2020/8/20 17:58
        * @Param [id]
        * @Return int
       **/
    int deleteWsSitePartitionItemByItemAndType(@Param("itemId") Integer itemId ,@Param("itemType") String itemType);

    /**
        * @Author liujie
        * @Description 更新归属分区
        * @Date 2020/8/21 13:56
        * @Param [wsSitePartitionItem]
        * @Return int
       **/
    int updateWsSitePartitionItemByItemAndType(WsSitePartitionItem wsSitePartitionItem);
}
