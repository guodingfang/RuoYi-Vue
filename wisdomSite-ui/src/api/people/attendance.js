import request from '@/utils/request'

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目参建单位 列表
export function listProjectParticipatingUnit(projectId) {
  return request({
    url: '/ws/projectParticipatingUnit/list',
    method: 'get',
    params: { projectId }
  })
}

// 获取首页 人员在岗，在册，在场，出勤数据
export function getOverview() {
  return request({
    url: '/ws/attendanceStatistics/personnelNumber',
    method: 'get',
  })
}

// 获取近7日 在场，出勤 数
export function getAttChartData() {
  return request({
    url: '/ws/attendanceStatistics/bePresentORattendanceToday',
    method: 'get',
  })
}

// 各项目在场人员占比前十
export function getZc10Data() {
  return request({
    url: '/ws/attendanceStatistics/personnelProjectProportion',
    method: 'get',
  })
}


// 各项目在场人员占比前十--查询全部
export function getZc10AllData() {
  return request({
    url: '/ws/attendanceStatistics/personnelProjectProportionWhole',
    method: 'get',
  })
}

// 下属项目出勤统计--集团级
export function getXsAttData(randomData = '2020-07-07') {
  return request({
    url: '/ws/attendanceStatistics/projectAttendance',
    method: 'get',
    params: { randomData }
  })
}
//--------------------项目首页--------------------------------
// 现场人员变化趋势图--项目级
export function getTrendData() {
  return request({
    url: '/ws/attendanceStatistics/scenePersonnelChangeTrend',
    method: 'get',
  })
}
// 现场工种前十统计--项目级
export function getProjZc10Data() {
  return request({
    url: '/ws/attendanceStatistics/fieldWork',
    method: 'get',
  })
}

// 现场工种前十统计--项目级 --查询全部
export function getProjZc10AllData() {
  return request({
    url: '/ws/attendanceStatistics/fieldWorkWhole',
    method: 'get',
  })
}
// 近7日人员出勤--项目级
export function getProjAttChartData() {
  return request({
    url: '/ws/attendanceStatistics/sevenDaysPersonnelAttendance',
    method: 'get',
  })
}
// 建筑工人分析前十--项目级
export function getProjWork10ChartData(query) {
  return request({
    url: '/ws/attendanceStatistics/constructionWorker',
    method: 'get',
    params: query
  })
}

// 建筑工人分析前十--项目级--查询全部
export function getProjWork10AllData(query) {
  return request({
    url: '/ws/attendanceStatistics/constructionWorkerWhole',
    method: 'get',
    params: query
  })
}

// 下属参建单位统计--项目级
export function getProjXsAttData(randomData = '2020-07-07') {
  return request({
    url: '/ws/attendanceStatistics/participatingUnits',
    method: 'get',
    params: { randomData }
  })
}
/** 出入记录相关接口*/
//出入记录列表
export function getAccessRecordList(query) {
  return request({
    url: '/ws/attendanceRecord/listRecord',
    method: 'get',
    params: query
  })
}

// 导出出入记录
export function exportAccessRecord(query) {
  return request({
    url: '/ws/attendanceRecord/exportRecord',
    method: 'get',
    params: query
  })
}

/** 报表中心关接口*/
// 花名册列表
export function getRosterList(query) {
  return request({
    url: '/ws/teamPerson/rosterList',
    method: 'get',
    params: query
  })
}

// 导出花名册列表
export function exportRoster(query) {
  return request({
    url: '/ws/teamPerson/rosterExport',
    method: 'get',
    params: query
  })
}

// 考勤记录
export function getAtteReport(query) {
  return request({
    url: '/ws/attendanceRecord/list',
    method: 'get',
    params: query
  })
}

