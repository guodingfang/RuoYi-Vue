import request from '@/utils/request'

// 查询项目参建单位 列表
export function listProjectParticipatingUnit(query) {
  return request({
    url: '/ws/projectParticipatingUnit/list',
    method: 'get',
    params: query
  })
}

// 查询项目参建单位 详细
export function getProjectParticipatingUnit(id) {
  return request({
    url: '/ws/projectParticipatingUnit/' + id,
    method: 'get'
  })
}

// 新增项目参建单位 
export function addProjectParticipatingUnit(data) {
  return request({
    url: '/ws/projectParticipatingUnit',
    method: 'post',
    data: data
  })
}

// 修改项目参建单位 
export function updateProjectParticipatingUnit(data) {
  return request({
    url: '/ws/projectParticipatingUnit',
    method: 'put',
    data: data
  })
}

// 删除项目参建单位 
export function delProjectParticipatingUnit(id) {
  return request({
    url: '/ws/projectParticipatingUnit/' + id,
    method: 'delete'
  })
}

// 导出项目参建单位 
export function exportProjectParticipatingUnit(query) {
  return request({
    url: '/ws/projectParticipatingUnit/export',
    method: 'get',
    params: query
  })
}