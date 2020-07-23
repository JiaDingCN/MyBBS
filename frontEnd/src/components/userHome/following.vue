<template>
  <div>
    <b-row>
      <b-col>
        <b-nav tabs align="center">
          <b-nav-item to="profile">profile</b-nav-item>
          <b-nav-item to="posts">帖子</b-nav-item>
          <b-nav-item active to="following">关注</b-nav-item>
          <b-nav-item to="collection">收藏</b-nav-item>
        </b-nav>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="4" v-for="(item, index) in followings" :key="index">
        <div class="sider" @click="clickOthers(item.uid)">
          <div style="width:100%;height:100px;overflow:hidden">
            <img
              src="../../assets/back.jpg"
              style="width:100%;overflow:hidden"
              alt=""
            />
          </div>
          <div id="icon_box">
            <img :src="item.head_portrait" alt="" />
          </div>
          <h2>{{ item.username }}</h2>
          <div id="site_intro">
            {{ item.comment }}
          </div>
        </div>
      </b-col>
    </b-row>
  </div>
</template>
<script>
import { getLikedPeople } from "../../apis/api";
export default {
  data() {
    return {
      followings: []
    };
  },
  methods: {
    clickOthers(uid) {
      this.$router.push("/userHome/" + uid);
    }
  },
  mounted() {
    getLikedPeople().then(res => {
      this.followings = res.data;
    });
  }
};
</script>
<style lang="scss" scoped>
.sider {
  @include card;
  margin: 10px;
  display: flex;
  flex-direction: column;
  #site_intro {
    border-top: 0.5px $background-color ridge;
    width: 80%;
    margin: 0 auto;
    text-align: center;
  }
  #icon_box {
    & img {
      border-radius: 50%;
      overflow: hidden;
      width: 80px;
      height: 80px;
    }
    position: relative;
    top: -40px;
  }
}
</style>
