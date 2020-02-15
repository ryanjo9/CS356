<template>
  <div class="Form">
    <form @submit.prevent="add" class="pure-form pure-form-aligned">
      <div>
        <label>ISBN</label> <input v-model="isbn" type="text" placeholder="isbn" required><br/><br/>
        <label>Title</label> <input v-model="title" type="text" placeholder="Title" required><br/><br/>
        <label>Author</label> <input v-model="author" type="text" placeholder="Author" required><br/><br/>
        <label>Category</label> <input v-model="category" type="text" placeholder="Category" required><br/><br/>
        <label :v-show="checkURL">Image URL</label> <input :v-show="checkURL" v-model="imagePath" type="text" placeholder="Image URL" required><br/><br/>
        <label>Condition</label> <input v-model="condition" type="text" placeholder="Condition" required><br/><br/>
        <label>Price</label> <input v-model="price" type="text" placeholder="Price" required><br/><br/>
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
      imagePath:'',
      error: ''
    }
  },
  methods: {
    async cancel() {
      this.$emit('cancel', null)
    },
    checkURL(){
      if(this.imagePath.includes("amazon")) return true;
      else return false;
    },
    async add() {
      if (
        !this.isbn ||
        !this.title ||
        !this.author || 
        !this.category ||
        !this.condition || 
        !this.price || !this.imagePath
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
        isbn: this.isbn,
        title: this.title,
        author: this.author,
        category: this.category,
        condition: this.condition,
        imagePath: this.imagePath,
        price: Number(price)
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
  margin-right: 10px;
  }
  label{
    width: 60px;
    margin-left: auto;
    margin-right: 10px;
  }
</style>