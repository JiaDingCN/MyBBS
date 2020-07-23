<template>
  <div id="back">
    <nav_head></nav_head>
  </div>
</template>
<script>
import nav_head from "../common/Header";
export default {
  components: {
    nav_head
  }
};
</script>
<style lang="scss" scoped>
#back {
  width: 100%;
  height: 100vh;
  background : {
    image: url("../../assets/about.png");
    size: 100% 100%;
    repeat: no-repeat;
    size: cover;
    position: center top;
  }
}
</style>
