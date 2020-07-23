<template>
  <div style="overflow:auto">
    <home_header></home_header>
    <b-container>
      <b-row>
        <b-col>
          <div>
            <!--横幅图片-->
            <img style="width:100%;" src="../../assets/个人中心.png" alt="" />
          </div>
        </b-col>
      </b-row>
      <br />
      <router-view></router-view><home_footer></home_footer>
    </b-container>
  </div>
</template>
<script>
import home_header from "../common/Header";
import home_footer from "../common/Footer";
export default {
  components: {
    home_header,
    home_footer
  }
};
</script>
<style lang="scss" scoped></style>
