import request from '@/utils/request'

// 查询项目 列表
export function listCheckManager(query) {
  return request({
    url: '/ws/checkManager/list',
    method: 'get',
    params: query
  })
}

// 整改
export function addRepair(data) {
  return request({
    url: '/ws/repair/add',
    method: 'post',
    data: data
  })
}

// 待整改列表
export function getRepairList(query) {
  return request({
    url: '/ws/repair/list',
    method: 'get',
    params: query
  })
}

// 待验证列表
export function getVerifyList(query) {
  return request({
    url: '/ws/verify/list',
    method: 'get',
    params: query
  })
}

// 验证
export function doVerify(data) {
  return request({
    url: '/ws/verify/add',
    method: 'post',
    data: data
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

// 查询验收待验证，不合格 列表
export function listAcceptanceApply(query) {
  return request({
    url: '/ws/apply/list',
    method: 'get',
    params: query
  })
}

// 验收不合格
export function listNoAcceptance(query) {
  return request({
    url: '/ws/record/list',
    method: 'get',
    params: query
  })
}

// 验收
export function addRecord(data) {
  return request({
    url: '/ws/record/add',
    method: 'post',
    data: data
  })
}

// 查询人员信息
export function getCheckUser() {
  return request({
    url: '/system/user/list',
    method: 'get'
  })
}

// 再次申请验收
export function addAcceptIncrease(data) {
  return request({
    url: '/ws/apply/update',
    method: 'put',
    data: data
  })
}

//查看验收详情
export function acceptanceApplyDetail(id) {
  return request({
    url: '/ws/apply/'+id,
    method: 'get'
  })
}
//查看验收详情
export function acceptanceRecordDetail(applyId) {
  return request({
    url: '/ws/record/'+applyId,
    method: 'get'
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






