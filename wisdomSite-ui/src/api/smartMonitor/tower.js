import request from '@/utils/request'

// 企业：监控统计-监控设备分析
export function getEquipAnaE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/monitoringEquipmentAnalysis',
    method: 'get',
    params: query
  })
}
// 企业：获取实时监测首页数据-根据项目id塔吊设备位置
export function rtProjTowerE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/realTimeTowercraneEquipment',
    method: 'get',
    params: query
  })
}
// 企业：监控统计-监控设备分析
export function getEquipViolAnaE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentViolationAnalysis',
    method: 'get',
    params: query
  })
}

// 企业：监控统计-违章项目排名
export function getViolationsRankE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/rankingOfViolations',
    method: 'get',
    params: query
  })
}
// 企业：监控统计-项目违章占比
export function getViolationsPerE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/projectViolationProportion',
    method: 'get',
    params: query
  })
}
/** 更多详情相关接口*/
// 企业：监控统计-项目违章占比
export function getequipmentListE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentList',
    method: 'get',
    params: query
  })
}

// 违章详情-按企业
export function violationDetailsE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/violationDetails',
    method: 'get',
    params: query
  })
}
// 违章详情-按项目
export function violationDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/violationDetailsProject',
    method: 'get',
    params: query
  })
}
// 工作详情-按项目
export function getGzListP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/workDetailsProject',
    method: 'get',
    params: query
  })
}
// 导出工作详情-按项目
export function exportGzDetailP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/exportWorkDetailsProject',
    method: 'get',
    params: query
  })
}
// 导出工作详情
export function exportWzDetailE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/illegalExportViolationDetails',
    method: 'get',
    params: query
  })
}
// 导出违章详情-按企业
export function exportWzDetailP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor//illegalExportViolationDetailsProject',
    method: 'get',
    params: query
  })
}
// 导出设备详情-按企业
export function exportSbDetailE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/illegalExportDetails',
    method: 'get',
    params: query
  })
}
// 设备详情-按企业
export function deviceDetailsE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/deviceDetails',
    method: 'get',
    params: query
  })
}
/** 自定义相关接口*/
// 违章项目排名-按企业--自定义
export function getViolationsRankCusE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/rankingOfViolationsCustom',
    method: 'get',
    params: query
  })
}
// 监控设备分析/设备违章分析-按企业--自定义
export function getMoniEquiViolE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/monitoringEquipmentOrViolation',
    method: 'get',
    params: query
  })
}
// 项目违章占比--自定义
export function getProjViolPropE(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/projectViolationProportionCustom',
    method: 'get',
    params: query
  })
}
/** 项目*/
//司机不良操作-按项目
export function badOperationDriveP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/projectBadOperationDriver',
    method: 'get',
    params: query
  })
}
//实时监控-按项目
export function equipmentRealtimeMonitoringP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentRealtimeMonitoring',
    method: 'get',
    params: query
  })
}
//设备违章-按项目
export function equipmentViolationP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentViolationProject',
    method: 'get',
    params: query
  })
}
//实时监控-按项目-告警记录：柱状图
export function equipmentWorkAlarmDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkAlarmDetails',
    method: 'get',
    params: query
  })
}
//实时监控-按项目-工作记录详情
export function equipmentWorkCycleDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkCycleDetails',
    method: 'get',
    params: query
  })
}
//实时监控-按项目-工作记录
export function equipmentWorkCycleRecordP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkCycleRecord',
    method: 'get',
    params: query
  })
}
// 实时监控-按项目-司机历史数据
export function equipmentWorkDriverDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkDriverDetails',
    method: 'get',
    params: query
  })
}
//设备工作记录-按项目
export function equipmentWorkRecordP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkRecord',
    method: 'get',
    params: query
  })
}
//实时监控-按项目-告警记录：今日告警 环形图
export function equipmentWorkWarningTodayDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkWarningTodayDetails',
    method: 'get',
    params: query
  })
}
//实时监控-按项目-出工作记录详情
export function exportEquipmentWorkCycleDetailsP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/exportEquipmentWorkCycleDetails',
    method: 'get',
    params: query
  })
}
//违章详情-按项目
export function violationDetailsProjectP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/violationDetailsProject',
    method: 'get',
    params: query
  })
}
//工作详情-按项目
export function workDetailsProjectP(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/workDetailsProject',
    method: 'get',
    params: query
  })
}
// 项目塔吊设备管理列表数据
export function towerEquipmentList(query) {
  return request({
    url: '/ws/towercraneEquipment/list',
    method: 'get',
    params: query
  })
}

