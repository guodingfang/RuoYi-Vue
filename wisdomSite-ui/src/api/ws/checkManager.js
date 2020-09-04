import request from '@/utils/request'

// 查询项目 列表
export function listCheckManager(query) {
  return request({
    url: '/ws/checkManager/list',
    method: 'get',
    params: query
  })
}

// 查询人员信息
export function getCheckUser() {
  return request({
    url: '/system/user/list',
    method: 'get'
  })
}

// 查询质量问题
export function getCheckType(data) {
  return request({
    url: '/ws/problem/list',
    method: 'get',
    params: data
  })
}

// 增加质量检查
export function addCheckManager(data) {
  return request({
    url: '/ws/checkManager/add',
    method: 'post',
    data: data
  })
}

// 导出质量检查
export function exportCheckManager(query) {
  return request({
    url: '/ws/checkManager/export',
    method: 'get',
    params: query
  })
}


// 查询质量问题 列表
export function problemTypeList(query) {
  return request({
    url: '/ws/problem/list',
    method: 'get',
    params: query
  })
}

// 增加质量问题
export function addProblemType(data) {
  return request({
    url: '/ws/problem/add',
    method: 'post',
    data: data
  })
}

// 修改质量问题
export function updateProblemType(data) {
  return request({
    url: '/ws/problem/update',
    method: 'put',
    data: data
  })
}

// 质量问题 启禁用
export function handleClickToForbiddenProblemType(data) {
  return request({
    url: '/ws/problem/enableDisable',
    method: 'put',
    data: data
  })
}

// 删除质量问题
export function deleteProblemType(id) {
  return request({
    url: '/ws/problem/'+id,
    method: 'delete'
  })
}

//查看质量详情
export function checkManagerDetail(id) {
  return request({
    url: '/ws/checkManager/'+id,
    method: 'get'
  })
}
//查看质量详情整改验证
export function checkManagerDetailList(checkId) {
  return request({
    url: '/ws/repair/'+checkId,
    method: 'get'
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





