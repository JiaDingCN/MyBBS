<template>
  <b-progress
    :value="value"
    :max="max"
    id="progress"
    animated
    height="5px"
  ></b-progress>
</template>
<script>
export default {
  data() {
    return {
      value: 0,
      max: 1
    };
  },
  methods: {
    handleScroll() {
      // 屏幕剩余的高度
      let surplus =
        document.documentElement.scrollHeight -
        document.documentElement.clientHeight;
      // 当前滑动高度

      let scrollY = document.documentElement.scrollTop;
      this.$store.commit("scroll", scrollY);

      // 当前位置百分比小数
      this.value = scrollY / surplus;
      // 设置导航栏，这里使用NProgress.set() 动态更改进度条
    }
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll); // 监听滚动条事件
  }
};
</script>
<style>
#progress {
  position: -webkit-sticky;
  position: sticky;
  top: 56px;
  z-index: 1020;
  background-color: initial;
}
</style>
