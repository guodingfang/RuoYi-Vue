
import request from '@/utils/request'

// 查询项目 列表
export function listProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

// 亮点 列表
export function getTabel(query) {
  return request({
    url: '/ws/brightspotManage/list',
    method: 'get',
    params: query
  })
}

// 增加亮点信息
export function addProjectDept(data) {
  return request({
    url: '/ws/brightspotManage/add',
    method: 'post',
    data: data
  })
}

// 查询亮点信息
export function getDetail(id) {
  return request({
    url: '/ws/brightspotManage/'+id,
    method: 'get'
  })
}