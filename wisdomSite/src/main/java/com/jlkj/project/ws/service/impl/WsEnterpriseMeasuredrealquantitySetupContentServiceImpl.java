package com.jlkj.project.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetup;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantitySetupVO;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantitySetupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantitySetupContentMapper;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetupContent;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantitySetupContentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实测实量设置分项工程实测内容 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantitySetupContentServiceImpl implements IWsEnterpriseMeasuredrealquantitySetupContentService {
    @Autowired
    private WsEnterpriseMeasuredrealquantitySetupContentMapper wsEnterpriseMeasuredrealquantitySetupContentMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantitySetupMapper wsEnterpriseMeasuredrealquantitySetupMapper;

    /**
     * 查询实测实量设置分项工程实测内容
     *
     * @param measuredrealquantitySetupId 分项工程id
     * @return 实测实量设置分项工程实测内容
     */
    @Override
    public WsEnterpriseMeasuredrealquantitySetupVO selectWsEnterpriseMeasuredrealquantitySetupContentById(Integer measuredrealquantitySetupId) {
        WsEnterpriseMeasuredrealquantitySetupVO emsVO = new WsEnterpriseMeasuredrealquantitySetupVO();
        List<WsEnterpriseMeasuredrealquantitySetupContent> contentList = wsEnterpriseMeasuredrealquantitySetupContentMapper.selectWsEnterpriseMeasuredrealquantitySetupContentById(measuredrealquantitySetupId);
        WsEnterpriseMeasuredrealquantitySetup ems = wsEnterpriseMeasuredrealquantitySetupMapper.selectWsEnterpriseMeasuredrealquantitySetupById(measuredrealquantitySetupId);
        emsVO.setWsEnterpriseMeasuredrealquantitySetupContent(contentList);
        emsVO.setWsEnterpriseMeasuredrealquantitySetup(ems);
        return emsVO;
    }


    /**
     * 新增实测实量设置分项工程实测内容
     *
     * @param wsEnterpriseMeasuredrealquantitySetupVO 实测实量设置分项工程实测DTO
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO) {

        Long organizationId = SecurityUtils.getUserInfo().getOrganizationId();
        try {
            //获取前台传来的数据
            WsEnterpriseMeasuredrealquantitySetup emsDTO = wsEnterpriseMeasuredrealquantitySetupVO.getWsEnterpriseMeasuredrealquantitySetup();
            emsDTO.setEnterpriseId(organizationId.intValue());
            List<WsEnterpriseMeasuredrealquantitySetupContent> emscDTO = wsEnterpriseMeasuredrealquantitySetupVO.getWsEnterpriseMeasuredrealquantitySetupContent();
            WsEnterpriseMeasuredrealquantitySetup ems = wsEnterpriseMeasuredrealquantitySetupMapper.selectWsEnterpriseMeasuredrealquantitySetupByName(emsDTO);
            //查看数据库有没有分项工程的数据
            if (ems != null) {
                if (ems.getSubitemProject().equals(emsDTO.getSubitemProject())) {
                    for (int i = 0; i < emscDTO.size(); i++) {
                        //获取分项工程id
                        emscDTO.get(i).setMeasuredrealquantitySetupId(ems.getId());
                    }
                }

            }else {
                //如果没有此条数据就添加分项工程信息

                int i = wsEnterpriseMeasuredrealquantitySetupMapper.insertWsEnterpriseMeasuredrealquantitySetup(emsDTO);
                if (i > 0) {
                    for (int j = 0; j < emscDTO.size(); j++) {
                        emscDTO.get(j).setMeasuredrealquantitySetupId(emsDTO.getId());
                    }
                }
            }
            //执行添加分项工程内容操作
            int num = 1;
            for (int i = 0; i < emscDTO.size(); i++) {
                int result = wsEnterpriseMeasuredrealquantitySetupContentMapper.insertWsEnterpriseMeasuredrealquantitySetupContent(emscDTO.get(i));
                if (result >= 1) {
                    num++;
                }
            }
            if (num > 1) {
                return num;
            }

        } catch (Exception e) {
            throw e;
        }
        return -1;
    }

    /**
     * 修改实测实量设置分项工程实测内容
     *
     * @param wsEnterpriseMeasuredrealquantitySetupContent 实测实量设置分项工程实测
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateWsEnterpriseMeasuredrealquantitySetupContent(List<WsEnterpriseMeasuredrealquantitySetupContent> wsEnterpriseMeasuredrealquantitySetupContent) {
        try {
            int result = 0;
            //遍历集合根据主键单个更新
            if (wsEnterpriseMeasuredrealquantitySetupContent != null) {
                for (int i = 0; i < wsEnterpriseMeasuredrealquantitySetupContent.size(); i++) {
                    result = wsEnterpriseMeasuredrealquantitySetupContentMapper.updateWsEnterpriseMeasuredrealquantitySetupContent(wsEnterpriseMeasuredrealquantitySetupContent.get(i));
                }
            }
            if (result > 0) {
                return result;
            } else {
                return -1;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 编辑保存实测实量设置分项工程实测内容
     *
     * @param wsEnterpriseMeasuredrealquantitySetupVO 实测实量设置分项工程实测VO
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int setWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO) {
        try {
            WsEnterpriseMeasuredrealquantitySetup emsDTO = wsEnterpriseMeasuredrealquantitySetupVO.getWsEnterpriseMeasuredrealquantitySetup();
            List<WsEnterpriseMeasuredrealquantitySetupContent> emscDTO = wsEnterpriseMeasuredrealquantitySetupVO.getWsEnterpriseMeasuredrealquantitySetupContent();
            List<WsEnterpriseMeasuredrealquantitySetupContent> wsemsc = wsEnterpriseMeasuredrealquantitySetupContentMapper.selectWsEnterpriseMeasuredrealquantitySetupContentById(emsDTO.getId());
            int result = 0;
            boolean flag = true;
            List<Integer> list = new ArrayList<Integer>();
            if (emscDTO != null) {
                //遍历数据库实测实量实测内容找出所有数据
                for (int i = 0; i < wsemsc.size(); i++) {
                    //遍历前台传过来的实测实量实测内容
                    for (int j = 0; j < emscDTO.size(); j++) {
                        //对比找出主键id的数据执行更新操作
                        if (emscDTO.get(j).getId() != null && emscDTO.get(j).getId().equals(wsemsc.get(i).getId())) {
                            result = wsEnterpriseMeasuredrealquantitySetupContentMapper.updateWsEnterpriseMeasuredrealquantitySetupContent(emscDTO.get(j));
                            //如果id相同 标记为假 表示不删除此条数据
                            if (result > 0) {
                                flag = false;
                            }
                        }
                        //对比找出没有id属性的数据执行新增操作
                        if (emscDTO != null && emscDTO.get(j).getId() == null) {
                            emscDTO.get(j).setMeasuredrealquantitySetupId(emsDTO.getId());
                            result = wsEnterpriseMeasuredrealquantitySetupContentMapper.insertWsEnterpriseMeasuredrealquantitySetupContent(emscDTO.get(j));
                        }
                    }
                    //标记为真时表示 前台传的数据 和数据库的数据没有相同的id 此条数据被前台删除 添加到list
                    if (flag) {
                        list.add(wsemsc.get(i).getId());
                    }
                    flag = true;

                }
                //执行 删除操作
                if (list.size() > 0) {
                    result = wsEnterpriseMeasuredrealquantitySetupContentMapper.deleteWsEnterpriseMeasuredrealquantitySetupContentByIds(list);
                }
            }
            //执行修改分项工程内容
            if (emsDTO.getSubitemProject() != null) {
                result = wsEnterpriseMeasuredrealquantitySetupMapper.updateWsEnterpriseMeasuredrealquantitySetup(emsDTO);

            }
            if (result > 0) {
                return result;
            }

        } catch (Exception e) {
            throw e;
        }
        return -1;
    }

    /**
     * 删除实测实量设置分项工程实测内容 信息
     *
     * @param id 实测实量设置分项工程实测内容 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantitySetupContentById(Integer id) {
        return wsEnterpriseMeasuredrealquantitySetupContentMapper.deleteWsEnterpriseMeasuredrealquantitySetupContentById(id);
    }
}
