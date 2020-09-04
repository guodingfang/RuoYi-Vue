import request from '@/utils/request'

//列表页
export function getCtable(query) {
  return request({
    url: '/ws/electricityMonitorManage/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addConfig(data) {
  return request({
    url: '/ws/electricityMonitorManage/add',
    method: 'post',
    data: data
  })
}

// 修改
export function modifyConfig(data) {
  return request({
    url: '/ws/electricityMonitorManage/edit',
    method: 'put',
    data: data
  })
}

// 启用禁用
export function stateConfig(data) {
  return request({
    url: '/ws/electricityMonitorManage/enableDisable',
    method: 'put',
    data: data
  })
}

// 删除项目参建单位
export function deleteConfig(id) {
  return request({
    url: '/ws/electricityMonitorManage/' + id,
    method: 'delete'
  })
}

// 详情
export function detailConfig(id) {
  return request({
    url: '/ws/electricityMonitorManage/'  + id,
    method: 'get',
  })
}

//监测设备
export function listMonit(query) {
  return request({
    url: '/ws/electricityMonitorManage/listMonitoringEquipment',
    method: 'get',
    params: query
  })
}

//监测设备
export function listSite(query) {
  return request({
    url: '/ws/electricityMonitorManage/listProjectSitePartition',
    method: 'get',
    params: query
  })
}