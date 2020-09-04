import request from '@/utils/request'

// 查询班组 列表
export function listParticipatingTeam(query) {
  return request({
    url: '/ws/participatingTeam/list',
    method: 'get',
    params: query
  })
}

// 查询班组 详细
export function getParticipatingTeam(id) {
  return request({
    url: '/ws/participatingTeam/' + id,
    method: 'get'
  })
}

// 新增班组 
export function addParticipatingTeam(data) {
  return request({
    url: '/ws/participatingTeam',
    method: 'post',
    data: data
  })
}

// 修改班组 
export function updateParticipatingTeam(data) {
  return request({
    url: '/ws/participatingTeam',
    method: 'put',
    data: data
  })
}

// 删除班组 
export function delParticipatingTeam(id) {
  return request({
    url: '/ws/participatingTeam/' + id,
    method: 'delete'
  })
}

// 导出班组 
export function exportParticipatingTeam(query) {
  return request({
    url: '/ws/participatingTeam/export',
    method: 'get',
    params: query
  })
}