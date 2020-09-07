import { constantRoutes } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'

const permission = {
  state: {
    originalRoutes: [],
    routes: [],
    addRoutes: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes;
      state.originalRoutes = routes;
      state.routes = constantRoutes.concat(routes);
    },
    SET_NEW_ROUTES: (state, routes) => {
      state.routes = constantRoutes.concat(routes);
      state.addRoutes = routes;
    }
  },
  actions: {
    // 生成路由
    GenerateRoutes({ dispatch, commit }) {
      return new Promise(resolve => {
        // 向后端请求路由数据
        getRouters().then(res => {
          const accessedRoutes = filterAsyncRouter(res.data)
          accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
          commit('SET_ROUTES', accessedRoutes)
          dispatch('SetRoutes')
          resolve(accessedRoutes)
        })
      })
    },
    // 设置路由
    SetRoutes({ commit, state, rootState }) {
      const { user: { curRole } } = rootState;
      const { originalRoutes } = state;
      if(originalRoutes.length === 0) {
        commit('SET_NEW_ROUTES', originalRoutes);
        return;
      }

      const routes = _setRoutes(originalRoutes, curRole)
      commit('SET_NEW_ROUTES', routes);
    }
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
  return asyncRouterMap.filter(route => {
    if (route.component) {
      // Layout组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
}

// 筛选路由
const _setRoutes = (routes, role) => {
  const { type = 0 } = role;
  let _routes = [];
  routes.map(route => {
    if(route.name === 'Wismonitor') {
      let _children = route.children;
      const enterprise = ['TowerMonitor', 'ElevatorEnterprise', 'DustEnterprise'];      // 企业
      const project = ['ProjTowerMonitor', 'ElevatorProject', 'DustProject'];           // 项目
      if(type === 0) {
        project.map(item => {
          _children = _children.filter(v => v.name !== item)
        })
      }
      if(type === 1) {
        enterprise.map(item => {
          _children = _children.filter(v => v.name !== item)
        })
      }
      _routes.push({...route, children: _children});
    } else {
      _routes.push(route);
    }
  })
  return _routes;
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) =>  require([`@/views/${view}`], resolve)
}

export default permission
