import request from '@/utils/request'

// 列表
export function getCamList(query) {
  return request({
    url: '/ws/video/monitoryPointManagement/list',
    method: 'get',
    params: query
  })
}

// 设置禁用/启用
export function setCamStatus(data) {
  return request({
    url: '/ws/video/monitoryPointManagement/enabled',
    method: 'put',
    data,
  })
}

// 监控设备列表
export function getChannelList(enableStatus) {
  return request({
    url: '/ws/video/channel/list',
    method: 'get',
    params: {
      enableStatus: enableStatus
    }
  })
}

// 区域列表（需界面添加固定的默认分区，新增监控点是用户选中默认分区后，传值zoneId=0）
export function getPartlList(projectId) {
  return request({
    url: '/ws/partition/list',
    method: 'get',
    params: {
      projectId
    }
  })
}

// 新增
export function addMoniPoint(data) {
  return request({
    url: '/ws/video/monitoryPointManagement',
    method: 'post',
    data,
  })
}


// 编辑
export function updateMoniPoint(data) {
  return request({
    url: '/ws/video/monitoryPointManagement',
    method: 'put',
    data,
  })
}

// 删除
export function delMoniPoint(id) {
  return request({
    url: `/ws/video/monitoryPointManagement/${id}`,
    method: 'delete',
  })
}
/* ----------------------视频首页相关接口-----------------------*/
// 项目列表
export function projectStatistics(query) {
  return request({
    url: '/ws/video/monitoryPointManagement/list/projectStatistics',
    method: 'get',
    params: query
  })
}

// 区域视频监控点列表
export function zoneStatistics(query) {
  return request({
    url: '/ws/video/monitoryPointManagement/list/zoneStatistics',
    method: 'get',
    params: query
  })
}

// 区域视频监控点列表
export function videoList(query) {
  return request({
    url: '/ws/video/monitoryPointManagement/videoList',
    method: 'get',
    params: query
  })
}































