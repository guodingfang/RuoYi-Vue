import request from '@/utils/request'

// 查询参建单位库列表
export function listParticipatingUnitLibrary(query) {
  return request({
    url: '/ws/participatingUnitLibrary/list',
    method: 'get',
    params: query
  })
}

// 查询参建单位库详细
export function getParticipatingUnitLibrary(id) {
  return request({
    url: '/ws/participatingUnitLibrary/' + id,
    method: 'get'
  })
}

// 新增参建单位库
export function addParticipatingUnitLibrary(data) {
  return request({
    url: '/ws/participatingUnitLibrary',
    method: 'post',
    data: data
  })
}

// 修改参建单位库
export function updateParticipatingUnitLibrary(data) {
  return request({
    url: '/ws/participatingUnitLibrary',
    method: 'put',
    data: data
  })
}

// 删除参建单位库
export function delParticipatingUnitLibrary(id) {
  return request({
    url: '/ws/participatingUnitLibrary/' + id,
    method: 'delete'
  })
}

// 导出参建单位库
export function exportParticipatingUnitLibrary(query) {
  return request({
    url: '/ws/participatingUnitLibrary/export',
    method: 'get',
    params: query
  })
}