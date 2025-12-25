<template>
  <div class="scooter-overview-body">
    <h2>All scooters details: </h2>
    <div class="scooter-table">
      <div class="table-responsive">
        <table class="inside-table">
          <thead>
          <tr>
            <th>Tag:</th>
          </tr>
          </thead>
          <tbody v-for="scooter in scooters" :key="scooter.id">
          <tr @click="onSelect(scooter)"
              :class="{ selected: scooter.id === currentId }">
            <td>{{ scooter.tag }}</td>
          </tr>
          </tbody>
        </table>

      </div>
      <button class="btn btn-primary"
              @click="onNewScooter()">
        New Scooter
      </button>
    </div>


    <router-view
        class="selected"
        v-if="currentId"
        @save="saveScooter"
        @cancel="cancelScooter"
        @delete="deleteScooter"
        :currentScooter="getCurrentScooter()"
    >

    </router-view>

    <div v-else class="not-selected">
      Select a scooter from the list at the left
    </div>

  </div>
</template>

<script>

import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersOverview33",


  data() {
    return {
      nextId: 3000,
      scooters: [],
      currentId: null
    };
  },

  created() {
    for (let i = 0; i < 4; i++) {
      const scooter = Scooter.createSampleScooter(this.nextId);
      this.scooters.push(scooter);
      this.nextId += 1
    }
    if (this.$route.params.id) {
      this.currentId = Number(this.$route.params.id);
    }
  },
  methods: {
    onNewScooter() {
      const scooter = Scooter.createSampleScooter(this.nextId);
      this.scooters.push(scooter);
      this.nextId += 1

      //auto select new
      this.currentId = scooter.id
      this.$router.push(this.$route.matched[0].path + "/" + scooter.id);

    },

    onSelect(scooter) {
      if (scooter != null && scooter.id !== this.currentId) {
        this.$router.push(this.$route.matched[0].path + "/" + scooter.id);
      } else if (this.currentId != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    },


    getCurrentScooter() {
      return this.scooters.find((val) => val.id === this.currentId)
    },

    saveScooter(save){
      const index = this.scooters.findIndex(val => val.id === save.id);
      this.scooters[index] = save;
      this.$router.push(this.$route.matched[0].path);
    },

    cancelScooter(){
      this.$router.push(this.$route.matched[0].path);
    },

    deleteScooter(id) {
      this.scooters = this.scooters.filter((val) => val.id !== id)
      if (this.currentId === id) {
        this.currentId = null
        this.$router.push(this.$route.matched[0].path);
      }
    },

    findSelectedFromRouteParam(route) {
      if (route.params && route.params.id) {
        const id = Number(route.params.id);
        const found = this.scooters.find((val) => val.id === id);
        return found ? found.id : null;
      }
      return null;
    },
  },

  watch: {
    '$route'() {
      this.currentId = this.findSelectedFromRouteParam(this.$route);
    }
  }
}

</script>

<style scoped>
tr.selected {
  background-color: pink;
}

.scooter-overview-body {
  height: 200px;
  margin-top: 20px;
}

.scooter-table {
  margin-right: 50px;
  width: 30%;
  text-align: center;
  float: left;
}


.selected, .not-selected {
  margin-left: 100px;
}


</style>
