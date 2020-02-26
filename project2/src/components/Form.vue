<template>
  <div class="Form">
    <p>{{ message }}</p>
    <form @submit.prevent="add" class="pure-form pure-form-aligned">
      <div>
        <div class="group">
          <input type="text" v-model="isbn" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>ISBN (dashes optional)</label>
        </div>

        <div class="group">
          <input type="text" v-model="title" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Title</label>
        </div>

        <div class="group">
          <input type="text" v-model="author" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Author</label>
        </div>

        <div class="group">
          <input type="text" v-model="imagePath" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Image URL</label>
        </div>

        <div class="group">
          <input type="text" v-model="condition" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Condition</label>
        </div>

        <div class="group">
          <input type="text" v-model="price" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Price</label>
        </div>

        <div class="group">
          <select v-model="category">
            <option v-for="(category, position) in categories" v-bind:key="position" style="align:center">{{category}}</option>
          </select>
          <label>Category</label>
        </div>  
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

  #bookForm {
    position: absolute;
    background-color: #ffffff;
    width: 50%;
    left: 25%;
  }
  /* form starting stylings ------------------------------- */
  .group            { 
    position:relative; 
    margin-bottom:45px; 
    left: 35%;
  }
  input               {
    font-size:18px;
    padding:10px 10px 10px 5px;
    display:block;
    width:300px;
    border:none;
    border-bottom:1px solid #757575;
  }
  input:focus         { outline:none; }

  /* LABEL ======================================= */
  label                {
    color:#999; 
    font-size:18px;
    font-weight:normal;
    position:absolute;
    pointer-events:none;
    left:5px;
    top:10px;
    transition:0.2s ease all; 
    -moz-transition:0.2s ease all; 
    -webkit-transition:0.2s ease all;
  }

  /* active state */
  input:focus ~ label, input:valid ~ label        {
    top:-20px;
    font-size:14px;
    color:#5264AE;
  }

  /* BOTTOM BARS ================================= */
  .bar    { position:relative; display:block; width:300px; }
  .bar:before, .bar:after     {
    content:'';
    height:2px; 
    width:0;
    bottom:1px; 
    position:absolute;
    background:#5264AE; 
    transition:0.2s ease all; 
    -moz-transition:0.2s ease all; 
    -webkit-transition:0.2s ease all;
  }
  .bar:before {
    left:50%;
  }
  .bar:after {
    right:50%; 
  }

  /* active state */
  input:focus ~ .bar:before, input:focus ~ .bar:after {
    width:50%;
  }

  /* HIGHLIGHTER ================================== */
  .highlight {
    position:absolute;
    height:60%; 
    width:100px; 
    top:25%; 
    left:0;
    pointer-events:none;
    opacity:0.5;
  }

  /* active state */
  input:focus ~ .highlight {
    -webkit-animation:inputHighlighter 0.3s ease;
    -moz-animation:inputHighlighter 0.3s ease;
    animation:inputHighlighter 0.3s ease;
  }

  /* ANIMATIONS ================ */
  @-webkit-keyframes inputHighlighter {
      from { background:#5264AE; }
    to    { width:0; background:transparent; }
  }
  @-moz-keyframes inputHighlighter {
      from { background:#5264AE; }
    to    { width:0; background:transparent; }
  }
  @keyframes inputHighlighter {
      from { background:#5264AE; }
    to    { width:0; background:transparent; }
  }
</style>