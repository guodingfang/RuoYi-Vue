import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token';
const CurRoleKey = 'Current-Role';

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setCurRole(curRole) {
  return Cookies.set(CurRoleKey, curRole)
}

export function getCurRole() {
  return !Cookies.get(CurRoleKey) ? '' : JSON.parse(Cookies.get(CurRoleKey))
}

export function removeCurRole() {
  return Cookies.remove(CurRoleKey)
}

