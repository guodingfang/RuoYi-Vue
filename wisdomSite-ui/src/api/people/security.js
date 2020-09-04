import request from '@/utils/request'

//安全之星（正）列表页
export function getJtable(query) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/list/persons',
    method: 'get',
    params: query
  })
}

//兑换物品信息
export function exchange(personId) {
  return request({
    url: '/ws/teamPersonBehaviorRecord/exchangeable/' + personId,
    method: 'get',
  })
}

//物品库存列表
export function getEtable(query) {
  return request({
    url: '/ws/exchangeItemManagement/list',
    method: 'get',
    params: query
  })
}

// 保存（正）
export function keepJust(data) {
  return request({
    url: '/ws/teamPersonExchangeRecord/batch',
    method: 'post',
    data: data
  })
}

