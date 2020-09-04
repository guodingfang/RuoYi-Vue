
import {
  getWorkType,
  getNationType,
  getCulture,
  getBankType,
  getLevel,
  getPolitics,
  getUnit,
  getTeam,
  getProject,
  getMachineType,
 } from "@/api/common";


export default {
  getWorkType: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getWorkType(),
    },
  },
  getNationType: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getNationType(),
    },
  },
  getCulture: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getCulture(),
    },
  },
  getBankType: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getBankType(),
    },
  },
  getLevel: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getLevel(),
    },
  },
  getPolitics: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getPolitics(),
    },
  },
  getTeam: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getTeam(),
    },
  },
  getUnit: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => {
        const re = await getUnit()
        return Promise.resolve({ data: re.rows })
      },
    },
  },
  getProject: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => {
        const re = await getProject()
        return Promise.resolve({ data: re.rows })
      },
    },
  },
  getMachineType: {
    key: 'label',
    value: 'value',
    refresh: {
      fetch: async() => getMachineType()
    },
  },
};
