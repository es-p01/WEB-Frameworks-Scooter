<template>
  <div class="navbar">
    <router-link to="/home">Home</router-link>
    <div class="dropdown">
      <button class="dropbtn">Scooters
        <i class="fa fa-caret-down"></i>
      </button>
      <div class="dropdown-content">
        <router-link to="/scooters/overview31">Scooters Overview</router-link>
        <router-link to="/scooters/overview32">Scooters (comp)</router-link>
        <router-link to="/scooters/overview33">Scooters edit (router)</router-link>
        <router-link to="/scooters/overview34">Scooters edit (managed)</router-link>
        <router-link to="/scooters/overview37">Scooters edit 37 (managed)</router-link>
      </div>
    </div>
    <a href="#trip">My Trups</a>
    <a href="#account">My Account</a>

    <div class="signup-login-entries">
      <router-link v-if="!isAuthenticated" to="/sign-up">Signup</router-link>
      <router-link v-if="!isAuthenticated" to="/sign-in">Login</router-link>
      <router-link v-if="isAuthenticated" to="/sign-out">Log out</router-link>
    </div>

  </div>
</template>

<script>

export default {
  name: "NavBar.vue",
  inject: ["sessionService"],

  created() {
    if (this.$route.query.signOut === 'true') {
      this.sessionService.signOut();
    }
  },

  computed: {
    isAuthenticated() {
      //console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },

  },
  watch: {
    '$route.query.signOut'(newVal) {
      if (newVal === 'true') {
        this.sessionService.signOut();
      }
    }
  }
}
</script>

<style scoped>

.signup-login-entries {
  float: right;
}

.navbar {
  overflow: hidden;
  background-color: lightpink;
  border-bottom: 3px solid white;
}

.navbar a {
  float: left;
  font-size: 16px;
  color: white;
  background-color: #42b983;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.dropdown {
  background-color: #42b983;
  float: left;
  overflow: hidden;
}

.dropdown .dropbtn {
  font-size: 16px;
  border: none;
  outline: none;
  color: white;
  padding: 14px 16px;
  background-color: inherit;
  font-family: inherit;
  margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
  background-color: red;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  z-index: 1;
}

.dropdown-content a {
  float: none;
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.router-link-exact-active {
  background-color: lightpink !important;
  color: white !important;
  font-weight: bold;
}
</style>
