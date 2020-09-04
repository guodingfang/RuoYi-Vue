import type from "./state";

export default {
  type: { ...type },
  pre: [
    "getWorkType",
    "getNationType",
    "getCulture",
    "getBankType",
    "getLevel",
    "getPolitics",
    "getUnit",
    "getTeam",
    "getProject",
    "getMachineType",
  ]
};
