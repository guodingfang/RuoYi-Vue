import request from '@/utils/request'

// 考勤列表
export function getDtable(query) {
  return request({
    url: '/ws/teamPerson/list/auth',
    method: 'get',
    params: query
  })
}
// 授权
export function addDance(data) {
  return request({
    url: '/ws/attendanceBinding/batch',
    method: 'post',
    data: data
  })
}

// 解绑
// export function detailDance(id) {
export function relieveDance(id) {
  return request({
    url: '/ws/attendanceBinding/' + id,
    method: 'delete',
  })
}
// 详情
export function detailDance(id) {
  return request({
    url: '/ws/teamPerson/' + id,
    method: 'get',
  })
}

// 授权列表 （0授权不成功，1授权成功，2授权中）
export function getStable(query) {
  return request({
    url: '/ws/attendanceBinding/list',
    method: 'get',
    params: query
  })
}

// 工地划分list（左）projectId=1
export function getZtable(query) {
  return request({
    url: '/ws/attendanceZone/list/machines',
    method: 'get',
    params: query
  })
}
// 考勤机list（右）projectId=1&zoneId=30
export function getMtable(query) {
  return request({
    url: '/ws/attendanceMachine/list',
    method: 'get',
    params: query
  })
}
