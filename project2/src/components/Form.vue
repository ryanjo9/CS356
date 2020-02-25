<template>
  <div class="Form">
    <p>{{ message }}</p>
    <form @submit.prevent="add" class="pure-form pure-form-aligned">
      <div>
        <label>ISBN</label> <input v-model="isbn" type="text" placeholder="ISBN (dashes optional)" required/><br/><br/>
        <label>Title</label> <input v-model="title" type="text" placeholder="Title" required/><br/><br/>
        <label>Author</label> <input v-model="author" type="text" placeholder="Author" required/><br/><br/>
        <label>Category</label>
        <select v-model="category">
            <option v-for="(category, position) in categories" v-bind:key="position" style="align:center">{{category}}</option>
        </select><br/>
        <label :v-show="checkURL">Image URL</label> 
        <input :v-show="checkURL" v-model="imagePath" type="text" placeholder="Image URL" required/><br/><br/>
        <label>Condition</label> 
        <input v-model="condition" type="text" placeholder="Condition" required/><br/><br/>
        <label>Price</label> 
        <input v-model="price" type="text" placeholder="Price" required/><br/><br/>
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
      message: 'Here\'s what we found. Please fill in missing data and double check that this information matches the book.',
      isbn: this.bookData.isbn || '',
      title: this.bookData.title || '',
      author: this.bookData.author || '',
      category:'',
      condition: '',
      price: '',
      imagePath: this.bookData.imagePath || '',
      error: '',
      categories:["Action and adventure","Children's","Biography","Diary","Health","History","Documentary",
      "Mystery","Religious","Comic book","Drama","Fairytale","Poetry","Romance","Fiction & Fantasy","Psychology",
      "Horror","Technology","Politics","Thriller","Mathematics","Cookbook","Literature Reviews","Art","Science","Memoir"].sort()
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
    display:block;
  }
  select{
    width: 200px;
  }
  input {
    border-radius: 4px;
    border-style: solid;
    border-width: 1px;
    border-color: #dddddd;
    display:block;
  }
  #bookForm {
    position: absolute;
    background-color: #ffffff;
    width: 50%;
    left: 25%;
  }
</style>