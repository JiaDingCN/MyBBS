<template>
  <div class="overflow-auto">
    <b-pagination-nav
      :link-gen="linkGen"
      :number-of-pages="pageNum"
      align="center"
      use-router
    ></b-pagination-nav>
  </div>
</template>

<script>
export default {
  props: {
    pageNum: {
      type: Number,
      default: 10
    }
  },
  data() {
    return {};
  },
  methods: {
    linkGen(pageNum) {
      var s = this.$route.path.split("/");
      var r = s.filter(function(s) {
        return s && s.trim(); // 注：IE9(不包含IE9)以下的版本没有trim()方法
      });
      var res = "/";
      for (var i = 0; i < r.length - 1; i++) {
        res += r[i];
        res += "/";
      }
      res += pageNum;
      return { path: res };
    }
  }
};
</script>
