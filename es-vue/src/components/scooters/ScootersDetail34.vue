<template>
  <div class="scooter-table">
    <div class="table-responsive">
      <table class="inside-table">
        <colgroup>
          <col span="1" style="background-color: rgba(230,194,255,0.51)">
        </colgroup>
        <thead>
        <tr>
          <th colspan="2">Scooter details (id={{ scooterCopy.id }})</th>
        </tr>
        </thead>
        <tbody v-if="scooterCopy">
        <tr>
          <td>Tag:</td>
          <td>
            <div class="form-group">
              <input type="text"
                     class="form-control"
                     id="tagInput"
                     placeholder="Tag"
                     v-model="scooterCopy.tag">
            </div>
          </td>

        </tr>
        <tr>
          <td>Status:</td>
          <td>
            <div class="status-drop">
              <select v-model="scooterCopy.status">
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
              <input type="number"
                     class="form-control"
                     id="batteryInput"
                     placeholder="Battery Charge"
                     v-model="scooterCopy.batteryCharge">
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
                     :value="formatNumber(scooterCopy.gpsLocation)">
            </div>
          </td>
        </tr>
        <tr>
          <td>Total Mileage (km):</td>
          <td>
            <div class="form-group">
              <input type="number"
                     class="form-control"
                     id="mileageInput"
                     placeholder="Mileage"
                     v-model="scooterCopy.mileage">
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <button type="button"
              class="btn btn-primary"
              :disabled="!hasChanged"
              @click="$emit('save', scooterCopy)">
        Save
      </button>
      <button type="button"
              class="btn btn-primary"
              @click="$emit('cancel', currentScooter.id)">
        Cancel
      </button>
      <button type="button"
              class="btn btn-primary"
              :disabled="!hasChanged"
              @click="resetScooter">
        Reset
      </button>
      <button type="button"
              class="btn btn-primary"
              @click="clearScooter">
        Clear
      </button>
      <button type="button"
              class="btn btn-primary"
              :disabled="hasChanged"
              @click="$emit('delete', currentScooter.id)">
        Delete
      </button>

    </div>

  </div>


</template>
<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersDetail34",
  props: ['currentScooter'],
  emits: ['save', 'delete', 'cancel'],

  data() {
    return {
      ScooterStatus: Scooter.Status,
      scooterCopy: null,
    }
  },

  created() {
    this.scooteCopy()
  },

  methods: {
    formatNumber(location) {
      return `${location.lat.toFixed(4)}N, ${location.lon.toFixed(4)}E`;
    },

    scooteCopy() {
      this.scooterCopy = Scooter.copyConstructor(this.currentScooter)
    },

    resetScooter() {
      this.scooteCopy()
    },

    clearScooter() {
      this.scooterCopy.tag = "";
      this.scooterCopy.status = null;
      this.scooterCopy.batteryCharge = "";
      this.scooterCopy.mileage = "";
    }
  },
  computed: {
    hasChanged() {
      // True if the copy is different from the original
      return !this.currentScooter.equals(this.scooterCopy)
    }
  },

  watch: {
    currentScooter() {
      this.scooteCopy()
    }
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
