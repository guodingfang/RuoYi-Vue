import request from '@/utils/request'

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}
// 获取项目子节点
export function getProjChild(projectId) {
  return request({
    url: '/ws/manage/tree/list',
    method: 'get',
    params: { projectId }
  })
}
// 根据分项工程查询实测实量
export function getProjMeasure(id) {
  return request({
    url: '/ws/measured/select/' + id,
    method: 'get',
  })
}

// 新增实测部位
export function addLocation(data) {
  return request({
    url: '/ws/location/add',
    method: 'post',
    data,
  })
}

// 新增实测实量
export function addMeasure(data) {
  return request({
    url: '/ws/content/add',
    method: 'post',
    data,
  })
}

// 编辑实测实量
export function editMeasure(data) {
  return request({
    url: '/ws/content/update',
    method: 'put',
    data,
  })
}

// 查询实测实量
export function getMeasureDetail(id) {
  return request({
    url: '/ws/content/select/' + id,
    method: 'get',
  })
}

//编辑实测部位
export function editLocation(data) {
  return request({
    url: '/ws/location/update',
    method: 'put',
    data,
  })
}

//删除实测部位
export function delLocation(id) {
  return request({
    url: '/ws/location/delete/' + id,
    method: 'put',
  })
}

// 查询区域详情
export function getAreaDetail(id) {
  return request({
    url: '/ws/manage/select/' + id,
    method: 'get',
  })
}

// 查询所有实测部位
export function getMeasureList(data) {
  return request({
    url: '/ws/location/list',
    method: 'get',
    params: data,
  })
}

// 查询所有实测实量设置分项工程
export function getSubProjList(subitemProject) {
  return request({
    url: '/ws/subdivisional/list',
    method: 'get',
    params: {
      subitemProject
    },
  })
}


// 新增分项工程
export function addSubProj(data) {
  return request({
    url: '/ws/measurement/add',
    method: 'post',
    data,
  })
}

// 编辑分项工程
export function editSubProj(data) {
  return request({
    url: '/ws/measurement/set',
    method: 'put',
    data,
  })
}

// 删除分项工程
export function delSubProj(id) {
  return request({
    url: `/ws/subdivisional/delete/${id}`,
    method: 'put',
  })
}

// 删除实测实量
export function delMeasure(id) {
  return request({
    url: `/ws/measured/delete/${id}`,
    method: 'delete',
  })
}

// 根据id查分项工程详情
export function getSubProjDetail(id) {
  return request({
    url: `/ws/measurement/select/${id}`,
    method: 'get',
  })
}



















