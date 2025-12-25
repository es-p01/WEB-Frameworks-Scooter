<template>
  <div class="modal-backdrop">
    <div class="modal">
      <h3>Please provide your login credentials:</h3>
      <form method="post"
            v-on:submit.prevent="onSignIn()">
        <div class="sign-in-block">
          <table class="sign-in-table">
            <tr>
              <td>User e-mail:</td>
              <td><input type="email" name="email" v-model.trim="userEmail" required
                         v-on:keydown.enter="$event.preventDefault()"
              ></td>
            </tr>
            <tr>
              <td>Password:</td>
              <td><input type="password" name="password" v-model="userPassword" required minlength="3"></td>
            </tr>
          </table>

          <button type="submit" class="btn btn-primary">Sign In</button>
          <p class="error-message">{{ errorMessage }}</p>
        </div>
        <h4>Current token:</h4>
        <div class="token-box">{{ sessionService.currentToken }}</div>
      </form>
    </div>
  </div>
</template>


<script>
export default {
  name: "SignIn",
  inject: ['sessionService'],
  data() {
    return {
      userEmail: null,
      userPassword: null,
      errorMessage: null
    }
  },
  created() {
    if (this.$route.query.signOut === 'true') {
      this.onSignOut();
    }
  },

  watch: {
    '$route.query.signOut'(newVal) {
      if (newVal === 'true') {
        this.onSignOut();
        // stay on same page, but remove query
        this.$router.replace({path: this.$route.path, query: {}});
      }
    }
  },
  methods: {
    onSignOut() {
      this.sessionService.signOut();
      this.userEmail = "";
      this.userPassword = "";
    },
    async onSignIn() {
      this.errorMessage = null;
      let account = await this.sessionService.asyncSignIn(this.userEmail, this.userPassword);
      if (account == null) {
        this.errorMessage = "Could not authenticate with provided credentials";
      }
    }
  }
}
</script>


<style scoped>
.sign-in-block {
  width: 50%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}

.btn.btn-primary {
  align-self: flex-end;
  margin-top: 12px;
}
.sign-in-table td input {
  width: 100%;
  box-sizing: border-box;
}
.sign-in-table td:first-child {
  text-align: right;
  background-color: rgba(230,194,255,0.51)
}

.error-message{
  color: #b63939;
}
</style>
