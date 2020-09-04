const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  loadingFlag: state => state.app.loadingFlag,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  nickName: state => state.user.nickName,
  introduction: state => state.user.introduction,
  roles: state => state.user.roles,
  curRole: state => state.user.curRole,
  permissions: state => state.user.permissions,
  permission_routes: state => state.permission.routes,
  videoProject: state => state.videoMoni.videoProject,
}
export default getters