// 项目实时监控
export function projectRealTimeMonitoring(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/projectRealTimeMonitoring',
    method: 'get',
    params: query
  })
}

// 设备监控
export function equipmentRealtimeMonitoring(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentRealtimeMonitoring',
    method: 'get',
    params: query
  })
}

// 监控详情
export function projectMonitoringDetails(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/projectMonitoringDetails',
    method: 'get',
    params: query
  })
}

// 吊钩视频
export function hookVideoDetails(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/hookVideoDetails',
    method: 'get',
    params: query
  })
}


// 设备状态分析
export function getDevStatusAna(monitoringEquipmentNo, frequency) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentStatusAnalysis',
    method: 'get',
    params: {
      monitoringEquipmentNo,
      frequency
    }
  })
}

// 吊装循环次数
export function getDevCycleAna(monitoringEquipmentNo, queryType) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/hoistingCycle',
    method: 'get',
    params: {
      monitoringEquipmentNo,
      queryType
    }
  })
}

// 司机历史数据
export function getDrivHisList(query) {
  return request({
    url: '/ws/towerCraneEquipmentMonitor/equipmentWorkDriverDetails',
    method: 'get',
    params: query
  })
}

// 监控设备
export function moniDeviceList(query) {
  return request({
    url: '/ws/towercraneEquipment/listMonitoringEquipment',
    method: 'get',
    params: query
  })
}


// 吊钩视频列表
export function hookVideoList(query) {
  return request({
    url: '/ws/towercraneEquipment/listHookVideo',
    method: 'get',
    params: query
  })
}

// 查询塔机人脸设备
export function towerFaceList(query) {
  return request({
    url: '/ws/towercraneEquipment/listTowerCraneFace',
    method: 'get',
    params: query
  })
}

// 新增塔机
export function addTower(data) {
  return request({
    url: '/ws/towercraneEquipment/add',
    method: 'post',
    data,
  })
}

// 塔机离场
export function departure(id) {
  return request({
    url: `/ws/towercraneEquipment/departure/${id}`,
    method: 'put',
  })
}

// 根据id获取详情
export function getTowerDetail(id) {
  return request({
    url: `/ws/towercraneEquipment/query/${id}`,
    method: 'get',
  })
}

// 塔机离场
export function editTower(data) {
  return request({
    url: `/ws/towercraneEquipment/edit`,
    method: 'put',
    data,
  })
}

// 重新进场
export function towerReentry(data) {
  return request({
    url: `/ws/towercraneEquipment/reentry`,
    method: 'put',
    data,
  })
}

// 删除塔机
export function delTower(id) {
  return request({
    url: `/ws/towercraneEquipment/${id}`,
    method: 'delete',
  })
}

/*防碰撞*/
// 回显场部图
export function getLayOutMap(projectId) {
  return request({
    url: `/ws/towercraneEquipment/fieldLayout/${projectId}`,
    method: 'get',
  })
}

// 增加场部图
export function addLayOutMap(data) {
  return request({
    url: `/ws/towercraneEquipment/addFieldLayout`,
    method: 'post',
    data,
  })
}

// 回显塔机
export function getTowPosList(projectId) {
  return request({
    url: `/ws/elevatorManager/list/onlineState`,
    method: 'get',
    params: { projectId, sign: 1 }
  })
}

// 可选设备列表
export function deviceManaList() {
  return request({
    url: `/ws/towercraneEquipment/listEquipment`,
    method: 'get',
  })
}

// 增加场部图设备
export function addMapDevice(data) {
  return request({
    url: `/ws/towercraneEquipment/addPosition`,
    method: 'post',
    data: {
      wsTowercraneEquipmentList: data
    }
  })
}

// 删除场部图设备
export function delMapDevice(id) {
  return request({
    url: `/ws/towercraneEquipment/position/${id}`,
    method: 'put'
  })
}

/*********************企业地图START*************************/
//地图-左侧区域整体统计
export function getAreaData(data) {
  return request({
    url: `/ws/towerCraneEquipmentMonitor/area`,
    method: 'post',
    data: {
      wsArea: data
    }
  })
}

//地图-右侧区域项目统计列表
export function getAreaAllData(data) {
  return request({
    url: `/ws/towerCraneEquipmentMonitor/realTimeMonitoring`,
    method: 'post',
    data: {
      wsArea: data
    }
  })
}
