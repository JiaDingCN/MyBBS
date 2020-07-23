<template>
  <div>
    <block_header></block_header>
    <progressbar></progressbar>
    <b-container class="list">
      <b-row>
        <b-col id="imageBox">
          <img
            src="../../assets/back.jpg"
            style="height: 300px;width:100%"
            alt=""
            class="rounded-lg"
          />
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <h5 id="introductionBox">
            {{ block_introduction }}
          </h5>
        </b-col>
      </b-row>
      <br />
      <b-row>
        <b-col md="8">
          <router-link
            style="color:black"
            class="link"
            v-for="(page, index) in pages"
            :to="page.url"
            :key="index"
          >
            <pageCard :page="page"></pageCard>
          </router-link>
        </b-col>
        <b-col md="4">
          <div id="sider" class="rounded-lg shadow">
            <h1>我要发贴</h1>
            <b-form @submit="onSubmit" @reset="onReset">
              <b-form-group label="标题：" label-for="input-1">
                <b-form-input
                  v-model="form.title"
                  id="input-1"
                  placeholder="Enter your title"
                ></b-form-input>
              </b-form-group>
              <b-form-group label="摘要：" label-for="input-1">
                <b-form-input
                  v-model="form.abstract"
                  id="input-1"
                  placeholder="Enter your summary"
                ></b-form-input>
              </b-form-group>
              <b-form-group label="正文：" label-for="input-3">
                <b-form-textarea
                  id="input-3"
                  v-model="form.text"
                  placeholder="Enter something..."
                  rows="3"
                  max-rows="6"
                ></b-form-textarea>
              </b-form-group>
              <b-button type="submit" :disabled="available" variant="primary"
                >Submit</b-button
              >
              <b-button type="reset" variant="danger">Reset</b-button>
            </b-form>
          </div>
        </b-col>
      </b-row>
      <br />
      <b-row>
        <b-col>
          <page_nav :pageNum="total_page"></page_nav>
        </b-col> </b-row
      ><block_footer></block_footer>
    </b-container>
  </div>
</template>
<script>
import {
  getPageQueryForViewByBlock,
  findByBid,
  writePost
} from "../../apis/api";
import block_header from "../common/Header";
import block_footer from "../common/Footer";
import page_nav from "../common/pagecounter";
import progressbar from "../common/progress";
import pageCard from "../common/postCard";
export default {
  data() {
    return {
      total_page: 100,
      pages: [],
      available: false,
      block_introduction: "",
      form: {
        title: "",
        abstract: "",

        text: ""
      }
    };
  },
  components: {
    block_header,
    block_footer,
    progressbar,
    page_nav,
    pageCard
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      writePost(
        this.form.title,
        this.form.abstract,
        this.form.text,
        this.$route.params.block_id
      ).then(res => {
        if (res.data.flag) {
          alert("发帖成功");
          this.form.title = "";
          this.form.abstract = "";
          this.form.text = "";
          this.available = true;
        }
      });
    },
    onReset(e) {
      e.preventDefault();
      this.form.title = "";
      this.form.abstract = "";
      this.form.text = "";
    }
  },
  mounted() {
    if (this.pages.length == 0) {
      getPageQueryForViewByBlock(
        this.$route.params.count,
        10,
        this.$route.params.block_id
      ).then(res => {
        this.pages = res.data.list;
        this.total_page = res.data.totalPage;
        for (var i = 0; i < this.pages.length; i++) {
          this.pages[i]["url"] = "/post/" + this.pages[i].pid;
        }
      });
    }
    findByBid(this.$route.params.block_id).then(res => {
      this.block_introduction = res.data.description;
    });
  },
  computed: {}
};
</script>
<style lang="scss" scoped>
.list {
  text-align: left;
  .link {
    text-decoration: none;
  }
  #imageBox {
    width: 100%;
    height: 280px;
  }
  #introductionBox {
    color: black;
    background-color: white;
    text-align: left;
    @include card;
  }
}
#sider {
  padding: 5%;
  position: sticky;
  background-color: white;
  top: 100px;
}
</style>
