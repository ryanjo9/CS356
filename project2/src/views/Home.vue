<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    <p v-if="username">Welcome {{ username }} </p>
    <HelloWorld msg="Usedbooks.com"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  data() {
    return {
      username: this.$store.state.username
    }
  },
  components: {
    HelloWorld
  },
  computed: {
    books() {
      return this.$store.state.books;
    }
  },
  async created() {
    await this.$store.dispatch('getBooks');
  }
}
</script>
