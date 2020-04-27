<template>
  <div id="app">
    <header class="text-center mb-5 bg-secondary rounded p-5">
      <h1>Bye Bye Book</h1>
      <h2>North America, here we come!</h2>	
    </header>
    <div v-if="isComplete" class="bg-success p-4 rounded mb-5">{{ LABELS.SUCCESS }}</div>
    <div v-else class="bg-warning p-4 rounded mb-5">{{ LABELS.DISCLAIMER }}</div>
    <router-view></router-view>
  </div>
</template>

<script>
  import { LABELS } from "./assets/labels";

  export default {
    name: 'app',
    data() {
      return {
        LABELS
      }
    },
    computed: {
      isComplete() {
        return this.$store.getters.isComplete
      }
    }
  }
</script>

<style>
  #app {
    margin: 20px auto;
    max-width: 950px;
  }
  #app>header>h1, #app>header>h2 {
    color: #ffc107;
  }
</style>
