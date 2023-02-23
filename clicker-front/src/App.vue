<template>
  <Clicker id="app"/>
</template>

<script>
import Clicker from './components/Clicker.vue'

export default {
  name: 'App',
  components: {
    Clicker
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  position: absolute;
  height: 100%;
  padding: 0;
  margin: 0;
  display: block;
  min-height: 100%;
  min-width: 99%;

}
</style>
