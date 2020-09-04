<template>
  <table class="mailTable" :style="styleObject" v-if="s_showByRow">
    <tr v-for="index in rowCount">
      <td class="column">{{ tableData[index * 3 - 3].key }}</td>
      <td class="td_co">{{ tableData[index * 3 - 3].value }}</td>
      <td class="column">
        {{
          tableData[index * 3 - 2] !== undefined
            ? tableData[index * 3 - 2].key
            : ''
        }}
      </td>
      <td class="td_co">
        {{
          tableData[index * 3 - 2] !== undefined
            ? tableData[index * 3 - 2].value
            : ''
        }}
      </td>
      <td class="column">
        {{
          tableData[index * 3 - 1] !== undefined
            ? tableData[index * 3 - 1].key
            : ''
        }}
      </td>
      <td class="td_co">
        {{
          tableData[index * 3 - 1] !== undefined
            ? tableData[index * 3 - 1].value
            : ''
        }}
      </td>
    </tr>
  </table>

  <!-- <table class="mailTable" :style="styleObject" v-else>
    <tr v-for="index in rowCount">
      <td class="column">{{ tableData[index - 1].key }}</td>
      <td>{{ tableData[index - 1].value }}</td>
      <td class="column">
        {{
          tableData[rowCount + index - 1] !== undefined
            ? tableData[rowCount + index - 1].key
            : ''
        }}
      </td>
      <td>
        {{
          tableData[rowCount + index - 1] !== undefined
            ? tableData[rowCount + index - 1].value
            : ''
        }}
      </td>
    </tr>
  </table> -->
</template>

<script>
export default {
  data() {
    return {
      styleObject: {},
      s_showByRow: true
    };
  },
  props: ["tableData", "tableStyle", "showByRow"],
  computed: {
    rowCount: function () {
      return Math.ceil(this.tableData.length / 3);
    }
  },
  created() {
    this.styleObject = this.tableStyle;
    if (this.showByRow !== undefined) {
      this.s_showByRow = this.showByRow;
    }
  }
};
</script>

<style scoped>
table {
  border-collapse: collapse;
  /* margin: 16px 0 0; */
}

.mailTable,
.mailTable tr,
.mailTable tr td {
  border: 1px solid #e6eaee;
  text-align: center;
}
.mailTable {
  font-size: 12px;
  color: #71787e;
}
.mailTable tr td {
  border: 1px solid #e6eaee;
  width: 300px;
  height: 50px;
  line-height: 50px;
  box-sizing: border-box;
  padding: 0 10px;
}
.mailTable tr td.column {
  background-color: #eff3f6;
  color: #393c3e;
  width: 150px;
}
</style>
