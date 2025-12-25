<template>
  <Header/>
  <Nav/>
  <router-view/>
</template>

<script>
import Header from "@/components/HeaderComponent";
import Nav from "@/components/NavBar";

  export default {
  name: "AppComponent33",
    components: {
      Header,
      Nav,

    }
}
</script>

<style scoped>

</style>
