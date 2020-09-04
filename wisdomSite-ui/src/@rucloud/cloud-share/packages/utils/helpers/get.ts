import { isUndef } from "./typeAssert";
import { pathResolve, path, pathArray } from "./share";

/**
 * @param source origin object {}, []
 * @param path [0, 'a', 'b'], 'a.b.c', ['a', 'a.b.c']
 * @param alterValue if path -> value is undefined or null this param will be the final value
 * @param judgeFn self judge like I just want path -> value === 'some value' -> return `alterValue`
 */

export function get(
  source: any,
  path: path | pathArray,
  alterValue?: any,
  judgeFn?: (value: any) => boolean // get策略
) {
  if (isUndef(source)) {
    // console.warn(`source data get ${path} is ${source}`);
    return alterValue; // source 无值返回 alterValue
  }
  let pathOut: string[] = pathResolve(path);
  let result: any;
  for (let i = 0; i < pathOut.length; i++) {
    const curPath = pathOut[i];
    let out = i === 0 ? source[curPath] : result[curPath];
    if (isUndef(out) || (judgeFn && judgeFn(out))) {
      result = alterValue;
      break;
    } else {
      result = out;
    }
  }
  return result;
}
