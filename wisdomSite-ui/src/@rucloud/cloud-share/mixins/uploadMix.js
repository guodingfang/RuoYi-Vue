// import Materia from './api';
// import { SUC_CODE } from '@/http/serve/response';
import cloneDeep from 'lodash/cloneDeep';
import axios from 'axios';
export default {
  data() {
    return {
      fileList: [],
      filePaths: [],
      markSwitch: true /* 服务器报错给的标示(删除fileList) */,
      formDataList: [],
      successFile: false,
      deepList: [], // 自动上传删除比对
    };
  },
  methods: {
    async myUpload(args) {
      /* 自定义上传方法 */
      this.formDataList.push(args);
      /* 判断,在修改和查看详情时直接调用上传 */
      const formData = new FormData();
      formData.append('files', args.file);
      formData.append('tableName', this.TN);
      formData.append('fid', this.fModel.fid);
      axios
        .post(`/file/upload`, formData, {
          onUploadProgress: event => {
            event.percent = (event.loaded / event.total) * 100;
            args.onProgress(event);
          },
        })
        .then(res => {
          if (res.code === this.$r_code.suc) {
            args.onSuccess(res.datas);
            this.deepList.push({
              ...args.file,
              id: res.datas
            })
          } else {
            args.onError(res.datas);
          }
        });
    },
    ajaxUpload(option, param) {
      const formData = new FormData();
      formData.append('files', option.file);
      formData.append('tableName', param.TN);
      formData.append('fid', param.fid);
      axios
        .post(`/file/upload`, formData, {
          onUploadProgress: event => {
            event.percent = (event.loaded / event.total) * 100;
            option.onProgress(event);
          },
        })
        .then(res => {
          if (res.code === this.$r_code.suc) {
            option.onSuccess(res.datas);
          } else {
            option.onError(res.datas);
          }
        });
    },
    async handleupload(fid, tableName) {
      /* 上传接口 */ /* （'material_apply'） */
      const vm = this;
      vm.formDataList.forEach(async item => {
        const formData = new FormData();
        formData.append('files', item.file);
        formData.append('tableName', tableName);
        formData.append('fid', fid);
        vm.filePaths.push(
          new Promise(function(resolve, reject) {
            // 做一些异步操作
            axios
              .post(`/file/upload`, formData, {
                onUploadProgress: event => {
                  event.percent = (event.loaded / event.total) * 100;
                  item.onProgress(event);
                },
              })
              .then(res => {
                if (res.code === vm.$r_code.suc) {
                  item.onSuccess(res.datas);
                } else {
                  item.onError(res.datas);
                }
                resolve(res);
              });
          })
        );
      });
      Promise.all(vm.filePaths).then(function(values) {
        vm.resetData();
        vm.$emit('on-close', true);
      });
    },
    comUpload() {
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].submit();
        }
      });
    },
    handSuccess(res, file, fileList) {
      /* 上传成功的回调 */
    },
    handleUploadError(c) {
    },
    uploadProgress(args) {
      // console.log(args,'uploadProgress');
    },
    uploadChange(file, fileList) {
      const { status } = file;
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M && (this.state === 'add' || this.state === 'purchaseToModify')) {
        this.$message.error('上传文件大小不能超过10MB!');
        if (this.$refs['upload']) {
          const fileIns = this.$refs['upload'].uploadFiles;
          fileIns.forEach((fileIn, index) => {
            if (fileIn.uid === file.uid) {
              fileIns.splice(index, 1);
            }
          });
        }
      }
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M) {
        this.$message.error('上传文件大小不能超过10MB!');
      }
      this.markSwitch = isLt2M;
      return isLt2M;
    },
    async handleRemove(file, fileList) {
      /* 删除上传 */
    },
    async handlePreview(file) {
      /* 点击列表 */
      if (file.id) {
        this.$confirm(`是否下载${file.name}？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        })
          .then(() => {
            // Materia.download({ fid: file.id }, file.name);
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消下载',
            });
          });
      } else {
        this.$message('该文件暂不可下载');
      }
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 10 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    async beforeRemove(file, fileList) {
      if (this.markSwitch) {
        await this.$confirm(`确定移除 ${file.name}？`, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        });
        console.log(fileList, 'fileList');
        let adopt;
        if (this.state !== 'add' && this.state !== 'purchaseToModify') {
          try {
            let id
            if (!file.id) {
              this.deepList.filter((item) => {
                if (item.uid === file.uid) {
                  id = item.id
                }
              })
            } else {
              id = file.id
            }
            // const res = await Materia.deleteFile({ fid: id });
            // if (res.code === SUC_CODE) {
            if (this.markSwitch) {
              this.$message({
                showClose: true,
                type: 'success',
                message: this.$lGet(res, 'message', res.message),
              });
            }
            adopt = true;
            // }
            if (res.ERROR_RESULT === false) {
              adopt = false;
            }
          } catch (err) {
            this.$message({
              type: 'info',
              message: '已取消删除',
            });
            adopt = false;
          }
        }
        return adopt;
      }

      // let defineOrCancel = this.$confirm(`确定移除 ${file.name}？`);
      // let adopt
      // try {
      //   await defineOrCancel
      //   adopt = true
      // } catch(err) {
      //   adopt = false
      // }
      // if (adopt) {
      //   const res = await Materia.deleteFile({ fid: file.id })
      //   if (res.code === SUC_CODE) {
      //     this.$message({
      //       showClose: true,
      //       type: 'success',
      //       message: this.$lGet(res, 'message', res.message),
      //     });
      //   }
      //   if (res.ERROR_RESULT === false) {
      //     off = false
      //   }
      //   console.log(await defineOrCancel);
      // }
      // return defineOrCancel
    },
  },
};
