import request from '@/utils/request'

// 查询项目 列表
export function getProjList(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目详情
export function getProjectById(id) {
  return request({
    url: '/ws/project/'+id,
    method: 'get'
  })
}

// 增加项目
export function addProject(data) {
  return request({
    url: '/ws/project/add',
    method: 'post',
    data: data
  })
}

// 修改项目
export function updateProject(data) {
  return request({
    url: '/ws/project/update',
    method: 'put',
    data: data
  })
}

// 逻辑删除项目
export function deleteProject(id) {
  return request({
    url: '/ws/project/delete/'+id,
    method: 'delete'
  })
}

// 导出项目信息
export function exportProject(query) {
  return request({
    url: '/ws/project/export',
    method: 'get',
    params: query
  })
}

// 查询项目里程碑计划信息
export function findProjectMilestonePlan(id) {
  return request({
    url: '/ws/milestoneplan/select/'+id,
    method: 'get'
  })
}

// 修改项目里程碑计划
export function updateProjectMilestonePlan(data) {
  return request({
    url: '/ws/milestoneplan/update',
    method: 'post',
    data: data
  })
}

//查询所有的项目
export function getAllProject() {
  return request({
    url: '/ws/project/all/list',
    method: 'get',
  })
}
