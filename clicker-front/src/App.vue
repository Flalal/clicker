<template>
  <h1>{{this.game}}</h1>

</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import { useMainStore } from "./store/index";
import {GameRepresentation} from "@/generated/clicker-api-types";

export default defineComponent({
  name: "App",
  setup() {
    const game = ref<GameRepresentation>();
    const mainStore = useMainStore();
    onMounted(() => {
      mainStore.fetchGameByPseudo().then(value => game.value = value);
    });
  },
});
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