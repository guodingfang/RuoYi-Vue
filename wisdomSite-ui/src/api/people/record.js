import request from '@/utils/request'

//行为记录列表页
export function getRtable(query) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/list',
    method: 'get',
    params: query
  })
}

//行为评价列表页
export function getEtable(query) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/evaluate',
    method: 'get',
    params: query
  })
}

//兑换记录列表页
export function getCtable(query) {
  return request({
    url: '/ws/teamPersonExchangeRecord/list',
    method: 'get',
    params: query
  })
}

//新增
export function getRecord(data) {
  return request({
    url: '/ws/teamPersonBehaviorRecord',
    method: 'post',
    data: data
  })
}

//人员列表
export function teamPerson(query) {
  return request({
    url: '/ws/teamPerson/list',
    method: 'get',
    params: query
  })
}

//事件列表
export function eventName(query) {
  return request({
    url: '/ws/behaviorEventManagement/list',
    method: 'get',
    params: query
  })
}

//人员信息统计（详情left top）
export function behaviorRecord(personId) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/points/' + personId,
    method: 'get',
  })
}

//人员行为记录列表（详情left bottom）
export function getLtable(query) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/list',
    method: 'get',
    params: query
  })
}

//兑换记录（详情right top）
export function exchangeRecord(personId) {
  return request({
    url: '/ws/teamPersonExchangeRecord/points/' + personId,
    method: 'get',
  })
}

//人员行为记录列表（详情right bottom）
export function getBtable(query) {
  return request({
    url: '/ws/teamPersonExchangeRecord/list',
    method: 'get',
    params: query
  })
}


