import request from '@/utils/request'
// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 动态带条件查询所有工程设备绑定
export function getDeviceList(query) {
  return request({
    url: '/ws/project/equipment/list',
    method: 'get',
    params: query
  })
}

// 工程设备转场
export function moveDevice(data) {
  return request({
    url: '/ws/project/equipment/project/change',
    method: 'put',
    data
  })
}

// 新增工程设备绑定
export function addDevice(data) {
  return request({
    url: '/ws/project/equipment/add',
    method: 'post',
    data
  })
}

// 修改
export function updateDevice(data) {
  return request({
    url: '/ws/project/equipment/update',
    method: 'put',
    data
  })
}


// 删除工程设备绑定，未绑定才能删除
export function delDevice(devcieId) {
  return request({
    url: '/ws/project/equipment/delete/' + devcieId,
    method: 'delete',
  })
}

// 查询是否存在工程设备绑定信息 TODO
// export function roleDeptTreeselect(roleId) {
//   return request({
//     url: '/ws/project/equipment/isExist' + roleId,
//     method: 'get'
//   })
// }

// 获取工程设备在线状态
export function roleDeptTreeselect() {
  return request({
    url: '/ws/project/equipment/query',
    method: 'post'
  })
}

/** ------------视频设备--------------*/

// 动态条件查询所有视频设备管理
export function getCamearList(query) {
  return request({
    url: '/ws/Video/equipment/list',
    method: 'get',
    params: query
  })
}

// 视频设备转场
export function moveCamear(data) {
  return request({
    url: '/ws/Video/equipment/project/change',
    method: 'put',
    data
  })
}

// 新增视频设备绑定
export function addCamear(data) {
  return request({
    url: '/ws/Video/equipment/add',
    method: 'post',
    data
  })
}

// 根据设备序列号查询单个视频设备管理信息
export function getCamearWithSN(equipmentSerialnumber) {
  return request({
    url: '/ws/Video/equipment/select/bySerialNumber/' + equipmentSerialnumber,
    method: 'get',
  })
}

// 修改
export function updateCamear(data) {
  return request({
    url: '/ws/Video/equipment/update',
    method: 'put',
    data
  })
}


// 删除视频设备绑定，未绑定才能删除
export function delCamear(equipmentSerialnumber) {
  return request({
    url: '/ws/Video/equipment/delete/' + equipmentSerialnumber,
    method: 'delete',
  })
}

// 查询录像机通道下视频信息
export function getCameraVideo(data) {
  return request({
    url: '/ws/Video/equipment/select/videoChannel',
    method: 'post',
    data,
  })
}

// 查看在线视频
export function getOnlineVideo(data) {
  return request({
    url: '/ws/Video/equipment/select/play',
    method: 'post',
    data,
  })
}

// 动态条件查询所有视频通道管理列表
export function getChannelList(data) {
  return request({
    url: '/ws/video/channel/list',
    method: 'post',
    data,
  })
}

// 摄像头设备绑定通道
export function updateChannel(data) {
  return request({
    url: '/ws/Video/equipment/channel/binding',
    method: 'put',
    data
  })
}

//删除视频设备通道信息
export function delChannel(id) {
  return request({
    url: '/ws/Video/equipment/delete/channel/' + id,
    method: 'delete',
  })
}

// 开启云台控制
export function camControl(data) {
  return request({
    url: '/ws/Video/equipment/start/PTZ',
    method: 'post',
    data
  })
}

// 开启云台控制
export function capture(data) {
  return request({
    url: '/ws/Video/equipment/capture',
    method: 'post',
    data
  })
}


// 查询视频
export function getVideo(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monitoringVideoDetails',
    method: 'get',
    params: query
  })
}