import request from '@/utils/request'

//列表页
export function getLtable(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

//列表页
export function getRtable(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

//权限树
export function treeSelect(query) {
  return request({
    url: '/system/menu/treeselect',
    method: 'get',
    params: query
  })
}

//列表页
export function getRoleList(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

//回显组织
export function detailOrganize(id) {
  return request({
    url: '/system/user/getOrgUserInfo/' + id,
    method: 'get',
    // params: query
  })
}


// 修改
export function modifyRole(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data: data
  })
}

// 删除项目参建单位
export function deleteFn(data) {
  return request({
    url: '/system/user/removeOrgUser',
    method: 'post',
    data:data
  })
}

// 详情
export function detailUser(id) {
  return request({
    url: '/system/user/' + id,
    method: 'get',
    // params: query
  })
}


// 详情
export function detailRole(id) {
  return request({
    url: '/system/role/' + id,
    method: 'get',
    // params: query
  })
}
// 详情
export function detailTree(id) {
  return request({
    url: 'system/menu/roleMenuTreeselect/' + id,
    method: 'get',
    // params: query
  })
}

// 新增
export function addRoleUser(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  })
}
// 修改
export function modifyRoleUser(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

// //监测设备
// export function listMonit(query) {
//   return request({
//     url: '/ws/electricityMonitorManage/listMonitoringEquipment',
//     method: 'get',
//     params: query
//   })
// }

// //监测设备
// export function listSite(query) {
//   return request({
//     url: '/ws/electricityMonitorManage/listProjectSitePartition',
//     method: 'get',
//     params: query
//   })
// }