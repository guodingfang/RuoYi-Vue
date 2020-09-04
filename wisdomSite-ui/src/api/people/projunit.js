import request from '@/utils/request'

// 查看项目参建单位库
export function getUtable(query) {
  return request({
    url: '/ws/participatingUnitLibrary/list',
    method: 'get',
    params: query
  })
}

// 查看详情
export function detailProjunit(id) {
  return request({
    url: '/ws/participatingUnitLibrary/' + id,
    method: 'get',
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