<template>
  <div>
    <nav_bar></nav_bar>
    <div id="loginBox">
      <b-form id="login">
        <h2 style="text-align:center">登录</h2>
        <br />
        <b-form-group
          id="input-group-2"
          label-align-md="center"
          label-cols-lg="2"
          label="用户名:"
          label-for="input-2"
        >
          <b-form-input
            :state="validation_name"
            id="input-2"
            v-model="form.userName"
            required
            placeholder="Enter name"
          ></b-form-input>

          <b-form-invalid-feedback v-if="alert" :state="validation_name">
            <p v-show="!validation_name_require">用户名不为空</p>
            <p v-show="!initial">用户名未激活</p>
          </b-form-invalid-feedback>
        </b-form-group>
        <b-form-group
          label="密码:"
          label-for="input-3"
          label-align-md="center"
          label-cols-lg="2"
        >
          <b-form-input
            :state="validation_password"
            id="input-3"
            v-model="form.password"
            required
            placeholder="Enter password"
          ></b-form-input>
          <b-form-invalid-feedback v-if="alert" :state="validation_password">
            <p v-show="!validation_password_require">密码缺少</p>
            <p v-show="!auth">密码错误</p>
          </b-form-invalid-feedback>
        </b-form-group>

        <b-button
          style="margin: 5px;"
          v-b-modal.modal-1
          @click="onSubmit"
          variant="primary"
          >登录</b-button
        >

        <b-button style="margin: 5px;" @click="onReset" variant="danger"
          >清除</b-button
        >
      </b-form>
    </div>
  </div>
</template>
<script>
let sha1 = require("js-sha1");
import nav_bar from "./header";
import { login } from "../../apis/api";
export default {
  data() {
    return {
      initial: true,
      auth: true,
      result: "",
      form: {
        userName: "",
        password: ""
      },
      alert: false,
      checkurl: ""
    };
  },
  computed: {
    validation_name() {
      if (!this.alert) return null;
      return this.initial && this.validation_name_require;
    },
    validation_name_require() {
      return this.form.userName.length > 0;
    },
    validation_password() {
      if (!this.alert) return null;
      return this.auth && this.validation_password_require;
    },
    validation_password_require() {
      return this.form.password.length > 0;
    }
  },
  components: {
    nav_bar
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      this.alert = true;
      login(this.form.userName, sha1(this.form.password)).then(res => {
        if (res.data.flag) {
          alert("登录成功"),
            this.$store.commit("login", {
              userName: this.form.userName,
              userId: res.data.data
            });
          this.$router.push("/");
        } else {
          alert("用户名或者密码错误");
        }
      });
    },
    onReset(e) {
      e.preventDefault();
      this.form.userName = "";
      this.form.password = "";
    }
  }
};
</script>
<style lang="scss" scoped>
#loginBox {
  @include log-container;

  #login {
    @include card;
    align-self: center;
    margin: 0 auto;
    width: 30%;
    text-align: left;
    padding: 30px;
    background-color: white;
  }
}
</style>
