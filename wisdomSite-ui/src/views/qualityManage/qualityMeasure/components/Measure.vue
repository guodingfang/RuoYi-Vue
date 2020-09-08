<template>
  <div class="measure-container">
    <div class="l-tree">
      <div class="tit">项目区域</div>
      <div class="tree-cont">
        <el-tree
          node-key="id"
          :props="defaultProps"
          :expand-on-click-node="false"
          :load="loadNode"
          @node-click="handleNodeClick"
          lazy>
          <span :class="['custom-tree-node']" slot-scope="{ node, data }">
            <span>{{ data.regionName }}</span>
            <span class="tree_right">
              <el-button
                type="text"
                size="mini"
                v-show="node.level!==1 && selNodeId===node.id"
                @click="addLocation(data)"
              >新增实测部位</el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>
    <div class="r-table">
      <div class="tit">实测部位（{{tableData.length}}）</div>
      <div class="table-cont">
        <Table :tableData="tableData" :tableConfig="tableConfig">
          <template v-slot:status>
            <el-table-column label="状态" width="80px">
              <template slot-scope="scope">
                <status-tag :status="scope.row.status===1?'启用':'锁定'" />
              </template>
            </el-table-column>
          </template>
          <template v-slot:operate>
            <el-table-column label="操作" align="left" width="300px">
              <template slot-scope="scope">
                <el-button type="text" class="jl-green" @click="lookDetail(scope.row.id)">查看</el-button>
                <el-button type="text" class="jl-green"  @click="editLocation(scope.row)">编辑</el-button>
                <el-button type="text" class="jl-green"  @click="delLocation(scope.row)">删除</el-button>
                <el-button v-if="scope.row.status===1" type="text" class="jl-green"  @click="lockLocation(scope.row.id, 2)">锁定</el-button>
                <el-button v-if="scope.row.status===2" type="text" class="jl-green"  @click="lockLocation(scope.row.id, 1)">启用</el-button>
                <el-button v-if="scope.row.status===1" type="text" class="jl-green"  @click="addMeasure(scope.row)">新增实测实量</el-button>
              </template>
            </el-table-column>
          </template>
        </Table>
        <Pagination
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getMeasureList"
        />
      </div>
    </div>
    <location-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="locationDialogFlag"
      :dataForms.sync="locationData"
      :selectTableData.sync="selectTableData"
      @query="getMeasureList"
    />
    <measure-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="measureDialogFlag"
      :dataForms.sync="measureData"
      @query="getMeasureList"
    />
    <detail-dialog
      :dialogFlag.sync="detialDialogFlag"
      :dataForms.sync="detailData"
      :selectTableData.sync="selectTableData"
      @query="getMeasureList"
      @refresh="refreshDetail"
    />
  </div>
</template>

