import request from '@/utils/request'

// 查询参建单位类型 列表
export function listParticipatingUnitType(query) {
  return request({
    url: '/ws/participatingUnitType/list',
    method: 'get',
    params: query
  })
}

// 查询参建单位类型 详细
export function getParticipatingUnitType(id) {
  return request({
    url: '/ws/participatingUnitType/' + id,
    method: 'get'
  })
}

// 新增参建单位类型 
export function addParticipatingUnitType(data) {
  return request({
    url: '/ws/participatingUnitType',
    method: 'post',
    data: data
  })
}

// 修改参建单位类型 
export function updateParticipatingUnitType(data) {
  return request({
    url: '/ws/participatingUnitType',
    method: 'put',
    data: data
  })
}

// 删除参建单位类型 
export function delParticipatingUnitType(id) {
  return request({
    url: '/ws/participatingUnitType/' + id,
    method: 'delete'
  })
}

// 导出参建单位类型 
export function exportParticipatingUnitType(query) {
  return request({
    url: '/ws/participatingUnitType/export',
    method: 'get',
    params: query
  })
}