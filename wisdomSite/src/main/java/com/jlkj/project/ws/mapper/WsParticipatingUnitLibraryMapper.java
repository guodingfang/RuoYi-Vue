package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.vo.WsParticipatingUnitLibraryVO;

import java.util.List;

/**
 * 参建单位库Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsParticipatingUnitLibraryMapper
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
        * @Author liujie
        * @Description 根据参建单位库统计单位项目经历记录
        * @Date 2020/6/27 14:25
        * @Param [wsParticipatingUnitLibrary]
        * @Return java.util.List<WsParticipatingUnitLibraryVO>
       **/
    List<WsParticipatingUnitLibraryVO> statisticsProjectParticipatingUnitHistoryRecord (WsParticipatingUnitLibrary wsParticipatingUnitLibrary);

    /**
     * 新增参建单位库
     *
     * @param wsProjectParticipatingUnit 工程参建单位
     * @return 结果
     */
     int insertWsParticipatingUnitLibraryByProject(WsProjectParticipatingUnit wsProjectParticipatingUnit);

     /**
         * @Author liujie
         * @Description 新增参建单位库
         * @Date 2020/6/13 9:40
         * @Param [wsParticipatingUnitLibrary]
         * @Return int
        **/
     int insertWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);



    /**
     * 修改参建单位库
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 结果
     */
    public int updateWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary);


    /**
     * @Author liujie
     * @Description 通过工程参建单位更新参建单位库
     * @Date 2020/6/13 11:15
     * @Param [wsProjectParticipatingUnit]
     * @Return void
     **/
    int updateWsParticipatingUnitLibraryByProject(WsProjectParticipatingUnit wsProjectParticipatingUnit);
    /**
     * 删除参建单位库
     *
     * @param id 参建单位库ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitLibraryById(Integer id);

    /**
     * 批量删除参建单位库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsParticipatingUnitLibraryByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 通过社会统一信用代码查找参建单位
        * @Date 2020/6/13 11:19
        * @Param [unitOrgcode]
        * @Return int
       **/
    int findWsParticipatingUnitByOrgcode(String unitOrgcode);


}
