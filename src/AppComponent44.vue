<template>
  <Header/>
  <NavBar/>
  <router-view/>
</template>

<script>
import Header from '@/components/HeaderSb.vue'
import NavBar from '@/components/NavBarSb.vue'

import {SessionSbService} from "@/services/session-sb-service.js";
import CONFIG from '@/app-config.js'
import {ScootersAdaptor} from "@/services/scooters-adaptor";
import {shallowReactive} from "vue";
import {FetchInterceptor} from "@/services/fetch-interceptor";

export default {
  name: "AppComponent44",
  components: {
    Header,
    NavBar
  },
  provide() {
    //create a singleton reactive service tracking the authorization data of the session
    this.theSessionService = shallowReactive(
        new SessionSbService(CONFIG.BACKEND_URL + "/authentication", CONFIG.JWT_STORAGE_ITEM));
    this.theFetchInterceptor =
        new FetchInterceptor(this.theSessionService, this.$router);
    return {
      // stateless data services adaptor singletons
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + "/scooters"),
      //reactive, stateful services
      sessionService: this.theSessionService,
    }

  },
  unmounted() {
    console.log("App.unmounted() hass been called. ");
    this.theFetchInterceptor.unregister();
  }
}
</script>

<style scoped>

</style>
