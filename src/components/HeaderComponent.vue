<template>
  <div class="header-body">
    <div class="header-logo-left">
      <img src="../assets/logo1.png" width="100" height="100">
    </div>
    <div class="header-middle">
      <div class="header-title">
        <h1>E-scooters Amsterdam</h1>
      </div>
      <div class="date-and-sub">
        <div class="date">
          <h2>today is</h2>
          <h2>{{ date }}</h2>
        </div>
        <div class="header-sub-title">
          <h2>Get your kicks on Route 66?</h2>
        </div>
      </div>
    </div>
    <div class="header-logo-right">
      <img src="../assets/logo2.png" width="100" height="100">
    </div>

  </div>

</template>

<script>
export default {
  name: "HeaderComponent",
  props: {
    msg: String,
  },
  data() {
    return {
      date: new Date().toLocaleString()
    }
  },
}
</script>

<style scoped>
.header-body {
  display: flex;
  align-items: center;
  width: 100%;
  height: 130px;
}

.header-middle {
  flex: 1;

}

.header-title {
  text-align: center;
  vertical-align: top;
  height: 100%;
  color: blue;
}

.date {
  width: 50%;
  float:left;
  color: blue;
}
.header-sub-title {
  height: 100%;
  width: 50%;
  float:left;
  text-align: right;
  color: blue;
}



</style>
