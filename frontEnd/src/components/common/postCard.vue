<template>
  <div class="pageCard">
    <b-row>
      <b-col style="display:flex;align-items:center ">
        <div @click="like">
          <b-icon
            style="display: inline-block"
            icon="star-fill"
            v-if="isFavourite"
          >
          </b-icon>
          <b-icon style="display: inline-block" icon="star" v-if="unFavourite">
          </b-icon>
        </div>
        <h4 style="display:inline-block;margin:10px">
          {{ page.title }}
        </h4>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <div id="page_abstract">
          <p>{{ page.summary }}</p>
        </div>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="7" style="padding: 0 10%">
        <img
          :src="auth.head_portrait"
          style="width:30px;height:30px;border-radius:50%"
          alt=""
        />
        {{ auth.username }}|{{ page.time }}
      </b-col>

      <b-col style="text-align: right;padding-right:20px">
        点赞{{ page.liked }}|浏览{{ page.view }}
      </b-col>
    </b-row>
  </div></template
>

<script>
import { getUserInfoById, isPostFavourite } from "../../apis/api";
export default {
  props: {
    page: {
      type: Object,
      default: () => {
        return {
          pid: 111,
          uid: 111,
          bid: 111,
          summary: "摘要",
          content: "正文",
          title: "标题",
          date: "发布时间",
          view: 10,
          liked: 10,
          isCanshow: true
        };
      }
    }
  },
  data() {
    return {
      auth: {},
      isFavourite: false
    };
  },
  methods: {
    like() {
      if (this.isFavorite) {
        this.isFavorite = false;
      } else {
        this.isFavorite = true;
      }
    }
  },
  computed: {
    unFavourite() {
      return !this.isFavourite;
    }
  },
  mounted() {
    getUserInfoById(this.page.uid).then(res => {
      this.auth = res.data;
    });
    isPostFavourite(this.page.pid).then(res => {
      this.isFavourite = res.data;
    });
  }
};
</script>

<style lang="scss" scoped>
.pageCard {
  padding: 2.5% 0;
  margin: 5% 0;
  @include card;
  text-align: left;
  #page_abstract {
    background-color: $background-color;
    display: block;
    margin: 20px;
  }
}
</style>
