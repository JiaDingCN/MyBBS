<template>
  <div>
    <b-row>
      <b-col md="3">
        <router-link to="/admin/user">
          <div class="card" style="background-color:green">
            <h1 style="color: aliceblue;width:100%">
              {{ userNum }}
            </h1>
            <p>
              用户
            </p>
          </div>
        </router-link>
      </b-col>
      <b-col md="3">
        <router-link to="/admin/post">
          <div class="card" style="background-color:black">
            <h1 style="color: aliceblue;width:100%">
              {{ postNum }}
            </h1>
            <p>
              用户
            </p>
          </div></router-link
        >
      </b-col>
      <b-col md="3">
        <router-link to="/admin/blocks">
          <div class="card" style="background-color:blue">
            <h1 style="color: aliceblue;width:100%">
              {{ blockNum }}
            </h1>
            <p>
              用户
            </p>
          </div></router-link
        >
      </b-col>
      <b-col md="3">
        <router-link to="/admin/visit">
          <div class="card" style="background-color:red">
            <h1 style="color: aliceblue;width:100%">
              {{ visitNum }}
            </h1>
            <p>
              用户
            </p>
          </div></router-link
        >
      </b-col>
    </b-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      postNum: 0,
      userNum: 0,
      blockNum: 0,
      visitNum: 10000
    };
  }
};
</script>
