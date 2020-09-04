import request from '@/utils/request'

//薪资列表页
export function getStable(query) {
  return request({
    url: '/ws/personSalaryDetail/list',
    method: 'get',
    params: query
  })
}

//薪资列表页
export function getSdata(query) {
  return request({
    url: '/ws/personSalaryDetail/satistics',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/ws/personSalaryDetail/importTemplate',
    method: 'get'
  })
}

// 批量文件上传
export function uploadAvatar(id,data) {
  return request({
    url: '/ws/personSalaryDetail/importData/' + id,
    method: 'post',
    data: data
  })
}

//导出
export function exportJob(query) {
  return request({
    url: '/ws/personSalaryDetail/export',
    method: 'get',
    params: query
  })
}
