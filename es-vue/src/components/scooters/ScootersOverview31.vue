<template>
  <div class="scooter-overview-body">
    <div class="scooter-table">
      <table class="inside-table">
        <tr>
          <th>Id:</th>
          <th>Tag:</th>
          <th>Status:</th>
          <th>Battery charge:</th>
          <th>GPSLocation:</th>
          <th>Mileage:</th>
        </tr>
        <tbody v-for="scooter in scooters" :key="scooter.id">
        <tr>
          <td>{{ scooter.id }}</td>
          <td>{{ scooter.tag }}</td>
          <td>{{ scooter.status }}</td>
          <td>{{ scooter.batteryCharge }}%</td>
          <td v-if="scooter.status !== 'INUSE'">{{ formatNumber(scooter.gpsLocation) }}</td>
          <td v-else>N/A</td>
          <td>{{ scooter.mileage }} km</td>
        </tr>
        </tbody>
      </table>
    </div>

    <button class="btn btn-primary"
            v-on:click="onNewScooter()">
      New Scooter
    </button>

  </div>
</template>

<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersOverview31",

  data() {
    return {
      scooters: [],
      nextId: 30000
    };
  }
  ,

  created() {
    for (let i = 0; i < 8; i++) {
      const scooter = Scooter.createSampleScooter(this.nextId);
      this.scooters.push(scooter);

      this.nextId += 3
    }
  },

  methods: {
    onNewScooter() {
      const scooter = Scooter.createSampleScooter(this.nextId);
      this.scooters.push(scooter);
      this.nextId += 1

    },

    formatNumber(location) {
      return `${location.lat.toFixed(4)}N, ${location.lon.toFixed(4)}E`;
    }


  }
};
</script>

<style scoped>

</style>
