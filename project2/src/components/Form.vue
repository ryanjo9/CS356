<template>
  <div class="Form">
    <br/>
    <h3>Add a Book</h3>
    <div class="message">
      <p>Here's what we found. Fill in missing data and double check that this information matches the book.</p>
    </div>
    <br/>
    <form @submit.prevent="add" class="pure-form pure-form-aligned">
      <div>
        <div class="group">
          <input type="text" v-model="isbn" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>ISBN (dashes optional)</label>
        </div>

        <div class="group">
          <input type="text" v-model="title" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Title</label>
        </div>

        <div class="group">
          <input type="text" v-model="author" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Author</label>
        </div>

        <div class="group">
          <v-select @input="editing" class="select-style" placeholder="Category" v-model="category" :options="categories" :required="!category"/>
        </div>

        <div class="group">
          <input type="text" v-model="imagePath" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Image URL</label>
        </div>

        <div class="group">
          <input type="text" v-model="condition" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Condition</label>
        </div>

        <div class="group">
          <input type="text" v-model="price" @input="editing" required>
          <span class="highlight"/>
          <span class="bar"/>
          <label>Price</label>
        </div>

      </div>
      <button type="submit" class="btn btn-outline-warning">Submit</button>
      <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
      <p>{{ message }}</p>
    </form>
    
    <br/>
  </div>
</template>

<script>
import vSelect from 'vue-select';
import 'vue-select/dist/vue-select.css';
export default {
  name: 'Form',
  props: ['bookData'],
  components: {
    vSelect
  },
  data() {
    return {
      message: '',
      isbn: this.bookData.isbn || '',
      title: this.bookData.title || '',
      author: this.bookData.author || '',
      category:'',
      condition: '',
      price: '',
      imagePath: this.bookData.imagePath || '',
      error: '',
      categories:["Adventure and Fantasy","Children's","History","Religious","Poetry","Romance","Psychology",
      "Horror","Technology","Politics","Cookbook","Art","Science","Memoir"].sort()
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
      let missing = [];
      const price = this.price.replace('$', '');

      if (isNaN(price)) {
        // error
        missing.push('price');
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

      Object.keys(book).forEach((key) => {
        const item = book[key];
        if (item === null || item === undefined || item === '') {
          missing.push(key);
        }
      })

      if (missing.length > 0 ) {
        // Incomplete data, don't save
        this.message = `Missing or invalid data for: ${missing}`
        return;
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
  .select-style .vs_search::placeholder,
  .select-style .vs_dropdown-menu,
  .select-style {
    width: 80%;
  }
  .message {
    width: 90%;
    padding-left: 10%;
    text-align: center
  }
  button{
    width: 100px;
    text-align: center;
    text-decoration: none;
    margin-right: 10px;
  }

  .Form {
    position: relative;
    top: 50%;
    left: 30%;
    background-color: #ffffff;
    width: 40%;
    border-style: solid;
    border-width: 1px;
    border-radius: 4px;
    border-color: #888888
  }
  /* form starting stylings ------------------------------- */
  .group            { 
    position:relative; 
    margin-bottom:45px; 
    left: 10%;
  }
  input               {
    font-size:18px;
    padding:10px 10px 10px 5px;
    display:block;
    width:80%;
    border:none;
    border-bottom:1px solid #757575;
    background-color: #ffffff;
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
  .bar    { position:relative; display:block; width:80%; }
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