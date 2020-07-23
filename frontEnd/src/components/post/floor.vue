<template>
  <b-row>
    <b-col>
      <div class="comment">
        <b-row>
          <b-col md="7">
            <div class="userBox" @click="link">
              <img :src="content.user.head_portrait" class="icon" alt="" />
              {{ content.user.username }}
            </div>
          </b-col>
          <b-col md="5">
            {{ content.Date }}
          </b-col>
        </b-row>
        <b-row class="textBox">
          <p>
            {{ content.content }}
          </p>
        </b-row>
      </div>
    </b-col>
  </b-row>
</template>
<script>
export default {
  props: {
    content: {
      type: Object,
      default: () => {
        return {
          user: {
            uid: "11",
            userName: "11",

            head_portrait: "http://gittub.cc/small2.png",
            admin_permission: false,
            status: true,
            comment: "5646465446454"
          },
          content: "testtesttest",

          Date: "2020.7.4",
          isCanshow: true,
          pid: "11"
        };
      }
    }
  },
  methods: {
    link() {
      this.$router.push("/userHome/" + this.content.user.uid);
    }
  }
};
</script>
<style lang="scss" scoped>
.comment {
  margin: 10px;
  border-bottom: 1px lightgray ridge;
  .userBox {
    float: left;

    .icon {
      height: 50px;
      width: 50px;
      border-radius: 50%;
    }
  }
}
</style>
