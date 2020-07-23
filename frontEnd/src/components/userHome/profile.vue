<template>
  <div>
    <b-row>
      <b-col>
        <b-nav tabs align="center">
          <b-nav-item active to="profile">profile</b-nav-item>
          <b-nav-item to="posts">帖子</b-nav-item>
          <b-nav-item to="following">关注</b-nav-item>
          <b-nav-item to="collection">收藏</b-nav-item>
        </b-nav>
      </b-col>
    </b-row>
    <b-row>
      <b-col md="4">
        <div>
          <br />
          <img
            :src="user.head_portrait"
            style="width:200px;height:200px;display:block"
            alt=""
          />
          <b-form-file v-model="icon" v-if="currUser" class="mt-3" plain
            >更换头像</b-form-file
          >
          <b-button variant="success" v-if="currUser" @click="submitIcon">
            更改头像</b-button
          >
          <b-button
            variant="success"
            v-if="others && !following"
            @click="follow"
            >关注</b-button
          >
          <b-button
            variant="danger"
            v-if="others && following"
            @click="unfollow"
            >取关</b-button
          >
        </div>
      </b-col>
      <b-col md="8">
        <b-row>
          <b-col>
            <div class="profile_card">
              <div>
                <h4>我的信息</h4>
              </div>
              <br />
              <b-row style="min-height:20px">
                <b-col cols="4">
                  用户名
                </b-col>
                <b-col cols="8">
                  <p v-if="show">{{ user.username }}</p>
                  <b-input-group v-if="input">
                    <b-form-input
                      v-model="userName"
                      :placeholder="user.username"
                    ></b-form-input>
                  </b-input-group>
                </b-col>
              </b-row>
              <br />
              <b-row v-if="input">
                <b-col cols="4">
                  密码
                </b-col>
                <b-col cols="8">
                  <b-input-group>
                    <b-form-input v-model="password"></b-form-input>

                    <b-input-group-append>
                      <b-button
                        variant="outline-secondary"
                        @click="submitPassword"
                        >更改</b-button
                      >
                    </b-input-group-append>
                  </b-input-group>
                </b-col>
              </b-row>
              <br />

              <b-row style="min-height:20px">
                <b-col cols="4">
                  个人简介：
                </b-col>
                <b-col cols="8">
                  <p v-if="show">{{ user.comment }}</p>
                  <b-form-textarea
                    id="textarea"
                    v-if="input"
                    v-model="introduction"
                    :placeholder="user.comment"
                    rows="3"
                    max-rows="10"
                  ></b-form-textarea>
                </b-col>
              </b-row>
              <br />
              <b-row>
                <b-col style="padding:0 10%">
                  <b-button
                    v-if="currUser"
                    style="float:center"
                    variant="success"
                    @click="click"
                  >
                    {{ state }}
                  </b-button>
                </b-col>
              </b-row>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </div>
</template>
<script>
let sha1 = require("js-sha1");
import {
  uploadHeadPortrait,
  modifyPassword,
  getUserInfoById,
  modifyUserComment,
  isUserFavourite,
  addFavouriteUser,
  deleteFavourite
} from "../../apis/api";
export default {
  data() {
    return {
      user: {},
      userName: "",
      password: "",
      state: "修改",
      input: false,
      icon: null,
      introduction: "",
      following: true
    };
  },
  computed: {
    show() {
      return !this.input;
    },
    currUser() {
      return this.$route.params.userId == this.$store.getters.getId;
    },
    others() {
      return !this.currUser;
    }
  },
  methods: {
    follow() {
      addFavouriteUser(this.$route.params.userId).then(res => {
        if (res.data.flag) {
          alert("关注成功");
          this.following = true;
        }
      });
    },
    unfollow() {
      deleteFavourite(this.$route.params.userId).then(res => {
        if (res.data.flag) {
          alert("取关成功");
          this.following = false;
        }
      });
    },

    click(e) {
      e.preventDefault();
      if (this.state == "修改") {
        this.state = "保存";
        this.input = "true";
      } else {
        this.state = "修改";
        this.input = false;
        this.submitComment();
      }
    },
    submitComment() {
      modifyUserComment(this.introduction).then(res => {
        if (res.data.flag) {
          alert("简介修改成功");
        }
      });
    },
    submitPassword() {
      modifyPassword(sha1(this.password)).then(res => {
        if (res.data.resultInfo) {
          alert("密码修改成功");
        }
      });
    },
    submitIcon() {
      let formData = new FormData();
      formData.append("file", this.icon);
      uploadHeadPortrait(formData).then(res => {
        if (res.data.flag) {
          alert("头像修改成功");
        }
      });
    }
  },
  mounted() {
    getUserInfoById(this.$route.params.userId).then(res => {
      this.user = res.data;
    });
    isUserFavourite(this.$route.params.userId).then(res => {
      this.following = res.data;
    });
    this.userName = this.user.username;
    this.password = this.user.password;
    this.introduction = this.user.comment;
  }
};
</script>
<style lang="scss" scoped>
.profile_card {
  @include card;
  padding: 10%;
}
</style>
