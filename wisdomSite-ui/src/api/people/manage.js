import request from '@/utils/request'

//行为记录列表页
export function getMtable(query) {
  return request({
    url: '/ws/exchangeItemManagement/list',
    method: 'get',
    params: query
  })
}

//启用禁用
export function stateManage(data) {
  return request({
    url: `/ws/exchangeItemManagement`,
    // url: `/ws/exchangeItemManagement/${enabled}/${id}`,
    method: 'put',
    data: data
  })
}

// 添加
export function addManage(data) {
  return request({
    url: '/ws/exchangeItemManagement',
    method: 'post',
    data: data
  })
}

// 编辑
export function modifyManage(data) {
  return request({
    url: '/ws/exchangeItemManagement',
    method: 'put',
    data: data
  })
}

// 详情
export function detailManage(id) {
  return request({
    url: '/ws/exchangeItemManagement/' + id,
    method: 'get',
  })
}

// 删除项目参建单位
export function deleteManage(id) {
  return request({
    url: '/ws/exchangeItemManagement/' + id,
    method: 'delete'
  })
}