import request from '@/utils/request'

// 亮点管理 列表
export function listBrightspotManager(query) {
  return request({
    url: '/ws/brightspotManage/list',
    method: 'get',
    params: query
  })
}

// 增加亮点信息
export function addDrightspot(data) {
  return request({
    url: '/ws/brightspotManage/add',
    method: 'post',
    data: data
  })
}

// 查询亮点信息
export function detailsBrightspotManager(id) {
  return request({
    url: '/ws/brightspotManage/'+id,
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





