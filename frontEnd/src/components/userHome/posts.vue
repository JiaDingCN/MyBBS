<template>
  <div>
    <b-row>
      <b-col>
        <b-nav tabs align="center">
          <b-nav-item :to="{ name: 'profile' }">profile</b-nav-item>
          <b-nav-item active :to="{ name: 'posts' }">帖子</b-nav-item>
          <b-nav-item :to="{ name: 'following' }">关注</b-nav-item>
          <b-nav-item :to="{ name: 'collection' }">收藏</b-nav-item>
        </b-nav>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <router-link
          v-for="(post, index) in pages"
          id="post"
          :to="post.url"
          :key="index"
        >
          <pageCard :page="post"></pageCard>
        </router-link>
      </b-col>
    </b-row>
    <pagecounter :pageNum="count"></pagecounter>
  </div>
</template>
<script>
import { getPosts } from "../../apis/api";
import pagecounter from "../common/pagecounter";
import pageCard from "../common/postCard";
export default {
  data() {
    return {
      pages: [],
      count: 10
    };
  },
  computed: {
    icon() {
      return this.$store.getters.getIcon;
    }
  },
  mounted() {
    getPosts(
      this.$route.params.count,
      this.page_num,
      this.$route.params.userId
    ).then(res => {
      this.pages = res.data.list;
      this.count = res.data.totalPage;
      for (var i in this.pages) {
        this.pages[i]["url"] = "/post/" + this.pages[i].pid;
      }
    });
  },
  components: {
    pagecounter,
    pageCard
  }
};
</script>
<style lang="scss" scoped>
#post {
  color: black;
  &:hover {
    text-decoration: none;
  }
}
</style>
