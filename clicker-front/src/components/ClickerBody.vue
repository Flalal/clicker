<template>
  <div class="clicker-body">
    <div class="clicker-social">Social</div>
    <div class="clicker-generators">Generators</div>
    <div class="clicker-generators-informations">Generators info</div>
    <div class="clicker-icons">IDK ?</div>
  </div>
</template>

<script>
export default {
  name: 'Clicker-Body',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.clicker-body {
  display: flex;
  flex-direction: row;
  flex: 1 1 100%;
}

.clicker-social {
  border: darkgrey .1rem solid;
  width: 10%;
}

.clicker-generators {
  border: darkgrey .1rem solid;
  width: 25%;
}

.clicker-generators-informations {
  border: darkgrey .1rem solid;
  width: 60%;
}

.clicker-icons {
  border: darkgrey .1rem solid;
  width: 5%;

}
</style>
