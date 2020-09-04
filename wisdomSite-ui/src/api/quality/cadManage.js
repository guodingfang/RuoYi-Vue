import request from '@/utils/request'

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目 列表
export function parentList(id) {
  return request({
    url: '/ws/manage/parent/list/' + id,
    method: 'get',
  })
}


// 所有实测实量平面图
export function getTableFn(query) {
  return request({
    url: '/ws/manage/tree/list',
    method: 'get',
    params: query
  })
}

// 所有实测实量平面图
export function getTableList(query) {
  return request({
    url: '/ws/manage/parent/list',
    method: 'get',
    params: query
  })
}


// 单个实测实量平面图: parentId
export function getDetailFn(id) {
  return request({
    url: '/ws/manage/select/'+id,
    method: 'get',
  })
}

// 所属区域: parentId
// export function selectList(id) {
//   return request({
//     url: '/ws/manage/parent/list'
//     method: 'get',
//   })
// }

// 修改实测实量平面图
export function modify(data) {
  return request({
    url: '/ws/manage/update',
    method: 'put',
    data: data
  })
}

// 删除实测实量平面图
export function deleteFn(id) {
  return request({
    url: '/ws/manage/delete/'+id,
    method: 'put'
  })
}

// 增加实测实量平面图
export function addFn(data) {
  return request({
    url: '/ws/manage/add',
    method: 'post',
    data: data
  })
}


