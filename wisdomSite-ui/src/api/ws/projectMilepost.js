import request from '@/utils/request'

// 查询项目里程碑模板
export function listProjectMilepost(query) {
  return request({
    url: '/ws/milepost/list',
    method: 'get',
    params: query
  })
}

// 查询里程碑模板详情
export function getProjectMilepostById(id) {
  return request({
    url: '/ws/milepost/'+id,
    method: 'get'
  })
}

// 移动项目里程碑模板
export function removeProjectMilepost(data) {
  return request({
    url: '/ws/milepost/remove',
    method: 'post',
    data: data
  })
}


// 增加项目里程碑模板
export function addProjectMilepost(data) {
  return request({
    url: '/ws/milepost/add',
    method: 'post',
    data: data
  })
}

// 修改项目里程碑模板
export function updateProjectMilepost(data) {
  return request({
    url: '/ws/milepost/update',
    method: 'put',
    data: data
  })
}

// 逻辑删除项目里程碑模板
export function deleteProjectMilepost(ids) {
  return request({
    url: '/ws/milepost/delete/'+ids,
    method: 'delete'
  })
}

// 导出项目里程碑模板
export function exportProjectMilepost(query) {
  return request({
    url: '/ws/milepost/export',
    method: 'get',
    params: query
  })
}
