<template>
  <section class="warp">
    <el-dialog
      title="人员"
      :visible.sync="isShow"
      width="900px"
      :before-close="handleClose"
      :close-on-click-modal="false"
    >
      <el-form
        ref="formData"
        :model="formData"
        :rules="rules"
        size="small"
        label-position="left"
        label-width="90px"
      >
      <vue-scroll ref="scroll" style="height: 530px;" :ops='{scrollPanel: {scrollingX: false}}'>
        <el-tabs v-model="activeName">
          <el-tab-pane label="基本信息" name="first" class="basic">
            <div class="fp_title">
              <el-form-item prop="personType">
                <el-select v-model="formData.personType" placeholder="建设工人">
                  <el-option label="建筑工人" value="0"></el-option>
                  <el-option label="管理人员" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="unitId">
                <el-select @change="getTeam" v-model="formData.unitId" placeholder="建设单位">
                  <el-option
                    v-for="item in unitList"
                    :key="item.id"
                    :label="item.unitName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="teamId">
                <el-select
                  v-model="formData.teamId"
                  placeholder="班组/部门"
                >
                  <el-option
                    v-for="item in teamsList"
                    :key="item.id"
                    :label="item.teamName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="workType">
                <el-select
                  v-model="formData.workType"
                  placeholder="工种/岗位"
                >
                  <el-option
                    v-for="item in $DStore.state.getWorkType"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <h4 class="tit">身份信息 <el-button>读卡</el-button></h4>
            <div class="fbj_warp">
              <div class="fbj_letf">
                <el-form-item class="fbj_name" prop='name'>
                  <el-input
                    v-model="formData.name"
                    placeholder=""
                    clearable
                  ></el-input>
                </el-form-item>
                <el-form-item class="fbj_sex">
                  <el-select v-model="formData.sex" placeholder="">
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="0"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item class="fbj_nation">
                  <el-select v-model="formData.nationCode" placeholder="">
                    <el-option label="汉" value="1"></el-option>
                    <el-option label="其他" value="0"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item class="fbj_birth" prop='birthDate'>
                  <el-date-picker
                    v-model="formData.birthDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    format="yyyy年MM月dd日"
                    placeholder="出生日期"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item class="fbj_address" prop='address'>
                  <el-input
                    v-model="formData.address"
                    placeholder=""
                    clearable
                  ></el-input>
                </el-form-item>
                <el-form-item class="fbj_idNumber" prop='idNumber'>
                  <el-input
                    v-model="formData.idNumber"
                    placeholder=""
                    clearable
                  ></el-input>
                </el-form-item>
              </div>
              <div class="fbj_right">
                <el-form-item class="fbj_authority" prop='authority'>
                  <el-input
                    v-model="formData.authority"
                    placeholder=""
                    clearable
                  ></el-input>
                </el-form-item>
                 <el-form-item class="fbj_bedate" prop='effectiveDate'>
                  <el-date-picker
                    v-model="formData.effectiveDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    format="yyyy-MM-dd"
                    placeholder="选择日期"
                  />
                   <el-date-picker
                    v-model="formData.invalidDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    format="yyyy-MM-dd"
                    placeholder="选择日期"
                  />
                 </el-form-item>
              </div>
            </div>
            <el-row :gutter="20" style="padding: 18px 0;">
              <el-col :span="12">
                <el-form-item prop="residenceNature">
                  <el-select v-model="formData.residenceNature" placeholder="户口性质">
                    <el-option label="城市" value="1"></el-option>
                    <el-option label="农村" value="0"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop='mobilePhone'>
                  <el-input
                    v-model="formData.mobilePhone"
                    placeholder="手机号码"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-input
                v-model="formData.comment"
                placeholder=""
                type="textarea"
                :rows="2"
                clearable
              ></el-input>
            </el-form-item>
            <h4 class="tit">工资卡信息</h4>
            <div class="fp_title fp_foot">
              <el-form-item>
                <el-select v-model="formData.payCardBank" placeholder="银行卡类型">
                  <el-option
                    v-for="item in $DStore.state.getBankType"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="formData.nameOfBank"
                  placeholder="开户银行"
                  clearable
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="formData.payCard"
                  placeholder="银行卡号"
                  clearable
                ></el-input>
              </el-form-item>
            </div>
          </el-tab-pane>
          <el-tab-pane label="扩展信息" name="second">
            <div class="k_dell">
              <div class="k_form">
                <div class="kf_wp">
                  <el-form-item label="文化程度">
                    <el-select v-model="formData.cultureStandardCode" placeholder="">
                      <el-option
                        v-for="item in $DStore.state.getCulture"
                        :key="item.dictValue"
                        :label="item.dictLabel"
                        :value="item.dictValue"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="政治面貌">
                    <el-select v-model="formData.politicsStatusCode" placeholder="">
                      <el-option
                        v-for="item in $DStore.state.getPolitics"
                        :key="item.dictValue"
                        :label="item.dictLabel"
                        :value="item.dictValue"
                      />
                    </el-select>
                  </el-form-item>
                </div>
                <div class="kf_wp">
                  <el-form-item label="身高(cm)">
                    <el-input
                      v-model="formData.height"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                 <el-form-item label="体重(kg)">
                    <el-input
                      v-model="formData.weight"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                </div>
                <div class="kf_wp">
                  <el-form-item label="心率(次/分)">
                    <el-input
                      v-model="formData.heartRate"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="血压(mmHg)">
                    <el-input
                      v-model="formData.bloodPressure"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                </div>
                  <el-form-item label="健康相关">
                    <el-checkbox-group
                      v-model="formData.healthRelateds">
                      <el-checkbox v-for="item in health" :label="item.key" :key="item.key">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                  </el-form-item>
                  <el-form-item label="健康备注">
                    <el-input
                      v-model="formData.healthRemark"
                      placeholder=""
                      type="textarea"
                      :rows="2"
                      clearable
                    ></el-input>
                  </el-form-item>
              </div>
              <div class="k_right">
                <p class="kr_title">
                  <span>采集照片</span>
                  <span>
                    <el-button type="text" @click='innerVisible = true'>查看规范</el-button>
                      <el-dialog
                        width="600px"
                        title="照片规范"
                        :visible.sync="innerVisible"
                        append-to-body>
                        <img style="width: 100%;" src="./../image/specification.jpg">
                      </el-dialog>
                  </span>
                </p>
                  <el-upload
                    action=""
                    list-type="picture-card"
                    :show-file-list="false"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                    :limit="1"
                    :before-upload="beforeUpload"
                  >
                    <img
                      v-if="formData.faceUrl"
                      :src="formData.faceUrl"
                      class="avatar"
                    />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>

              </div>
            </div>

          </el-tab-pane>
          <el-tab-pane label="资格证书" name="cit">
            <template v-for="(item,index) in formData.certificates" >
              <section :key="index" class="k_dell">
                <h4 class="tit">
                  <span>资格证书{{index+1}}</span>
                  <el-button type="text" class="tit_right" @click="delCertificates(index)">删除</el-button>
                </h4>
                <div class="kf_wp">
                  <el-form-item label="证书名称">
                    <el-input
                      v-model="item.certificateName"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="证书编号">
                    <el-input
                      v-model="item.certificateNumber"
                      placeholder=""
                      clearable
                    ></el-input>
                  </el-form-item>
                </div>
                <div class="kf_wp">
                  <el-form-item label="证书类型">
                    <el-select  v-model="item.certificateType" placeholder="">
                      <el-option
                        v-for="item in $DStore.state.getLevel"
                        :key="item.dictValue"
                        :label="item.dictLabel"
                        :value="item.dictValue"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="证书等级">
                    <el-select v-model="item.certificateLevelCode" placeholder="">
                      <el-option
                        v-for="item in $DStore.state.getLevel"
                        :key="item.dictValue"
                        :label="item.dictLabel"
                        :value="item.dictValue"
                      />
                    </el-select>
                  </el-form-item>
                </div>
                <div class="kf_wp">
                  <el-form-item label="有效日期(起)">
                    <el-date-picker
                      v-model="item.effectiveDate"
                      type="date"
                      value-format="yyyy-MM-dd"
                      format="yyyy-MM-dd"
                      placeholder="选择日期"
                    />
                  </el-form-item>
                  <el-form-item label="有效日期(止)">
                    <el-date-picker
                      v-model="item.invalidDate"
                      type="date"
                      value-format="yyyy-MM-dd"
                      format="yyyy-MM-dd"
                      placeholder="选择日期"
                    />
                  </el-form-item>
                </div>
                <div class="kf_wp">
                  <el-form-item label="证书正面">
                    <el-upload
                      action="#"
                      list-type="picture-card"
                      :show-file-list="false"
                      :limit="1"
                      :before-upload="beforeSload"
                      :on-change="(file, fileList) => {handleChange(file, fileList, 'frontImage' ,index)}"
                    >
                      <img
                        v-if="item.frontImage"
                        :src="item.frontImage"
                        class="avatar"
                      />
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                  <el-form-item label="证书反面">
                    <el-upload
                      action="#"
                      list-type="picture-card"
                      :show-file-list="false"
                      :limit="1"
                      :before-upload="beforeSload"
                      :on-change="(file, fileList) => {handleChange(file, fileList, 'reverseImage', index)}"
                    >
                      <img
                        v-if="item.reverseImage"
                        :src="item.reverseImage"
                        class="avatar"
                      />
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </div>
              </section>
            </template>
            <div class="kd_button">
              <el-button icon="el-icon-plus" @click="addCertificates">新增资格证书</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="劳务合同" name="dic">
            <h4 class="tit">合同信息</h4>
            <div class="dic_form">
              <el-form-item label="合同编号">
                <el-input
                  v-model="formData.contactNumber"
                  placeholder=""
                  clearable
                ></el-input>
              </el-form-item>
              <el-form-item label="合同类型">
                <el-select v-model="formData.contactType" placeholder="">
                  <el-option label="无合同" value="0"></el-option>
                  <el-option label="固定期限合同 " value="1"></el-option>
                  <el-option label="不定期合同" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="生效日期">
                <el-date-picker
                  v-model="formData.contactEffectiveDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  format="yyyy-MM-dd"
                  placeholder="选择日期"
                />
              </el-form-item>
              <el-form-item label="失效日期">
                <el-date-picker
                  v-model="formData.contactInvalidDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  format="yyyy-MM-dd"
                  placeholder="选择日期"
                />
              </el-form-item>
            </div>
            <h4 class="tit">附件信息</h4>
            <div class="dic_form">
              <el-upload
                action="#"
                :limit="1"
                class="elUp"
                :before-upload="beforeAtt"
                :file-list="formData.fileInfo"
                :on-exceed="handleExceed">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">	附件支持格式(jpg,png,pdf,doc,docx,xls,xlsx)，不超过1M</div>
              </el-upload>
            </div>
          </el-tab-pane>
        </el-tabs>
      </vue-scroll>
        添加建筑工人？试试从建筑工人库中搜索添加 <el-button type="text" @click="add_table">点击添加</el-button>
        <div class="footer">
          <el-checkbox v-model="formData.monitor" :false-label="0" :true-label="1">设为班组长</el-checkbox>
          <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
        </div>

      </el-form>
    </el-dialog>
  </section>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
