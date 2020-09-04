import { get } from '@rucloud/cloud-share/dist/utils';

export default function areaFn() {
  const vmCom = this;
  return function area(h, config, ins) {
    const { valuesIn } = this;
    // 设置 marginBottom
    // set(config, 'itemmap.style.marginBottom', 0);
    return (
      <section>
        <el-form-item
          rules={config.rules} /* 只是为了开启验证 */
          label={config.label}
          {...config.itemmap}
          class='f_photo'
          style={{ width: '100%' }}
        >
          <el-form-item style={{ 'margin-bottom': 0 }} rules={config.rules} prop='photo' key='photo'>  {/*  rules={config.rules} */}
            <img-lists imageUrl={vmCom.STATE === 'MODIFY' ? `${valuesIn.photo}` : valuesIn.photo}/>
          </el-form-item>
        </el-form-item>
      </section>

    );
  };
}
