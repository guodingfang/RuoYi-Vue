import request from '@/utils/request'

//工种【岗位】
export function getWorkType() {
  return request({
    url: '/system/dict/data/type/ws_work_type',
    method: 'get'
  })
}

//民族
export function getNationType() {
  return request({
    url: '/system/dict/data/type/ws_nation_type',
    method: 'get'
  })
}

//文化程度
export function getCulture() {
  return request({
    url: '/system/dict/data/type/ws_culture_standard',
    method: 'get'
  })
}

//银行类型
export function getBankType() {
  return request({
    url: '/system/dict/data/type/ws_bank_type',
    method: 'get'
  })
}

//证书等级
export function getLevel() {
  return request({
    url: '/system/dict/data/type/ws_certificate_level',
    method: 'get'
  })
}
//政治面貌
export function getPolitics() {
  return request({
    url: '/system/dict/data/type/ws_politics_status',
    method: 'get'
  })
}

//班组
export function getTeam() {
  return request({
    url: '/system/dict/data/type/ws_work_type',
    method: 'get'
  })
}

//参建单位
export function getUnit() {
  return request({
    url: '/ws/projectParticipatingUnit/list',
    method: 'get'
  })
}

//参建单位
export function getProject(query) {
  return request({
    url: '/ws/project/list',
    method: 'get',
    params: query
  })
}

//考勤机类型
export function getMachineType(query) {
  return request({
    url: '/system/dict/data/type/ws_attendance_machine_type',
    method: 'get',
    params: query
  })
}