import { uploadAll } from "@/api/people/blacklist";
import debounce from 'lodash/debounce';
import { Message } from 'element-ui';
import { getUnit,addStaff,modifyStaff, } from "@/api/people/company";
export default {
  name: "addStaff",
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  computed: {
    imgList() {
      return [{
        name: this.getName(this.formData.attachmentOneUrl),
        url: this.formData.attachmentOneUrl,
      }]
    }
  },
  data() {
    const checkAge = (rule, value, callback) => {
      const now = this.$dayjs(this.formData.birthDate).valueOf();
      const past = this.$dayjs(this.formData.effectiveDate).valueOf();
      // console.log(now,past,this.formData.invalidDate,this.formData.birthDate);
      if (!value) {
        callback(new Error());
      } else if (this.formData.birthDate) {
        // this.formRef.clearValidate(['beginDate', 'endDate']);
        if (past < now) {
          Message.closeAll()
          Message.error({
            message: '有效期要在出生日期之后',
          });
          callback(new Error(``));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      // pickerNew: {
      //   disabledDate(time) {
      //     let curDate = this.formData.birthDate ? this.formData.birthDate : Date.now();  // 当前日期
      //     // let one = 31 * 24 * 3600 * 1000;  //一个月时间
      //     // let oneMonth = curDate - one;  //时间差
      //     return (time.getTime() < (curDate.getTime()))  //如果当天可选 就不用减8.64e7
      //   }
      // },
      innerVisible: false,
      activeName: "first",
      // activeName: "cit",
      optsions: [
        {
          valsue: "选项1",
          labsel: "黄金糕"
        },
        {
          valsue: "选项2",
          labsel: "双皮奶"
        }
      ],
      formData: {
        fileInfo: [],
      },
      // 表单校验
      rules: {
        'name': [{ required: true, message: " " }],
        'mobilePhone': [{ required: true, message: " " }],
        'idNumber': [{ required: true, message: " " }],
        'birthDate': [{ required: true, message: ' ', trigger: 'change' }],
        'unitId': [{ required: true, message: " " }],
        'teamId': [{ required: true, message: " " }],
        'workType': [{ required: true, message: " " }],
        'personType': [{ required: true, message: " " }],
        'address': [{ required: true, message: " " }],
        'authority': [{ required: true, message: " " }],

        'effectiveDate': [{ required: true, validator:checkAge, message: " " }],

        'residenceNature': [{ required: true, message: " " }],
      },
      health: [
        {key:1, label:'是否有重大病史'},
        {key:2,label:'是否有建筑行业禁忌病史'},
        {key:3,label:'有无购买工伤或意外伤害保险'},
      ],
      successData: {},
      fileList: [{
        name: 'food.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
      }],
      unitList: [],
      teamsList: [],
    };
  },
  watch: {
    isShow: {
      async handler(flag) {
        if (flag) {
          if (this.state == 'addStaff') {
            this.formData = {
              healthRelateds: [],
              certificates:[
                {
                  certificateNumber:'',certificateName:'',
                  certificateType:'',certificateLevel:'',
                  effectiveDate:'',invalidDate:'',
                  frontImage:'',reverseImage:''
                },
                // {frontImage:'',reverseImage:''},
              ]
            }
          } else {
            this.formData = {
              ...this.detailData,
              healthRelateds: this.$lGet(this,'detailData.healthRelateds',[]),
              certificates: this.$lGet(this,'detailData.certificates',[]),
            }
            await this.getUnit()
            this.getTeam()
            // this.initTeam()
          }
        }
      },
      immediate:true
    },
    'formData.monitor' (val) {
      console.log(val);
      if (val == 1) {
        this.$message({
          showClose: true,
          message: '班组长设置成功',
          type: 'success'
        });
      }

    }
  },
  methods: {
    getName(a) {
      let index = a.lastIndexOf("\/");  
      let str = a.substring(index + 1,a.length);
      return str
    },
    // async initTeam() {
    //   const re = await getUnit({projectId:this.detailData.projectId})
    //   console.log(re.rows,this.formData.unitId);
    //   let arr = re.rows.filter(item => {
    //     return item.id == String(this.formData.unitId)
    //   })
    //   console.log(arr);
    //   this.teamsList = arr[0].teams
    // },
    //查询建筑单位
    async getUnit() {
      const re = await getUnit({projectId:this.detailData.projectId})
      this.unitList = re.rows
    },
    getTeam() {
      if (this.unitList && this.unitList.length > 0) {
        let arr = this.unitList.filter(item => {
          return item.id == this.formData.unitId
        })
        this.teamsList = arr[0].teams
      }

    },
    changeValue(e,name){
      console.log(e,name);
      this.$forceUpdate()
      // this.$refs.formData.validateField('name',(phoneError) => {
      //   console.log(phoneError);
      // })
    },
    handleClose() {
      this.$emit("on-close");
    },
    _submit() {
      console.log(this.formData);
      // this.$refs["formData"].resetFields();
      this.$refs["formData"].validate(async valid => {
        if (valid) {
          let re
          if (this.state === 'addStaff') {
            re = await addStaff({
              ...this.formData,
              projectId:this.detailData.projectId
            });
          } else {
            re = await modifyStaff(this.formData);
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.$emit("on-close",'setStaff')
          }
        }
      });
    },
    addCertificates() {
      let arr = [
        {
          certificateNumber:'',certificateName:'',
          certificateType:'',certificateLevel:'',
          effectiveDate:'',invalidDate:'',
          frontImage:'',reverseImage:''
        },
      ]
      this.formData.certificates.push(...arr)
      this.$forceUpdate()
    },
    delCertificates(index) {
      if (this.formData.certificates && this.formData.certificates.length > 1) {
        this.formData.certificates.splice(index,1)
      } else {
        this.$message({
          showClose: true,
          message: '最后一条不可删除'
        });
      }
      // this.$forceUpdate()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(item) {
      // console.log('itme',item);
    },
    handleChange(file, fileList, item,index) {
      this.successData = {
        nameStp: item,
        index: index
      }
    },
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.uploadFile(copyFile,'faceUrl');
        return false;
      }
    },
    // 上传预处理
    beforeSload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.uploadFile(copyFile,this.successData.nameStp);
        return false;
      }
    },
    // 上传图片
    uploadFile(file,data) {
      let formData = new FormData();
      formData.append("avatarfile", file);
      uploadAvatar(formData).then(response => {
        if (response.code === 200) {
          let imgSrc = response.imgUrl;
          if (data == 'faceUrl') {
            this.formData.faceUrl = imgSrc;
          }
          if (data == 'frontImage') {
            this.formData.certificates[this.successData.index].frontImage = imgSrc
          }
          if (data == 'reverseImage') {
            this.formData.certificates[this.successData.index].reverseImage = imgSrc
          }
          this.$forceUpdate()
        }
      });

    },
    beforeAtt(file) {
      this.dialogVisibleimg = true;
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      let arr = ['jpg','png','pdf','doc','docx','.xls','.xlsx']
      if (arr.indexOf(testmsg) === -1) {
        alert('上传文件格式有误')
        return false
      }else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.upload(copyFile);
        return false;
      }
    },
    upload(file) {
      let formData = new FormData();
      formData.append("file", file);
      uploadAll(formData).then(response => {
        if (response.code === 200) {
          let imgSrc = process.env.VUE_APP_BASE_API + response.data.url;
          let arr = [{
            name: response.data.name,
            url: imgSrc
          }]
          this.formData.fileInfo = arr
          this.formData.attachmentOneUrl = response.data.id
          this.$forceUpdate()
          this.$refs.upload.clearValidate();
          // this.dialogImageUrl = imgSrc;
          // this.msgSuccess("修改成功");
        }
      });
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    add_table() {
      this.handleClose()
      this.$emit('on-stable')
    }
  },
  async created() {
    await this.getUnit()
    this.$DStore.refresh(['getWorkType'], true);
    // console.log(this.$DStore.state);
    // let arr = [1,2,3]
    // let arr1 = [
    //   {a:1234}
    // ]
    // console.log(this.$lGet(arr,[0],null));
    // console.log(this.$lGet(arr1,['0','a'],null));
  },
};
</script>
<style lang="scss" scoped>
::v-deep{
  .el-date-editor{
    width: 100%;
  }
  .elUp{
    .el-upload-list__item{
      transition: none
    }
  }
}
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right{
    padding: 0;
    float: right;
  }
}
.fp_title {
  margin-top: 18px;
  display: flex;
  justify-content: space-between;
}
.fp_foot{
  .el-form-item{
    width: 260px;
  }
}

