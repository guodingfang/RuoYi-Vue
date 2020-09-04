import request from '@/utils/request'

// 查询班组人员 列表
export function listTeamPerson(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

// 查询班组人员 详细
export function getTeamPerson(id) {
  return request({
    url: '/ws/teamPerson/' + id,
    method: 'get'
  })
}

// 新增班组人员 
export function addTeamPerson(data) {
  return request({
    url: '/ws/teamPerson',
    method: 'post',
    data: data
  })
}

// 修改班组人员 
export function updateTeamPerson(data) {
  return request({
    url: '/ws/teamPerson',
    method: 'put',
    data: data
  })
}

// 删除班组人员 
export function delTeamPerson(id) {
  return request({
    url: '/ws/teamPerson/' + id,
    method: 'delete'
  })
}

// 导出班组人员 
export function exportTeamPerson(query) {
  return request({
    url: '/ws/teamPerson/export',
    method: 'get',
    params: query
  })
}