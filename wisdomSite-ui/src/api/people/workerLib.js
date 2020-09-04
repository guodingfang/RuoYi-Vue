import request from '@/utils/request'

// 查询建筑工人库列表
export function getPtable(query) {
  return request({
    url: '/ws/teamPersonLibrary/list',
    method: 'get',
    params: query
  })
}

// 详情
export function detailWork(id) {
  return request({
    url: '/ws/teamPersonLibrary/' + id,
    method: 'get',
  })
}

// 职业履历列表 deleted=1&id=
export function getRtable(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

// 职业履历列表
export function getNtable(idNumber) {
  return request({
    url: `/ws/teamPersonLibrary/${idNumber}/ceitificates`,
    method: 'get',
  })
}

// 黑名单列表
export function getBtable(query) {
  return request({
    url: '/ws/blacklist/list',
    method: 'get',
    params: query
  })
}

// 黑名单列表
export function getJtable(query) {
  return request({
    url: '/ws/projectParticipatingUnit/list',
    method: 'get',
    params: query
  })
}

// // 查询班组下人员 列表
// export function getPerson(query) {
//   return request({
//     url: '/ws/teamPerson/list',
//     method: 'get',
//     params: query
//   })
// }
// // 查询班组下人员 列表
// export function detailAtte(id) {
//   return request({
//     url: '/ws/attendanceRecord/list/' + id,
//     method: 'get',
//   })
// }

//