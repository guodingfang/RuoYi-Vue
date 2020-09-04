import request from '@/utils/request'

//黑名单列表页
export function getBtable(query) {
  return request({
    url: '/ws/blacklist/list',
    method: 'get',
    params: query
  })
}

//删除黑名单人员
export function deleteBlackStaff(id) {
  return request({
    url: '/ws/blacklist/' + id,
    method: 'delete',
  })
}

// 添加
export function addBlack(data) {
  return request({
    url: '/ws/teamPerson/batchChange',
    method: 'put',
    data: data
  })
}

// 编辑
export function modifyBlack(data) {
  return request({
    url: '/ws/blacklist',
    method: 'put',
    data: data
  })
}

// 详情
export function detailBlack(id) {
  return request({
    url: '/ws/blacklist/' + id,
    method: 'get',
  })
}

// 人员库表格
export function getTPtable(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}


//导出
export function exportJob(query) {
  return request({
    url: '/ws/blacklist/export',
    method: 'get',
    params: query
  })
}

// 通用上传
export function uploadAll(data) {
  return request({
    url: '/ws/fileInfo/upload',
    method: 'post',
    data: data
  })
}
// // 新增项目参建单位
// export function addProjectUnit(data) {
//   return request({
//     url: '/ws/projectParticipatingUnit',
//     method: 'post',
//     data: data
//   })
// }