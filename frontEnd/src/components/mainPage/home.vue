<template>
  <div>
    <div style="width:100%;position:relative;">
      <img
        src="../../assets/sitehead.png"
        style="width:100%;height:auto"
        alt=""
      />
    </div>
    <home_header style="margin-top:-13.55%"></home_header>
    <progressbar></progressbar>
    <br /><br /><br /><br /><br /><br />
    <b-container id="contain">
      <br />
      <b-row>
        <b-col md="8" id="carousel" class="incontainer">
          <carousel></carousel>
        </b-col>
        <b-col md="4">
          <div id="fashion">
            <ul>
              <router-link
                v-for="(item, index) in fashion"
                :key="index"
                :to="item.url"
                class="fashion_article"
              >
                <li style="display: block;">
                  {{ item.title }}
                  <p style="text-align: end;">{{ item.time }}</p>
                </li>
              </router-link>
            </ul>
          </div>
        </b-col>
      </b-row>
      <br />
      <b-row>
        <b-col
          class="nopadding rounded-top incontainer"
          style="border: 0.5px lightgray ridge ;"
        >
          论坛版块
        </b-col>
      </b-row>

      <b-row id="main">
        <b-col md="8" id="blocks">
          <br />
          <div>
            <b-row>
              <b-col v-for="(item, index) in blocks" :key="index" cols="6">
                <router-link :to="item.url" class="block_box">
                  <div style="max-width: 20rem;" class="mb-2 block_card">
                    <div class="imgbox">
                      <img :src="item.logo" alt="" />
                    </div>
                    <h5>
                      {{ item.blockName }}
                    </h5>
                    <p>
                      {{ item.describe }}
                    </p>
                  </div>
                </router-link>
              </b-col>
            </b-row>
          </div>
        </b-col>
        <b-col md="4">
          <br /><br /><br /><br />
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
                <img src="http://gittub.cc/small2.png" alt="" />
              </div>
              <h2>Gittub</h2>
              <p style="display:block;padding : 5%">
                GitTub是为程序员和互联网工作者所提供的一个自由、平等、开放的交流平台。
                在这里，您可以畅所欲言，发表自己的意见和看法。
                如果喜欢，请将他推荐给您的朋友，一起进入GitTub的世界！
              </p>
              <div id="site_intro"></div>
            </div>
          </div>
        </b-col>
      </b-row>
      <home_footer></home_footer>
    </b-container>
  </div>
</template>
<script>
import { findAllBlocks, getPopularPosts } from "../../apis/api";
import home_header from "../common/Header";
import home_footer from "../common/Footer";
import carousel from "../common/carousel";
import progressbar from "../common/progress";
export default {
  name: "home",
  data() {
    return {
      blocks: [],
      fashion: []
    };
  },
  components: {
    home_header,
    home_footer,
    carousel,
    progressbar
  },
  methods: {},
  mounted() {
    if (this.$store.getters.getBlocks.length == 0) {
      findAllBlocks().then(res => {
        this.blocks = res.data;
        for (var i in res.data) {
          this.$store.commit("commitBlock", i);
        }
        for (var j = 0; j < this.blocks.length; j++) {
          this.blocks[j].url = "/block/" + this.blocks[j].bid;
        }
      });
    } else {
      this.blocks = this.$store.getters.getBlocks;
      for (var j = 0; j < this.blocks.length; j++) {
        this.blocks[j].url = "/block/" + this.blocks[j].bid;
      }
    }

    getPopularPosts(4).then(res => {
      this.fashion = res.data;

      for (var i = 0; i < 4; i++) {
        this.fashion[i]["url"] = "/post/" + this.fashion[i].pid;
        if (this.fashion[i].title.length > 10) {
          this.fashion[i].title = this.fashion[i].title.substr(0, 10) + "...";
        }
      }
    });
  }
};
</script>
<style lang="scss" scoped>
.title {
  border: 1px ridge;
  padding: 15px;
}

#fashion {
  @include card;
  text-align: left;
  padding: 10px;
  & ul {
    padding-left: 0;
  }
  & li {
    border-bottom: 0.5px $background-color ridge;
    height: 50px;
  }
  .fashion_article {
    margin: 10px;
    color: black;
    display: block;
    &:hover {
      text-decoration: none;
      background-color: $background-color;
    }
  }
}

#main {
  #sider_bar {
    width: 100%;
    .sider {
      @include card;

      display: flex;
      flex-direction: column;
      #site_intro {
        border-top: 0.5px $background-color ridge;
        width: 80%;
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

  #blocks {
    margin: 0 auto;
    .block_box:hover img {
      transform: scale(1.5);
      -webkit-transform: scale(1.5);
    }

    .block_box img {
      width: 100%;
      height: auto;
      transition: transform 2s;
      -webkit-transition: -webkit-transform 2s;
    }
    .block_card {
      @include card;
    }
    border-right: 0.5px ridge $background-color;
  }
  .imgbox {
    overflow: hidden;
    width: 100%;
  }
}

.nopadding {
  padding: 0;
}
</style>
