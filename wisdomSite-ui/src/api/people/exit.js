import request from '@/utils/request'

// 退场表格接口
export function getEtable(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

//导出
export function exportJob(query) {
  return request({
    url: '/ws/teamPerson/export',
    method: 'get',
    params: query
  })
}
