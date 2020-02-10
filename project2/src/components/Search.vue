<template>
  <div class="search">
    <h1>Add a book</h1>
    <form @submit.prevent="search" class="pure-form pure-form-aligned">
      <fieldset>
      <div class="pure-control-group">
        <!-- <label for="isbn">Username</label> -->
        <input v-model="isbn" type="text" placeholder="Enter the ISB to autofill book info">
      </div>

      <div class="pure-controls">
        <button type="submit" class="pure-button pure-button-primary">Next</button>
        <button type="submit" class="pure-button pure-button-primary">Cancel</button>
      </div>
      </fieldset>
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
form {
  border: 1px solid #ccc;
  background-color: #eee;
  border-radius: 4px;
  padding: 20px;
}
.pure-controls {
  display: flex;
}
.pure-controls button {
  margin-left: auto;
}
</style>