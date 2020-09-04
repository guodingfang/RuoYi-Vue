import request from '@/utils/request'

// 查询人员考勤平台注册列表
export function listAttendanceRegister(query) {
  return request({
    url: '/ws/attendanceRegister/list',
    method: 'get',
    params: query
  })
}

// 查询人员考勤平台注册详细
export function getAttendanceRegister(id) {
  return request({
    url: '/ws/attendanceRegister/' + id,
    method: 'get'
  })
}

// 新增人员考勤平台注册
export function addAttendanceRegister(data) {
  return request({
    url: '/ws/attendanceRegister',
    method: 'post',
    data: data
  })
}

// 修改人员考勤平台注册
export function updateAttendanceRegister(data) {
  return request({
    url: '/ws/attendanceRegister',
    method: 'put',
    data: data
  })
}

// 删除人员考勤平台注册
export function delAttendanceRegister(id) {
  return request({
    url: '/ws/attendanceRegister/' + id,
    method: 'delete'
  })
}

// 导出人员考勤平台注册
export function exportAttendanceRegister(query) {
  return request({
    url: '/ws/attendanceRegister/export',
    method: 'get',
    params: query
  })
}