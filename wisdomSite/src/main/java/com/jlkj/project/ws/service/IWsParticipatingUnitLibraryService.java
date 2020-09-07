package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.vo.WsParticipatingUnitLibraryVO;

import java.util.List;

/**
 * 参建单位库Service接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface IWsParticipatingUnitLibraryService
{
    /**
     * 查询参建单位库
     *
     * @param id 参建单位库ID
     * @return 参建单位库
     */
    public WsParticipatingUnitLibrary selectWsParticipatingUnitLibraryById(Integer id);

    /**
     * 查询参建单位库列表
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 参建单位库集合
     */
    public List<WsParticipatingUnitLibrary> selectWsParticipatingUnitLibraryList(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);

    /**
     * 新增参建单位库
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 结果
     */
    public int insertWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);

    /**
     * 修改参建单位库
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 结果
     */
    public int updateWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);

    /**
     * 批量删除参建单位库
     *
     * @param ids 需要删除的参建单位库ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitLibraryByIds(Integer[] ids);

    /**
     * 删除参建单位库信息
     *
     * @param id 参建单位库ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitLibraryById(Integer id);

    /**
        * @Author liujie
        * @Description 统计参建单位历史项目经历
        * @Date 2020/6/27 15:16
        * @Param [wsParticipatingUnitLibrary]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsParticipatingUnitLibraryVO>
       **/
    List<WsParticipatingUnitLibraryVO> statisticsProjectParticipatingUnitHistoryRecord(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);
}
