const videoMoni = {
  state: {
    videoProject: {
      projectName: '暂无',
      totalNum: 0,
      projectId: 0,
    },
  },

  mutations: {
    SET_VIDEO_PROJECTID: (state, videoProject) => {
      state.videoProject = videoProject
    }
  },

  actions: {
    setVideoPid({ commit }, videoProject) {
      commit('SET_VIDEO_PROJECTID', videoProject)
    }
  }
}

export default videoMoni
