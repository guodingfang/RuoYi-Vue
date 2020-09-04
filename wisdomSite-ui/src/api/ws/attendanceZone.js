import request from '@/utils/request'

// 查询考勤分区 列表
export function listAttendanceZone(query) {
  return request({
    url: '/ws/attendanceZone/list',
    method: 'get',
    params: query
  })
}

// 查询考勤分区 详细
export function getAttendanceZone(id) {
  return request({
    url: '/ws/attendanceZone/' + id,
    method: 'get'
  })
}

// 新增考勤分区 
export function addAttendanceZone(data) {
  return request({
    url: '/ws/attendanceZone',
    method: 'post',
    data: data
  })
}

// 修改考勤分区 
export function updateAttendanceZone(data) {
  return request({
    url: '/ws/attendanceZone',
    method: 'put',
    data: data
  })
}

// 删除考勤分区 
export function delAttendanceZone(id) {
  return request({
    url: '/ws/attendanceZone/' + id,
    method: 'delete'
  })
}

// 导出考勤分区 
export function exportAttendanceZone(query) {
  return request({
    url: '/ws/attendanceZone/export',
    method: 'get',
    params: query
  })
}