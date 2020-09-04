import request from '@/utils/request'

// 质量验收 列表
export function listQualityAccept(query) {
  return request({
    url: '/ws/apply/list',
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

// 增加验收申请
export function addApply(data) {
  return request({
    url: '/ws/apply/add',
    method: 'post',
    data: data
  })
}

// 导出验收
export function exportApplyAccept(query) {
  return request({
    url: '/ws/apply/export',
    method: 'get',
    params: query
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

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}










