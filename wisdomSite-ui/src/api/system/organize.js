import request from '@/utils/request'

// 获取组织项目树列表
export function getOrgTree(query) {
  return request({
    url: '/system/enterprise/treeselect',
    method: 'get',
    params: query
  })
}

// 新增组织
export function addOrganize(data) {
  return request({
    url: '/system/enterprise',
    method: 'post',
    data: data
  })
}

// 登陆选择组织接口
export function organizationSelect(data) {
  return request({
    url: '/system/enterprise/organizationSelect',
    method: 'post',
    data: data
  })
}

// 组织-添加成员-根据手机号查询用户信息
export function getUserInfo(phonenumber) {
  return request({
    url: '/system/user/getUserInfo',
    method: 'get',
    params: {
      phonenumber
    }
  })
}

// 组织-账号接口，组织对应人员，集团人员
export function getMemberList(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}


// 组织-选择已有成员
export function selAlreadyMember(data) {
  return request({
    url: '/system/user/selectAlreadyHave',
    method: 'post',
    data: data
  })
}

// 组织-新增成员-获取所有角色
export function getAllRole() {
  return request({
    url: '/system/role/optionselect',
    method: 'get',
  })
}

//组织-角色设置-角色列表
export function getRoleList() {
  return request({
    url: '/system/role/list',
    method: 'get',
  })
}

//组织-新增成员-保存
export function addMember(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data,
  })
}
// 组织-编辑-公司删除
export function delOrganize(enterpriseId) {
  return request({
    url: `/system/enterprise/${enterpriseId}`,
    method: 'delete',
  })
}
// 组织-移除/删除 用户与组织的关系
export function delMember(data) {
  return request({
    url: '/system/user/removeOrgUser',
    method: 'post',
    data,
  })
}
// 组织-编辑-回显数据
export function getOrgData(enterpriseId) {
  return request({
    url: `/system/enterprise/${enterpriseId}`,
    method: 'get',
  })
}
// 组织-编辑-更新
export function updateOrg(data) {
  return request({
    url: '/system/enterprise',
    method: 'put',
    data,
  })
}

// 组织-编辑-获取用户回显
export function getMemberData(userId) {
  return request({
    url: `/system/user/${userId}`,
    method: 'get',
  })
}

// 用户-编辑-更新
export function updateMember(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data,
  })
}

// 组织-添加成员-选择已有成员-校验是否已属于该组织
export function checkOrgUser(data) {
  return request({
    url: '/system/user/checkOrgUser',
    method: 'post',
    data,
  })
}
