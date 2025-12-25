<template>
  <div class="welcome-body">
    <div class="left-column">
      <div class="left-hand-side">
        <div class="dropdown">
          <h2>Pick a brand:</h2>
          <select>
            <option>Choose</option>
            <option>Brand 1</option>
            <option>Brand 2</option>
            <option>Brand 3</option>
          </select>
        </div>
        <div class="location-list">
          <h2>Availability:</h2>
          <ul class="locations">
            <li>Leidseplein</li>
            <li>Rembrandtplein</li>
            <li>Centraal Staion</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="centre-column">
      <h2>Photo gallery:</h2>
    </div>
    <div class="right-column">
      <h2>City Tours</h2>
      <p>Some interesting information about touring in Amsterdam...</p>
    </div>

  </div>

</template>

<script>
export default {
  name: "WelcomeComponent",
  props: {
    msg: String
  }
}
</script>

<style scoped>
.welcome-body {
  display: flex;
  height: 535px;
  width: 100%;
}

.left-column {
  padding-left: 5px;
  background-color: rgba(190, 137, 225, 0.65);
  width: 300px;
  height: 100%;
  border-right: 3px solid white;
}

.centre-column {
  padding: 5px;
  background-color: lightpink;
  flex: 1;
  border-right: 3px solid white;
}

.right-column {
  padding-left: 5px;
  background-color: rgba(190, 137, 225, 0.65);
  width: 430px;
  height: 100%;
}


</style>
