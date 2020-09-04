import request from '@/utils/request'

// 考勤记录
export function getAtable(query) {
  return request({
    url: '/ws/attendanceMachine/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addInstall(data) {
  return request({
    url: '/ws/attendanceMachine',
    method: 'post',
    data: data
  })
}

// 修改
export function modifyInstall(data) {
  return request({
    url: '/ws/attendanceMachine',
    method: 'put',
    data: data
  })
}

// 详情
export function detailInstall(id) {
  return request({
    url: '/ws/attendanceMachine/' + id,
    method: 'get',
  })
}

// 删除
export function deleteInstall(id) {
  return request({
    url: '/ws/attendanceMachine/' + id,
    method: 'delete',
  })
}