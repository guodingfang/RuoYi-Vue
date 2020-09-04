import request from '@/utils/request'

// 查询项目分区列表
export function getAreaList(projectId) {
  return request({
    url: '/ws/partition/list',
    method: 'get',
    params: { projectId }
  })
}

// 增加分区
export function addArea(data) {
  return request({
    url: '/ws/partition/add',
    method: 'post',
    data,
  })
}

// 删除分区
export function delArea(id) {
  return request({
    url: '/ws/partition/delete/' + id,
    method: 'delete'
  })
}

// 编辑分区
export function updateArea(data) {
  return request({
    url: '/ws/partition/update',
    method: 'put',
    data,
  })
}
