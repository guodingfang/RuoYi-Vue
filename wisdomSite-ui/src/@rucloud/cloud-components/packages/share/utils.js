import { isArray } from './rucloud-share';

export function setHelper(judgement = [], callBack = []) {
  judgement.forEach((judge, index) => {
    const fn = callBack[index];
    let v, f;
    if (isArray(judge)) {
      v = judge[0];
      f = judge[1];
    } else {
      v = judge;
      f = undefined;
    }

    if (v === f) {
      fn && fn();
    }
  });
}

export { default as mixin } from './mixin';