.fbj_warp {
  display: flex;
  justify-content: space-around;
  .fbj_letf,
  .fbj_right {
    width: 374px;
    height: 235px;
    position: relative;
    .fbj_name, .fbj_sex, .fbj_nation, .fbj_birth,.fbj_address,.fbj_idNumber,.fbj_authority,.fbj_bedate {
      position: absolute;
    }
  }
  .fbj_letf {
    background: url(./../image/lbj.jpg);
    .fbj_name {
      width: 160px;
      top: 16px;
      left: 70px;
    }
    .fbj_sex,
    .fbj_nation {
      top: 52px;
      left: 70px;
      width: 40px;
      ::v-deep {
        .el-input__inner {
          padding-right: 0;
        }
        .el-input__suffix {
          display: none;
        }
      }
    }
    .fbj_nation {
      left: 160px;
      width: 70px;
    }
    .fbj_birth {
      top: 96px;
      left: 70px;
      width: 160px;
      .el-date-editor {
        width: 160px;
      }
    }
    .fbj_address{
      top: 134px;
      left: 70px;
    }
    .fbj_idNumber{
      top: 172px;
      left: 136px;
    }

  }
  .fbj_right {
    background: url(./../image/rbj.jpg);
    .fbj_authority {
      width: 200px; top: 139px; left: 150px;
    }
    .fbj_bedate{
      top: 175px;left: 150px;
      ::v-deep{
        .el-date-editor{
          width: 100px;
          .el-input__inner{
            padding: 0 10px;
          }
          .el-input__prefix{
            display: none;
          }
        }
      }

    }
  }
}

