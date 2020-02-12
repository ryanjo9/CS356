<template>
  <div class="search">
    <h1>Add a book</h1>
    <form @submit.prevent="search" class="pure-form pure-form-aligned">
      <div>
        <input v-model="isbn" type="text" placeholder="Enter the ISB to autofill book info">
        </div>
        <button type="submit" class="btn btn-outline-warning">Next</button>
        <button type="submit" class="btn btn-outline-secondary">Cancel</button>
    </form>
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
    /**
     * Queries for book data usinb isbn and pushes to store
     */
    async search() {
      try {
        this.error = await this.$store.dispatch('search', this.isbn);
        if (this.error === '') {
          // move to next component somehow. Emit?
        }
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