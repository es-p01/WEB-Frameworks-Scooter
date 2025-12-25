<template>
  <div class="scooter-table">
    <div class="table-responsive">
      <table class="inside-table">
        <colgroup>
          <col span="1" style="background-color: rgba(230,194,255,0.51)">
        </colgroup>
        <thead>
        <tr>
          <th colspan="2" v-if="scooterCopy">Scooter details (id={{ scooterCopy.id }})</th>
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
                     placeholder="GPS Location"
                     v-model="scooterCopy.gpsLocation">
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
              @click="saveScooter">
        Save
      </button>
      <button type="button"
              class="btn btn-primary"
              @click="cancel">
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
              @click="delScooter">
        Delete
      </button>

    </div>

  </div>


</template>
<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersDetail37",
  inject: ['scootersService'],
  emits: ['refresh'],

  data() {
    return {
      ScooterStatus: Scooter.Status,
      scooterCopy: null,
      currentScooter: null,
    }
  },

  async created() {
    await this.getScooterCopy();
  },

  methods: {

    async saveScooter() {
      await this.scootersService.asyncSave(this.scooterCopy)
      this.$emit('refresh');
    },

    async delScooter() {
      await this.scootersService.asyncDeleteById(this.scooterCopy.id)
      this.$emit('refresh');
    },

    async getScooterCopy() {
      this.currentScooter = await this.scootersService.asyncFindById(this.$route?.params?.id);
      this.scooterCopy = Scooter.copyConstructor(this.currentScooter);
    },

    cancel(){
      this.$emit('refresh');
    },

    resetScooter() {
      this.scooterCopy = Scooter.copyConstructor(this.currentScooter);
    },

    clearScooter() {
      this.scooterCopy.tag = "";
      this.scooterCopy.status = null;
      this.scooterCopy.batteryCharge = "";
      this.scooterCopy.gpsLocation = "";
      this.scooterCopy.mileage = "";
    }
  },
  computed: {
    hasChanged() {
      // True if the copy is different from the original
      if (!this.currentScooter || !this.scooterCopy) return false;
      return !this.currentScooter.equals(this.scooterCopy);
    }

  },

  watch: {
    currentScooter() {
      this.scooterCopy = Scooter.copyConstructor(this.currentScooter);
    },
    '$route'() {
      this.getScooterCopy();
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
