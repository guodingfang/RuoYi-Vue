import request from '@/utils/request'

// 查询考勤记录 列表
export function listAttendanceRecord(query) {
  return request({
    url: '/ws/attendanceRecord/list',
    method: 'get',
    params: query
  })
}

// 查询考勤记录 详细
export function getAttendanceRecord(id) {
  return request({
    url: '/ws/attendanceRecord/' + id,
    method: 'get'
  })
}

// 新增考勤记录 
export function addAttendanceRecord(data) {
  return request({
    url: '/ws/attendanceRecord',
    method: 'post',
    data: data
  })
}

// 修改考勤记录 
export function updateAttendanceRecord(data) {
  return request({
    url: '/ws/attendanceRecord',
    method: 'put',
    data: data
  })
}

// 删除考勤记录 
export function delAttendanceRecord(id) {
  return request({
    url: '/ws/attendanceRecord/' + id,
    method: 'delete'
  })
}

// 导出考勤记录 
export function exportAttendanceRecord(query) {
  return request({
    url: '/ws/attendanceRecord/export',
    method: 'get',
    params: query
  })
}