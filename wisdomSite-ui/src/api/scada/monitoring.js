import request from '@/utils/request'

//电表详情
export function getMtable(query) {
  return request({
    url: '/ws/electricityMonitorManage/listMeterDetails',
    method: 'get',
    params: query
  })
}

//用电量
export function getKwh(query) {
  return request({
    url: '/ws/electricityMonitorManage/electricityConsumption',
    method: 'get',
    params: query
  })
}

//能耗分析
export function getEchartsData(query) {
  return request({
    url: '/ws/electricityMonitorManage/listEnergyConsumptionAnalysis',
    method: 'get',
    params: query
  })
}

//用电详情
export function detailKwh(query) {
  return request({
    url: '/ws/electricityMonitorManage/listPowerConsumptionDetails',
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

// // 查看项目参建单位详情
// export function detailProjectUnit(id) {
//   return request({
//     url: '/ws/projectParticipatingUnit/'+id,
//     method: 'get',
//   })
// }