<script>
  import {
    listProject,
    getProjChild,
    getAreaDetail,
    getMeasureList,
    delLocation,
    editLocation,
    getMeasureDetail
  } from "@/api/quality/measure";
  import Table from '@/components/Table';
  import StatusTag from "@/components/StatusTag/index";
  import Pagination from "@/components/Pagination";
  import LocationDialog from "./LocationDialog";
  import MeasureDialog from "./MeasureDialog";
  import DetailDialog from "./DetailDialog";
  export default {
    name: "Measure",
    components: {
      MeasureDialog,
      LocationDialog,
      DetailDialog,
      Table,
      StatusTag,
      Pagination,
    },
    data() {
      return {
        locationDialogFlag: false,
        locationData: {},
        measureDialogFlag: false,
        detialDialogFlag: false,
        detailData: {},
        isCreate: true,
        measureData: {},
        selNodeId: '',
        defaultProps: {
          children: "children",
          label: "regionName",
          isLeaf: 'leaf'
        },
        tableConfig: [
          { slot: "operate" },
          { prop: "respectiveRegionName", label: "所属区域", width: "" },
          { prop: "measuredLocation", label: "实测部位", width: "" },
          { prop: "measurementsNo", label: "测量次数", width: "" },
          { prop: "createdName", label: "创建人", width: "" },
          { prop: "createdTime", label: "创建时间", width: "" },
          { prop: "comment", label: "备注", width: "" },
          { slot: "status" },
        ],
        total: 0,
        tableData: [],
        selectTableData: [],
        queryParams: {
          measuredLocation: undefined,
          planImageAddres	: undefined,
          projectId: undefined,
          respectiveRegionId: undefined,
          pageNum: 1,
          pageSize: 10,
        }
      };
    },
    watch: {

    },
    computed: {
    },
    created() {
    },
    methods: {
      loadNode(node, resolve) {
        if (node.level === 0) {
          this.getProjectList(resolve);
        }
        if (node.level === 1) {
          this.getArea(node, resolve);
        }
        if (node.level === 2) {
          let newData = node.data.children.map(item => ({
            ...item,
            leaf: true
          }));
          return resolve(newData)
        }
        if (node.level === 3) {
          return resolve([])
        }
      },
      //查看详情
      async lookDetail(id) {
        const { data } = await getMeasureDetail(id);
        this.getSelectTableData({id: data.wsEnterpriseMeasuredrealquantityMeasuredLocation.respectiveRegionId})
        this.detailData = data;
        this.detialDialogFlag = true;
      },
      async refreshDetail(id) {
        const { data } = await getMeasureDetail(id);
        this.detailData = data;
      },
      //获取所选图片的所有点信息
      getSelectTableData({ id, editId = '' }) {
        const _selectTableData = this.tableData.filter(item => item.respectiveRegionId === id);
        console.log('_selectTableData', _selectTableData, 'editId', editId);
        this.selectTableData = _selectTableData.map(item => ({
          center: [Number(item.coordinateX||0), Number(item.coordinateY||0)],
          radius: 6,
          color: item.id === editId ? 'yellow' : 'red'
        }));
        console.log('selectTableData', this.selectTableData);
      },
      //获取工程下的实测实量
      getMeasureList() {
        // getMeasureList(Object.assign({}, this.queryParams)).then(res => {
        getMeasureList(this.queryParams).then(res => {
          this.tableData = res.rows;
          this.total = res.total
        })
      },
      //获取所有的项目列表
      getProjectList(resolve) {
        listProject().then(res => {
          let newData = res.rows.map(item => ({
             ...item,
             regionName: item.projectName,
          }));
          this.queryParams.projectId = res.rows[0].id;
          this.getMeasureList();
          resolve(newData);
        }).catch(err => {
          this.msgError(err)
        })
      },
      //获取项目下的区域树
      getArea(node, resolve) {
        getProjChild(node.data.id).then(res => {
          resolve(res.data)
        })
      },
      handleNodeClick(data, node, ele) {
        this.selNodeId = node.id;
        if (node.level === 1) {
          this.queryParams.projectId = data.id;
          this.queryParams.respectiveRegionId = '';
        } else if(node.level === 2) { //下面应该用递归
          this.queryParams.projectId = node.parent.data.id;
          this.queryParams.respectiveRegionId = data.id;
        } else if (node.level === 3) {
          this.queryParams.projectId = node.parent.parent.data.id;
          this.queryParams.respectiveRegionId = data.id;
        }
        this.getMeasureList()
      },
      //新增实测实量
      async addMeasure(row) {
        this.isCreate = true;
        this.measureData = row;
        this.measureDialogFlag = true;
      },
      //新增实测部位
      async addLocation(data) {
        const res = await getAreaDetail(data.id)
        this.locationData = {
          respectiveRegionId: data.id,
          respectiveRegionName: data.regionName,
          planImageAddres: res.data[0].planImageAddres,
          projectId: res.data[0].projectId
        }
        this.getSelectTableData({id: data.id});
        this.isCreate = true;
        this.locationDialogFlag = true;
      },
      //编辑点位
      async editLocation(row) {
        console.log('row', row);
        this.isCreate = false;
        this.locationData = row;
        this.getSelectTableData({id: row.respectiveRegionId, editId: row.id});
        this.locationDialogFlag = true;
      },
      async lockLocation(id, status) {
        await editLocation({ id, status });
        this.getMeasureList();
      },
      async delLocation(row) {
        try {
          await this.$confirm('确定删除吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          });
          await delLocation(row.id);
          await this.getMeasureList();
          this.msgSuccess('删除成功');
        } catch (e) {
          console.log(e)
        }
      },
      renderContent(h, { node, data, store }) {
        // if(node.level === 3) {
        //   return (
        //     <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
        //     <span>
        //     <span>{node.label}</span>
        //     </span>
        //     <span>
        //     <el-button style="font-size: 12px;" type="text" on-click={ () => this.append(data) }>新增实测部位 </el-button>
        //   </span>
        //   </span>);
        // }
      },
    }
  };
</script>

<style scoped lang="scss">
  .measure-container{
    border: 1px solid #dddcdc;
    /*padding: 0 20px 0;*/
    display: flex;
    /deep/ .el-tree-node__label{
      font-size: 12px;
    }
    .custom-tree-node {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 12px;
      padding-right: 8px;
      &.disable{
        color: #c0c4cc;
      }
      &.has-sel{
        background-color: #ebf7f4;
      }
    }
    .tit{
      height: 60px;
      line-height: 60px;
      border-bottom: 1px solid #dddcdc;
      text-indent: 20px;
    }
    .l-tree{
      width: 380px;
      border-right: 1px solid #dddcdc;
      height: 100%;
    }
    .tree-cont{
      padding: 10px;
      box-sizing: border-box;
    }
    .r-table{
      flex: 1;
    }
    .table-cont{
      padding: 10px;
      box-sizing: border-box;
    }
    .measure-tit{
      font-size: 14px;
      line-height: 40px;
      border-bottom: 1px solid #dddcdc;
    }
  }
</style>
