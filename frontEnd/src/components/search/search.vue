<template>
  <div>
    <nav_head></nav_head>
    <progressBar></progressBar>
    <b-container>
      <br />
      <br />
      <br />
      <h3>搜索结果</h3>
      <b-row>
        <b-col>
          <br /><br />
          <router-link
            :to="page.url"
            v-for="(page, index) in pages"
            :key="index"
          >
            <postCard :page="page"></postCard>
          </router-link>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <pagecounter :pageNum="count"></pagecounter>
        </b-col>
      </b-row>
    </b-container>
    <footerBar></footerBar>
  </div>
</template>
<script>
import nav_head from "../common/Header";
import postCard from "../common/postCard";
import progressBar from "../common/progress";
import footerBar from "../common/Footer";
import pagecounter from "../common/pagecounter";
import { getPageQueryForSearch } from "../../apis/api";
export default {
  data() {
    return {
      pages: [],
      count: 0
    };
  },
  components: {
    nav_head,
    postCard,
    progressBar,
    footerBar,
    pagecounter
  },
  mounted() {
    getPageQueryForSearch(
      this.$route.params.count,
      10,
      this.$route.params.block,
      this.$route.params.keyword
    ).then(res => {
      this.pages = res.data.list;
      this.count = res.data.totalPage;
      for (var i in this.pages) {
        this.pages[i]["url"] = "/post/" + this.pages[i].pid;
      }
    });
  }
};
</script>
<style lang="scss" scoped></style>
