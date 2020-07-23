<template>
  <b-navbar :class="curr_class" toggleable="lg" type="light" :sticky="true">
    <b-navbar-brand to="/">gittub</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item to="/aboutus">关于我们</b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <label class="mr-sm-2" for="inline-form-custom-select-pref"
            >搜索分区</label
          >
          <b-form-select
            class="mr-sm-2 p-6 mb-0"
            v-model="currBlock"
            :options="blocks"
            id="inline-form-custom-select-pref"
          ></b-form-select>
          <b-form-input
            size="md    "
            class="mr-sm-2"
            placeholder="Search"
            v-model="keyword"
          ></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" @click="search" type="submit"
            >搜索</b-button
          >
        </b-nav-form>

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          <template v-slot:button-content>
            <em>我</em>
          </template>
          <b-dropdown-item v-if="logged" :to="userhome">用户</b-dropdown-item>
          <b-dropdown-item v-if="logged" @click="exitUser"
            >登出</b-dropdown-item
          >
          <b-dropdown-item v-if="unlogged" to="/signin">注册</b-dropdown-item>
          <b-dropdown-item v-if="unlogged" to="/login">登录</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>
<script>
import { findAllBlocks, exit } from "../../apis/api";
export default {
  data() {
    return {
      curr_class: "up",
      blocks: [],
      currBlock: "",
      keyword: ""
    };
  },
  methods: {
    search: function() {
      this.$router.push("/search/" + this.currBlock + "/" + this.keyword);
    },
    handleScroll() {
      if (this.$store.getters.getScroll > 50) {
        this.curr_class = "down shadow";
      } else {
        this.curr_class = "up";
      }
    },
    exitUser() {
      this.$store.commit("exit");
      exit();
    }
  },
  computed: {
    logged() {
      return this.$store.getters.getLogState;
    },
    unlogged() {
      return !this.logged;
    },
    userhome() {
      return "/userHome/" + this.$store.getters.getId;
    }
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
    if (this.$store.getters.getBlocks.length != 0) {
      for (var i = 0; i < this.$store.getters.getBlocks.length; i++) {
        this.blocks.push(this.$store.getters.getBlocks[i].blockName);
      }
    } else {
      findAllBlocks().then(res => {
        for (var i = 0; i < res.data.length; i++) {
          this.blocks.push({
            value: res.data[i].blockName,
            text: res.data[i].blockName,
            bid: res.data[i].uid
          });
          this.$store.commit("commitBlock", res.data[i]);
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
@media (max-width: 768px) {
  .up {
    background-color: $main-color !important;
  }
}
.up {
  background-color: initial;
}
.down {
  background-color: $main-color;
}
</style>
