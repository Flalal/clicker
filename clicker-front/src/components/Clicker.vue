<template>
  <div class="clicker">
    <nav-bar />
    <clicker-body/>
  </div>
</template>

<script>

import Nav from "@/components/Nav";
import ClickerBody from "@/components/ClickerBody";
export default {
  name: 'HelloWorld',
  components: {
    ClickerBody,
    'nav-bar': Nav,
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.clicker {
  width: 100%;
}
</style>
