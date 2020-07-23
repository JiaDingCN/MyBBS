<template>
  <div id="back">
    <navhead></navhead>
  </div>
</template>

<script>
import navhead from "../common/Header";
export default {
  components: {
    navhead
  }
};
</script>

<style lang="scss" scope>
#back {
  width: 100%;
  height: 100vh;
  background : {
    image: url("http://qdnhdgyix.bkt.clouddn.com/404.png");
    size: 100% 100%;
    repeat: no-repeat;
    size: cover;
    position: center top;
  }
}
</style>
