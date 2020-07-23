<template>
  <div>
    <page_header></page_header>
    <progress_bar></progress_bar>
    <b-container>
      <b-row>
        <b-col md="8"
          ><div id="page_container">
            <br />
            <b-row>
              <b-col cols="6">
                <div class="userBox" @click="link">
                  <img :src="auth.head_portrait" class="icon" alt="" />
                  {{ auth.username }}
                </div>
              </b-col>
              <b-col cols="6">
                <b-button v-if="unFollowing" variant="success" @click="follow"
                  >关注</b-button
                >
                <b-button v-if="following" variant="danger" @click="unFollow"
                  >取关</b-button
                >
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <h2>{{ post.title }}</h2>
                <p>{{ post.time }}</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="textBox">
                <p>{{ post.content }}</p>
              </b-col>
            </b-row>
            <b-row
              style="margin: 0 5%;border-top:0.5px ridge lightgray;border-bottom:0.5px ridge lightgray"
            >
              <b-col col>
                <a href="#input_comment">
                  <div>
                    <b-icon icon="chat-fill"></b-icon>
                    {{ comments }}
                  </div>
                </a>
              </b-col>
              <b-col col @click="clickLike">
                <b-icon v-if="unFavourited" icon="star"></b-icon>
                <b-icon v-if="favourited" icon="star-fill"></b-icon>
                {{ post.liked }}
              </b-col>
              <b-col col>
                <b-icon icon="eye-fill"></b-icon>
                {{ post.view }}
              </b-col>
            </b-row>
            <floor
              v-for="(item, index) in items"
              :content="item"
              :key="index"
            ></floor>
            <b-row>
              <b-col>
                <div id="input_comment">
                  <h4>发表评论</h4>
                  <b-form-textarea
                    id="textarea"
                    v-model="text"
                    placeholder="Enter something..."
                    rows="3"
                    max-rows="6"
                  ></b-form-textarea>
                  <br />
                  <b-button
                    variant="success"
                    style="float:right;display:block"
                    @click="submit"
                    >提交</b-button
                  >
                </div>
              </b-col>
            </b-row>
          </div></b-col
        >
        <b-col md="4">
          <div id="sider_bar" class="rounded-lg ">
            <div class="sider">
              <div style="width:100%;height:100px;overflow:hidden">
                <img
                  src="../../assets/back.jpg"
                  style="width:100%;overflow:hidden"
                  alt=""
                />
              </div>
              <div id="icon_box">
                <img :src="auth.head_portrait" alt="" />
              </div>
              <h2>{{ auth.username }}</h2>
              <div id="site_intro">
                {{ auth.comment }}
              </div>
            </div>
            <br />
            <div class="sider">
              <h2>板块链接</h2>
              <b-row style="padding : 0 10px">
                <b-col
                  style="margin: 10px 0"
                  v-for="(item, index) in blocks"
                  :key="index"
                  cols="4"
                >
                  <div @click="blockLink(item.url)">
                    <img style="width:100%" :src="item.icon" alt="" />
                    {{ item.name }}
                  </div>
                </b-col>
              </b-row>
            </div>
          </div>
        </b-col>
      </b-row>
      <page_footer></page_footer>
    </b-container>
  </div>
</template>
<script>
import page_header from "../common/Header.vue";
import page_footer from "../common/Footer";

import progress_bar from "../common/progress";
import floor from "./floor";
import {
  findAllBlocks,
  findOne,
  isPostFavourite,
  addFavouritePost,
  cancelFavourite,
  getUserInfoById,
  submitComment,
  allCommentsInThisPost,
  addFavouriteUser,
  deleteFavourite,
  isUserFavourite
} from "../../apis/api";
export default {
  data() {
    return {
      items: [],
      comments: 0,
      post: {},
      auth: {
        uid: "11",
        userName: "11",
        password: "2656",
        head_portrait: "http://gittub.cc/small2.png",
        admin_permission: false,
        status: true,
        comment: "5646465446454"
      },
      blocks: [],
      favourited: false,
      following: false,
      text: ""
    };
  },
  mounted() {
    findOne(this.$route.params.page_id).then(res => {
      this.post = res.data;
      isPostFavourite(this.$route.params.page_id).then(res => {
        this.favourited = res.data;
      });
      getUserInfoById(this.post.uid).then(res => {
        this.auth = res.data;
      });
      allCommentsInThisPost(this.$route.params.page_id).then(res => {
        this.items = res.data;
        this.comments = res.data.length;
        this.comments = this.items.length;
      });
      isUserFavourite(this.post.uid).then(res => {
        this.following = res.data;
      });
    });
    var b = this.$store.getters.getBlocks;

    if (b.length != 0) {
      for (var i in b) {
        this.blocks.push({
          name: b[i].blockName,
          icon: b[i].logo,
          bid: b[i].bid
        });
      }
      for (var j = 0; j < this.blocks.length; j++) {
        this.blocks[j].url = "/block/" + this.blocks[j].bid;
      }
    } else {
      findAllBlocks().then(res => {
        for (var i in res.data) {
          this.blocks.push({
            name: res.data[i].blockName,
            icon: res.data[i].logo,
            bid: res.data[i].bid
          });
          this.$store.commit("commitBlock", i);
        }
        for (var j = 0; j < this.blocks.length; j++) {
          this.blocks[j]["url"] = "/block/" + this.blocks[j].bid;
        }
      });
    }

    console.log(12);
  },
  computed: {
    unFollowing() {
      return !this.following;
    },
    unFavourited() {
      return !this.favourited;
    }
  },
  methods: {
    clickLike(e) {
      e.preventDefault();
      if (this.favourited) {
        cancelFavourite(this.$route.params.page_id).then(res => {
          if (res.data.flag) {
            this.post.liked--;
            this.favourited = !this.favourited;
          }
        });
      } else {
        addFavouritePost(this.$route.params.page_id).then(res => {
          if (res.data.flag) {
            this.post.liked++;
            this.favourited = !this.favourited;
          }
        });
      }
    },
    blockLink(url) {
      this.$router.push(url);
    },
    link() {
      this.$router.push("/userHome/" + this.auth.uid);
    },
    submit() {
      submitComment(this.post.pid, this.text).then(res => {
        if (res.data.flag) {
          alert("评论成功");
          this.$router.go(0);
        }
      });
    },
    follow() {
      if (this.$store.getters.getId == this.auth.uid) {
        alert("不能关注自己");
        return;
      }
      addFavouriteUser(this.auth.uid).then(res => {
        if (res.data.flag) {
          this.following = true;
        }
      });
    },
    unFollow() {
      deleteFavourite(this.auth.uid).then(res => {
        if (res.data.flag) {
          this.following = false;
        }
      });
    }
  },
  components: {
    page_header,
    page_footer,

    progress_bar,
    floor
  }
};
</script>
<style lang="scss" scoped>
.userBox {
  float: left;
  .icon {
    height: 50px;
    width: 50px;
    border-radius: 50%;
  }
}
#page_container {
  @include card;
  #input_comment {
    text-align: left;
    padding: 10px;
  }
}
#sider_bar {
  position: sticky;
  width: 100%;
  .sider {
    @include card;

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
  position: sticky;
  top: 60px;
}
</style>
