import request from '@/utils/request'

// 企业类型下拉
export function getEnterprise(query) {
  return request({
    url: '/ws/participatingUnitType/list',
    method: 'get',
    params: query
  })
}
// 区域3级联动
export function getArea(query) {
  return request({
    url: '/ws/area/list',
    method: 'get',
    params: query
  })
}
// 企业性质
export function getNature(query) {
  return request({
    url: '/system/dict/data/type/enterprise_nature',
    method: 'get',
    params: query
  })
}

//班组数
export function getTree(query) {
  return request({
    url: '/ws/projectParticipatingUnit/treeselect',
    method: 'get',
    params: query
  })
}

// 新增项目参建单位
export function addProjectUnit(data) {
  return request({
    url: '/ws/projectParticipatingUnit',
    method: 'post',
    data: data
  })
}

// 修改项目参建单位
export function modifyProjectUnit(data) {
  return request({
    url: '/ws/projectParticipatingUnit/bacthEdit',
    method: 'put',
    data: data
  })
}

// 删除项目参建单位
export function deleteProjectUnit(id) {
  return request({
    url: '/ws/projectParticipatingUnit/' + id,
    method: 'delete'
  })
}

// 查看项目参建单位详情
export function detailProjectUnit(id) {
  return request({
    url: '/ws/projectParticipatingUnit/'+id,
    method: 'get',
  })
}

// 查看项目参建单位库
export function getUtable(query) {
  return request({
    url: '/ws/participatingUnitLibrary/list',
    method: 'get',
    params: query
  })
}
// 批量新增项目参建单位
export function addUtable(data) {
  return request({
    url: '/ws/projectParticipatingUnit/batchAdd',
    method: 'post',
    data: data
  })
}

// 右侧人员表格数据
export function getPtable(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

// 批量文件上传
export function uploadAvatar(id,data) {
  return request({
    url: '/ws/teamPerson/importData/' + id,
    method: 'post',
    data: data
  })
}

// 查询劳务公司/建筑单位（联动2级） personType
export function getUnit(query) {
  return request({
    url: '/ws/projectParticipatingUnit/list',
    method: 'get',
    params: query
  })
}

// 新增人员
export function addStaff(data) {
  return request({
    url: '/ws/teamPerson',
    method: 'post',
    data: data
  })
}

// 修改人员
export function modifyStaff(data) {
  return request({
    url: '/ws/teamPerson',
    method: 'put',
    data: data
  })
}

// 查看人员详情
export function detailStaff(id) {
  return request({
    url: '/ws/teamPerson/' + id,
    method: 'get',
  })
}

//获取参建单位下的班组列表  unitId
export function getTeam(query) {
  return request({
    url: '/ws/participatingTeam/list',
    method: 'get',
    params: query
  })
}

//编辑后保存班组
export function addTeam(data) {
  return request({
    url: '/ws/participatingTeam/batch',
    method: 'put',
    data: data
  })
}

// 查询班组是否可以删除
export function delTeam(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

// //保存班组
// export function setAllData(data) {
//   return request({
//     url: '/ws/participatingTeam/batch',
//     method: 'put',
//     data: data
//   })
// }}

//设置人员(退场，黑名单)
export function setAllData(data) {
  return request({
    url: '/ws/teamPerson/batchChange',
    method: 'put',
    data: data
  })
}

//删除人员
export function deleteStaff(id) {
  return request({
    url: '/ws/teamPerson/' + id,
    method: 'delete',
  })
}

//建筑人员库表格，批量添加用
export function getPtableAll(query) {
  return request({
    url: '/ws/teamPersonLibrary/list',
    method: 'get',
    params: query
  })
}

// 通用上传
export function uploadAll(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}


// 查询项目 列表
export function parentList(query) {
  return request({
    url: '/ws/manage/parent/list',
    method: 'get',
    params: query
  })
}

// 新增人员（参建单位库）
export function addBatch(data) {
  return request({
    url: '/ws/teamPerson/batch',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/ws/teamPerson/importTemplate',
    method: 'get'
  })
}

//--------------------------------
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


