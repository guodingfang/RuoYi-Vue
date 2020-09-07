package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jlkj.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
    * @Author liujie
    * @Description 工程班组树VO类
    * @Date 2020/6/18 11:10
   **/
@ApiModel("项目班组树")
public class WsProjectTeamTreeVO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer nodeId;
    @ApiModelProperty("节点类型")
    /**节点类型：1=项目,2=企业类型，3=参建单位，4=班组 */
    private Integer nodeType;
    @ApiModelProperty("标签名称")
    private String label;
    @ApiModelProperty("序号")
    private String id;
    /** 父级id*/
    private String parentId;

    /** 项目ID*/
    @ApiModelProperty("项目id")
    private Integer projectId;


    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<WsProjectTeamTreeVO> children;

    public WsProjectTeamTreeVO(WsProjectTeamTreeVO wsProjectTeamTreeVO) {
        this.nodeId = wsProjectTeamTreeVO.nodeId;
        this.nodeType = wsProjectTeamTreeVO.nodeType;
        this.label = wsProjectTeamTreeVO.label;
        this.id = wsProjectTeamTreeVO.id;
        this.parentId = wsProjectTeamTreeVO.parentId;
        this.projectId = wsProjectTeamTreeVO.projectId;
        if(StringUtils.isNotEmpty(wsProjectTeamTreeVO.getChildren())){
            this.children = wsProjectTeamTreeVO.getChildren().stream().map(WsProjectTeamTreeVO::new).collect(Collectors.toList());
        }else{
            this.children=new ArrayList<>();
        }
    }




    public List<WsProjectTeamTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<WsProjectTeamTreeVO> children) {
        this.children = children;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public WsProjectTeamTreeVO()
    {

    }



    public WsProjectTeamTreeVO(String id,Integer nodeId, Integer nodeType, String label,  String parentId, Integer projectId) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.label = label;
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsProjectTeamTreeVO{");
        sb.append("nodeId=").append(nodeId);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", label='").append(label).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", projectId=").append(projectId);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}
