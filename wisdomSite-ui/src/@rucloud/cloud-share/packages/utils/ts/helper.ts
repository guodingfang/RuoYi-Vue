export function getType(source: any): string {
  return Object.prototype.toString.call(source).slice("[object ".length, -1);
}

export function isArray(arr: any): boolean {
  return getType(arr) === "Array";
}

export function isString(arr: any): boolean {
  return getType(arr) === "String";
}

export function isFunction(arr: any): boolean {
  return getType(arr) === "Function";
}

export function isDef(inParam: any): Boolean {
  return inParam === undefined || inParam === null;
}

export function noop(): void {}

type uni = string | number;
type mutiArray = uni[];

export function get(
  source: any,
  path: uni | mutiArray,
  alterValue?: any,
  judgeFn?: (value: any) => boolean // get策略
) {
  if (isDef(source)) return source;
  let pathResolve: string[] = [];
  if (isString(path)) {
    pathResolve = (path as string).split(".");
  } else if (isArray(path)) {
    const flatPath = (path as mutiArray).reduce((cur: uni, next: uni) => {
      return `${cur}.${next}`;
    });
    pathResolve = (flatPath as string).split(".");
  } else {
    console.warn("get methods need pass 'string or array' as path");
  }
  let result: any;
  for (let i = 0; i < pathResolve.length; i++) {
    const curPath = pathResolve[i];
    let out = i === 0 ? source[curPath] : result[curPath];
    if (isDef(out) || (judgeFn && judgeFn(out))) {
      result = alterValue;
      break;
    } else {
      result = out;
    }
  }
  return result;
}
