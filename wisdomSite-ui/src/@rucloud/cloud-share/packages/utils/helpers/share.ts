import { isArray, isNumber, isString } from "./typeAssert";

export type path = string | number;
export type pathArray = path[];
/* istanbul ignore next function */
export function noop(): void {}

/**
 * set get pathSolve
 * @param path
 */
export function pathResolve(path: path | pathArray): string[] {
  let pathResolve: string[] = [];
  /* istanbul ignore else */
  if (isString(path) || isNumber(path)) {
    pathResolve = String(path).split(".");
  } else if (isArray(path)) {
    if (path.length === 0) return pathResolve;
    const flatPath = path.reduce((cur: path, next: path) => `${cur}.${next}`) as string;
    pathResolve = flatPath.split(".");
  }
  return pathResolve;
}
