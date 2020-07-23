<template>
  <div class="d-flex flex-row">
    <div id="sider"></div>
    <b-container>
      <router-view></router-view>
    </b-container>
  </div>
</template>
<script></script>
<style lang="scss" scoped>
#sider {
  width: 20%;
  background-color: blueviolet;
  position: sticky;
  height: 100vh;
}
</style>
