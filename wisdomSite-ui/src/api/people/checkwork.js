import request from '@/utils/request'

// 查询单位下班组 列表
export function getTeam(query) {
  return request({
    url: '/ws/participatingTeam/list',
    method: 'get',
    params: query
  })
}

// 查询班组下人员 列表
export function getPerson(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}
// 详情
export function detailAtte(id,query) {
  return request({
    url: '/ws/attendanceRecord/list/' + id,
    method: 'get',
    params: query
  })
}

// 导出出入记录
export function exportAccessRecord(id,query) {
  return request({
    url: '/ws/attendanceRecord/export/' + id,
    method: 'get',
    params: query
  })
}