import request from '@/utils/request'

//行为记录列表页
export function getEtable(query) {
  return request({
    url: '/ws/behaviorEventManagement/list',
    method: 'get',
    params: query
  })
}

// 添加
export function addInstall(data) {
  return request({
    url: '/ws/behaviorEventManagement',
    method: 'post',
    data: data
  })
}

// 编辑
export function modifyInstall(data) {
  return request({
    url: '/ws/behaviorEventManagement',
    method: 'put',
    data: data
  })
}

// 详情
export function detailInstall(id) {
  return request({
    url: '/ws/behaviorEventManagement/' + id,
    method: 'get',
  })
}

// 删除
export function deleteInstall(id) {
  return request({
    url: '/ws/behaviorEventManagement/' + id,
    method: 'delete',
  })
}
