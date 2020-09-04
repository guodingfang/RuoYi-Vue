/**
 * @AtLeast at least several params
 */
type AtLeast<T, K extends keyof T> = Partial<T> & Pick<T, K>;

/**
 * inspired by
 * https://medium.com/@flut1/deep-flatten-typescript-types-with-finite-recursion-cb79233d93ca
 * flatten value or key
 * max level is 9
 * @DeepFlattenValue
 * @DeepFlattenKey
 * -- other types --
 * @ValuesOf
 * @NonObjectKeysOf
 * @ObjectValuesOf
 * @UnionToIntersection
 * @KeysOf
 */
type NonObjectKeysOf<T> = {
  [K in keyof T]: T[K] extends Array<any> ? K : T[K] extends object ? never : K;
}[keyof T];

type ValuesOf<T> = T[keyof T];

type ObjectValuesOf<T extends Object> = Exclude<
  Exclude<Extract<ValuesOf<T>, object>, never>,
  Array<any>
>;

type UnionToIntersection<U> = (U extends any
? (k: U) => void
: never) extends (k: infer I) => void
  ? I
  : never;

type DFBase<T, Recursor> = ValuesOf<T> | Recursor;

// max level 9
type DeepFlattenValue<T> = T extends any
  ? DFBase<T, DF2<ObjectValuesOf<T>>>
  : never;
type DF2<T> = T extends any ? DFBase<T, DF3<ObjectValuesOf<T>>> : never;
type DF3<T> = T extends any ? DFBase<T, DF4<ObjectValuesOf<T>>> : never;
type DF4<T> = T extends any ? DFBase<T, DF5<ObjectValuesOf<T>>> : never;
type DF5<T> = T extends any ? DFBase<T, DF6<ObjectValuesOf<T>>> : never;
type DF6<T> = T extends any ? DFBase<T, DF7<ObjectValuesOf<T>>> : never;
type DF7<T> = T extends any ? DFBase<T, DF8<ObjectValuesOf<T>>> : never;
type DF8<T> = T extends any ? DFBase<T, DF9<ObjectValuesOf<T>>> : never;
type DF9<T> = T extends any ? ValuesOf<T> : never;

type KeysOf<T> = keyof T;

type DFBaseKey<T, Recursor> = KeysOf<T> | Recursor;
// max level 9
type DeepFlattenKey<T> = T extends any
  ? DFBaseKey<T, DFKey2<ObjectValuesOf<T>>>
  : never;
type DFKey2<T> = T extends any
  ? DFBaseKey<T, DFKey3<ObjectValuesOf<T>>>
  : never;
type DFKey3<T> = T extends any
  ? DFBaseKey<T, DFKey4<ObjectValuesOf<T>>>
  : never;
type DFKey4<T> = T extends any
  ? DFBaseKey<T, DFKey5<ObjectValuesOf<T>>>
  : never;
type DFKey5<T> = T extends any
  ? DFBaseKey<T, DFKey6<ObjectValuesOf<T>>>
  : never;
type DFKey6<T> = T extends any
  ? DFBaseKey<T, DFKey7<ObjectValuesOf<T>>>
  : never;
type DFKey7<T> = T extends any
  ? DFBaseKey<T, DFKey8<ObjectValuesOf<T>>>
  : never;
type DFKey8<T> = T extends any
  ? DFBaseKey<T, DFKey9<ObjectValuesOf<T>>>
  : never;
type DFKey9<T> = T extends any ? KeysOf<T> : never;
