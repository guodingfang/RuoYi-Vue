import request from '@/utils/request'

// 企业：监控统计-监控设备分析
export function getEquipAnaE(query) {
  return request({
    url: '/ws/elevatorManager/statistics/list',
    method: 'get',
    params: query
  })
}
// 企业：设备告警分析
export function alarmingAnaE(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/statistics/alarming',
    method: 'get',
    params: query
  })
}
// 企业：项目告警分析
export function projAlarmAnaE(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/project/alarming',
    method: 'get',
    params: query
  })
}

// 企业：报警项目排名
export function alarmRankE(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/project/alarming/list',
    method: 'get',
    params: query
  })
}

// 项目：司机不良操作
export function driverBadP(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/statistics/driver',
    method: 'get',
    params: query
  })
}

// 项目：设备告警
export function deviceAlarmP(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/statistics',
    method: 'get',
    params: query
  })
}

// 企业：违章详情
export function illegalsListE(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/illegals',
    method: 'get',
    params: query
  })
}

// 违章导出
export function exportIllegals(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/exportIllegals',
    method: 'get',
    params: query
  })
}

// 企业：设备详情
export function deviceListE(query) {
  return request({
    url: '/ws/elevatorManager/project/details',
    method: 'get',
    params: query
  })
}

// 企业：设备详情-导出
export function exportSbDetailE(query) {
  return request({
    url: '/ws/elevatorManager/project/exportDetails',
    method: 'get',
    params: query
  })
}

// 企业-监控统计-违章详细导出
export function exportWzDetailE(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/exportIllegals',
    method: 'get',
    params: query
  })
}

// 工作详情-项目
export function getWorkList(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/details',
    method: 'get',
    params: query
  })
}

// 工作详情-项目
export function exportGzDetailP(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/exportDetails',
    method: 'get',
    params: query
  })
}

/*********************项目实时监控START*************************/
// 实时监控-项目整体统计
export function getRtAll(query) {
  return request({
    url: '/ws/elevatorManager/project/all',
    method: 'get',
    params: query
  })
}

// 实时监控-列表
export function getRtList(query) {
  return request({
    url: '/ws/elevatorWorkCycleManager/statistics/realTime',
    method: 'get',
    params: query
  })
}

/*********************项目实时监控END*************************/

/*********************设备管理相关START*************************/
// 设备列表
export function deviceManaList(query) {
  return request({
    url: '/ws/elevatorManager/list',
    method: 'get',
    params: query
  })
}

// 新增升降机
export function addElevator(data) {
  return request({
    url: '/ws/elevatorManager',
    method: 'post',
    data,
  })
}

// 编辑升降机
export function editElevator(data) {
  return request({
    url: '/ws/elevatorManager',
    method: 'put',
    data,
  })
}


//获取升降机设备或人脸设备列表
export function getSelList(projectId, equipmentType) {
  return request({
    url: '/ws/project/equipment/list',
    method: 'get',
    params: {
      projectId,
      equipmentType,
      enableStatus: 0,
    }
  })
}

// 根据id获取升降机详情
export function getElevatorDetail(id) {
  return request({
    url: `/ws/elevatorManager/${id}`,
    method: 'get',
  })
}

// 离场
export function departure(id) {
  return request({
    url: `/ws/elevatorManager/${id}/offSite`,
    method: 'put',
  })
}

// 增加场部图
export function addLayOutMap(data) {
  return request({
    url: `/ws/projectFieldLayout`,
    method: 'post',
    data,
  })
}

// 回显场部图
export function getLayOutMap(projectId, equipmentType=2) {
  return request({
    url: `/ws/projectFieldLayout/${projectId}/${equipmentType}`,
    method: 'get',
  })
}

// 回显电梯
export function getElePosList(projectId) {
  return request({
    url: `/ws/elevatorManager/list/onlineState`,
    method: 'get',
    params: { projectId, sign: 1 }
  })
}

// 增加场部图设备
export function addMapDevice(data) {
  return request({
    url: `/ws/elevatorManager/bacthSign`,
    method: 'post',
    data: {
      elevators: data
    }
  })
}

// 删除场部图设备
export function delMapDevice(id) {
  return request({
    url: `/ws/elevatorManager/unSign/${id}`,
    method: 'put',
  })
}


// 删除
export function delElevator(id) {
  return request({
    url: `/ws/elevatorManager/${id}`,
    method: 'delete',
  })
}

/*********************设备管理相关END*************************/

/*********************监控详情START*************************/
// 升降机信息
export function getElevInfo(id) {
  return request({
    url: `/ws/elevatorManager/${id}`,
    method: 'get',
  })
}

// 司机信息
export function getDriverInfo(serialNumber) {
  return request({
    url: `/ws/elevatorManager/driver`,
    method: 'get',
    params: {
      serialNumber
    }
  })
}

// 昨日今日统计
export function getYeAndToInfo(serialNumbers) {
  return request({
    url: `/ws/elevatorWorkCycleManager/statistics/yesterdayAndToday`,
    method: 'get',
    params: {
      serialNumbers
    }
  })
}

// 设备状态分析
export function getDevStatusAna(serialNumbers, rowNumber=20) {
  return request({
    url: `/ws/elevatorWorkCycleManager/list`,
    method: 'get',
    params: {
      rowNumber,
      serialNumbers,
    }
  })
}

// 吊装循环次数
export function getDevCycleAna(serialNumbers, days=7) {
  return request({
    url: `/ws/elevatorWorkCycleManager/statistics/days`,
    method: 'get',
    params: {
      days: `-${days}`,
      serialNumbers,
    }
  })
}

// 实时监控
export function getTopRtData(serialNumber) {
  return request({
    url: `/ws/elevatorMonitorData/realTime`,
    method: 'get',
    params: {
      serialNumber
    }
  })
}

// 司机历史记录
export function getDrivHisList(query) {
  return request({
    url: `/ws/elevatorManager/driverList`,
    method: 'get',
    params: query
  })
}

// 近期违章率
export function getRecentWz(serialNumbers, rowNumber=1) {
  return request({
    url: `/ws/elevatorWorkCycleManager/statistics/times`,
    method: 'get',
    params: {
      serialNumbers,
      rowNumber
    }
  })
}
/*********************监控详情END*************************/
/*********************企业地图START*************************/
//地图-左侧区域整体统计
export function getAreaData(data) {
  return request({
    url: `/ws/elevatorManager/area`,
    method: 'post',
    data: {
      wsArea: data
    }
  })
}

//地图-右侧区域项目统计列表
export function getAreaAllData(data) {
  return request({
    url: `/ws/elevatorManager/area/all`,
    method: 'post',
    data: {
      wsArea: data
    }
  })
}

/*********************企业地图END*************************/
