package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsBlacklist;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.vo.WsBlacklistVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【人员黑名单】Mapper接口
 *
 * @author gaowei
 * @date 2020-06-19
 */
public interface WsBlacklistMapper
{
    /**
     * 查询【人员黑名单】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public WsBlacklistVO selectWsBlacklistById(Integer id);

    /**
     * 查询【人员黑名单】列表
     *
     * @param wsBlacklistVO 【人员黑名单】，scope 【项目范围】
     * @return 【人员黑名单】集合
     */
    public List<WsBlacklistVO> selectWsBlacklistList(@Param("wsBlacklistVO") WsBlacklistVO wsBlacklistVO, @Param("dataScope")  List<WsProject>dataScope);

    /**
     * 新增【人员黑名单】
     *
     * @param wsBlacklist 【人员黑名单】
     * @return 结果
     */
    public int insertWsBlacklist(WsBlacklist wsBlacklist);

    /**
     * 修改【人员黑名单】
     *
     * @param wsBlacklist 【人员黑名单】
     * @return 结果
     */
    public int updateWsBlacklist(WsBlacklist wsBlacklist);

    /**
     * 删除【人员黑名单】
     *
     * @param id 【人员黑名单】ID
     * @return 结果
     */
    public int deleteWsBlacklistById(Integer id);

    /**
     * 批量删除【人员黑名单】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsBlacklistByIds(Integer[] ids);


    /**
    * @Author liujie
    * @Description 批量插入黑名单数据
    * @Date 2020/6/19 17:54
    * @Param [wsBlacklists]
    * @Return int
   **/
    int batchInsertWsBlacklist(List<WsBlacklist> wsBlacklists);

    /**
        * @Author liujie
        * @Description 根据ID批量移除黑名单
        * @Date 2020/6/19 19:12
        * @Param [ids]
        * @Return int
       **/
    int batchRemoveWsBlacklist(Integer[] ids);
}
