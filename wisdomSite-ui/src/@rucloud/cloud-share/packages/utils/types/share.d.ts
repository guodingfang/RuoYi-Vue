type TArray<T> = T | T[];

type HM = "get" | "post" | "put" | "delete";

type IFetchHash = {
  [key in HM]?: (string | number)[];
};