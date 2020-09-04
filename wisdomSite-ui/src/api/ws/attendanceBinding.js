import request from '@/utils/request'

// 查询考勤绑定 列表
export function listAttendanceBinding(query) {
  return request({
    url: '/ws/attendanceBinding/list',
    method: 'get',
    params: query
  })
}

// 查询考勤绑定 详细
export function getAttendanceBinding(id) {
  return request({
    url: '/ws/attendanceBinding/' + id,
    method: 'get'
  })
}

// 新增考勤绑定 
export function addAttendanceBinding(data) {
  return request({
    url: '/ws/attendanceBinding',
    method: 'post',
    data: data
  })
}

// 修改考勤绑定 
export function updateAttendanceBinding(data) {
  return request({
    url: '/ws/attendanceBinding',
    method: 'put',
    data: data
  })
}

// 删除考勤绑定 
export function delAttendanceBinding(id) {
  return request({
    url: '/ws/attendanceBinding/' + id,
    method: 'delete'
  })
}

// 导出考勤绑定 
export function exportAttendanceBinding(query) {
  return request({
    url: '/ws/attendanceBinding/export',
    method: 'get',
    params: query
  })
}