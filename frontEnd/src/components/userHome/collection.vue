<template>
  <div>
    <b-row>
      <b-col>
        <b-nav tabs align="center">
          <b-nav-item :to="{ name: 'profile' }">profile</b-nav-item>
          <b-nav-item :to="{ name: 'posts' }">帖子</b-nav-item>
          <b-nav-item :to="{ name: 'following' }">关注</b-nav-item>
          <b-nav-item active :to="{ name: 'collection' }">收藏</b-nav-item>
        </b-nav>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <router-link
          id="post"
          v-for="(post, index) in posts"
          :to="post.link"
          :key="index"
        >
          <postCard :page="post"></postCard>
        </router-link>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <pagecounter :pageNum="count"></pagecounter>
      </b-col>
    </b-row>
  </div>
</template>
<script>
import { getPosts } from "../../apis/api";
import pagecounter from "../common/pagecounter";
import postCard from "../common/postCard";
export default {
  data() {
    return {
      posts: [],
      count: 0
    };
  },
  computed: {
    icon() {
      return this.$store.getters.getIcon;
    }
  },
  mounted() {
    getPosts(this.$route.params.count, 10, this.$route.params.userId).then(
      res => {
        this.posts = res.data.list;
        this.count = res.data.totalPage;
        for (var i in this.posts) {
          this.posts[i]["link"] = "/post/" + this.posts[i].pid;
        }
      }
    );
  },
  components: {
    postCard,
    pagecounter
  }
};
</script>
<style lang="scss" scoped>
#post {
  color: black;
  &:hover {
    color: black;
    text-decoration: none;
  }
}
</style>
