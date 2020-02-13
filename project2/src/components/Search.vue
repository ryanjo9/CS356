<template>
  <div class="search">
    <h1>Add a book</h1>
    <form @submit.prevent="search" class="pure-form pure-form-aligned">
      <div>
        <input v-model="isbn" type="text" placeholder="Enter the ISB to autofill book info"><br />
      </div>
        <button type="submit" class="btn btn-outline-warning">Submit</button> 
        <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
        <br/>
    </form>
    <a href="#"> I'll Enter it manually </a>
  </div>
</template>

<script>
export default {
  name: 'search',
  data() {
    return {
      isbn: '',
      error: ''
    }
  },
  methods: {
    async cancel() {
      this.$store.dispatch('clearBook')
      this.$router.push('/')
    },
    /**
     * Queries for book data usinb isbn and pushes to store
     */
    async search() {
      try {
        const result = await this.$store.dispatch('search', this.isbn);

        this.$emit('searchDone', result);
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style scoped>
button{
  width: 100px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}
</style>