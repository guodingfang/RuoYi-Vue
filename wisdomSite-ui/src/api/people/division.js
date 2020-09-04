import request from '@/utils/request'

// 工地划分
export function getZtable(query) {
  return request({
    url: '/ws/attendanceZone/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addInstall(data) {
  return request({
    url: '/ws/attendanceZone',
    method: 'post',
    data: data
  })
}

// 修改
export function modifyInstall(data) {
  return request({
    url: '/ws/attendanceZone',
    method: 'put',
    data: data
  })
}

// 详情
export function detailInstall(id) {
  return request({
    url: '/ws/attendanceZone/' + id,
    method: 'get',
  })
}

// 删除
export function deleteInstall(id) {
  return request({
    url: '/ws/attendanceZone/' + id,
    method: 'delete',
  })
}