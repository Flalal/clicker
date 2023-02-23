<template>
  <div class="nav">
    <div class="left-nav">
      <span class="left-nav-info nav-button">Home</span>
      <span class="left-nav-info nav-button">Workspaces</span>
      <span class="left-nav-info nav-button">API Network</span>
    </div>
    <div class="middle-nav">
      <input/>
    </div>
    <div class="right-nav">
      <span class="right-nav-info nav-button">Information APIs</span>
      <span class="right-nav-info nav-button">Settings</span>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Nav-bar',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style type="scss" scoped>
.nav {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: darkgrey .1rem solid;
  padding: 1rem;
}

.left-nav {
  display: flex;
  flex-direction: row;
}

.nav-button {
  margin-right: 1rem;
  cursor: pointer;
}
</style>
