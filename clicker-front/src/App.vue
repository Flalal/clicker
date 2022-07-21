<template>
  <h1>{{this.$refs.game}}</h1>

</template>

<script lang="ts" setup>
import { useMainStore } from "./store/index";
import {GameRepresentation} from "@/generated/clicker-api-types";
import {VueElement} from "vue";


export default class App extends VueElement {

  private game?: GameRepresentation;

  mounted() {
    const mainStore = useMainStore();
    mainStore.fetchGameByPseudo().then(value => this.game = value);
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>