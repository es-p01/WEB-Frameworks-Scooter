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
          <tr @click="setCurrentId(scooter.id)"
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


    <div v-if="currentId" class="selected">
      <scooters-detail32
          @delete="deleteScooter"
          :currentScooter="getCurrentScooter()"
      >
      </scooters-detail32>
    </div>
    <div v-else class="not-selected">
      Select a scooter from the list at the left
    </div>

  </div>
</template>

<script>

import {Scooter} from "@/models/scooter";
import ScootersDetail32 from "@/components/scooters/ScootersDetail32";

export default {
  name: "ScootersOverview32",
  components: {
    ScootersDetail32
  },

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
  },
  methods: {
    onNewScooter() {
      const scooter = Scooter.createSampleScooter(this.nextId);
      this.scooters.push(scooter);
      this.nextId += 1

      //auto select new
      this.currentId = scooter.id
    },

    setCurrentId(id) {
      this.currentId = id
    },

    getCurrentScooter() {
      return this.scooters.find((val) => val.id === this.currentId)
    },

    deleteScooter(id) {
      this.scooters = this.scooters.filter((val) => val.id !== id)
      if (this.currentId === id) {
        this.currentId = null
      }
    }
  },
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
