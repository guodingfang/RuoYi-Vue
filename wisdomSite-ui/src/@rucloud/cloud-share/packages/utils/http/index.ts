// axios 二次封装 依赖 axios
import { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from "axios";
import { get, isArray, notEmpty } from "../helpers";

type AxiosInstanceConfig = TArray<AxiosInstance>;

export interface ICode {
  success: number | string;
  fail: number | string;
}
// error response
export type MyError = {
  error: AxiosError;
  isSysError?: boolean;
  [index: string]: boolean | AxiosError | undefined;
};

export interface ICodeHandler {
  [propName: string]: (code: number | string, error: any) => any;
}

type codeKeys = keyof ICodeHandler;

export interface IHttpConfig {
  instance: AxiosInstanceConfig;
  code: ICode;
  autoSetting: boolean;
  errorFlag: string;
  codeHandler: ICodeHandler;
  request(config: AxiosRequestConfig): AxiosRequestConfig;
  response(res: AxiosResponse): AxiosResponse;
  transIns(ins: AxiosInstance): AxiosInstance;
}

interface IRestfulHash {
  [name: string]: (data?: any, reserve?: boolean) => Promise<AxiosResponse | MyError>;
}

export class HttpCloud {
  private configComputed: AtLeast<IHttpConfig, "autoSetting" | "errorFlag" | "instance">;
  private instance: AxiosInstanceConfig;

  private deConfig: AtLeast<IHttpConfig, "autoSetting" | "errorFlag"> = {
    autoSetting: true,
    errorFlag: "isSysError"
  };

  constructor(config: AtLeast<IHttpConfig, "instance">) {
    this.configComputed = {
      ...this.deConfig,
      ...config
    };
    this.instance = this.configComputed.instance;
    this.init(this.configComputed as IHttpConfig);
  }

  getInstance(index?: number) {
    if (isArray(this.instance)) {
      /* istanbul ignore else */
      if (index || index === 0) {
        return this.instance[index];
      }
    }
    return this.instance as AxiosInstance;
  }

  getMultiInstance() {
    if (isArray(this.instance)) {
      return this.instance;
    } else {
      console.warn("signle instance use getInstance");
      return [this.getInstance()];
    }
  }

  init(config: IHttpConfig) {
    if (isArray(this.instance)) {
      this.instance.forEach(ins => {
        this.handleInstance(ins);
      });
    } else {
      this.handleInstance(this.instance);
    }
    /* istanbul ignore else */
    if (this.configComputed.autoSetting === true) {
      this.setConfig();
    }
  }

  setConfig() {
    const handleIns = (insSingle: AxiosInstance): void => {
      if (this.configComputed.transIns) {
        this.configComputed.transIns(insSingle);
      }
    };
    if (isArray(this.instance)) {
      this.instance.forEach(ins => {
        handleIns(ins);
      });
    } else {
      handleIns(this.instance);
    }
  }

  handleInstance(instance: AxiosInstance) {
    instance.interceptors.request.use(this.preRequest.bind(this), this.catchError.bind(this));
    instance.interceptors.response.use(this.handleResponse.bind(this), this.catchError.bind(this));
  }

  preRequest(config: AxiosRequestConfig): AxiosRequestConfig {
    // prehandle config
    if (this.configComputed.request) {
      config = this.configComputed.request(config);
    }
    return config;
  }

  handleResponse(result: AxiosResponse): AxiosResponse {
    if (this.configComputed.response) {
      result = this.configComputed.response(result);
    }
    return result;
  }

  catchError(error: AxiosError): Promise<MyError> {
    const code: number = get(error, "response.status", false);
    const errorFlag = this.configComputed.errorFlag;
    const codeHandler = this.configComputed.codeHandler;
    try {
      if (codeHandler) {
        const hName: codeKeys = `${code}` as codeKeys;
        /* istanbul ignore else */
        if (codeHandler[hName]) {
          codeHandler[hName](code, error);
        } else if (codeHandler.others) {
          codeHandler.others(code, error);
        }
      }
    } catch (error) {
      /* istanbul ignore next */
      console.warn(error);
    }
    // 主要是 懒的 try catch
    return Promise.resolve({
      [errorFlag]: true,
      error
    });
  }

  isError(x: any): x is MyError {
    return x[this.configComputed.errorFlag] === true;
  }

  genRest(fetchHash: IFetchHash, prefix?: string) {
    return HttpCloud.genRestful(this.instance as AxiosInstance, fetchHash, prefix);
  }

  // 解析 path 中的参数选项
  static interParam = function<T, K extends keyof T>(urlIn: string, dataIn: T, reserve = false) {
    let url = urlIn;
    let data = { ...dataIn };
    const matchs = url.match(/{([^/.]+)}/g);
    if (matchs?.length) {
      matchs.forEach(param => {
        const key: K = param.replace(/[{}]/gi, "") as K;
        url = url.replace(param, String(data[key]));
        if (!reserve) {
          delete data[key];
        }
      });
    }
    return { url, data };
  };

  // restful生成方法
  static genRestful = function(
    ins: AxiosInstance,
    fetchHash: IFetchHash,
    prefix: string = ""
  ): IRestfulHash {
    const methods: HM[] = ["get", "post", "put", "delete"];
    const out: IRestfulHash = Object.create(null);
    methods.forEach(me => {
      // 如若没有返回空数组
      (get(fetchHash, [me], []) as (string | number)[]).forEach((interIn, index) => {
        // 第一个 key
        let inter = String(interIn)
          .split("/")
          .filter(i => notEmpty(i))[0];
        const urlIn: string = `${prefix}${interIn}`;
        // 重名
        if (out[inter]) {
          inter = `${inter}_${me}`;
          // 再次重名
          if (out[inter]) {
            inter = `${inter}_${index}`;
          }
        }
        switch (me) {
          case "get":
            out[inter] = function(dataIn?: any, reserve = false) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn, reserve);
              return ins.get(url, { params: data });
            };
            break;
          case "post":
            out[inter] = function(dataIn?: any, reserve = false) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn, reserve);
              return ins.post(url, data);
            };
            break;
          case "put":
            out[inter] = function(dataIn?: any, reserve = false) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn, reserve);
              return ins.put(url, data);
            };
            break;
          case "delete":
            out[inter] = function(dataIn?: any, reserve = false) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn, reserve);
              return ins.delete(url, { data });
            };
            break;
          /* istanbul ignore next */
          default:
            // never
            const meNever: never = me;
            console.warn(`${meNever} method can not be found`);
        }
      });
    });
    return out;
  };
}
