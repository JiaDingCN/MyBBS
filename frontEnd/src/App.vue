<template>
  <div id="app">
    <div>
      <router-view v-if="routerAvailible"></router-view>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      routerAvailible: true
    };
  }
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
