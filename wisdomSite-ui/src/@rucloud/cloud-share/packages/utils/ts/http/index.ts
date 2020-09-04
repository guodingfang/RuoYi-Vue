// axios 二次封装 依赖 axios
import {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError
} from "axios";
import { get, getType } from "../helper";

type AxiosInstanceConfig = TArray<AxiosInstance>;

export interface ICode {
  success: number | string;
  fail: number | string;
}

export interface IMyerror {
  error: AxiosError;
  [index: string]: boolean | AxiosError;
}

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
  [name: string]: (data: any) => Promise<AxiosResponse>;
}

export class HttpCloud {
  private configComputed: AtLeast<
    IHttpConfig,
    "autoSetting" | "errorFlag" | "instance"
  >;
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

  getInstance() {
    return this.instance;
  }

  init(config: IHttpConfig) {
    const typeIns = getType(this.instance);
    if (typeIns === "Array") {
      (this.instance as AxiosInstance[]).forEach(ins => {
        this.handleInstance(ins);
      });
    } else {
      this.handleInstance(this.instance as AxiosInstance);
    }
    if (this.configComputed.autoSetting === true) {
      this.setConfig();
    }
  }

  setConfig() {
    const typeIns = getType(this.instance);
    const handleIns = (insSingle: AxiosInstance): void => {
      if (this.configComputed.transIns) {
        this.configComputed.transIns(insSingle);
      }
    };
    if (typeIns === "Array") {
      (this.instance as AxiosInstance[]).forEach(ins => {
        handleIns(ins);
      });
    } else {
      handleIns(this.instance as AxiosInstance);
    }
  }

  handleInstance(instance: AxiosInstance) {
    instance.interceptors.request.use(
      this.preRequest.bind(this),
      this.catchError.bind(this)
    );
    instance.interceptors.response.use(
      this.handleResponse.bind(this),
      this.catchError.bind(this)
    );
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

  catchError(error: AxiosError): Promise<IMyerror> {
    const code: number = get(error, "response.status", false);
    const errorFlag = this.configComputed.errorFlag;
    try {
      if (this.configComputed.codeHandler) {
        const hName: codeKeys = `${code}` as codeKeys;
        this.configComputed.codeHandler[hName](code, error);
      }
    } catch (error) {
      console.warn(error);
    }
    // 主要是 懒的 try catch
    return Promise.resolve({
      [errorFlag]: true,
      error
    });
  }

  genRest(fetchHash: IFetchHash, prefix?: string) {
    return HttpCloud.genRestful(
      this.instance as AxiosInstance,
      fetchHash,
      prefix
    );
  }

  // 解析 path 中的参数选项
  static interParam = function<T, K extends keyof T>(
    urlIn: string,
    dataIn: T,
    reserve?: boolean
  ) {
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
    prefix?: string
  ): IRestfulHash {
    const methods: HM[] = ["get", "post", "put", "delete"];
    const out: IRestfulHash = Object.create(null);
    methods.forEach(me => {
      (get(fetchHash, [me], []) as (string | number)[]).forEach(interIn => {
        // 第一个 key
        const inter = String(interIn)
          .split("/")
          .filter(i => [null, undefined, ""].indexOf(i) === -1)[0];
        const urlIn: string = `${prefix}${inter}`;
        switch (me) {
          case "get":
            out[inter] = function(dataIn: any) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn);
              return ins.get(url, { params: data });
            };
            break;
          case "post":
            out[inter] = function(dataIn: any) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn);
              return ins.post(url, data);
            };
            break;
          case "put":
            out[inter] = function(dataIn: any) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn);
              return ins.put(url, data);
            };
            break;
          case "delete":
            out[inter] = function(dataIn: any) {
              const { url, data } = HttpCloud.interParam(urlIn, dataIn);
              return ins.delete(url, { data });
            };
            break;
          default:
            const meNever: never = me;
            console.warn(`${meNever} can not be found`);
        }
      });
    });
    return out;
  };
}
