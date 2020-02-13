<template>
  <div class="Form">
    <form @submit.prevent="add" class="pure-form pure-form-aligned">
      <div>
        <input v-model="isbn" type="text" placeholder="isbn"><br/><br/>
        <input v-model="title" type="text" placeholder="Title"><br/><br/>
        <input v-model="author" type="text" placeholder="Author"><br/><br/>
        <input v-model="category" type="text" placeholder="Category"><br/><br/>
        <input v-model="condition" type="text" placeholder="Condition"><br/><br/>
        <input v-model="price" type="text" placeholder="Price"><br/><br/>
      </div>
      <button type="submit" class="btn btn-outline-warning">Submit</button>
      <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Form',
  props: ['bookData'],
  data() {
    return {
      isbn: this.bookData.isbn || '',
      title: this.bookData.title || '',
      author: this.bookData.author || '',
      category: this.bookData.category || '',
      condition: '',
      price: '',
      error: ''
    }
  },
  methods: {
    async cancel() {
      this.$store.dispatch('clearBook')
      this.$router.push('/')
    },
    async add() {
      if (
        !this.isbn ||
        !this.title ||
        !this.author || 
        !this.category ||
        !this.condition || 
        !this.price
      ) {
        // Incomplete data, don't save
        return;
      }

      if (isNaN(this.price)) {
        // error
        return;
      }

      const book = {
        isbn: this.isbn,
        title: this.title,
        author: this.author,
        category: this.category,
        condition: this.condition,
        price: Number(this.price)
      }

      if (this.bookData.imagePath) {
        book.imagePath = this.bookData.imagePath;
      }

      try {
        this.error = await this.$store.dispatch('addBook', book)

        if (this.error === '') {
          this.$router.push('/');
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