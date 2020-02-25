<template>
  <div class="search">
    <h1>Add a book</h1>
    <form @submit.prevent.stop="search" class="pure-form pure-form-aligned">
      <div>
        <input v-model="isbn" type="text" placeholder='Enter the ISBN to autofill book info (dashes optional)'><br />
      </div>
        <br/>
    </form>
    <p>Or hit next to enter a book manually</p>
    <button type="submit" class="btn btn-outline-warning" @click="search">Next</button> 
    <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
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
      this.isbn = ''
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

.search {
  position: absolute;
  top: 50%;
  left: 30%;
  margin-top: -100px;
  margin-left: -50px;
  width: 50%;
  border-style: solid;
  border-width: 1px;
  border-radius: 4px;
  background-color: #ffffff
}

input {
  border-radius: 15px;
  width: 60%;
  border-style: solid;
  border-width: 1px;
  border-color: #888888;
  text-align: center;
}
</style>