.k_dell{
  display: flex;
  margin-top: 18px;
  flex-wrap: wrap;
  .k_form{flex:2}
  .k_right{ flex: 1;text-align: center;
  .kr_title { span { margin: 0 20px; } }
  }
  .kf_wp{
    display: flex;
    width: 100%;
    .el-form-item{
      margin-right: 10px;
      flex: 1;
    }
    ::v-deep{
      .el-upload--picture-card{
        width: 60px;
        height: 60px;
        line-height: 68px;
      }
    }

  }
}
.kd_button{
  text-align: center;
}
.dic_form{
  padding: 0 20px;
}
.footer{
  overflow: hidden;
  .r_button{
    float: right;
  }
}
::v-deep {
  .el-dialog__body {
    padding-top: 0;
  }
  .el-tabs{
    padding: 0 10px;
  }
  .el-select{
    width: 100%;
  }
  .basic {
    .el-form-item {
      margin: 0;
    }
    .el-form-item__content {
      margin-left: 0 !important;
      .el-input__inner {
        background: rgba(250, 250, 250, 0);
        border-color: #ccc;
      }
    }
    .is-error .el-input__inner {
      border-color: #ff4949 !important;
    }
  }
}
.avatar {
  width: 100%;
  height: 100%;
}
// .warp {
//   position: absolute;
//   top: -50px;
//   left: 0;
//   bottom: 0;
//   right: 0;
//   background: #fff;
//   z-index: $eq-pop;
//   // padding: 0 40px 20px;
//   overflow-y: auto;
// }
</style>
