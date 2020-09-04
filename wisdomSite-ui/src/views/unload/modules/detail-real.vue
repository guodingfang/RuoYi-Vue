<template>
  <!-- 分配角色数据权限对话框 -->
  <el-dialog
    title="监控详情"
    :visible.sync="isShow"
    width="1400px"
    append-to-body
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <vue-scroll ref="scroll" style="height: 700px">
      <div class="d_warp">
        <div class="dl_warp">
          <div class="dl_device">
            <p class="split-tit">设备信息</p>
            <p>设备名称：{{ detailData.unloadingEquipmentName }}</p>
            <p>设备编号：{{ detailData.monitoringEquipmentNo }}</p>
          </div>
          <div class="dl_device">
            <p class="split-tit">
              实时数据
              <span class="spt-right">{{ detailData.realUpdateTime }}</span>
            </p>
            <p>载重统计</p>
            <div class="dl_content">
              <div class="dlc_l">
                <p>{{ detailData.currentLoad }}kg</p>
                <p>当前载重量</p>
              </div>
              <div class="dlc_r">
                <p>{{ detailData.maxAllowableLoad }}kg</p>
                <p>最大允许载重</p>
              </div>
            </div>
          </div>
          <div class="dl_device dl_electric">
            <p class="split-tit">电量统计</p>
            <v-chart
              ref="rb"
              style="width: 100%"
              :options="rbOptions"
              autoresize
            />
            <div class="dle_text">
              <p class="dlet_t">{{ detailData.batteryPercentage }}%</p>
              <span class="dlet_b">电量百分比</span>
            </div>
          </div>
        </div>
        <div class="dr_warp">
          <div class="dr_card">
            <p class="split-tit st_cb">
              工作记录
              <el-radio-group class="stb_r" v-model="radio">
                <el-radio :label="1">今日数据</el-radio>
                <el-radio :label="2">近15天数据</el-radio>
              </el-radio-group>
              <el-button size="mini" @click="innerVisible = true"
                >更多详情</el-button
              >
            </p>
            <v-chart
              style="width: 100%;height: 320px"
              ref="rt"
              :options="Toptions"
              autoresize
            />
          </div>
          <div class="dr_card">
            <p class="split-tit">
              告警记录
              <span>近15天数据</span>
            </p>
            <div class="drc_bw">
              <div class="drc_l" style="position: relative;">
                <v-chart
                  ref="rb"
                  style="width: 100%;height: 320px"
                  :options="blOption"
                  autoresize
                />
                <div class="dle_text">
                  <p class="dlet_t" :class="{'dlet_n': $lGet(detailData,'alarm.0',0) == 0}">{{ $lGet(detailData,'alarm.0',0) }}</p>
                  <span class="dlet_b">今日告警</span>
                </div>
              </div>
              <div class="drc_r">
                <v-chart
                  style="width: 100%;height: 320px"
                  ref="rt"
                  :options="brOption"
                  autoresize
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-dialog
        width="1000px"
        title="历史数据"
        :visible.sync="innerVisible"
        append-to-body
      >
        <tableLoad />
      </el-dialog>
    </vue-scroll>
  </el-dialog>
