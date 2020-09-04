import request from '@/utils/request'

//列表页
export function getItable(query) {
  return request({
    url: '/ws/inspection/record/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addInspec(data) {
  return request({
    url: '/ws/inspection/record/add',
    method: 'post',
    data: data
  })
}

// 详情
export function detailInspec(id) {
  return request({
    url: '/ws/inspection/record/select/' + id,
    method: 'get',
  })
}

// 详情循环字段
export function detailInspecTest(id) {
  return request({
    url: '/ws/inspection/record/query/' + id,
    method: 'get',
  })
}

// 导出
export function exportInspec(query) {
  return request({
    url: '/ws/inspection/record/export',
    method: 'get',
    params: query
  })
}

// 作业类型 list
export function getOtable(query) {
  return request({
    url: '/ws/operation/type/list',
    method: 'get',
    params: query
  })
}

//新增 作业类型
export function addJob(data) {
  return request({
    url: '/ws/operation/type/add',
    method: 'post',
    data: data
  })
}

// 修改 作业类型
export function modifyJob(data) {
  return request({
    url: '/ws/operation/type/update',
    method: 'put',
    data: data
  })
}

// 详情
export function detailJob(id) {
  return request({
    url: '/ws/operation/type/select/' + id,
    method: 'get',
  })
}
// // 启用禁用
// export function stateInspec(data) {
//   return request({
//     url: '/ws/electricityMonitorManage/enableDisable',
//     method: 'put',
//     data: data
//   })
// }

// 删除项目参建单位
export function deleteJob(id) {
  return request({
    url: '/ws/operation/type/delete/' + id,
    method: 'delete'
  })
}


//隐患整改
export function addRectify(data) {
  return request({
    url: '/ws/hazard/rectify/add',
    method: 'post',
    data: data
  })
}


//隐患验证
export function addReform(data) {
  return request({
    url: '/ws/hazard/verify/add',
    method: 'post',
    data: data
  })
}
