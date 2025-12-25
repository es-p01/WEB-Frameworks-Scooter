<template>
<h2>Oops, an error has occured...</h2>
  <p>There is no component available at route: {{$route.path}}</p>
</template>

<script>
export default {
  name: "UnknownRoute"
}
</script>

<style scoped>

</style>