</template>
<script>
/* selectList */
import dayjs from "dayjs";
import { getRoleList, addRoleUser } from "@/api/system/enterpriseUser";
import { getDays, getBechart, getFive, getTechart } from "@/api/unload";
import tableLoad from './../components/table-uploud'
export default {
  name: "addForm",
  components: { tableLoad },
  data() {
    return {
      rbOptions: {
        color: ["#5bcaba", "#3d8da0"],
        tooltip: {
          trigger: "item",
          formatter: "{b}: {c} ({d}%)"
        },
        graphic: {
          type: "text",
          left: "center",
          top: "center",
          style: {
            // text: "   85% \n 电量百分比",
            // text: `   ${this.a}% \n 电量百分比`,
            textAlign: "center",
            fill: "#5bcaba",
            fontSize: 27
          }
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center"
            },
            emphasis: {
              label: {
                show: false,
                fontSize: "30",
                fontWeight: "bold"
              }
            },

            labelLine: {
              show: false
            },
            data: [
              { value: this.detailData.batteryPercentage, name: "电量百分比" },
              { value: (100 - this.detailData.batteryPercentage), name: "已用电量" },
            ]
          }
        ]
      },
      radio: 1,
      Toptions: {},
      blOption: {},
      brOption: {},
      innerVisible: false
    };
  },
  provide() {
    return {
      detailParm: this.detailData,
    };
  },
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
    },
    roleIdList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    isShow: {
      async handler(val) {
        if (val) {
          // console.log(this.roleIds, this.roleIdList);
          const re = await getDays({
            monitoringEquipmentNo: this.detailData.monitoringEquipmentNo
          });
          this.setTEcharts(re.rows);

          const res = await getBechart({
            monitoringEquipmentNo: this.detailData.monitoringEquipmentNo
          });
          let objData = {
            x: res.rows.map(i => i.alarmTime),
            a: res.rows.map(i => i.earlyWarningNum),
            b: res.rows.map(i => i.callPoliceNum)
          };
          this.setBrEcharts(objData);

          const rt = await getTechart({
            monitoringEquipmentNo: this.detailData.monitoringEquipmentNo
          })
          let objTd = {
            a: [rt.data.totalNum],
            b: [rt.data.callPoliceNum]
          };
          this.setBlEcharts(objTd);
        }
      },
      immediate: true
    },
    async radio(val) {
      let re
      if (val == 1) {
         re = await getDays({
            monitoringEquipmentNo: this.detailData.monitoringEquipmentNo
          });
      }else {
         re = await getFive({
            monitoringEquipmentNo: this.detailData.monitoringEquipmentNo
          });
      }
      this.setTEcharts(re.rows);
    },
  },
  async created() {},
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    setTEcharts(obj) {
      let objs = {
        data: obj.map(i => i.workRecordTime),
        value: obj.map(i => i.loadweight),

        // data: obj.map((i, index) => {
        //   return [i.workRecordTime, i.loadweight];
        // }),
      };
      // this.Toptions = {
      //   backgroundColor: "#ffffff",
      //   // legend: {
      //   //   left: 'center'
      //   // },
      //   tooltip: {
      //     trigger: "axis",
      //     axisPointer: {
      //       type: "shadow",
      //       textStyle: {
      //         color: "#fff"
      //       }
      //     }
      //   },
      //   grid: {
      //     left: "30",
      //     right: "20",
      //     bottom: "15",
      //     top: "70",
      //     containLabel: true
      //   },
      //   calculable: true,
      //   xAxis: [
      //     {
      //       type: "time",
      //       axisLine: {
      //         lineStyle: {
      //           color: "#9aa2a2"
      //         }
      //       },
      //       splitLine: {
      //         show: false
      //       },
      //       axisTick: {
      //         show: false
      //       },
      //       splitArea: {
      //         show: false
      //       },
      //       // interval:
      //       //   this.radio == 1 ? 5400 * 1000 : 60 * 5400 * 1000,
      //       axisLabel: {
      //         textStyle: {
      //           fontSize: 10,
      //           color: "#9ba3a3"
      //         },
      //         formatter: val => {
      //           var date = new Date(val);
      //           const minute = dayjs(date).minute();
      //           var texts = [dayjs(date).hour(), minute == 0 ? "00" : minute];
      //           var month = [dayjs(date).month() + 1, dayjs(date).date()];
      //           return texts.join(":")
      //           // return this.radio == 1
      //           //   ? texts.join(":")
      //           //   : month.join("-");
      //         }
      //       },
      //     }
      //   ],
      //   yAxis: [
      //     {
      //       type: "value",
      //       name: "吊重（t）",
      //       min: 0,
      //       minInterval: 1,
      //       // nameRotate: 90,
      //       // nameGap: 61,
      //       nameTextStyle: {
      //         padding: [0, 0, 20, 0],
      //         fontSize: 14
      //       },
      //       // nameLocation: "middle",
      //       position: "left",
      //       axisLine: {
      //         show: false
      //       },
      //       axisTick: {
      //         show: false
      //       },
      //       splitLine: {
      //         show: true,
      //         lineStyle: {
      //           type: "dashed"
      //         }
      //       }
      //     }
      //   ],
      //   // dataZoom: {
      //   //   // show: this.radio == 1 ? false : true,
      //   //   show: true,
      //   //   // type: 'inside',
      //   //   start: 0,
      //   //   end: 5
      //   // },
      //   series: [
      //     {
      //       name: "载重",
      //       // type: "bar",
      //       type: "line",
      //       symbol: "none", //取消折点圆圈
      //       // stack: "总量",
      //       barMaxWidth: 15,
      //       barGap: "10%",
      //       itemStyle: {
      //         normal: {
      //           color: "#e279e5"
      //         }
      //       },
      //       data: objs.data
      //     }
      //   ]
      // };
      this.Toptions = {
        backgroundColor: "#ffffff",
        // legend: {
        //   left: 'center'
        // },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            }
          }
        },
        grid: {
          left: "20",
          right: "5",
          bottom: "40",
          top: "70",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              }
            },
            data: objs.data
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "吊重（t）",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 20, 0],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "left",
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        dataZoom: {
          // show: this.radio == 1 ? false : true,
          show: true,
          // type: 'inside',
          start: 0,
          end: 5
        },
        series: [
          {
            name: "载重",
            // type: "bar",
            type: "line",
            // stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#e279e5"
              }
            },
            data: objs.value
          }
        ]
      };
    },
    setBlEcharts(obj) {
      this.detailData.alarm = parseInt(obj.a) + parseInt(obj.b)
      console.log(123,obj.b);
      this.blOption = {
        color: obj.b[0] == 0 ? ['#dbdbdc','#dbdbdc'] : ["#f7c376", "#f0874e"],
        // color: ["#f7c376", "#f0874e"],
        tooltip: {
          trigger: "item",
          formatter: "{b}: {c} ({d}%)"
        },
				// title: {
				// 	x: '130',
				// 	y: '130',
				// 	text: `${obj.b}`,
				// 	textAlign: 'center',
				// 	textStyle: {
				// 		fontSize: 22,
				// 		color: '#f0874e',
				// 		fontWeight: 'bold',
				// 	},
				// 	// subtext: `${total}`,
				// 	subtext: `今日告警`,
				// 	subtextStyle: {
				// 		// fontSize: 22,
				// 		// color: '#DAEBFF',
				// 	},
				// },
        series: [
          {
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center"
            },
            emphasis: {
              label: {
                show: false,
                fontSize: "30",
                fontWeight: "bold"
              }
            },

            labelLine: {
              show: false
            },
            data: [
              /*{ value: obj.a, name: "预警次数" },*/
              { value: parseInt(obj.a) + parseInt(obj.b), name: "告警次数" }
            ]
          }
        ]
      };
      console.log(this.blOption);
    },
    setBrEcharts(obj) {
      this.brOption = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            }
          }
        },
        legend: {
          x: "right"
        },
        dataZoom: {
          show: true,
          // type: 'inside',
          start: 0,
          end: 40
        },
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              }
            },
            data: obj.x
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "单位（次）",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 20, 0],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "left",
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        series: [
          {
            name: "预警次数",
            type: "bar",
            stack: "1",
            barMaxWidth: 17,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#f7c376"
              }
            },
            data: obj.a
          },
          {
            name: "报警次数",
            type: "bar",
            stack: "1",
            barMaxWidth: 17,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#f0874e"
              }
            },
            data: obj.b
          }
        ]
      };
    }
  }
};
</script>
<style lang="scss" scoped>
.split-tit.st_cb {
  align-items: center;
  justify-content: start;
}
.split-tit.st_cb::after {
  top: 7px;
}
.stb_r {
  margin: 0 17px 0 auto;
}
.d_warp {
  display: flex;
}
.dr_warp {
  flex: 1;
  margin-left: 27px;
}
.dl_device {
  width: 370px;
  // height: 770px;
  padding: 0 10px;
  border: 1px solid #e1ece8;
  margin-bottom: 17px;
}
.dl_electric {
  position: relative;
  .dle_text {
    position: absolute;
    left: 52%;
    top: 53%;
    transform: translate(-50%, -50%);
    width: 100px;
    font-size: 20px;
    text-align: center;
    .dlet_t {
      color: #5bcaba;
      font-weight: bold;
      margin: 0 0 10px;
    }
  }
}
.drc_l{
  .dle_text {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 100px;
    font-size: 14px;
    text-align: center;
    .dlet_t {
      color: #f0874e;
      font-size: 22px;
      font-weight: bold;
      margin: 0 0 10px;
    }
    .dlet_n {
      color: #dbdbdc;
    }
  }
}

.spt-right {
  margin-right: 10px;
}
.dl_content {
  @include flex(center, center);
  color: #00b588;
  margin: 20px 0;
  .dlc_l,
  .dlc_r {
    flex: 1;
    text-align: center;
  }
  .dlc_l {
    border-right: 1px solid;
  }
}
.dr_card {
  padding: 0 10px;
  border: 1px solid #e1ece8;
  margin-bottom: 17px;
  .drc_bw {
    @include flex(center, center);
  }
  .drc_l {
    width: 270px;
  }
  .drc_r {
    flex: 1;
  }
}
</style>
