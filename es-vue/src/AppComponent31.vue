<template>
  <Header/>
  <Nav/>
<!--  <ScootersOverview31/>-->
  <ScootersOverview32/>
</template>


<script>
import Header from './components/HeaderComponent.vue'
import Nav from './components/NavBar.vue'
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";

// import ScootersOverview31 from "@/components/scooters/ScootersOverview31";


export default {
  name: "AppComponent31",
  components: {
    ScootersOverview32,
    Header,
    Nav,
    // ScootersOverview31

  }
}
</script>

<style scoped>

</style>
