<template>
  <el-dialog title="项目" :visible.sync="flag" width="900px" @close="closeDialog" :close-on-click-modal="false">
    <div class="overview-wrap">
      <el-form ref="projForm" :model="projForm" :rules="rules" size="small" label-width="140px">
        <el-form-item label="项目名称：" prop="projectName">
          <el-input v-model="projForm.projectName" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="简称：" prop="projectAbbreviation">
          <el-input v-model="projForm.projectAbbreviation" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="地图位置：" prop="latitude">
          <div class="map-wrap">
            <div id="result">
              <input type="text" class="el-input" ref="suggest" id="suggestId" size="20"  style="width:250px;"/></div>
            <div ref="searchResultPanel" id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
            <div id="map" class="map-body"></div>
          </div>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="归属公司：" prop="projectUnitId">
              <el-input :disabled="true" v-model="projForm.projectUnitName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目负责人：" prop="projectPersonId">
              <el-select
                placeholder="请选择项目负责人"
                v-model="projForm.projectPersonId"
                :remote-method="searchMember"
                remote
                filterable
                class="w100">
                <el-option
                  v-for="item in memberList"
                  :key="item.userId"
                  :value="item.userId"
                  :label="item.nickName+'('+item.phonenumber+')'"
                >{{item.nickName}}({{item.phonenumber}})</el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目状态：">
              <el-select placeholder="请选择项目状态" v-model="projForm.projectStatus" class="w100">
                <el-option label="未建" :value="3"></el-option>
                <el-option label="完工" :value="2"></el-option>
                <el-option label="在建" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建设规模：">
              <el-radio-group v-model="projForm.buildScale" class="w100">
                <el-radio :label="3">大型</el-radio>
                <el-radio :label="2">中型</el-radio>
                <el-radio :label="1">小型</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="投资总额(万元)：">
          <el-input v-model="projForm.totalInvestment" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="项目LOGO：">
          <pic-upload
            :value="projForm.projectLogo"
            @input="picChange"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="isCreate === false?updateProject():addProject(true)">保 存</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import { addProject, updateProject } from "@/api/project/project";
  import {
    getMemberList,
  } from '@/api/system/organize';
  import PicUpload from "@/components/PicUpload/index";
  export default {
    name: 'AddProject',
    components: { PicUpload },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      projForms: {
        type: Object,
        default: () => {},
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
    },
    data() {
      return{
        selPos: '',
        memberList: [],
        projForm: {
          areaCode: '',
          projectAddress: '',
          projectName: '',
          projectPersonId: '',
          projectUnitId: '',
          projectUnitName: '',
          startTime: '',
          longitude: '',
          latitude: '',
          totalInvestment: '',
          projectLogo: '',
          buildScale: '',
        },
        dialogImageUrl: '',
        dialogVisible: false,
        cities: [

        ],
        // 表单校验
        rules: {
          projectName: [
            { required: true, message: "项目名称", trigger: "blur" }
          ],
          latitude: [
            { required: true, message: "选择位置", trigger: "blur" }
          ],
          projectPersonId: [
            { required: true, message: "输入项目负责人", trigger: "blur" }
          ],
          projectUnitId: [
            { required: true ,message: "选择归属公司", trigger: "blur" }
          ],
        },
        queryParams: {
          pageNum: 1,
          // pageSize: 10,
          enterpriseId: this.$store.state.user.enterpriseId,
          phonenumber: '',
          // organizationId: '',
          // type: '',
        },
      }
    },
    created() {
      this.getMemberList();
    },
    mounted() {

    },
    methods: {
      searchMember(query) {
        if (!!query) {
          this.queryParams.phonenumber = query;
          this.getMemberList();
        } else {
          this.memberList = []
        }
      },
      /** 查询 列表 */
      getMemberList() {
        getMemberList(this.queryParams).then(response => {
          this.memberList = response.rows;
        });
      },
      picChange(e) {
        this.projForm.projectLogo = e
      },
      /** 地图初始化 */
      initMapData() {
        let _this = this
        this.$nextTick(() => {
          this.map = new BMap.Map('map'); // 创建Map实例
          this.map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
          this.map.centerAndZoom(new BMap.Point(117.34145366186866, 31.908246281191683), 13);
          let { latitude, longitude, projectAddress } = this.projForm;
          if ( !!latitude ) {
            let oldPoint = new BMap.Point(longitude, latitude);
            this.map.centerAndZoom(oldPoint, 13);
            this.map.clearOverlays(); //清除地图上所有覆盖物
            this.map.addOverlay(new BMap.Marker(oldPoint)); //添加标注
            this.$refs.suggest.placeholder = projectAddress;
          }
          let ac = new BMap.Autocomplete({
            "input": "suggestId",
            "location": this.map
          });
          ac.addEventListener("onhighlight", function (e) { //鼠标放在下拉列表上的事件
            let str = "";
            let _value = e.fromitem.value;
            let value = "";
            if (e.fromitem.index > -1) {
              value = _value.province + _value.city + _value.district + _value.street + _value.business;
            }
            str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

            value = "";
            if (e.toitem.index > -1) {
              _value = e.toitem.value;
              value = _value.province + _value.city + _value.district + _value.street + _value.business;
            }
            str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
            document.getElementById("searchResultPanel").innerHTML = str;
          });
          let myValue;
          ac.addEventListener("onconfirm", function (e) { //鼠标点击下拉列表后的事件
            let _value = e.item.value;
            myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
            _this.selPos = myValue;
            _this.projForm.projectAddress =  myValue;
            document.getElementById("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index +
              "<br />myValue = " +
              myValue;
            _this.setPlace();
          });
        });
      },
      setPlace() {
        let that = this
        this.map.clearOverlays(); //清除地图上所有覆盖物
        let local = new BMap.LocalSearch(that.map, { //智能搜索
          onSearchComplete: function () {
            let pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
            that.projForm.latitude = pp.lat;
            that.projForm.longitude = pp.lng;
            that.map.centerAndZoom(pp, 18);
            that.map.addOverlay(new BMap.Marker(pp)); //添加标注
          }
        });
        local.search(this.selPos);
      },
      /** 编辑项目*/
      updateProject() {
        this.$refs["projForm"].validate( valid => {
          if (!valid) return;
          updateProject(this.projForm).then(res => {
            this.msgSuccess("编辑成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /** 添加项目*/
      addProject() {
        this.$refs["projForm"].validate( valid => {
          if (!valid) return;
          addProject(this.projForm).then(res => {
            this.msgSuccess("添加成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /* 关闭项目框 */
      closeDialog() {
        this.projForm = {};
        this.$refs["projForm"].resetFields();
      },
      cancel() {
        this.flag = false;
      }
    },
    computed: {
      flag: {
        get() {
          if(this.dialogFlag) {
            this.initMapData()
            //TODO 这里不能这么做，应该单独获取，时间问题，先这样
            // this.memberList = this.$parent.tableData
          }
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
        },
      },
    },
    watch: {
      projForms(val) {
        this.projForm = Object.assign({projectLogo: ''}, val);
      },
    },
  }
</script>

<style scoped lang="scss">
  .dialog-footer{
    text-align: right;
  }
  .overview-wrap{
    width: 800px;
    padding-top: 40px;
    .map-wrap{
      position: relative;
      #result{
        position: absolute;
        top: 4px;
        left: 8px;
        height: 30px;
        border: 1px solid #f2f2f2;
        z-index: 9999;
        input {
          border: none;
          height: 100%;
          &:focus{
            outline: none;
          }
        }
      }
      .map-body{
        height: 160px;
      }
    }
  }
</style>
