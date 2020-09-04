import request from '@/utils/request'

// 查询班组人员库列表
export function listTeamPersonLibrary(query) {
  return request({
    url: '/ws/teamPersonLibrary/list',
    method: 'get',
    params: query
  })
}

// 查询班组人员库详细
export function getTeamPersonLibrary(id) {
  return request({
    url: '/ws/teamPersonLibrary/' + id,
    method: 'get'
  })
}

// 新增班组人员库
export function addTeamPersonLibrary(data) {
  return request({
    url: '/ws/teamPersonLibrary',
    method: 'post',
    data: data
  })
}

// 修改班组人员库
export function updateTeamPersonLibrary(data) {
  return request({
    url: '/ws/teamPersonLibrary',
    method: 'put',
    data: data
  })
}

// 删除班组人员库
export function delTeamPersonLibrary(id) {
  return request({
    url: '/ws/teamPersonLibrary/' + id,
    method: 'delete'
  })
}

// 导出班组人员库
export function exportTeamPersonLibrary(query) {
  return request({
    url: '/ws/teamPersonLibrary/export',
    method: 'get',
    params: query
  })
}