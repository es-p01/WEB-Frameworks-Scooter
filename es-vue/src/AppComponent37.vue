<template>
  <Header/>
  <Nav/>
  <router-view/>
</template>

<script>
import Header from "@/components/HeaderComponent";
import Nav from "@/components/NavBar";
import CONFIG from '@/app-config.js'
import {ScootersAdaptor} from "@/services/scooters-adaptor";

export default {
  name: "AppComponent37",
  components: {
    Header,
    Nav,
  },
  provide() {
    return {
      // stateless data services adaptor singletons
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + "/scooters"),
    }
  }
}
</script>

<style scoped>

</style>
