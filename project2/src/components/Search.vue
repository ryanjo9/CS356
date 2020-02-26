<template>
  <div class="search">
    <h1>Add a book</h1>
    <p>Enter the ISBN of the book to search the internet for the book information.</p>
    <br/>
    <form @submit.prevent.stop="search" class="pure-form pure-form-aligned">
      <div class="group">
        <input type="text" v-model="isbn" required>
        <span class="highlight"/>
        <span class="bar"/>
        <label>ISBN (dashes optional)</label>
      </div>
    </form>
    <p>Or hit next to enter a book manually</p>
    <button type="submit" class="btn btn-outline-warning" @click="search">Next</button> 
    <button type="submit" v-on:click="cancel" class="btn btn-outline-secondary">Cancel</button>
    <br/>
    <br/>
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
  background-color: #ffffff;
}

/* form starting stylings ------------------------------- */
  .group            { 
    position:relative; 
    margin-bottom:45px; 
    width: 100%;
    left: 25%;
    margin-left: 30px;
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