import request from '@/utils/request'

// 查询项目里程碑模板
export function listProjectMilepost(query) {
  return request({
    url: '/ws/milepost/list',
    method: 'get',
    params: query
  })
}

// 查询项目里程碑计划
export function getProjectMileStone(projectId) {
  return request({
    url: '/ws/milestoneplan/select/'+projectId,
    method: 'get',
  })
}



// 查询里程碑模板详情
export function getProjectMilepostById(id) {
  return request({
    url: '/ws/milestoneplan/'+id,
    method: 'get'
  })
}

// 移动项目里程碑模板
export function removeProjectMilepost(data) {
  return request({
    url: '/ws/milestoneplan/remove',
    method: 'post',
    data: data
  })
}


// 增加项目里程碑模板
export function addProjectMilepost(data) {
  return request({
    url: '/ws/milestoneplan/add',
    method: 'post',
    data: data
  })
}

// 修改项目里程碑模板
export function updateProjectMilepost(data) {
  return request({
    url: '/ws/milestoneplan/update',
    method: 'post',
    data: data
  })
}

// 逻辑删除项目里程碑模板
export function deleteProjectMilepost(ids) {
  return request({
    url: '/ws/milestoneplan/delete/'+ids,
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
