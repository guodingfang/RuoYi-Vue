export function getType(source: any): string {
  return Object.prototype.toString.call(source).slice("[object ".length, -1);
}

export function isArray(x: any): x is Array<any> {
  return getType(x) === "Array";
}

export function isString(x: any): x is string {
  return getType(x) === "String";
}

export function isNumber(x: any): x is number {
  return getType(x) === "Number";
}

export function isFunction(x: any): x is Function {
  return getType(x) === "Function";
}

export function isUndef(x: any): x is undefined | null {
  return x === undefined || x === null;
}

export function notEmpty(x: any): Boolean {
  return [null, undefined, ""].indexOf(x) === -1;
}

export function isObject(x: any): x is Object {
  return getType(x) === "Object";
}

export function isEmptyObject(x: Object): Boolean {
  let flag = true;
  for (let att in x) {
    /* istanbul ignore else */
    if (x.hasOwnProperty(att)) {
      flag = false;
    }
  }
  return flag;
}

export function isNumberPlus(x: any): x is number {
  return /^\d+$/gi.test(x);
}
