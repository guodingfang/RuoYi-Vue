import request from '@/utils/request'

// 查询考勤机 列表
export function listAttendanceMachine(query) {
  return request({
    url: '/ws/attendanceMachine/list',
    method: 'get',
    params: query
  })
}

// 查询考勤机 详细
export function getAttendanceMachine(id) {
  return request({
    url: '/ws/attendanceMachine/' + id,
    method: 'get'
  })
}

// 新增考勤机 
export function addAttendanceMachine(data) {
  return request({
    url: '/ws/attendanceMachine',
    method: 'post',
    data: data
  })
}

// 修改考勤机 
export function updateAttendanceMachine(data) {
  return request({
    url: '/ws/attendanceMachine',
    method: 'put',
    data: data
  })
}

// 删除考勤机 
export function delAttendanceMachine(id) {
  return request({
    url: '/ws/attendanceMachine/' + id,
    method: 'delete'
  })
}

// 导出考勤机 
export function exportAttendanceMachine(query) {
  return request({
    url: '/ws/attendanceMachine/export',
    method: 'get',
    params: query
  })
}