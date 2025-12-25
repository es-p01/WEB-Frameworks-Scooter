<template>
  <div class="scooter-table">
    <div class="table-responsive">
      <table class="inside-table">
        <colgroup>
          <col span="1" style="background-color: rgba(230,194,255,0.51)">
        </colgroup>
        <thead>
        <tr>
          <th colspan="2">Scooter details (id={{ currentScooter.id }})</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>Tag:</td>
          <td>
            <div class="form-group">
              <input type="text"
                     class="form-control"
                     id="tagInput"
                     placeholder="Tag"
                     v-model="currentScooter.tag">
            </div>
          </td>

        </tr>
        <tr>
          <td>Status:</td>
          <td>
            <div class="status-drop">
              <select v-model="currentScooter.status">
                <option disabled selected value="">{{ currentScooter.status }}</option>
                <option v-for="(item, key) in ScooterStatus" :key="key">
                  {{ item }}
                </option>
              </select>
            </div>
          </td>
        </tr>
        <tr>
          <td>Battery Charge (%):</td>
          <td>
            <div class="form-group">
              <input type="text"
                     class="form-control"
                     id="batteryInput"
                     placeholder="Battery Charge"
                     v-model="currentScooter.batteryCharge">
            </div>
          </td>
        </tr>
        <tr>
          <td>GPS Location:</td>
          <td>
            <div class="form-group">
              <input type="text"
                     class="form-control"
                     id="gpsLocation"
                     placeholder="GPS Location" readonly
                     :value="formatNumber(currentScooter.gpsLocation)">
            </div>
          </td>
        </tr>
        <tr>
          <td>Total Mileage (km):</td>
          <td>
            <div class="form-group">
              <input type="text"
                     class="form-control"
                     id="mileageInput"
                     placeholder="Mileage"
                     v-model="currentScooter.mileage">
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <button type="button"
              class="btn btn-primary"
              @click="$emit('delete', currentScooter.id)">
        Delete
      </button>
    </div>

  </div>


</template>
<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersDetail32",
  props: ['currentScooter'],
  emits: ['delete'],

  data() {
    return {
      ScooterStatus: Scooter.Status,

    }
  },

  methods: {
    formatNumber(location) {
      return `${location.lat.toFixed(4)}N, ${location.lon.toFixed(4)}E`;
    },

  }

}
</script>

<style scoped>
td:first-child {
  text-align: right;
}

td:last-child .form-control, .status-drop {
  text-align: left;
  width: 95%;
}


</style>
