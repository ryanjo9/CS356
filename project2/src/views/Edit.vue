<template>
  <div class="Edit">
  <h1>{{ message }}</h1>
  <form @submit.prevent="add" class="pure-form pure-form-aligned">
    <div>
    <label>ISBN</label> <input @input="editing" v-model="isbn" type="text" placeholder="isbn" required/><br/><br/>
    <label>Title</label> <input @input="editing" v-model="title" type="text" placeholder="Title" required/><br/><br/>
    <label>Author</label> <input @input="editing" v-model="author" type="text" placeholder="Author" required/><br/><br/>
    <label>Category</label>
    <select v-model="category" @input="editing">
      <option @input="editing" v-for="(category, position) in categories" v-bind:key="position" style="align:center">{{category}}</option>
    </select><br/>
    <label>Image URL</label> <input @input="editing" v-model="imagePath" type="text" placeholder="Image URL" required/><br/><br/>
    <label>Condition</label> <input @input="editing" v-model="condition" type="text" placeholder="Condition" required/><br/><br/>
    <label>Price</label> <input @input="editing" v-model="price" type="text" placeholder="Price" required/><br/><br/>
    </div>
    <button type="submit" class="btn btn-outline-warning">Submit</button>
    <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
  </form>
  </div>
</template>

<script>
export default {
  name: 'Edit',
  props: ['book'],
  data() {
    return {
      isbn: this.book.isbn || '',
      title: this.book.title || '',
      author: this.book.author || '',
      category: this.book.category || '',
      condition: this.book.condition || '',
      price: `${this.book.price}` || '',
      imagePath: this.book.imagePath || '',
      error: '',
      saved: false,
      message: 'Edit a Book',
      categories:["Adventure and Fantasy","Children's","History","Religious","Poetry","Romance","Psychology",
      "Horror","Technology","Politics","Cookbook","Art","Science","Memoir"].sort()
    }
  },
  methods: {
    async cancel() {
      this.$router.push('/')
    },
    editing() {
      this.saved = false
      this.message = 'Edit a Book'
    },
    async add() {
      if (this.saved) {
        return;
      }
      if (
        !this.isbn ||
        !this.title ||
        !this.author || 
        !this.category ||
        !this.condition || 
        !this.price || 
        !this.imagePath
      ) {
        // Incomplete data, don't save
        return;
      }

      const price = this.price.replace('$', '');

      if (isNaN(price)) {
        // error
        return;
      }      

      const book = {
        _id: this.book._id,
        isbn: this.isbn,
        title: this.title,
        author: this.author,
        category: this.category,
        condition: this.condition,
        imagePath: this.imagePath,
        price: Number(price)
      }

      try {
        this.error = await this.$store.dispatch('editBook', book)

        this.saved = true
        this.message = 'Successfully updated book'
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
  margin-right: 10px;
  }
  label{
  width: 60px;
  margin-left: auto;
  margin-right: 10px;
  }
  select{
  width: 200px;
  }
</style>