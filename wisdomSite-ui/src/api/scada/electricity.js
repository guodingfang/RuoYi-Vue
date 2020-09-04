import request from '@/utils/request'

//黑名单列表页
export function getEtable(query) {
  return request({
    url: '/ws/electricityMonitorManage/listElectricitySecurity',
    method: 'get',
    params: query
  })
}
// // 新增项目参建单位
// export function addProjectUnit(data) {
//   return request({
//     url: '/ws/projectParticipatingUnit',
//     method: 'post',
//     data: data
//   })
// }

// // 修改项目参建单位
// export function modifyProjectUnit(data) {
//   return request({
//     url: '/ws/projectParticipatingUnit/bacthEdit',
//     method: 'put',
//     data: data
//   })
// }

// // 删除项目参建单位
// export function deleteProjectUnit(id) {
//   return request({
//     url: '/ws/projectParticipatingUnit/' + id,
//     method: 'delete'
//   })
// }

// 查看项目参建单位详情
export function detailModel(query) {
  return request({
    url: '/ws/electricityMonitorManage/realTimeMonitoring',
    method: 'get',
    params: query
  })
}


// 查看监测图表
export function getEcharts(query) {
  return request({
    url: '/ws/electricityMonitorManage/listMonitoringRecords',
    method: 'get',
    params: query
  })
}