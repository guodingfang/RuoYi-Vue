package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsBlacklist;
import com.jlkj.project.ws.domain.vo.WsBlacklistVO;

import java.util.List;

/**
 * 【人员黑名单】Service接口
 *
 * @author liujie
 * @date 2020-06-19
 */

public interface IWsBlacklistService
{
    /**
     * 查询【人员黑名单】
     *
     * @param id 【人员黑名单】ID
     * @return 【人员黑名单】
     */
    public WsBlacklistVO selectWsBlacklistById(Integer id);

    /**
     * 查询【人员黑名单】列表
     *
     * @param wsBlacklistVO 【人员黑名单】
     * @return 【人员黑名单】集合
     */
    public List<WsBlacklistVO> selectWsBlacklistList(WsBlacklistVO wsBlacklistVO);

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
     * 批量删除【人员黑名单】
     *
     * @param ids 需要删除的【人员黑名单】ID
     * @return 结果
     */
    public int deleteWsBlacklistByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 批量移除黑名单
        * @Date 2020/6/19 19:30
        * @Param [ids]
        * @Return int
       **/
    int batchRemoveBlacklist(Integer[] ids);

    /**
     * 删除【人员黑名单】信息
     *
     * @param id 【人员黑名单】ID
     * @return 结果
     */
    public int deleteWsBlacklistById(Integer id);

    /**
        * @Author liujie
        * @Description //批量插入黑名单
        * @Date 2020/7/10 16:31
        * @Param [wsBlacklist]
        * @Return int
       **/
    int batchWsBlacklist(WsBlacklist wsBlacklist);
}
