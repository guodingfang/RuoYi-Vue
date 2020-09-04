import request from '@/utils/request'

// 查询项目 列表(表格)
export function getProjetList(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/list',
    method: 'get',
    params: query
  })
}

// 查询项目 列表(无监测的项目数)
export function getProjetNum(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/nothingMonitoring',
    method: 'get',
    params: query
  })
}

// 查询项目 列表(无监测的项目数)
export function getProjetNumList(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/nothingMonitoringList',
    method: 'get',
    params: query
  })
}
// 监测点下拉数据
export function getSelectList(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/projectIdMonitoringEquipmentNo',
    method: 'get',
    params: query
  })
}

//环境监测底部文字
export function getRealText(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monitoringEquipmentNoDust',
    method: 'get',
    params: query
  })
}

//环境监测7天扬尘变化
export function getMonitTop(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/dustSevenDaySituation',
    method: 'get',
    params: query
  })
}
//环境监测7天噪音变化
export function getMonitBottom(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/noiseSevenDaySituation',
    method: 'get',
    params: query
  })
}

//环境监测--扬尘变化每日
export function getDayPm(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/sameDayDustPm25OrDustPm10',
    method: 'get',
    params: query
  })
}
//环境监测--扬尘变化每月
export function getMonthPm(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monthlyDayDustPm25OrDustPm10',
    method: 'get',
    params: query
  })
}
//环境监测--噪音变化每日
export function getDayDb(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/sameDayDustStrepitus',
    method: 'get',
    params: query
  })
}
//环境监测--噪音变化每月
export function getMonthDb(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monthlyDayDustStrepitus',
    method: 'get',
    params: query
  })
}
//环境监测--温度变化每日
export function getDayTm(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/sameDayDustTemperature',
    method: 'get',
    params: query
  })
}
//环境监测--温度变化每月
export function getMonthTm(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monthlyDayDustTemperature',
    method: 'get',
    params: query
  })
}
//环境监测--风速变化每日
export function getDaySp(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/sameDayDustWindSpeed',
    method: 'get',
    params: query
  })
}
//环境监测--风速变化每月
export function getMonthSp(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/monthlyDayDustWindSpeed',
    method: 'get',
    params: query
  })
}

//监测统计：pm10,噪音，温度，风速（每日）-企业-项目
export function getDayText(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/averageDustMonitoringDetailsDay',
    method: 'get',
    params: query
  })
}

//监测统计：pm10,噪音，温度，风速（每月）-企业-项目
export function getMonthText(query) {
  return request({
    url: '/ws/dustMonitoringIntelligent/averageDustMonitoringDetailsMonth',
    method: 'get',
    params: query
  })
}



//监测点列表
export function getPointTable(query) {
  return request({
    url: '/ws/dustMonitoringpoint/list',
    method: 'get',
    params: query
  })
}

//查询监测设备（下拉）
export function getDust(query) {
  return request({
    url: '/ws/dustMonitoringpoint/listDustMonitoring',
    method: 'get',
    params: query
  })
}

//查询视频设备
export function getVideo(query) {
  return request({
    url: '/ws/dustMonitoringpoint/listHookVideo',
    method: 'get',
    params: query
  })
}

//查询视频设备是否重复
export function hooksVideo(query) {
  return request({
    url: '/ws/dustMonitoringpoint/listHookVideoRepeat',
    method: 'get',
    params: query
  })
}

//删除黑名单人员
export function deletePoint(id) {
  return request({
    url: '/ws/dustMonitoringpoint/' + id,
    method: 'delete',
  })
}

// 添加
export function addPoint(data) {
  return request({
    url: '/ws/dustMonitoringpoint/add',
    method: 'post',
    data: data
  })
}

// 编辑
export function modifyPoint(data) {
  return request({
    url: '/ws/dustMonitoringpoint/edit',
    method: 'put',
    data: data
  })
}

//启用禁用
export function statePoint(isDelete,id) {
  return request({
    url: `/ws/dustMonitoringpoint/enableDisable/${isDelete}/${id}`,
    method: 'put',
  })
}

// 详情
export function detailPoint(id) {
  return request({
    url: '/ws/dustMonitoringpoint/' + id,
    method: 'get',
  })
}