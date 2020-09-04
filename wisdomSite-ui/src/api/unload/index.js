import request from '@/utils/request'

/** 设备管理*/
//查询卸料平台-实时监控-设备数量信息
export function getDMList(query) {
  return request({
    url: '/ws/unloadingPlatformManage/list',
    method: 'get',
    params: query
  })
}

//查询卸料平台监测设备 （新增查询设备下拉框）
export function getJkList(query) {
  return request({
    url: '/ws/unloadingPlatformManage/listMonitoringEquipment',
    method: 'get',
    params: query
  })
}

//新增卸料平台设备管理
export function addUnload(data) {
  return request({
    url: '/ws/unloadingPlatformManage/add',
    method: 'post',
    data
  })
}

//修改卸料平台设备管理
export function editUnload(data) {
  return request({
    url: '/ws/unloadingPlatformManage/edit',
    method: 'put',
    data
  })
}

//删除
export function delUnload(id) {
  return request({
    url: `/ws/unloadingPlatformManage/${id}`,
    method: 'delete',
  })
}

//获取卸料平台设备管理 详细信息：
export function getUnloadDetail(id) {
  return request({
    url: `/ws/unloadingPlatformManage/${id}`,
    method: 'get',
  })
}


// 启用禁用
export function stateConfig(data) {
  return request({
    url: '/ws/unloadingPlatformManage/enableDisable',
    method: 'put',
    data: data
  })
}

// 回显场部图
export function getLayOutMap(projectId) {
  return request({
    url: `/ws/unloadingPlatformManage/fieldLayout`,
    method: 'get',
    params: {
      projectId
    }
  })
}


// 增加场部图
export function addLayOutMap(data) {
  return request({
    url: `/ws/unloadingPlatformManage/addFieldLayout`,
    method: 'post',
    data,
  })
}

// 回显设备
export function getUnloPosList(projectId) {
  return request({
    url: `/ws/elevatorManager/list/onlineState`,
    method: 'get',
    params: { projectId, sign: 1 }
  })
}

// 可选设备列表
export function deviceManaList() {
  return request({
    url: `/ws/unloadingPlatformManage/listUnloadingPlatformManage`,
    method: 'get',
  })
}

// 增加场部图设备
export function addMapDevice(data) {
  return request({
    url: `/ws/unloadingPlatformManage/addPosition`,
    method: 'post',
    data: {
      wsUnloadingPlatformManageList: data
    }
  })
}
// 删除场部图设备
export function delMapDevice(id) {
  return request({
    url: `/ws/unloadingPlatformManage/position/${id}`,
    method: 'put'
  })
}


/** 设备管理end*/


//查询卸料平台-实时监控-设备数量信息
export function getNum(query) {
  return request({
    url: '/ws/unloadingPlatformManage/realTimeMonitoring',
    method: 'get',
    params: query
  })
}

//查询卸料平台-实时监控-实时数据信息
export function getVdata(query) {
  return request({
    url: '/ws/unloadingPlatformManage/realTimeMonitoringData',
    method: 'get',
    params: query
  })
}

//查询卸料平台-实时监控-实时数据信息
export function getImage(query) {
  return request({
    url: '/ws/unloadingPlatformManage/fieldLayout',
    method: 'get',
    params: query
  })
}

// 详情
export function detailLeft(query) {
  return request({
    url: '/ws/unloadingPlatformManage/equipmentRealTimeInformation',
    method: 'get',
    params: query
  })
}

//查询卸料平台-监控详情-工作记录-今日
export function getDays(query) {
  return request({
    url: '/ws/unloadingPlatformManage/todayWorkRecord',
    method: 'get',
    params: query
  })
}

//查询卸料平台-监控详情-工作记录-15天
export function getFive(query) {
  return request({
    url: '/ws/unloadingPlatformManage/fifteenWorkRecord',
    method: 'get',
    params: query
  })
}

//查询卸料平台-监控详情-告警记录-15天
export function getBechart(query) {
  return request({
    url: '/ws/unloadingPlatformManage/fifteenAlarmLog',
    method: 'get',
    params: query
  })
}

//查询卸料平台-监控详情-告警记录-环形图-今日告警
export function getTechart(query) {
  return request({
    url: '/ws/unloadingPlatformManage/todayAlarmLog',
    method: 'get',
    params: query
  })
}

// 更多详情table
export function getEtable(query) {
  return request({
    url: '/ws/unloadingPlatformManage/equipmentMoredetails',
    method: 'get',
    params: query
  })
}
//导出
export function exportJob(query) {
  return request({
    url: '/ws/unloadingPlatformManage/exportHistoricalData',
    method: 'get',
    params: query
  })
}

//------------------------------------------
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

// 删除项目参建单位
export function deleteConfig(id) {
  return request({
    url: '/ws/electricityMonitorManage/' + id,
    method: 'delete'
  })
}

// 详情
export function detailConfig(query) {
  return request({
    url: '/ws/electricityMonitorManage/listProjectSitePartition',
    method: 'get',
    params: query
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
